Server端
1.下载seata-server，https://github.com/seata/seata/releases
2.配置server端配置文件， 含 file.conf和registry.conf
file.conf:
mode: "db"


registry.conf
type:nacos

  nacos {
    application = "serverAddr"
    serverAddr = "192.168.126.1:8848"
    group = "SEATA_GROUP"-- 固定为此，源码中会从该group查找配置
    namespace = ""
    cluster = "default"
    username = "nacos"
    password = "nacos"
  }

改完启动，发现报错：

原因： 连接的数据库mysql.user对应的host不允许其他host登录，仅仅允许localhost
修改如下： 
update mysql.user set host = '%' where user = 'root';
flush PRIVILEGES;


连接的mysql执行下列sql；
CREATE TABLE IF NOT EXISTS `global_table`
(
    `xid`                       VARCHAR(128) NOT NULL,
    `transaction_id`            BIGINT,
    `status`                    TINYINT      NOT NULL,
    `application_id`            VARCHAR(32),
    `transaction_service_group` VARCHAR(32),
    `transaction_name`          VARCHAR(128),
    `timeout`                   INT,
    `begin_time`                BIGINT,
    `application_data`          VARCHAR(2000),
    `gmt_create`                DATETIME,
    `gmt_modified`              DATETIME,
    PRIMARY KEY (`xid`),
    KEY `idx_gmt_modified_status` (`gmt_modified`, `status`),
    KEY `idx_transaction_id` (`transaction_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- the table to store BranchSession data
CREATE TABLE IF NOT EXISTS `branch_table`
(
    `branch_id`         BIGINT       NOT NULL,
    `xid`               VARCHAR(128) NOT NULL,
    `transaction_id`    BIGINT,
    `resource_group_id` VARCHAR(32),
    `resource_id`       VARCHAR(256),
    `branch_type`       VARCHAR(8),
    `status`            TINYINT,
    `client_id`         VARCHAR(64),
    `application_data`  VARCHAR(2000),
    `gmt_create`        DATETIME(6),
    `gmt_modified`      DATETIME(6),
    PRIMARY KEY (`branch_id`),
    KEY `idx_xid` (`xid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- the table to store lock data
CREATE TABLE IF NOT EXISTS `lock_table`
(
    `row_key`        VARCHAR(128) NOT NULL,
    `xid`            VARCHAR(96),
    `transaction_id` BIGINT,
    `branch_id`      BIGINT       NOT NULL,
    `resource_id`    VARCHAR(256),
    `table_name`     VARCHAR(32),
    `pk`             VARCHAR(36),
    `gmt_create`     DATETIME,
    `gmt_modified`   DATETIME,
    PRIMARY KEY (`row_key`),
    KEY `idx_branch_id` (`branch_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

配置nacos配置中心
https://github.com/seata/seata/blob/1.3.0/script/config-center/README.md
去下载config.txt文件和nacos-config.sh文件，然后执行：
sh ./nacos-config.sh -h 192.168.126.1 -p 8848 -g DEFAULT_GROUP -u nacos -w nacos
（注意层级关系，config.txt在nacos-config.sh上级，与nacos同级），进行同步配置信息，写到配置中心中。 注意去修改config.txt中配置内容，具体以script脚本中config.txt作参考

重启成功！
到此为止seata-server部署成功

客户端
1. 加入依赖 spring-cloud-alibaba-seata
2.mysql写入undo_log表，每个涉及的库都要执行sql
-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT(20)   NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(100) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';
3.每个客户端新增配置项目
server:
  port: 9000
spring:
  application:
    name: seata-integration
  cloud:
    nacos:
      server-addr: localhost:8848
    alibaba:
      seata:
        tx-service-group: seata-integration-fescar-service-group # seata配置项，必须要和file.conf中一致
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      driver-class-name:  com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/integration?serverTimezone=GMT%2B8
      username: root
      password: shao0526
      initial-size: 2
      max-active: 5
      min-idle: 1
      max-wait: 15000
      test-on-borrow: true
      validation-query: select 'x'
      filter:
        config:
          enabled: true
      ## 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大。 默认为false
      pool-prepared-statements: true
      max-open-prepared-statements: 20
#      connection-properties: config.decrypt=true;config.decrypt.key=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAM0e4jFj2HOU3BTsfhie3DpSnYTlnzpC9MT1u+gW16Hn+3ScvCNe7nz/8KzqeMLEXXcgegj9uHDyI88XHvpyPD8CAwEAAQ==
      #      connectionProperties: config.decrypt=true;config.decrypt.key=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALk068fVgeFx6MWgyOzs4IC6d9h6SCp1WtDOAlXvEq68Xr2VCY4tCWk2yIfKS2Yp+hTvsEdYutjWFof7bvOVzV8CAwEAAQ==

#以下为seata配置项目
seata:
  enabled: true
  application-id: seata-integration
  tx-service-group: seata-integration-fescar-service-group # 要与服务端nacos-config.txt中service.vgroup_mapping的后缀对应
  service:
    vgroup_mapping:
      seata-integration-fescar-service-group: default
  registry:
    type: nacos
    nacos:
      application: serverAddr #服务端配置的server name
      server-addr: "192.168.126.150:8091" #服务端地址
      group : "SEATA_GROUP" #固定为此
      namespace: ""
  config:
    type: nacos
    nacos:
      server-addr: "192.168.126.150:8091"
      group : "SEATA_GROUP"
      namespace: ""
4.每个客户端新增文件：file.conf, registry.conf，具体参考script
4.TM上加注解@GlobalTransactional