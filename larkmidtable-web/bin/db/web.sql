/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : flinkx_web

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 15/12/2019 22:27:10
*/
CREATE DATABASE `web` CHARACTER SET utf8 COLLATE utf8_general_ci;
USE  `web`;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `dev_env_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `name` varchar(100) DEFAULT NULL COMMENT 'property name',
  `prop_value` varchar(300) DEFAULT NULL COMMENT 'property value',
  `description` varchar(500) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT 'creator id',
  `flag` tinyint(4) DEFAULT '1' COMMENT '0 not available, 1 available',
  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `update_time` datetime DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- web.job_group definition

CREATE TABLE `job_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) NOT NULL COMMENT '执行器AppName',
  `title` varchar(32) NOT NULL COMMENT '执行器名称',
  `order` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `address_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` varchar(512) DEFAULT NULL COMMENT '执行器地址列表，多地址逗号分隔',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- web.job_info definition

CREATE TABLE `job_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_cron` varchar(128) NOT NULL COMMENT '任务执行CRON',
  `job_desc` varchar(255) NOT NULL,
  `project_id` int(11) DEFAULT NULL COMMENT '所属项目id',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` int(11) NOT NULL COMMENT '修改用户',
  `alarm_email` varchar(255) DEFAULT NULL COMMENT '报警邮件',
  `executor_route_strategy` varchar(50) DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(11) NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位分钟',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `glue_type` varchar(50) NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '上次调度时间',
  `trigger_next_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '下次调度时间',
  `job_json` text COMMENT 'flinkx运行脚本',
  `replace_param` varchar(100) DEFAULT NULL COMMENT '动态参数',
  `jvm_param` varchar(200) DEFAULT NULL COMMENT 'jvm参数',
  `inc_start_time` datetime DEFAULT NULL COMMENT '增量初始时间',
  `partition_info` varchar(100) DEFAULT NULL COMMENT '分区信息',
  `last_handle_code` int(11) DEFAULT '0' COMMENT '最近一次执行状态',
  `replace_param_type` varchar(255) DEFAULT NULL COMMENT '增量时间格式',
  `reader_table` varchar(255) DEFAULT NULL COMMENT 'reader表名称',
  `primary_key` varchar(50) DEFAULT NULL COMMENT '增量表主键',
  `inc_start_id` varchar(20) DEFAULT NULL COMMENT '增量初始id',
  `increment_type` tinyint(4) DEFAULT '0' COMMENT '增量类型',
  `datasource_id` bigint(11) DEFAULT NULL COMMENT '数据源id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- web.job_jdbc_datasource definition

CREATE TABLE `job_jdbc_datasource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `datasource_name` varchar(200) NOT NULL COMMENT '数据源名称',
  `datasource` varchar(45) NOT NULL COMMENT '数据源',
  `datasource_group` varchar(200) DEFAULT 'Default' COMMENT '数据源分组',
  `database_name` varchar(45) DEFAULT NULL COMMENT '数据库名',
  `jdbc_username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `jdbc_password` varchar(200) DEFAULT NULL COMMENT '密码',
  `jdbc_url` varchar(500) NOT NULL COMMENT 'jdbc url',
  `jdbc_driver_class` varchar(200) DEFAULT NULL COMMENT 'jdbc驱动类',
  `zk_adress` varchar(200) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0删除 1启用 2禁用',
  `create_by` varchar(20) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='jdbc数据源配置';


-- web.job_lock definition

CREATE TABLE `job_lock` (
  `lock_name` varchar(50) NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- web.job_log definition

CREATE TABLE `job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `job_desc` varchar(255) DEFAULT NULL,
  `executor_address` varchar(255) DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int(11) DEFAULT '0' COMMENT '失败重试次数',
  `trigger_time` datetime DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(11) NOT NULL COMMENT '调度-结果',
  `trigger_msg` text COMMENT '调度-日志',
  `handle_time` datetime DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(11) NOT NULL COMMENT '执行-状态',
  `handle_msg` text COMMENT '执行-日志',
  `alarm_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  `process_id` varchar(20) DEFAULT NULL COMMENT 'flinkx进程Id',
  `max_id` bigint(20) DEFAULT NULL COMMENT '增量表max id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `I_trigger_time` (`trigger_time`) USING BTREE,
  KEY `I_handle_code` (`handle_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=687 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- web.job_log_report definition

CREATE TABLE `job_log_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime DEFAULT NULL COMMENT '调度-时间',
  `running_count` int(11) NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
  `suc_count` int(11) NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
  `fail_count` int(11) NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- web.job_logglue definition

CREATE TABLE `job_logglue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- web.job_permission definition

CREATE TABLE `job_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '权限名',
  `description` varchar(11) DEFAULT NULL COMMENT '权限描述',
  `url` varchar(255) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- web.job_project definition

CREATE TABLE `job_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `name` varchar(100) DEFAULT NULL COMMENT 'project name',
  `description` varchar(200) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT 'creator id',
  `flag` tinyint(4) DEFAULT '1' COMMENT '0 not available, 1 available',
  `create_time` datetime DEFAULT NULL COMMENT 'create time',
  `update_time` datetime DEFAULT NULL COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- web.job_registry definition

CREATE TABLE `job_registry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) NOT NULL,
  `registry_key` varchar(191) NOT NULL,
  `registry_value` varchar(191) NOT NULL,
  `cpu_usage` double DEFAULT NULL,
  `memory_usage` double DEFAULT NULL,
  `load_average` double DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `i_g_k_v` (`registry_group`,`registry_key`,`registry_value`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- web.job_template definition

CREATE TABLE `job_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_cron` varchar(128) NOT NULL COMMENT '任务执行CRON',
  `job_desc` varchar(255) NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` int(11) NOT NULL COMMENT '修改用户',
  `alarm_email` varchar(255) DEFAULT NULL COMMENT '报警邮件',
  `executor_route_strategy` varchar(50) DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器参数',
  `executor_block_strategy` varchar(50) DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(11) NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `glue_type` varchar(50) NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_last_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '上次调度时间',
  `trigger_next_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '下次调度时间',
  `job_json` text COMMENT 'flinkx运行脚本',
  `jvm_param` varchar(200) DEFAULT NULL COMMENT 'jvm参数',
  `project_id` int(11) DEFAULT NULL COMMENT '所属项目Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- web.job_user definition

CREATE TABLE `job_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `role` varchar(50) DEFAULT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- web.lark_api_auth definition

CREATE TABLE `lark_api_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token_id` text,
  `group_id` varchar(100) DEFAULT NULL,
  `update_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


-- web.lark_api_auth_config definition

CREATE TABLE `lark_api_auth_config` (
  `auth_id` varchar(100) DEFAULT NULL,
  `config_id` varchar(100) DEFAULT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- web.lark_api_config definition

CREATE TABLE `lark_api_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `group_id` varchar(100) DEFAULT NULL,
  `describe` varchar(100) DEFAULT NULL,
  `datasource_id` varchar(100) DEFAULT NULL,
  `params` varchar(100) DEFAULT NULL,
  `create_time` varchar(100) DEFAULT NULL,
  `update_time` varchar(100) DEFAULT NULL,
  `sql_text` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lark_api_config_un` (`path`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;


-- web.lark_base_resource definition

CREATE TABLE `lark_base_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `resource_address` varchar(100) DEFAULT NULL,
  `update_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;


-- web.lark_dev_tasklist definition

CREATE TABLE `lark_dev_tasklist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `tasktype` varchar(100) DEFAULT NULL,
  `runtype` varchar(100) DEFAULT NULL,
  `sql_text` text,
  `run_param` varchar(100) DEFAULT NULL,
  `jarpath` varchar(100) DEFAULT NULL,
  `task_describe` varchar(100) DEFAULT NULL,
  `update_time` varchar(100) DEFAULT NULL,
  `create_time` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

INSERT INTO web.dev_env_setting (name,prop_value,description,user_id,flag,create_time,update_time) VALUES
	 ('Flink_HOME','/home/webmidtable/flink1.13/job','Flink任务JAR存储的位置',3,1,'2022-01-20 15:28:06.0',NULL),
	 ('SPARK_HOME','/home/webmidtable/spark-2.2.0/job','Spark任务JAR存储位置',3,1,'2022-01-30 21:43:49.0',NULL),
	 (NULL,NULL,NULL,1,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL);
INSERT INTO web.dev_env_setting (name,prop_value,description,user_id,flag,create_time,update_time) VALUES
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL);
INSERT INTO web.dev_env_setting (name,prop_value,description,user_id,flag,create_time,update_time) VALUES
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 (NULL,NULL,NULL,4,1,NULL,NULL),
	 ('testarg1','1','testarg1',3,1,'2022-03-24 22:59:36.0',NULL);INSERT INTO web.job_group (app_name,title,`order`,address_type,address_list) VALUES
	 ('executor','flinkx',1,0,NULL),
	 ('executor','datax',2,0,NULL),
	 ('executor','seatunnel',3,0,NULL);INSERT INTO web.job_info (job_group,job_cron,job_desc,project_id,add_time,update_time,user_id,alarm_email,executor_route_strategy,executor_handler,executor_param,executor_block_strategy,executor_timeout,executor_fail_retry_count,glue_type,glue_source,glue_remark,glue_updatetime,child_jobid,trigger_status,trigger_last_time,trigger_next_time,job_json,replace_param,jvm_param,inc_start_time,partition_info,last_handle_code,replace_param_type,reader_table,primary_key,inc_start_id,increment_type,datasource_id) VALUES
	 (1,'* 05 * ? * * *','student_2',3,'2022-03-06 15:14:41.0','2022-03-06 15:14:41.0',1,'','RANDOM','executorJobHandler','','DISCARD_LATER',0,0,'flinkx',NULL,NULL,'2022-03-06 15:14:41.0','',0,0,0,'{
  "job": {
    "setting": {
      "speed": {
        "channel": 1,
        "bytes": 0
      },
      "errorLimit": {
        "record": 100
      },
      "restore": {
        "maxRowNumForCheckpoint": 0,
        "isRestore": false,
        "restoreColumnName": "",
        "restoreColumnIndex": 0
      },
      "log": {
        "isLogger": false,
        "level": "debug",
        "path": "",
        "pattern": ""
      }
    },
    "content": [
      {
        "reader": {
          "name": "mysqlreader",
          "parameter": {
            "username": "root",
            "password": "root",
            "column": [
              {
                "name": "id"
              },
              {
                "name": "name"
              },
              {
                "name": "address"
              }
            ],
            "splitPk": "",
            "connection": [
              {
                "table": [
                  "student_2"
                ],
                "jdbcUrl": [
                  "jdbc:mysql://localhost:3306/test?serverTimezone=UTC"
                ]
              }
            ]
          }
        },
        "writer": {
          "name": "postgresqlwriter",
          "parameter": {
            "username": "postgres",
            "password": "123456",
            "writeMode": "insert",
            "column": [
              {
                "name": "id"
              },
              {
                "name": "name"
              },
              {
                "name": "address"
              }
            ],
            "connection": [
              {
                "table": [
                  "student.student2"
                ],
                "jdbcUrl": "jdbc:postgresql://localhost:5432/postgres"
              }
            ]
          }
        }
      }
    ]
  }
}',NULL,'',NULL,NULL,0,'Timestamp',NULL,NULL,NULL,0,0),
	 (4,'* * 01 ? * * *','student',9,'2022-03-23 23:16:36.0','2022-03-24 12:26:55.0',3,'','RANDOM','executorJobHandler','','DISCARD_LATER',0,0,'flinkx',NULL,NULL,'2022-03-23 23:16:36.0','70',0,0,0,'{
  "job": {
    "setting": {
      "speed": {
        "channel": 1,
        "bytes": 0
      },
      "errorLimit": {
        "record": 100
      },
      "restore": {
        "maxRowNumForCheckpoint": 0,
        "isRestore": false,
        "restoreColumnName": "",
        "restoreColumnIndex": 0
      },
      "log": {
        "isLogger": false,
        "level": "debug",
        "path": "",
        "pattern": ""
      }
    },
    "content": [
      {
        "reader": {
          "name": "mysqlreader",
          "parameter": {
            "username": "web",
            "password": "LarkMid!@#123",
            "column": [
              {
                "name": "id"
              },
              {
                "name": "name"
              }
            ],
            "splitPk": "",
            "connection": [
              {
                "table": [
                  "student"
                ],
                "jdbcUrl": [
                  "jdbc:mysql://localhost:3310/source?serverTimezone=UTC"
                ]
              }
            ]
          }
        },
        "writer": {
          "name": "mysqlwriter",
          "parameter": {
            "username": "web",
            "password": "LarkMid!@#123",
            "writeMode": "insert",
            "column": [
              {
                "name": "id"
              },
              {
                "name": "name"
              }
            ],
            "connection": [
              {
                "table": [
                  "student"
                ],
                "jdbcUrl": "jdbc:mysql://localhost:3310/sink?serverTimezone=UTC"
              }
            ]
          }
        }
      }
    ]
  }
}',NULL,'',NULL,NULL,0,'Timestamp',NULL,NULL,NULL,0,0),
	 (4,'00 13 03 * * ? *','test.table_name',9,'2022-03-24 22:55:43.0','2022-03-25 16:47:38.0',3,'','RANDOM','executorJobHandler','','DISCARD_LATER',0,0,'flinkx',NULL,NULL,'2022-03-24 22:55:43.0','',1,0,1648321980000,'{
  "job": {
    "setting": {
      "speed": {
        "channel": 1,
        "bytes": 0
      },
      "errorLimit": {
        "record": 100
      },
      "restore": {
        "maxRowNumForCheckpoint": 0,
        "isRestore": false,
        "restoreColumnName": "",
        "restoreColumnIndex": 0
      },
      "log": {
        "isLogger": false,
        "level": "debug",
        "path": "",
        "pattern": ""
      }
    },
    "content": [
      {
        "reader": {
          "name": "sqlserverreader",
          "parameter": {
            "username": "sa",
            "password": "Boray2022",
            "splitPk": "",
            "connection": [
              {
                "querySql": [
                  "select * from test.table_name"
                ],
                "jdbcUrl": [
                  "jdbc:jtds:sqlserver://49.7.58.10:1433;DatabaseName=master"
                ]
              }
            ]
          }
        },
        "writer": {
          "name": "sqlserverwriter",
          "parameter": {
            "username": "sa",
            "password": "Boray2022",
            "writeMode": "insert",
            "column": [
              "column_1",
              "column_2"
            ],
            "preSql": [
              "delete from table_name where 1=0"
            ],
            "postSql": [
              "delete from table_name where 1=0"
            ],
            "connection": [
              {
                "table": [
                  "test.table_name"
                ],
                "jdbcUrl": "jdbc:jtds:sqlserver://49.7.58.10:1433;DatabaseName=master"
              }
            ]
          }
        }
      }
    ]
  }
}',NULL,'',NULL,NULL,0,'Timestamp',NULL,NULL,NULL,0,0),
	 (4,'00 13 03 * * ? *','student',9,'2022-03-25 15:49:40.0','2022-03-25 15:49:40.0',3,'','RANDOM','executorJobHandler','','DISCARD_LATER',0,0,'flinkx',NULL,NULL,'2022-03-25 15:49:40.0','',0,0,0,'{
  "job": {
    "setting": {
      "speed": {
        "channel": 1,
        "bytes": 0
      },
      "errorLimit": {
        "record": 100
      },
      "restore": {
        "maxRowNumForCheckpoint": 0,
        "isRestore": false,
        "restoreColumnName": "",
        "restoreColumnIndex": 0
      },
      "log": {
        "isLogger": false,
        "level": "debug",
        "path": "",
        "pattern": ""
      }
    },
    "content": [
      {
        "reader": {
          "name": "mysqlreader",
          "parameter": {
            "username": "web",
            "password": "LarkMid!@#123",
            "splitPk": "id",
            "connection": [
              {
                "querySql": [
                  "select * from student;"
                ],
                "jdbcUrl": [
                  "jdbc:mysql://localhost:3310/source?serverTimezone=UTC"
                ]
              }
            ]
          }
        },
        "writer": {
          "name": "mysqlwriter",
          "parameter": {
            "username": "web",
            "password": "LarkMid!@#123",
            "writeMode": "insert",
            "column": [
              "id",
              "name"
            ],
            "connection": [
              {
                "table": [
                  ""
                ],
                "jdbcUrl": "jdbc:mysql://localhost:3310/sink?serverTimezone=UTC"
              }
            ]
          }
        }
      }
    ]
  }
}',NULL,'',NULL,NULL,0,'Timestamp',NULL,NULL,NULL,0,0),
	 (4,'00 13 03 * * ? *','student',9,'2022-03-25 16:46:13.0','2022-03-25 16:46:13.0',3,'','RANDOM','executorJobHandler','','DISCARD_LATER',0,0,'flinkx',NULL,NULL,'2022-03-25 16:46:13.0','',0,0,0,'{
  "job": {
    "setting": {
      "speed": {
        "channel": 1,
        "bytes": 0
      },
      "errorLimit": {
        "record": 100
      },
      "restore": {
        "maxRowNumForCheckpoint": 0,
        "isRestore": false,
        "restoreColumnName": "",
        "restoreColumnIndex": 0
      },
      "log": {
        "isLogger": false,
        "level": "debug",
        "path": "",
        "pattern": ""
      }
    },
    "content": [
      {
        "reader": {
          "name": "mysqlreader",
          "parameter": {
            "username": "web",
            "password": "LarkMid!@#123",
            "column": [
              "id",
              "name"
            ],
            "splitPk": "",
            "connection": [
              {
                "table": [
                  "student"
                ],
                "jdbcUrl": [
                  "jdbc:mysql://localhost:3310/sink?serverTimezone=UTC"
                ]
              }
            ]
          }
        },
        "writer": {
          "name": "mysqlwriter",
          "parameter": {
            "username": "web",
            "password": "LarkMid!@#123",
            "writeMode": "insert",
            "column": [
              "id",
              "name"
            ],
            "connection": [
              {
                "table": [
                  "student"
                ],
                "jdbcUrl": "jdbc:mysql://localhost:3310/sink?serverTimezone=UTC"
              }
            ]
          }
        }
      }
    ]
  }
}',NULL,'',NULL,NULL,0,'Timestamp',NULL,NULL,NULL,0,0),
	 (4,'00 13 03 * * ? *','student',9,'2022-03-25 16:46:31.0','2022-03-25 16:46:31.0',3,'','RANDOM','executorJobHandler','','DISCARD_LATER',0,0,'flinkx',NULL,NULL,'2022-03-25 16:46:31.0','',0,0,0,'{
  "job": {
    "setting": {
      "speed": {
        "channel": 1,
        "bytes": 0
      },
      "errorLimit": {
        "record": 100
      },
      "restore": {
        "maxRowNumForCheckpoint": 0,
        "isRestore": false,
        "restoreColumnName": "",
        "restoreColumnIndex": 0
      },
      "log": {
        "isLogger": false,
        "level": "debug",
        "path": "",
        "pattern": ""
      }
    },
    "content": [
      {
        "reader": {
          "name": "mysqlreader",
          "parameter": {
            "username": "web",
            "password": "LarkMid!@#123",
            "column": [
              "id",
              "name"
            ],
            "where": "id = 1",
            "splitPk": "",
            "connection": [
              {
                "table": [
                  "student"
                ],
                "jdbcUrl": [
                  "jdbc:mysql://localhost:3310/source?serverTimezone=UTC"
                ]
              }
            ]
          }
        },
        "writer": {
          "name": "mysqlwriter",
          "parameter": {
            "username": "web",
            "password": "LarkMid!@#123",
            "writeMode": "insert",
            "column": [
              "id",
              "name"
            ],
            "connection": [
              {
                "table": [
                  "student"
                ],
                "jdbcUrl": "jdbc:mysql://localhost:3310/sink?serverTimezone=UTC"
              }
            ]
          }
        }
      }
    ]
  }
}',NULL,'',NULL,NULL,0,'Timestamp',NULL,NULL,NULL,0,0),
	 (4,'00 13 03 * * ? *','student',9,'2022-03-25 23:34:29.0','2022-03-25 23:34:29.0',3,'','RANDOM','executorJobHandler','','DISCARD_LATER',0,0,'flinkx',NULL,NULL,'2022-03-25 23:34:29.0','',0,0,0,'{
  "job": {
    "setting": {
      "speed": {
        "channel": 1,
        "bytes": 0
      },
      "errorLimit": {
        "record": 100
      },
      "restore": {
        "maxRowNumForCheckpoint": 0,
        "isRestore": false,
        "restoreColumnName": "",
        "restoreColumnIndex": 0
      },
      "log": {
        "isLogger": false,
        "level": "debug",
        "path": "",
        "pattern": ""
      }
    },
    "content": [
      {
        "reader": {
          "name": "mysqlreader",
          "parameter": {
            "username": "web",
            "password": "LarkMid!@#123",
            "column": [
              {
                "name": "id"
              },
              {
                "name": "name"
              }
            ],
            "splitPk": "",
            "connection": [
              {
                "table": [
                  "student"
                ],
                "jdbcUrl": [
                  "jdbc:mysql://localhost:3310/source?serverTimezone=UTC"
                ]
              }
            ]
          }
        },
        "writer": {
          "name": "mysqlwriter",
          "parameter": {
            "username": "web",
            "password": "LarkMid!@#123",
            "writeMode": "insert",
            "column": [
              {
                "name": "id"
              },
              {
                "name": "name"
              }
            ],
            "connection": [
              {
                "table": [
                  "student"
                ],
                "jdbcUrl": "jdbc:mysql://localhost:3310/sink?serverTimezone=UTC"
              }
            ]
          }
        }
      }
    ]
  }
}',NULL,'',NULL,NULL,0,'Timestamp',NULL,NULL,NULL,0,0),
	 (4,'00 13 03 * * ? *','student',9,'2022-03-25 23:46:22.0','2022-03-25 23:46:22.0',3,'','RANDOM','executorJobHandler','','DISCARD_LATER',0,0,'flinkx',NULL,NULL,'2022-03-25 23:46:22.0','',0,0,0,'{"job":{"setting":{"speed":{"channel":1,"bytes":0},"errorLimit":{"record":100},"restore":{"maxRowNumForCheckpoint":0,"isRestore":false,"restoreColumnName":"","restoreColumnIndex":0},"log":{"isLogger":false,"level":"debug","path":"","pattern":""}},"content":[{"reader":{"name":"mysqlreader","parameter":{"username":"web","password":"LarkMid!@#123","column":[{"name":"id"},{"name":"name"}],"splitPk":"","connection":[{"table":["student"],"jdbcUrl":["jdbc:mysql://localhost:3310/source?serverTimezone=UTC"]}]}},"writer":{"name":"mysqlwriter","parameter":{"username":"web","password":"LarkMid!@#123","writeMode":"insert","column":[{"name":"id"},{"name":"name"}],"connection":[{"table":["student"],"jdbcUrl":"jdbc:mysql://localhost:3310/sink?serverTimezone=UTC"}]}}}]}}',NULL,'',NULL,NULL,0,NULL,NULL,NULL,NULL,0,0);INSERT INTO web.job_jdbc_datasource (datasource_name,datasource,datasource_group,database_name,jdbc_username,jdbc_password,jdbc_url,jdbc_driver_class,zk_adress,status,create_by,create_date,update_by,update_date,comments) VALUES
	 ('mysql的source源','mysql','source',NULL,'r1zeucGv0jVj6vMQMIxIFA==','ILKXd6gtmqnoBZ0mdMHpzQ==','jdbc:mysql://localhost:3310/source?serverTimezone=UTC','com.mysql.jdbc.Driver',NULL,1,'admin','2022-03-09 21:27:50.0','admin','2022-03-24 21:18:02.0',''),
	 ('mysql的sink源','mysql','sink',NULL,'r1zeucGv0jVj6vMQMIxIFA==','ILKXd6gtmqnoBZ0mdMHpzQ==','jdbc:mysql://localhost:3310/sink?serverTimezone=UTC','com.mysql.jdbc.Driver',NULL,1,'admin','2022-03-09 21:29:21.0','admin','2022-03-23 23:12:17.0','');INSERT INTO web.job_lock (lock_name) VALUES
	 ('schedule_lock');INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student',NULL,NULL,NULL,NULL,0,'2022-03-23 23:17:03.0',200,NULL,NULL,0,NULL,0,'31900',NULL),
	 (4,96,'student',NULL,NULL,NULL,NULL,0,'2022-03-24 01:00:00.0',200,NULL,NULL,0,NULL,0,'13812',NULL),
	 (4,96,'student',NULL,NULL,NULL,NULL,0,'2022-03-24 01:00:06.0',200,NULL,NULL,0,NULL,0,'13935',NULL),
	 (4,96,'student',NULL,NULL,NULL,NULL,0,'2022-03-24 01:01:15.0',200,NULL,NULL,0,NULL,0,'13972',NULL),
	 (4,96,'student',NULL,NULL,NULL,NULL,0,'2022-03-24 01:01:21.0',200,NULL,NULL,0,NULL,0,'14168',NULL),
	 (4,96,'student',NULL,NULL,NULL,NULL,0,'2022-03-24 01:01:26.0',200,NULL,NULL,0,NULL,0,'14215',NULL),
	 (4,96,'student',NULL,NULL,NULL,NULL,0,'2022-03-24 01:01:32.0',200,NULL,NULL,0,NULL,0,'14262',NULL),
	 (4,96,'student',NULL,NULL,NULL,NULL,0,'2022-03-24 01:01:37.0',200,NULL,NULL,0,NULL,0,'14317',NULL),
	 (4,96,'student','961648054902731.out',NULL,NULL,NULL,0,'2022-03-24 01:01:43.0',200,NULL,NULL,0,NULL,0,'38824',NULL),
	 (4,96,'student','961648054908505.out',NULL,NULL,NULL,0,'2022-03-24 01:01:48.0',200,NULL,NULL,0,NULL,0,'47772',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648054913956.out',NULL,NULL,NULL,0,'2022-03-24 01:01:54.0',200,NULL,NULL,0,NULL,0,'48628',NULL),
	 (4,96,'student','961648054919374.out',NULL,NULL,NULL,0,'2022-03-24 01:01:59.0',200,NULL,NULL,0,NULL,0,'44740',NULL),
	 (4,96,'student','961648054924784.out',NULL,NULL,NULL,0,'2022-03-24 01:02:04.0',200,NULL,NULL,0,NULL,0,'22868',NULL),
	 (4,96,'student','961648054930196.out',NULL,NULL,NULL,0,'2022-03-24 01:02:10.0',200,NULL,NULL,0,NULL,0,'37772',NULL),
	 (4,96,'student','961648054935648.out',NULL,NULL,NULL,0,'2022-03-24 01:02:15.0',200,NULL,NULL,0,NULL,0,'47616',NULL),
	 (4,96,'student','961648054941055.out',NULL,NULL,NULL,0,'2022-03-24 01:02:21.0',200,NULL,NULL,0,NULL,0,'48020',NULL),
	 (4,96,'student','961648054946541.out',NULL,NULL,NULL,0,'2022-03-24 01:02:26.0',200,NULL,NULL,0,NULL,0,'48432',NULL),
	 (4,96,'student','961648054951959.out',NULL,NULL,NULL,0,'2022-03-24 01:02:32.0',200,NULL,NULL,0,NULL,0,'47448',NULL),
	 (4,96,'student','961648054957402.out',NULL,NULL,NULL,0,'2022-03-24 01:02:37.0',200,NULL,NULL,0,NULL,0,'48812',NULL),
	 (4,96,'student','961648054962812.out',NULL,NULL,NULL,0,'2022-03-24 01:02:42.0',200,NULL,NULL,0,NULL,0,'48092',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648054968221.out',NULL,NULL,NULL,0,'2022-03-24 01:02:48.0',200,NULL,NULL,0,NULL,0,'45560',NULL),
	 (4,96,'student','961648054973651.out',NULL,NULL,NULL,0,'2022-03-24 01:02:53.0',200,NULL,NULL,0,NULL,0,'47812',NULL),
	 (4,96,'student','961648054979063.out',NULL,NULL,NULL,0,'2022-03-24 01:02:59.0',200,NULL,NULL,0,NULL,0,'46204',NULL),
	 (4,96,'student','961648054984470.out',NULL,NULL,NULL,0,'2022-03-24 01:03:04.0',200,NULL,NULL,0,NULL,0,'48828',NULL),
	 (4,96,'student','961648054989895.out',NULL,NULL,NULL,0,'2022-03-24 01:03:09.0',200,NULL,NULL,0,NULL,0,'36860',NULL),
	 (4,96,'student','961648054995315.out',NULL,NULL,NULL,0,'2022-03-24 01:03:15.0',200,NULL,NULL,0,NULL,0,'35196',NULL),
	 (4,96,'student','961648055000734.out',NULL,NULL,NULL,0,'2022-03-24 01:03:20.0',200,NULL,NULL,0,NULL,0,'48096',NULL),
	 (4,96,'student','961648055006143.out',NULL,NULL,NULL,0,'2022-03-24 01:03:26.0',200,NULL,NULL,0,NULL,0,'40732',NULL),
	 (4,96,'student','961648055011566.out',NULL,NULL,NULL,0,'2022-03-24 01:03:31.0',200,NULL,NULL,0,NULL,0,'47856',NULL),
	 (4,96,'student','961648055016974.out',NULL,NULL,NULL,0,'2022-03-24 01:03:37.0',200,NULL,NULL,0,NULL,0,'48360',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055022436.out',NULL,NULL,NULL,0,'2022-03-24 01:03:42.0',200,NULL,NULL,0,NULL,0,'18004',NULL),
	 (4,96,'student','961648055027841.out',NULL,NULL,NULL,0,'2022-03-24 01:03:47.0',200,NULL,NULL,0,NULL,0,'45052',NULL),
	 (4,96,'student','961648055033253.out',NULL,NULL,NULL,0,'2022-03-24 01:03:53.0',200,NULL,NULL,0,NULL,0,'29304',NULL),
	 (4,96,'student','961648055038671.out',NULL,NULL,NULL,0,'2022-03-24 01:03:58.0',200,NULL,NULL,0,NULL,0,'46736',NULL),
	 (4,96,'student','961648055044123.out',NULL,NULL,NULL,0,'2022-03-24 01:04:04.0',200,NULL,NULL,0,NULL,0,'42220',NULL),
	 (4,96,'student','961648055049533.out',NULL,NULL,NULL,0,'2022-03-24 01:04:09.0',200,NULL,NULL,0,NULL,0,'20832',NULL),
	 (4,96,'student','961648055054946.out',NULL,NULL,NULL,0,'2022-03-24 01:04:15.0',200,NULL,NULL,0,NULL,0,'33524',NULL),
	 (4,96,'student','961648055060350.out',NULL,NULL,NULL,0,'2022-03-24 01:04:20.0',200,NULL,NULL,0,NULL,0,'48492',NULL),
	 (4,96,'student','961648055065755.out',NULL,NULL,NULL,0,'2022-03-24 01:04:25.0',200,NULL,NULL,0,NULL,0,'41324',NULL),
	 (4,96,'student','961648055071159.out',NULL,NULL,NULL,0,'2022-03-24 01:04:31.0',200,NULL,NULL,0,NULL,0,'43484',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055076567.out',NULL,NULL,NULL,0,'2022-03-24 01:04:36.0',200,NULL,NULL,0,NULL,0,'46528',NULL),
	 (4,96,'student','961648055081979.out',NULL,NULL,NULL,0,'2022-03-24 01:04:42.0',200,NULL,NULL,0,NULL,0,'46104',NULL),
	 (4,96,'student','961648055087397.out',NULL,NULL,NULL,0,'2022-03-24 01:04:47.0',200,NULL,NULL,0,NULL,0,'46688',NULL),
	 (4,96,'student','961648055092814.out',NULL,NULL,NULL,0,'2022-03-24 01:04:52.0',200,NULL,NULL,0,NULL,0,'46452',NULL),
	 (4,96,'student','961648055098221.out',NULL,NULL,NULL,0,'2022-03-24 01:04:58.0',200,NULL,NULL,0,NULL,0,'47592',NULL),
	 (4,96,'student','961648055103648.out',NULL,NULL,NULL,0,'2022-03-24 01:05:03.0',200,NULL,NULL,0,NULL,0,'48616',NULL),
	 (4,96,'student','961648055109076.out',NULL,NULL,NULL,0,'2022-03-24 01:05:09.0',200,NULL,NULL,0,NULL,0,'45984',NULL),
	 (4,96,'student','961648055114518.out',NULL,NULL,NULL,0,'2022-03-24 01:05:14.0',200,NULL,NULL,0,NULL,0,'48796',NULL),
	 (4,96,'student','961648055119935.out',NULL,NULL,NULL,0,'2022-03-24 01:05:20.0',200,NULL,NULL,0,NULL,0,'46636',NULL),
	 (4,96,'student','961648055125335.out',NULL,NULL,NULL,0,'2022-03-24 01:05:25.0',200,NULL,NULL,0,NULL,0,'47480',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055130748.out',NULL,NULL,NULL,0,'2022-03-24 01:05:30.0',200,NULL,NULL,0,NULL,0,'48844',NULL),
	 (4,96,'student','961648055136156.out',NULL,NULL,NULL,0,'2022-03-24 01:05:36.0',200,NULL,NULL,0,NULL,0,'47112',NULL),
	 (4,96,'student','961648055141572.out',NULL,NULL,NULL,0,'2022-03-24 01:05:41.0',200,NULL,NULL,0,NULL,0,'47092',NULL),
	 (4,96,'student','961648055146986.out',NULL,NULL,NULL,0,'2022-03-24 01:05:47.0',200,NULL,NULL,0,NULL,0,'37740',NULL),
	 (4,96,'student','961648055152396.out',NULL,NULL,NULL,0,'2022-03-24 01:05:52.0',200,NULL,NULL,0,NULL,0,'47484',NULL),
	 (4,96,'student','961648055157824.out',NULL,NULL,NULL,0,'2022-03-24 01:05:57.0',200,NULL,NULL,0,NULL,0,'48996',NULL),
	 (4,96,'student','961648055163230.out',NULL,NULL,NULL,0,'2022-03-24 01:06:03.0',200,NULL,NULL,0,NULL,0,'47180',NULL),
	 (4,96,'student','961648055168643.out',NULL,NULL,NULL,0,'2022-03-24 01:06:08.0',200,NULL,NULL,0,NULL,0,'45544',NULL),
	 (4,96,'student','961648055174055.out',NULL,NULL,NULL,0,'2022-03-24 01:06:14.0',200,NULL,NULL,0,NULL,0,'42528',NULL),
	 (4,96,'student','961648055179488.out',NULL,NULL,NULL,0,'2022-03-24 01:06:19.0',200,NULL,NULL,0,NULL,0,'13912',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055184906.out',NULL,NULL,NULL,0,'2022-03-24 01:06:24.0',200,NULL,NULL,0,NULL,0,'21268',NULL),
	 (4,96,'student','961648055190339.out',NULL,NULL,NULL,0,'2022-03-24 01:06:30.0',200,NULL,NULL,0,NULL,0,'48972',NULL),
	 (4,96,'student','961648055195767.out',NULL,NULL,NULL,0,'2022-03-24 01:06:35.0',200,NULL,NULL,0,NULL,0,'45380',NULL),
	 (4,96,'student','961648055201188.out',NULL,NULL,NULL,0,'2022-03-24 01:06:41.0',200,NULL,NULL,0,NULL,0,'45084',NULL),
	 (4,96,'student','961648055206619.out',NULL,NULL,NULL,0,'2022-03-24 01:06:46.0',200,NULL,NULL,0,NULL,0,'24536',NULL),
	 (4,96,'student','961648055212061.out',NULL,NULL,NULL,0,'2022-03-24 01:06:52.0',200,NULL,NULL,0,NULL,0,'47172',NULL),
	 (4,96,'student','961648055217467.out',NULL,NULL,NULL,0,'2022-03-24 01:06:57.0',200,NULL,NULL,0,NULL,0,'47580',NULL),
	 (4,96,'student','961648055222877.out',NULL,NULL,NULL,0,'2022-03-24 01:07:02.0',200,NULL,NULL,0,NULL,0,'17824',NULL),
	 (4,96,'student','961648055228323.out',NULL,NULL,NULL,0,'2022-03-24 01:07:08.0',200,NULL,NULL,0,NULL,0,'35760',NULL),
	 (4,96,'student','961648055233754.out',NULL,NULL,NULL,0,'2022-03-24 01:07:13.0',200,NULL,NULL,0,NULL,0,'48272',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055239171.out',NULL,NULL,NULL,0,'2022-03-24 01:07:19.0',200,NULL,NULL,0,NULL,0,'46900',NULL),
	 (4,96,'student','961648055244581.out',NULL,NULL,NULL,0,'2022-03-24 01:07:24.0',200,NULL,NULL,0,NULL,0,'48200',NULL),
	 (4,96,'student','961648055250002.out',NULL,NULL,NULL,0,'2022-03-24 01:07:30.0',200,NULL,NULL,0,NULL,0,'5508',NULL),
	 (4,96,'student','961648055255432.out',NULL,NULL,NULL,0,'2022-03-24 01:07:35.0',200,NULL,NULL,0,NULL,0,'48916',NULL),
	 (4,96,'student','961648055260848.out',NULL,NULL,NULL,0,'2022-03-24 01:07:40.0',200,NULL,NULL,0,NULL,0,'5296',NULL),
	 (4,96,'student','961648055266276.out',NULL,NULL,NULL,0,'2022-03-24 01:07:46.0',200,NULL,NULL,0,NULL,0,'47860',NULL),
	 (4,96,'student','961648055271706.out',NULL,NULL,NULL,0,'2022-03-24 01:07:51.0',200,NULL,NULL,0,NULL,0,'48456',NULL),
	 (4,96,'student','961648055277124.out',NULL,NULL,NULL,0,'2022-03-24 01:07:57.0',200,NULL,NULL,0,NULL,0,'48328',NULL),
	 (4,96,'student','961648055282534.out',NULL,NULL,NULL,0,'2022-03-24 01:08:02.0',200,NULL,NULL,0,NULL,0,'45716',NULL),
	 (4,96,'student','961648055287945.out',NULL,NULL,NULL,0,'2022-03-24 01:08:08.0',200,NULL,NULL,0,NULL,0,'6960',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055293386.out',NULL,NULL,NULL,0,'2022-03-24 01:08:13.0',200,NULL,NULL,0,NULL,0,'38792',NULL),
	 (4,96,'student','961648055298804.out',NULL,NULL,NULL,0,'2022-03-24 01:08:18.0',200,NULL,NULL,0,NULL,0,'47144',NULL),
	 (4,96,'student','961648055304220.out',NULL,NULL,NULL,0,'2022-03-24 01:08:24.0',200,NULL,NULL,0,NULL,0,'48268',NULL),
	 (4,96,'student','961648055309655.out',NULL,NULL,NULL,0,'2022-03-24 01:08:29.0',200,NULL,NULL,0,NULL,0,'42160',NULL),
	 (4,96,'student','961648055315068.out',NULL,NULL,NULL,0,'2022-03-24 01:08:35.0',200,NULL,NULL,0,NULL,0,'38500',NULL),
	 (4,96,'student','961648055320487.out',NULL,NULL,NULL,0,'2022-03-24 01:08:40.0',200,NULL,NULL,0,NULL,0,'47788',NULL),
	 (4,96,'student','961648055325901.out',NULL,NULL,NULL,0,'2022-03-24 01:08:45.0',200,NULL,NULL,0,NULL,0,'15908',NULL),
	 (4,96,'student','961648055331344.out',NULL,NULL,NULL,0,'2022-03-24 01:08:51.0',200,NULL,NULL,0,NULL,0,'48648',NULL),
	 (4,96,'student','961648055336772.out',NULL,NULL,NULL,0,'2022-03-24 01:08:56.0',200,NULL,NULL,0,NULL,0,'47700',NULL),
	 (4,96,'student','961648055342200.out',NULL,NULL,NULL,0,'2022-03-24 01:09:02.0',200,NULL,NULL,0,NULL,0,'10224',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055347629.out',NULL,NULL,NULL,0,'2022-03-24 01:09:07.0',200,NULL,NULL,0,NULL,0,'48476',NULL),
	 (4,96,'student','961648055353040.out',NULL,NULL,NULL,0,'2022-03-24 01:09:13.0',200,NULL,NULL,0,NULL,0,'46476',NULL),
	 (4,96,'student','961648055358470.out',NULL,NULL,NULL,0,'2022-03-24 01:09:18.0',200,NULL,NULL,0,NULL,0,'48488',NULL),
	 (4,96,'student','961648055363897.out',NULL,NULL,NULL,0,'2022-03-24 01:09:23.0',200,NULL,NULL,0,NULL,0,'33336',NULL),
	 (4,96,'student','961648055369323.out',NULL,NULL,NULL,0,'2022-03-24 01:09:29.0',200,NULL,NULL,0,NULL,0,'48264',NULL),
	 (4,96,'student','961648055374728.out',NULL,NULL,NULL,0,'2022-03-24 01:09:34.0',200,NULL,NULL,0,NULL,0,'46524',NULL),
	 (4,96,'student','961648055380135.out',NULL,NULL,NULL,0,'2022-03-24 01:09:40.0',200,NULL,NULL,0,NULL,0,'48676',NULL),
	 (4,96,'student','961648055385566.out',NULL,NULL,NULL,0,'2022-03-24 01:09:45.0',200,NULL,NULL,0,NULL,0,'47496',NULL),
	 (4,96,'student','961648055390973.out',NULL,NULL,NULL,0,'2022-03-24 01:09:51.0',200,NULL,NULL,0,NULL,0,'48632',NULL),
	 (4,96,'student','961648055396392.out',NULL,NULL,NULL,0,'2022-03-24 01:09:56.0',200,NULL,NULL,0,NULL,0,'47728',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055401799.out',NULL,NULL,NULL,0,'2022-03-24 01:10:01.0',200,NULL,NULL,0,NULL,0,'47736',NULL),
	 (4,96,'student','961648055407215.out',NULL,NULL,NULL,0,'2022-03-24 01:10:07.0',200,NULL,NULL,0,NULL,0,'10644',NULL),
	 (4,96,'student','961648055412620.out',NULL,NULL,NULL,0,'2022-03-24 01:10:12.0',200,NULL,NULL,0,NULL,0,'48408',NULL),
	 (4,96,'student','961648055418028.out',NULL,NULL,NULL,0,'2022-03-24 01:10:18.0',200,NULL,NULL,0,NULL,0,'46684',NULL),
	 (4,96,'student','961648055423461.out',NULL,NULL,NULL,0,'2022-03-24 01:10:23.0',200,NULL,NULL,0,NULL,0,'48344',NULL),
	 (4,96,'student','961648055428891.out',NULL,NULL,NULL,0,'2022-03-24 01:10:28.0',200,NULL,NULL,0,NULL,0,'48480',NULL),
	 (4,96,'student','961648055434309.out',NULL,NULL,NULL,0,'2022-03-24 01:10:34.0',200,NULL,NULL,0,NULL,0,'47016',NULL),
	 (4,96,'student','961648055439736.out',NULL,NULL,NULL,0,'2022-03-24 01:10:39.0',200,NULL,NULL,0,NULL,0,'45384',NULL),
	 (4,96,'student','961648055445146.out',NULL,NULL,NULL,0,'2022-03-24 01:10:45.0',200,NULL,NULL,0,NULL,0,'48720',NULL),
	 (4,96,'student','961648055450559.out',NULL,NULL,NULL,0,'2022-03-24 01:10:50.0',200,NULL,NULL,0,NULL,0,'48440',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055455983.out',NULL,NULL,NULL,0,'2022-03-24 01:10:56.0',200,NULL,NULL,0,NULL,0,'5604',NULL),
	 (4,96,'student','961648055461406.out',NULL,NULL,NULL,0,'2022-03-24 01:11:01.0',200,NULL,NULL,0,NULL,0,'47036',NULL),
	 (4,96,'student','961648055466837.out',NULL,NULL,NULL,0,'2022-03-24 01:11:06.0',200,NULL,NULL,0,NULL,0,'47744',NULL),
	 (4,96,'student','961648055472251.out',NULL,NULL,NULL,0,'2022-03-24 01:11:12.0',200,NULL,NULL,0,NULL,0,'37312',NULL),
	 (4,96,'student','961648055477670.out',NULL,NULL,NULL,0,'2022-03-24 01:11:17.0',200,NULL,NULL,0,NULL,0,'38812',NULL),
	 (4,96,'student','961648055483086.out',NULL,NULL,NULL,0,'2022-03-24 01:11:23.0',200,NULL,NULL,0,NULL,0,'47376',NULL),
	 (4,96,'student','961648055488498.out',NULL,NULL,NULL,0,'2022-03-24 01:11:28.0',200,NULL,NULL,0,NULL,0,'47840',NULL),
	 (4,96,'student','961648055493921.out',NULL,NULL,NULL,0,'2022-03-24 01:11:34.0',200,NULL,NULL,0,NULL,0,'48464',NULL),
	 (4,96,'student','961648055499365.out',NULL,NULL,NULL,0,'2022-03-24 01:11:39.0',200,NULL,NULL,0,NULL,0,'44176',NULL),
	 (4,96,'student','961648055504781.out',NULL,NULL,NULL,0,'2022-03-24 01:11:44.0',200,NULL,NULL,0,NULL,0,'41508',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055510198.out',NULL,NULL,NULL,0,'2022-03-24 01:11:50.0',200,NULL,NULL,0,NULL,0,'21932',NULL),
	 (4,96,'student','961648055515622.out',NULL,NULL,NULL,0,'2022-03-24 01:11:55.0',200,NULL,NULL,0,NULL,0,'48460',NULL),
	 (4,96,'student','961648055521035.out',NULL,NULL,NULL,0,'2022-03-24 01:12:01.0',200,NULL,NULL,0,NULL,0,'48400',NULL),
	 (4,96,'student','961648055526461.out',NULL,NULL,NULL,0,'2022-03-24 01:12:06.0',200,NULL,NULL,0,NULL,0,'38724',NULL),
	 (4,96,'student','961648055531876.out',NULL,NULL,NULL,0,'2022-03-24 01:12:11.0',200,NULL,NULL,0,NULL,0,'46216',NULL),
	 (4,96,'student','961648055537292.out',NULL,NULL,NULL,0,'2022-03-24 01:12:17.0',200,NULL,NULL,0,NULL,0,'45300',NULL),
	 (4,96,'student','961648055542711.out',NULL,NULL,NULL,0,'2022-03-24 01:12:22.0',200,NULL,NULL,0,NULL,0,'15268',NULL),
	 (4,96,'student','961648055548100.out',NULL,NULL,NULL,0,'2022-03-24 01:12:28.0',200,NULL,NULL,0,NULL,0,'48876',NULL),
	 (4,96,'student','961648055553552.out',NULL,NULL,NULL,0,'2022-03-24 01:12:33.0',200,NULL,NULL,0,NULL,0,'48644',NULL),
	 (4,96,'student','961648055558969.out',NULL,NULL,NULL,0,'2022-03-24 01:12:39.0',200,NULL,NULL,0,NULL,0,'48276',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055564400.out',NULL,NULL,NULL,0,'2022-03-24 01:12:44.0',200,NULL,NULL,0,NULL,0,'48664',NULL),
	 (4,96,'student','961648055569814.out',NULL,NULL,NULL,0,'2022-03-24 01:12:49.0',200,NULL,NULL,0,NULL,0,'47648',NULL),
	 (4,96,'student','961648055575228.out',NULL,NULL,NULL,0,'2022-03-24 01:12:55.0',200,NULL,NULL,0,NULL,0,'13132',NULL),
	 (4,96,'student','961648055580614.out',NULL,NULL,NULL,0,'2022-03-24 01:13:00.0',200,NULL,NULL,0,NULL,0,'1712',NULL),
	 (4,96,'student','961648055586065.out',NULL,NULL,NULL,0,'2022-03-24 01:13:06.0',200,NULL,NULL,0,NULL,0,'44588',NULL),
	 (4,96,'student','961648055591486.out',NULL,NULL,NULL,0,'2022-03-24 01:13:11.0',200,NULL,NULL,0,NULL,0,'46180',NULL),
	 (4,96,'student','961648055596909.out',NULL,NULL,NULL,0,'2022-03-24 01:13:16.0',200,NULL,NULL,0,NULL,0,'46380',NULL),
	 (4,96,'student','961648055602322.out',NULL,NULL,NULL,0,'2022-03-24 01:13:22.0',200,NULL,NULL,0,NULL,0,'47456',NULL),
	 (4,96,'student','961648055607738.out',NULL,NULL,NULL,0,'2022-03-24 01:13:27.0',200,NULL,NULL,0,NULL,0,'48604',NULL),
	 (4,96,'student','961648055613153.out',NULL,NULL,NULL,0,'2022-03-24 01:13:33.0',200,NULL,NULL,0,NULL,0,'47040',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055618546.out',NULL,NULL,NULL,0,'2022-03-24 01:13:38.0',200,NULL,NULL,0,NULL,0,'31012',NULL),
	 (4,96,'student','961648055623956.out',NULL,NULL,NULL,0,'2022-03-24 01:13:44.0',200,NULL,NULL,0,NULL,0,'6948',NULL),
	 (4,96,'student','961648055629385.out',NULL,NULL,NULL,0,'2022-03-24 01:13:49.0',200,NULL,NULL,0,NULL,0,'46624',NULL),
	 (4,96,'student','961648055634806.out',NULL,NULL,NULL,0,'2022-03-24 01:13:54.0',200,NULL,NULL,0,NULL,0,'46668',NULL),
	 (4,96,'student','961648055640225.out',NULL,NULL,NULL,0,'2022-03-24 01:14:00.0',200,NULL,NULL,0,NULL,0,'40688',NULL),
	 (4,96,'student','961648055645680.out',NULL,NULL,NULL,0,'2022-03-24 01:14:05.0',200,NULL,NULL,0,NULL,0,'17608',NULL),
	 (4,96,'student','961648055651111.out',NULL,NULL,NULL,0,'2022-03-24 01:14:11.0',200,NULL,NULL,0,NULL,0,'5856',NULL),
	 (4,96,'student','961648055656563.out',NULL,NULL,NULL,0,'2022-03-24 01:14:16.0',200,NULL,NULL,0,NULL,0,'39308',NULL),
	 (4,96,'student','961648055661975.out',NULL,NULL,NULL,0,'2022-03-24 01:14:22.0',200,NULL,NULL,0,NULL,0,'49904',NULL),
	 (4,96,'student','961648055667415.out',NULL,NULL,NULL,0,'2022-03-24 01:14:27.0',200,NULL,NULL,0,NULL,0,'49180',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055672872.out',NULL,NULL,NULL,0,'2022-03-24 01:14:32.0',200,NULL,NULL,0,NULL,0,'49860',NULL),
	 (4,96,'student','961648055678291.out',NULL,NULL,NULL,0,'2022-03-24 01:14:38.0',200,NULL,NULL,0,NULL,0,'44580',NULL),
	 (4,96,'student','961648055683699.out',NULL,NULL,NULL,0,'2022-03-24 01:14:43.0',200,NULL,NULL,0,NULL,0,'50024',NULL),
	 (4,96,'student','961648055689128.out',NULL,NULL,NULL,0,'2022-03-24 01:14:49.0',200,NULL,NULL,0,NULL,0,'47508',NULL),
	 (4,96,'student','961648055694548.out',NULL,NULL,NULL,0,'2022-03-24 01:14:54.0',200,NULL,NULL,0,NULL,0,'49312',NULL),
	 (4,96,'student','961648055699959.out',NULL,NULL,NULL,0,'2022-03-24 01:15:00.0',200,NULL,NULL,0,NULL,0,'49444',NULL),
	 (4,96,'student','961648055705369.out',NULL,NULL,NULL,0,'2022-03-24 01:15:05.0',200,NULL,NULL,0,NULL,0,'46212',NULL),
	 (4,96,'student','961648055710781.out',NULL,NULL,NULL,0,'2022-03-24 01:15:10.0',200,NULL,NULL,0,NULL,0,'49356',NULL),
	 (4,96,'student','961648055716206.out',NULL,NULL,NULL,0,'2022-03-24 01:15:16.0',200,NULL,NULL,0,NULL,0,'46292',NULL),
	 (4,96,'student','961648055721631.out',NULL,NULL,NULL,0,'2022-03-24 01:15:21.0',200,NULL,NULL,0,NULL,0,'49208',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055727058.out',NULL,NULL,NULL,0,'2022-03-24 01:15:27.0',200,NULL,NULL,0,NULL,0,'49452',NULL),
	 (4,96,'student','961648055732467.out',NULL,NULL,NULL,0,'2022-03-24 01:15:32.0',200,NULL,NULL,0,NULL,0,'48452',NULL),
	 (4,96,'student','961648055737889.out',NULL,NULL,NULL,0,'2022-03-24 01:15:37.0',200,NULL,NULL,0,NULL,0,'49336',NULL),
	 (4,96,'student','961648055743300.out',NULL,NULL,NULL,0,'2022-03-24 01:15:43.0',200,NULL,NULL,0,NULL,0,'49464',NULL),
	 (4,96,'student','961648055748714.out',NULL,NULL,NULL,0,'2022-03-24 01:15:48.0',200,NULL,NULL,0,NULL,0,'10752',NULL),
	 (4,96,'student','961648055754126.out',NULL,NULL,NULL,0,'2022-03-24 01:15:54.0',200,NULL,NULL,0,NULL,0,'47280',NULL),
	 (4,96,'student','961648055759542.out',NULL,NULL,NULL,0,'2022-03-24 01:15:59.0',200,NULL,NULL,0,NULL,0,'49652',NULL),
	 (4,96,'student','961648055764966.out',NULL,NULL,NULL,0,'2022-03-24 01:16:05.0',200,NULL,NULL,0,NULL,0,'49048',NULL),
	 (4,96,'student','961648055770375.out',NULL,NULL,NULL,0,'2022-03-24 01:16:10.0',200,NULL,NULL,0,NULL,0,'47760',NULL),
	 (4,96,'student','961648055775786.out',NULL,NULL,NULL,0,'2022-03-24 01:16:15.0',200,NULL,NULL,0,NULL,0,'48900',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055781205.out',NULL,NULL,NULL,0,'2022-03-24 01:16:21.0',200,NULL,NULL,0,NULL,0,'49288',NULL),
	 (4,96,'student','961648055786623.out',NULL,NULL,NULL,0,'2022-03-24 01:16:26.0',200,NULL,NULL,0,NULL,0,'49704',NULL),
	 (4,96,'student','961648055792034.out',NULL,NULL,NULL,0,'2022-03-24 01:16:32.0',200,NULL,NULL,0,NULL,0,'50020',NULL),
	 (4,96,'student','961648055797440.out',NULL,NULL,NULL,0,'2022-03-24 01:16:37.0',200,NULL,NULL,0,NULL,0,'48588',NULL),
	 (4,96,'student','961648055802858.out',NULL,NULL,NULL,0,'2022-03-24 01:16:42.0',200,NULL,NULL,0,NULL,0,'49692',NULL),
	 (4,96,'student','961648055808261.out',NULL,NULL,NULL,0,'2022-03-24 01:16:48.0',200,NULL,NULL,0,NULL,0,'47300',NULL),
	 (4,96,'student','961648055813671.out',NULL,NULL,NULL,0,'2022-03-24 01:16:53.0',200,NULL,NULL,0,NULL,0,'41192',NULL),
	 (4,96,'student','961648055819092.out',NULL,NULL,NULL,0,'2022-03-24 01:16:59.0',200,NULL,NULL,0,NULL,0,'47776',NULL),
	 (4,96,'student','961648055824499.out',NULL,NULL,NULL,0,'2022-03-24 01:17:04.0',200,NULL,NULL,0,NULL,0,'45668',NULL),
	 (4,96,'student','961648055829914.out',NULL,NULL,NULL,0,'2022-03-24 01:17:09.0',200,NULL,NULL,0,NULL,0,'49104',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055835343.out',NULL,NULL,NULL,0,'2022-03-24 01:17:15.0',200,NULL,NULL,0,NULL,0,'44260',NULL),
	 (4,96,'student','961648055840759.out',NULL,NULL,NULL,0,'2022-03-24 01:17:20.0',200,NULL,NULL,0,NULL,0,'49488',NULL),
	 (4,96,'student','961648055846204.out',NULL,NULL,NULL,0,'2022-03-24 01:17:26.0',200,NULL,NULL,0,NULL,0,'49736',NULL),
	 (4,96,'student','961648055851620.out',NULL,NULL,NULL,0,'2022-03-24 01:17:31.0',200,NULL,NULL,0,NULL,0,'18268',NULL),
	 (4,96,'student','961648055857038.out',NULL,NULL,NULL,0,'2022-03-24 01:17:37.0',200,NULL,NULL,0,NULL,0,'46168',NULL),
	 (4,96,'student','961648055862474.out',NULL,NULL,NULL,0,'2022-03-24 01:17:42.0',200,NULL,NULL,0,NULL,0,'34960',NULL),
	 (4,96,'student','961648055867902.out',NULL,NULL,NULL,0,'2022-03-24 01:17:47.0',200,NULL,NULL,0,NULL,0,'50140',NULL),
	 (4,96,'student','961648055873334.out',NULL,NULL,NULL,0,'2022-03-24 01:17:53.0',200,NULL,NULL,0,NULL,0,'49220',NULL),
	 (4,96,'student','961648055878744.out',NULL,NULL,NULL,0,'2022-03-24 01:17:58.0',200,NULL,NULL,0,NULL,0,'49348',NULL),
	 (4,96,'student','961648055884159.out',NULL,NULL,NULL,0,'2022-03-24 01:18:04.0',200,NULL,NULL,0,NULL,0,'49868',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055889580.out',NULL,NULL,NULL,0,'2022-03-24 01:18:09.0',200,NULL,NULL,0,NULL,0,'49284',NULL),
	 (4,96,'student','961648055895006.out',NULL,NULL,NULL,0,'2022-03-24 01:18:15.0',200,NULL,NULL,0,NULL,0,'47624',NULL),
	 (4,96,'student','961648055900412.out',NULL,NULL,NULL,0,'2022-03-24 01:18:20.0',200,NULL,NULL,0,NULL,0,'2324',NULL),
	 (4,96,'student','961648055905830.out',NULL,NULL,NULL,0,'2022-03-24 01:18:25.0',200,NULL,NULL,0,NULL,0,'49864',NULL),
	 (4,96,'student','961648055911259.out',NULL,NULL,NULL,0,'2022-03-24 01:18:31.0',200,NULL,NULL,0,NULL,0,'14300',NULL),
	 (4,96,'student','961648055916674.out',NULL,NULL,NULL,0,'2022-03-24 01:18:36.0',200,NULL,NULL,0,NULL,0,'49576',NULL),
	 (4,96,'student','961648055922079.out',NULL,NULL,NULL,0,'2022-03-24 01:18:42.0',200,NULL,NULL,0,NULL,0,'46096',NULL),
	 (4,96,'student','961648055927501.out',NULL,NULL,NULL,0,'2022-03-24 01:18:47.0',200,NULL,NULL,0,NULL,0,'48028',NULL),
	 (4,96,'student','961648055932923.out',NULL,NULL,NULL,0,'2022-03-24 01:18:52.0',200,NULL,NULL,0,NULL,0,'48544',NULL),
	 (4,96,'student','961648055938336.out',NULL,NULL,NULL,0,'2022-03-24 01:18:58.0',200,NULL,NULL,0,NULL,0,'47752',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055943752.out',NULL,NULL,NULL,0,'2022-03-24 01:19:03.0',200,NULL,NULL,0,NULL,0,'49560',NULL),
	 (4,96,'student','961648055949167.out',NULL,NULL,NULL,0,'2022-03-24 01:19:09.0',200,NULL,NULL,0,NULL,0,'48136',NULL),
	 (4,96,'student','961648055954618.out',NULL,NULL,NULL,0,'2022-03-24 01:19:14.0',200,NULL,NULL,0,NULL,0,'48836',NULL),
	 (4,96,'student','961648055960030.out',NULL,NULL,NULL,0,'2022-03-24 01:19:20.0',200,NULL,NULL,0,NULL,0,'44632',NULL),
	 (4,96,'student','961648055965459.out',NULL,NULL,NULL,0,'2022-03-24 01:19:25.0',200,NULL,NULL,0,NULL,0,'50028',NULL),
	 (4,96,'student','961648055970868.out',NULL,NULL,NULL,0,'2022-03-24 01:19:30.0',200,NULL,NULL,0,NULL,0,'49656',NULL),
	 (4,96,'student','961648055976292.out',NULL,NULL,NULL,0,'2022-03-24 01:19:36.0',200,NULL,NULL,0,NULL,0,'49744',NULL),
	 (4,96,'student','961648055981719.out',NULL,NULL,NULL,0,'2022-03-24 01:19:41.0',200,NULL,NULL,0,NULL,0,'44436',NULL),
	 (4,96,'student','961648055987151.out',NULL,NULL,NULL,0,'2022-03-24 01:19:47.0',200,NULL,NULL,0,NULL,0,'49840',NULL),
	 (4,96,'student','961648055992578.out',NULL,NULL,NULL,0,'2022-03-24 01:19:52.0',200,NULL,NULL,0,NULL,0,'37884',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648055997989.out',NULL,NULL,NULL,0,'2022-03-24 01:19:58.0',200,NULL,NULL,0,NULL,0,'6352',NULL),
	 (4,96,'student','961648056003412.out',NULL,NULL,NULL,0,'2022-03-24 01:20:03.0',200,NULL,NULL,0,NULL,0,'41244',NULL),
	 (4,96,'student','961648056008826.out',NULL,NULL,NULL,0,'2022-03-24 01:20:08.0',200,NULL,NULL,0,NULL,0,'29432',NULL),
	 (4,96,'student','961648056014248.out',NULL,NULL,NULL,0,'2022-03-24 01:20:14.0',200,NULL,NULL,0,NULL,0,'48348',NULL),
	 (4,96,'student','961648056019655.out',NULL,NULL,NULL,0,'2022-03-24 01:20:19.0',200,NULL,NULL,0,NULL,0,'49924',NULL),
	 (4,96,'student','961648056025064.out',NULL,NULL,NULL,0,'2022-03-24 01:20:25.0',200,NULL,NULL,0,NULL,0,'48728',NULL),
	 (4,96,'student','961648056030475.out',NULL,NULL,NULL,0,'2022-03-24 01:20:30.0',200,NULL,NULL,0,NULL,0,'36196',NULL),
	 (4,96,'student','961648056035890.out',NULL,NULL,NULL,0,'2022-03-24 01:20:35.0',200,NULL,NULL,0,NULL,0,'46940',NULL),
	 (4,96,'student','961648056041310.out',NULL,NULL,NULL,0,'2022-03-24 01:20:41.0',200,NULL,NULL,0,NULL,0,'49032',NULL),
	 (4,96,'student','961648056046725.out',NULL,NULL,NULL,0,'2022-03-24 01:20:46.0',200,NULL,NULL,0,NULL,0,'49400',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056052129.out',NULL,NULL,NULL,0,'2022-03-24 01:20:52.0',200,NULL,NULL,0,NULL,0,'48808',NULL),
	 (4,96,'student','961648056057531.out',NULL,NULL,NULL,0,'2022-03-24 01:20:57.0',200,NULL,NULL,0,NULL,0,'48004',NULL),
	 (4,96,'student','961648056062945.out',NULL,NULL,NULL,0,'2022-03-24 01:21:03.0',200,NULL,NULL,0,NULL,0,'48552',NULL),
	 (4,96,'student','961648056068352.out',NULL,NULL,NULL,0,'2022-03-24 01:21:08.0',200,NULL,NULL,0,NULL,0,'44216',NULL),
	 (4,96,'student','961648056073755.out',NULL,NULL,NULL,0,'2022-03-24 01:21:13.0',200,NULL,NULL,0,NULL,0,'48068',NULL),
	 (4,96,'student','961648056079164.out',NULL,NULL,NULL,0,'2022-03-24 01:21:19.0',200,NULL,NULL,0,NULL,0,'46036',NULL),
	 (4,96,'student','961648056084582.out',NULL,NULL,NULL,0,'2022-03-24 01:21:24.0',200,NULL,NULL,0,NULL,0,'49572',NULL),
	 (4,96,'student','961648056089956.out',NULL,NULL,NULL,0,'2022-03-24 01:21:30.0',200,NULL,NULL,0,NULL,0,'49524',NULL),
	 (4,96,'student','961648056095334.out',NULL,NULL,NULL,0,'2022-03-24 01:21:35.0',200,NULL,NULL,0,NULL,0,'48664',NULL),
	 (4,96,'student','961648056100746.out',NULL,NULL,NULL,0,'2022-03-24 01:21:40.0',200,NULL,NULL,0,NULL,0,'46668',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056106187.out',NULL,NULL,NULL,0,'2022-03-24 01:21:46.0',200,NULL,NULL,0,NULL,0,'48480',NULL),
	 (4,96,'student','961648056111593.out',NULL,NULL,NULL,0,'2022-03-24 01:21:51.0',200,NULL,NULL,0,NULL,0,'41508',NULL),
	 (4,96,'student','961648056117001.out',NULL,NULL,NULL,0,'2022-03-24 01:21:57.0',200,NULL,NULL,0,NULL,0,'21340',NULL),
	 (4,96,'student','961648056122412.out',NULL,NULL,NULL,0,'2022-03-24 01:22:02.0',200,NULL,NULL,0,NULL,0,'48844',NULL),
	 (4,96,'student','961648056127838.out',NULL,NULL,NULL,0,'2022-03-24 01:22:07.0',200,NULL,NULL,0,NULL,0,'45404',NULL),
	 (4,96,'student','961648056133245.out',NULL,NULL,NULL,0,'2022-03-24 01:22:13.0',200,NULL,NULL,0,NULL,0,'46688',NULL),
	 (4,96,'student','961648056138683.out',NULL,NULL,NULL,0,'2022-03-24 01:22:18.0',200,NULL,NULL,0,NULL,0,'15640',NULL),
	 (4,96,'student','961648056144088.out',NULL,NULL,NULL,0,'2022-03-24 01:22:24.0',200,NULL,NULL,0,NULL,0,'33352',NULL),
	 (4,96,'student','961648056149502.out',NULL,NULL,NULL,0,'2022-03-24 01:22:29.0',200,NULL,NULL,0,NULL,0,'49520',NULL),
	 (4,96,'student','961648056154905.out',NULL,NULL,NULL,0,'2022-03-24 01:22:34.0',200,NULL,NULL,0,NULL,0,'49740',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056160313.out',NULL,NULL,NULL,0,'2022-03-24 01:22:40.0',200,NULL,NULL,0,NULL,0,'49456',NULL),
	 (4,96,'student','961648056165725.out',NULL,NULL,NULL,0,'2022-03-24 01:22:45.0',200,NULL,NULL,0,NULL,0,'49192',NULL),
	 (4,96,'student','961648056171139.out',NULL,NULL,NULL,0,'2022-03-24 01:22:51.0',200,NULL,NULL,0,NULL,0,'13480',NULL),
	 (4,96,'student','961648056176546.out',NULL,NULL,NULL,0,'2022-03-24 01:22:56.0',200,NULL,NULL,0,NULL,0,'48836',NULL),
	 (4,96,'student','961648056181962.out',NULL,NULL,NULL,0,'2022-03-24 01:23:02.0',200,NULL,NULL,0,NULL,0,'49492',NULL),
	 (4,96,'student','961648056187379.out',NULL,NULL,NULL,0,'2022-03-24 01:23:07.0',200,NULL,NULL,0,NULL,0,'46368',NULL),
	 (4,96,'student','961648056192784.out',NULL,NULL,NULL,0,'2022-03-24 01:23:12.0',200,NULL,NULL,0,NULL,0,'49312',NULL),
	 (4,96,'student','961648056198190.out',NULL,NULL,NULL,0,'2022-03-24 01:23:18.0',200,NULL,NULL,0,NULL,0,'48012',NULL),
	 (4,96,'student','961648056203602.out',NULL,NULL,NULL,0,'2022-03-24 01:23:23.0',200,NULL,NULL,0,NULL,0,'48648',NULL),
	 (4,96,'student','961648056209009.out',NULL,NULL,NULL,0,'2022-03-24 01:23:29.0',200,NULL,NULL,0,NULL,0,'47776',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056214421.out',NULL,NULL,NULL,0,'2022-03-24 01:23:34.0',200,NULL,NULL,0,NULL,0,'47592',NULL),
	 (4,96,'student','961648056219826.out',NULL,NULL,NULL,0,'2022-03-24 01:23:39.0',200,NULL,NULL,0,NULL,0,'47796',NULL),
	 (4,96,'student','961648056225253.out',NULL,NULL,NULL,0,'2022-03-24 01:23:45.0',200,NULL,NULL,0,NULL,0,'48104',NULL),
	 (4,96,'student','961648056230654.out',NULL,NULL,NULL,0,'2022-03-24 01:23:50.0',200,NULL,NULL,0,NULL,0,'49792',NULL),
	 (4,96,'student','961648056236093.out',NULL,NULL,NULL,0,'2022-03-24 01:23:56.0',200,NULL,NULL,0,NULL,0,'48056',NULL),
	 (4,96,'student','961648056241497.out',NULL,NULL,NULL,0,'2022-03-24 01:24:01.0',200,NULL,NULL,0,NULL,0,'48500',NULL),
	 (4,96,'student','961648056246958.out',NULL,NULL,NULL,0,'2022-03-24 01:24:07.0',200,NULL,NULL,0,NULL,0,'41312',NULL),
	 (4,96,'student','961648056252366.out',NULL,NULL,NULL,0,'2022-03-24 01:24:12.0',200,NULL,NULL,0,NULL,0,'39044',NULL),
	 (4,96,'student','961648056257771.out',NULL,NULL,NULL,0,'2022-03-24 01:24:17.0',200,NULL,NULL,0,NULL,0,'49084',NULL),
	 (4,96,'student','961648056263178.out',NULL,NULL,NULL,0,'2022-03-24 01:24:23.0',200,NULL,NULL,0,NULL,0,'46304',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056268583.out',NULL,NULL,NULL,0,'2022-03-24 01:24:28.0',200,NULL,NULL,0,NULL,0,'48764',NULL),
	 (4,96,'student','961648056274027.out',NULL,NULL,NULL,0,'2022-03-24 01:24:34.0',200,NULL,NULL,0,NULL,0,'47232',NULL),
	 (4,96,'student','961648056279440.out',NULL,NULL,NULL,0,'2022-03-24 01:24:39.0',200,NULL,NULL,0,NULL,0,'49640',NULL),
	 (4,96,'student','961648056284855.out',NULL,NULL,NULL,0,'2022-03-24 01:24:44.0',200,NULL,NULL,0,NULL,0,'46372',NULL),
	 (4,96,'student','961648056290291.out',NULL,NULL,NULL,0,'2022-03-24 01:24:50.0',200,NULL,NULL,0,NULL,0,'49380',NULL),
	 (4,96,'student','961648056295695.out',NULL,NULL,NULL,0,'2022-03-24 01:24:55.0',200,NULL,NULL,0,NULL,0,'44456',NULL),
	 (4,96,'student','961648056301106.out',NULL,NULL,NULL,0,'2022-03-24 01:25:01.0',200,NULL,NULL,0,NULL,0,'49596',NULL),
	 (4,96,'student','961648056306515.out',NULL,NULL,NULL,0,'2022-03-24 01:25:06.0',200,NULL,NULL,0,NULL,0,'49916',NULL),
	 (4,96,'student','961648056311952.out',NULL,NULL,NULL,0,'2022-03-24 01:25:12.0',200,NULL,NULL,0,NULL,0,'47324',NULL),
	 (4,96,'student','961648056317375.out',NULL,NULL,NULL,0,'2022-03-24 01:25:17.0',200,NULL,NULL,0,NULL,0,'47828',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056322791.out',NULL,NULL,NULL,0,'2022-03-24 01:25:22.0',200,NULL,NULL,0,NULL,0,'36028',NULL),
	 (4,96,'student','961648056328229.out',NULL,NULL,NULL,0,'2022-03-24 01:25:28.0',200,NULL,NULL,0,NULL,0,'49168',NULL),
	 (4,96,'student','961648056333630.out',NULL,NULL,NULL,0,'2022-03-24 01:25:33.0',200,NULL,NULL,0,NULL,0,'49508',NULL),
	 (4,96,'student','961648056339056.out',NULL,NULL,NULL,0,'2022-03-24 01:25:39.0',200,NULL,NULL,0,NULL,0,'48540',NULL),
	 (4,96,'student','961648056344463.out',NULL,NULL,NULL,0,'2022-03-24 01:25:44.0',200,NULL,NULL,0,NULL,0,'46396',NULL),
	 (4,96,'student','961648056349869.out',NULL,NULL,NULL,0,'2022-03-24 01:25:49.0',200,NULL,NULL,0,NULL,0,'44436',NULL),
	 (4,96,'student','961648056355297.out',NULL,NULL,NULL,0,'2022-03-24 01:25:55.0',200,NULL,NULL,0,NULL,0,'35196',NULL),
	 (4,96,'student','961648056360704.out',NULL,NULL,NULL,0,'2022-03-24 01:26:00.0',200,NULL,NULL,0,NULL,0,'48932',NULL),
	 (4,96,'student','961648056366125.out',NULL,NULL,NULL,0,'2022-03-24 01:26:06.0',200,NULL,NULL,0,NULL,0,'47016',NULL),
	 (4,96,'student','961648056371537.out',NULL,NULL,NULL,0,'2022-03-24 01:26:11.0',200,NULL,NULL,0,NULL,0,'49976',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056376973.out',NULL,NULL,NULL,0,'2022-03-24 01:26:17.0',200,NULL,NULL,0,NULL,0,'49704',NULL),
	 (4,96,'student','961648056382383.out',NULL,NULL,NULL,0,'2022-03-24 01:26:22.0',200,NULL,NULL,0,NULL,0,'47760',NULL),
	 (4,96,'student','961648056387824.out',NULL,NULL,NULL,0,'2022-03-24 01:26:27.0',200,NULL,NULL,0,NULL,0,'49964',NULL),
	 (4,96,'student','961648056393245.out',NULL,NULL,NULL,0,'2022-03-24 01:26:33.0',200,NULL,NULL,0,NULL,0,'40732',NULL),
	 (4,96,'student','961648056398656.out',NULL,NULL,NULL,0,'2022-03-24 01:26:38.0',200,NULL,NULL,0,NULL,0,'49668',NULL),
	 (4,96,'student','961648056404082.out',NULL,NULL,NULL,0,'2022-03-24 01:26:44.0',200,NULL,NULL,0,NULL,0,'48164',NULL),
	 (4,96,'student','961648056409485.out',NULL,NULL,NULL,0,'2022-03-24 01:26:49.0',200,NULL,NULL,0,NULL,0,'50064',NULL),
	 (4,96,'student','961648056414915.out',NULL,NULL,NULL,0,'2022-03-24 01:26:54.0',200,NULL,NULL,0,NULL,0,'49324',NULL),
	 (4,96,'student','961648056420316.out',NULL,NULL,NULL,0,'2022-03-24 01:27:00.0',200,NULL,NULL,0,NULL,0,'48244',NULL),
	 (4,96,'student','961648056425728.out',NULL,NULL,NULL,0,'2022-03-24 01:27:05.0',200,NULL,NULL,0,NULL,0,'49096',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056431166.out',NULL,NULL,NULL,0,'2022-03-24 01:27:11.0',200,NULL,NULL,0,NULL,0,'15176',NULL),
	 (4,96,'student','961648056436574.out',NULL,NULL,NULL,0,'2022-03-24 01:27:16.0',200,NULL,NULL,0,NULL,0,'49348',NULL),
	 (4,96,'student','961648056441981.out',NULL,NULL,NULL,0,'2022-03-24 01:27:22.0',200,NULL,NULL,0,NULL,0,'46996',NULL),
	 (4,96,'student','961648056447387.out',NULL,NULL,NULL,0,'2022-03-24 01:27:27.0',200,NULL,NULL,0,NULL,0,'42308',NULL),
	 (4,96,'student','961648056452798.out',NULL,NULL,NULL,0,'2022-03-24 01:27:32.0',200,NULL,NULL,0,NULL,0,'46692',NULL),
	 (4,96,'student','961648056458214.out',NULL,NULL,NULL,0,'2022-03-24 01:27:38.0',200,NULL,NULL,0,NULL,0,'21268',NULL),
	 (4,96,'student','961648056463625.out',NULL,NULL,NULL,0,'2022-03-24 01:27:43.0',200,NULL,NULL,0,NULL,0,'26872',NULL),
	 (4,96,'student','961648056469035.out',NULL,NULL,NULL,0,'2022-03-24 01:27:49.0',200,NULL,NULL,0,NULL,0,'47252',NULL),
	 (4,96,'student','961648056474446.out',NULL,NULL,NULL,0,'2022-03-24 01:27:54.0',200,NULL,NULL,0,NULL,0,'29432',NULL),
	 (4,96,'student','961648056479877.out',NULL,NULL,NULL,0,'2022-03-24 01:27:59.0',200,NULL,NULL,0,NULL,0,'48876',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056485280.out',NULL,NULL,NULL,0,'2022-03-24 01:28:05.0',200,NULL,NULL,0,NULL,0,'47708',NULL),
	 (4,96,'student','961648056490702.out',NULL,NULL,NULL,0,'2022-03-24 01:28:10.0',200,NULL,NULL,0,NULL,0,'10224',NULL),
	 (4,96,'student','961648056496123.out',NULL,NULL,NULL,0,'2022-03-24 01:28:16.0',200,NULL,NULL,0,NULL,0,'48380',NULL),
	 (4,96,'student','961648056501552.out',NULL,NULL,NULL,0,'2022-03-24 01:28:21.0',200,NULL,NULL,0,NULL,0,'48616',NULL),
	 (4,96,'student','961648056506949.out',NULL,NULL,NULL,0,'2022-03-24 01:28:27.0',200,NULL,NULL,0,NULL,0,'46272',NULL),
	 (4,96,'student','961648056512342.out',NULL,NULL,NULL,0,'2022-03-24 01:28:32.0',200,NULL,NULL,0,NULL,0,'50016',NULL),
	 (4,96,'student','961648056517753.out',NULL,NULL,NULL,0,'2022-03-24 01:28:37.0',200,NULL,NULL,0,NULL,0,'9144',NULL),
	 (4,96,'student','961648056523166.out',NULL,NULL,NULL,0,'2022-03-24 01:28:43.0',200,NULL,NULL,0,NULL,0,'48312',NULL),
	 (4,96,'student','961648056528584.out',NULL,NULL,NULL,0,'2022-03-24 01:28:48.0',200,NULL,NULL,0,NULL,0,'45464',NULL),
	 (4,96,'student','961648056533986.out',NULL,NULL,NULL,0,'2022-03-24 01:28:54.0',200,NULL,NULL,0,NULL,0,'47412',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056539374.out',NULL,NULL,NULL,0,'2022-03-24 01:28:59.0',200,NULL,NULL,0,NULL,0,'41028',NULL),
	 (4,96,'student','961648056544777.out',NULL,NULL,NULL,0,'2022-03-24 01:29:04.0',200,NULL,NULL,0,NULL,0,'45668',NULL),
	 (4,96,'student','961648056550168.out',NULL,NULL,NULL,0,'2022-03-24 01:29:10.0',200,NULL,NULL,0,NULL,0,'48304',NULL),
	 (4,96,'student','961648056555561.out',NULL,NULL,NULL,0,'2022-03-24 01:29:15.0',200,NULL,NULL,0,NULL,0,'48196',NULL),
	 (4,96,'student','961648056560962.out',NULL,NULL,NULL,0,'2022-03-24 01:29:21.0',200,NULL,NULL,0,NULL,0,'38500',NULL),
	 (4,96,'student','961648056566368.out',NULL,NULL,NULL,0,'2022-03-24 01:29:26.0',200,NULL,NULL,0,NULL,0,'50116',NULL),
	 (4,96,'student','961648056571753.out',NULL,NULL,NULL,0,'2022-03-24 01:29:31.0',200,NULL,NULL,0,NULL,0,'24992',NULL),
	 (4,96,'student','961648056577170.out',NULL,NULL,NULL,0,'2022-03-24 01:29:37.0',200,NULL,NULL,0,NULL,0,'49600',NULL),
	 (4,96,'student','961648056582558.out',NULL,NULL,NULL,0,'2022-03-24 01:29:42.0',200,NULL,NULL,0,NULL,0,'50012',NULL),
	 (4,96,'student','961648056587981.out',NULL,NULL,NULL,0,'2022-03-24 01:29:48.0',200,NULL,NULL,0,NULL,0,'40688',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056593371.out',NULL,NULL,NULL,0,'2022-03-24 01:29:53.0',200,NULL,NULL,0,NULL,0,'48160',NULL),
	 (4,96,'student','961648056598764.out',NULL,NULL,NULL,0,'2022-03-24 01:29:58.0',200,NULL,NULL,0,NULL,0,'48488',NULL),
	 (4,96,'student','961648056604162.out',NULL,NULL,NULL,0,'2022-03-24 01:30:04.0',200,NULL,NULL,0,NULL,0,'45644',NULL),
	 (4,96,'student','961648056609548.out',NULL,NULL,NULL,0,'2022-03-24 01:30:09.0',200,NULL,NULL,0,NULL,0,'49460',NULL),
	 (4,96,'student','961648056614973.out',NULL,NULL,NULL,0,'2022-03-24 01:30:15.0',200,NULL,NULL,0,NULL,0,'39672',NULL),
	 (4,96,'student','961648056620378.out',NULL,NULL,NULL,0,'2022-03-24 01:30:20.0',200,NULL,NULL,0,NULL,0,'48792',NULL),
	 (4,96,'student','961648056625786.out',NULL,NULL,NULL,0,'2022-03-24 01:30:25.0',200,NULL,NULL,0,NULL,0,'44324',NULL),
	 (4,96,'student','961648056631178.out',NULL,NULL,NULL,0,'2022-03-24 01:30:31.0',200,NULL,NULL,0,NULL,0,'46680',NULL),
	 (4,96,'student','961648056636572.out',NULL,NULL,NULL,0,'2022-03-24 01:30:36.0',200,NULL,NULL,0,NULL,0,'49624',NULL),
	 (4,96,'student','961648056641958.out',NULL,NULL,NULL,0,'2022-03-24 01:30:42.0',200,NULL,NULL,0,NULL,0,'49288',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056647386.out',NULL,NULL,NULL,0,'2022-03-24 01:30:47.0',200,NULL,NULL,0,NULL,0,'49368',NULL),
	 (4,96,'student','961648056652801.out',NULL,NULL,NULL,0,'2022-03-24 01:30:52.0',200,NULL,NULL,0,NULL,0,'46288',NULL),
	 (4,96,'student','961648056658202.out',NULL,NULL,NULL,0,'2022-03-24 01:30:58.0',200,NULL,NULL,0,NULL,0,'48428',NULL),
	 (4,96,'student','961648056663597.out',NULL,NULL,NULL,0,'2022-03-24 01:31:03.0',200,NULL,NULL,0,NULL,0,'49628',NULL),
	 (4,96,'student','961648056668998.out',NULL,NULL,NULL,0,'2022-03-24 01:31:09.0',200,NULL,NULL,0,NULL,0,'48604',NULL),
	 (4,96,'student','961648056674396.out',NULL,NULL,NULL,0,'2022-03-24 01:31:14.0',200,NULL,NULL,0,NULL,0,'28368',NULL),
	 (4,96,'student','961648056679795.out',NULL,NULL,NULL,0,'2022-03-24 01:31:19.0',200,NULL,NULL,0,NULL,0,'45528',NULL),
	 (4,96,'student','961648056685183.out',NULL,NULL,NULL,0,'2022-03-24 01:31:25.0',200,NULL,NULL,0,NULL,0,'38052',NULL),
	 (4,96,'student','961648056690577.out',NULL,NULL,NULL,0,'2022-03-24 01:31:30.0',200,NULL,NULL,0,NULL,0,'43328',NULL),
	 (4,96,'student','961648056695976.out',NULL,NULL,NULL,0,'2022-03-24 01:31:36.0',200,NULL,NULL,0,NULL,0,'48740',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056701388.out',NULL,NULL,NULL,0,'2022-03-24 01:31:41.0',200,NULL,NULL,0,NULL,0,'47348',NULL),
	 (4,96,'student','961648056706782.out',NULL,NULL,NULL,0,'2022-03-24 01:31:46.0',200,NULL,NULL,0,NULL,0,'47484',NULL),
	 (4,96,'student','961648056712179.out',NULL,NULL,NULL,0,'2022-03-24 01:31:52.0',200,NULL,NULL,0,NULL,0,'49848',NULL),
	 (4,96,'student','961648056717596.out',NULL,NULL,NULL,0,'2022-03-24 01:31:57.0',200,NULL,NULL,0,NULL,0,'49512',NULL),
	 (4,96,'student','961648056722982.out',NULL,NULL,NULL,0,'2022-03-24 01:32:03.0',200,NULL,NULL,0,NULL,0,'7836',NULL),
	 (4,96,'student','961648056728417.out',NULL,NULL,NULL,0,'2022-03-24 01:32:08.0',200,NULL,NULL,0,NULL,0,'49892',NULL),
	 (4,96,'student','961648056733826.out',NULL,NULL,NULL,0,'2022-03-24 01:32:13.0',200,NULL,NULL,0,NULL,0,'48784',NULL),
	 (4,96,'student','961648056739222.out',NULL,NULL,NULL,0,'2022-03-24 01:32:19.0',200,NULL,NULL,0,NULL,0,'45124',NULL),
	 (4,96,'student','961648056744608.out',NULL,NULL,NULL,0,'2022-03-24 01:32:24.0',200,NULL,NULL,0,NULL,0,'47456',NULL),
	 (4,96,'student','961648056750004.out',NULL,NULL,NULL,0,'2022-03-24 01:32:30.0',200,NULL,NULL,0,NULL,0,'45152',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056755396.out',NULL,NULL,NULL,0,'2022-03-24 01:32:35.0',200,NULL,NULL,0,NULL,0,'50128',NULL),
	 (4,96,'student','961648056760786.out',NULL,NULL,NULL,0,'2022-03-24 01:32:40.0',200,NULL,NULL,0,NULL,0,'44064',NULL),
	 (4,96,'student','961648056766176.out',NULL,NULL,NULL,0,'2022-03-24 01:32:46.0',200,NULL,NULL,0,NULL,0,'44956',NULL),
	 (4,96,'student','961648056771570.out',NULL,NULL,NULL,0,'2022-03-24 01:32:51.0',200,NULL,NULL,0,NULL,0,'47728',NULL),
	 (4,96,'student','961648056776977.out',NULL,NULL,NULL,0,'2022-03-24 01:32:57.0',200,NULL,NULL,0,NULL,0,'47496',NULL),
	 (4,96,'student','961648056782371.out',NULL,NULL,NULL,0,'2022-03-24 01:33:02.0',200,NULL,NULL,0,NULL,0,'46716',NULL),
	 (4,96,'student','961648056787777.out',NULL,NULL,NULL,0,'2022-03-24 01:33:07.0',200,NULL,NULL,0,NULL,0,'45684',NULL),
	 (4,96,'student','961648056793165.out',NULL,NULL,NULL,0,'2022-03-24 01:33:13.0',200,NULL,NULL,0,NULL,0,'47196',NULL),
	 (4,96,'student','961648056798589.out',NULL,NULL,NULL,0,'2022-03-24 01:33:18.0',200,NULL,NULL,0,NULL,0,'21352',NULL),
	 (4,96,'student','961648056803982.out',NULL,NULL,NULL,0,'2022-03-24 01:33:24.0',200,NULL,NULL,0,NULL,0,'37108',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056809396.out',NULL,NULL,NULL,0,'2022-03-24 01:33:29.0',200,NULL,NULL,0,NULL,0,'38812',NULL),
	 (4,96,'student','961648056814787.out',NULL,NULL,NULL,0,'2022-03-24 01:33:34.0',200,NULL,NULL,0,NULL,0,'5604',NULL),
	 (4,96,'student','961648056820208.out',NULL,NULL,NULL,0,'2022-03-24 01:33:40.0',200,NULL,NULL,0,NULL,0,'46596',NULL),
	 (4,96,'student','961648056825602.out',NULL,NULL,NULL,0,'2022-03-24 01:33:45.0',200,NULL,NULL,0,NULL,0,'42540',NULL),
	 (4,96,'student','961648056831013.out',NULL,NULL,NULL,0,'2022-03-24 01:33:51.0',200,NULL,NULL,0,NULL,0,'4820',NULL),
	 (4,96,'student','961648056836424.out',NULL,NULL,NULL,0,'2022-03-24 01:33:56.0',200,NULL,NULL,0,NULL,0,'4180',NULL),
	 (4,96,'student','961648056841825.out',NULL,NULL,NULL,0,'2022-03-24 01:34:01.0',200,NULL,NULL,0,NULL,0,'33140',NULL),
	 (4,96,'student','961648056847216.out',NULL,NULL,NULL,0,'2022-03-24 01:34:07.0',200,NULL,NULL,0,NULL,0,'44260',NULL),
	 (4,96,'student','961648056852622.out',NULL,NULL,NULL,0,'2022-03-24 01:34:12.0',200,NULL,NULL,0,NULL,0,'49488',NULL),
	 (4,96,'student','961648056858007.out',NULL,NULL,NULL,0,'2022-03-24 01:34:18.0',200,NULL,NULL,0,NULL,0,'39104',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056863413.out',NULL,NULL,NULL,0,'2022-03-24 01:34:23.0',200,NULL,NULL,0,NULL,0,'46476',NULL),
	 (4,96,'student','961648056868806.out',NULL,NULL,NULL,0,'2022-03-24 01:34:28.0',200,NULL,NULL,0,NULL,0,'47188',NULL),
	 (4,96,'student','961648056874211.out',NULL,NULL,NULL,0,'2022-03-24 01:34:34.0',200,NULL,NULL,0,NULL,0,'48828',NULL),
	 (4,96,'student','961648056879734.out',NULL,NULL,NULL,0,'2022-03-24 01:34:39.0',200,NULL,NULL,0,NULL,0,'48232',NULL),
	 (4,96,'student','961648056885162.out',NULL,NULL,NULL,0,'2022-03-24 01:34:45.0',200,NULL,NULL,0,NULL,0,'48912',NULL),
	 (4,96,'student','961648056890548.out',NULL,NULL,NULL,0,'2022-03-24 01:34:50.0',200,NULL,NULL,0,NULL,0,'48992',NULL),
	 (4,96,'student','961648056895933.out',NULL,NULL,NULL,0,'2022-03-24 01:34:55.0',200,NULL,NULL,0,NULL,0,'49248',NULL),
	 (4,96,'student','961648056901320.out',NULL,NULL,NULL,0,'2022-03-24 01:35:01.0',200,NULL,NULL,0,NULL,0,'49804',NULL),
	 (4,96,'student','961648056906746.out',NULL,NULL,NULL,0,'2022-03-24 01:35:06.0',200,NULL,NULL,0,NULL,0,'32684',NULL),
	 (4,96,'student','961648056912146.out',NULL,NULL,NULL,0,'2022-03-24 01:35:12.0',200,NULL,NULL,0,NULL,0,'48212',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056917538.out',NULL,NULL,NULL,0,'2022-03-24 01:35:17.0',200,NULL,NULL,0,NULL,0,'50040',NULL),
	 (4,96,'student','961648056922930.out',NULL,NULL,NULL,0,'2022-03-24 01:35:22.0',200,NULL,NULL,0,NULL,0,'49204',NULL),
	 (4,96,'student','961648056928328.out',NULL,NULL,NULL,0,'2022-03-24 01:35:28.0',200,NULL,NULL,0,NULL,0,'42520',NULL),
	 (4,96,'student','961648056933722.out',NULL,NULL,NULL,0,'2022-03-24 01:35:33.0',200,NULL,NULL,0,NULL,0,'45740',NULL),
	 (4,96,'student','961648056939114.out',NULL,NULL,NULL,0,'2022-03-24 01:35:39.0',200,NULL,NULL,0,NULL,0,'49180',NULL),
	 (4,96,'student','961648056944501.out',NULL,NULL,NULL,0,'2022-03-24 01:35:44.0',200,NULL,NULL,0,NULL,0,'47672',NULL),
	 (4,96,'student','961648056949909.out',NULL,NULL,NULL,0,'2022-03-24 01:35:49.0',200,NULL,NULL,0,NULL,0,'49176',NULL),
	 (4,96,'student','961648056955307.out',NULL,NULL,NULL,0,'2022-03-24 01:35:55.0',200,NULL,NULL,0,NULL,0,'23932',NULL),
	 (4,96,'student','961648056960698.out',NULL,NULL,NULL,0,'2022-03-24 01:36:00.0',200,NULL,NULL,0,NULL,0,'49580',NULL),
	 (4,96,'student','961648056966112.out',NULL,NULL,NULL,0,'2022-03-24 01:36:06.0',200,NULL,NULL,0,NULL,0,'2640',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648056971538.out',NULL,NULL,NULL,0,'2022-03-24 01:36:11.0',200,NULL,NULL,0,NULL,0,'48864',NULL),
	 (4,96,'student','961648056976942.out',NULL,NULL,NULL,0,'2022-03-24 01:36:17.0',200,NULL,NULL,0,NULL,0,'48024',NULL),
	 (4,96,'student','961648056982330.out',NULL,NULL,NULL,0,'2022-03-24 01:36:22.0',200,NULL,NULL,0,NULL,0,'49172',NULL),
	 (4,96,'student','961648056987727.out',NULL,NULL,NULL,0,'2022-03-24 01:36:27.0',200,NULL,NULL,0,NULL,0,'49504',NULL),
	 (4,96,'student','961648056993138.out',NULL,NULL,NULL,0,'2022-03-24 01:36:33.0',200,NULL,NULL,0,NULL,0,'18400',NULL),
	 (4,96,'student','961648056998526.out',NULL,NULL,NULL,0,'2022-03-24 01:36:38.0',200,NULL,NULL,0,NULL,0,'48100',NULL),
	 (4,96,'student','961648057003927.out',NULL,NULL,NULL,0,'2022-03-24 01:36:43.0',200,NULL,NULL,0,NULL,0,'2824',NULL),
	 (4,96,'student','961648057009315.out',NULL,NULL,NULL,0,'2022-03-24 01:36:49.0',200,NULL,NULL,0,NULL,0,'47420',NULL),
	 (4,96,'student','961648057014745.out',NULL,NULL,NULL,0,'2022-03-24 01:36:54.0',200,NULL,NULL,0,NULL,0,'45120',NULL),
	 (4,96,'student','961648057020136.out',NULL,NULL,NULL,0,'2022-03-24 01:37:00.0',200,NULL,NULL,0,NULL,0,'49888',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057025528.out',NULL,NULL,NULL,0,'2022-03-24 01:37:05.0',200,NULL,NULL,0,NULL,0,'49840',NULL),
	 (4,96,'student','961648057030923.out',NULL,NULL,NULL,0,'2022-03-24 01:37:10.0',200,NULL,NULL,0,NULL,0,'49620',NULL),
	 (4,96,'student','961648057036296.out',NULL,NULL,NULL,0,'2022-03-24 01:37:16.0',200,NULL,NULL,0,NULL,0,'46464',NULL),
	 (4,96,'student','961648057041692.out',NULL,NULL,NULL,0,'2022-03-24 01:37:21.0',200,NULL,NULL,0,NULL,0,'50148',NULL),
	 (4,96,'student','961648057047086.out',NULL,NULL,NULL,0,'2022-03-24 01:37:27.0',200,NULL,NULL,0,NULL,0,'50076',NULL),
	 (4,96,'student','961648057052476.out',NULL,NULL,NULL,0,'2022-03-24 01:37:32.0',200,NULL,NULL,0,NULL,0,'49276',NULL),
	 (4,96,'student','961648057057901.out',NULL,NULL,NULL,0,'2022-03-24 01:37:37.0',200,NULL,NULL,0,NULL,0,'49308',NULL),
	 (4,96,'student','961648057063289.out',NULL,NULL,NULL,0,'2022-03-24 01:37:43.0',200,NULL,NULL,0,NULL,0,'49048',NULL),
	 (4,96,'student','961648057068745.out',NULL,NULL,NULL,0,'2022-03-24 01:37:48.0',200,NULL,NULL,0,NULL,0,'48736',NULL),
	 (4,96,'student','961648057074151.out',NULL,NULL,NULL,0,'2022-03-24 01:37:54.0',200,NULL,NULL,0,NULL,0,'50140',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057079578.out',NULL,NULL,NULL,0,'2022-03-24 01:37:59.0',200,NULL,NULL,0,NULL,0,'45616',NULL),
	 (4,96,'student','961648057084991.out',NULL,NULL,NULL,0,'2022-03-24 01:38:05.0',200,NULL,NULL,0,NULL,0,'48424',NULL),
	 (4,96,'student','961648057090404.out',NULL,NULL,NULL,0,'2022-03-24 01:38:10.0',200,NULL,NULL,0,NULL,0,'35332',NULL),
	 (4,96,'student','961648057095802.out',NULL,NULL,NULL,0,'2022-03-24 01:38:15.0',200,NULL,NULL,0,NULL,0,'36020',NULL),
	 (4,96,'student','961648057101210.out',NULL,NULL,NULL,0,'2022-03-24 01:38:21.0',200,NULL,NULL,0,NULL,0,'49028',NULL),
	 (4,96,'student','961648057106620.out',NULL,NULL,NULL,0,'2022-03-24 01:38:26.0',200,NULL,NULL,0,NULL,0,'45336',NULL),
	 (4,96,'student','961648057112048.out',NULL,NULL,NULL,0,'2022-03-24 01:38:32.0',200,NULL,NULL,0,NULL,0,'48660',NULL),
	 (4,96,'student','961648057117457.out',NULL,NULL,NULL,0,'2022-03-24 01:38:37.0',200,NULL,NULL,0,NULL,0,'25512',NULL),
	 (4,96,'student','961648057122866.out',NULL,NULL,NULL,0,'2022-03-24 01:38:42.0',200,NULL,NULL,0,NULL,0,'23508',NULL),
	 (4,96,'student','961648057128261.out',NULL,NULL,NULL,0,'2022-03-24 01:38:48.0',200,NULL,NULL,0,NULL,0,'47932',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057133659.out',NULL,NULL,NULL,0,'2022-03-24 01:38:53.0',200,NULL,NULL,0,NULL,0,'38652',NULL),
	 (4,96,'student','961648057139080.out',NULL,NULL,NULL,0,'2022-03-24 01:38:59.0',200,NULL,NULL,0,NULL,0,'40032',NULL),
	 (4,96,'student','961648057144512.out',NULL,NULL,NULL,0,'2022-03-24 01:39:04.0',200,NULL,NULL,0,NULL,0,'49564',NULL),
	 (4,96,'student','961648057149915.out',NULL,NULL,NULL,0,'2022-03-24 01:39:09.0',200,NULL,NULL,0,NULL,0,'34792',NULL),
	 (4,96,'student','961648057155312.out',NULL,NULL,NULL,0,'2022-03-24 01:39:15.0',200,NULL,NULL,0,NULL,0,'29304',NULL),
	 (4,96,'student','961648057160849.out',NULL,NULL,NULL,0,'2022-03-24 01:39:20.0',200,NULL,NULL,0,NULL,0,'46624',NULL),
	 (4,96,'student','961648057166259.out',NULL,NULL,NULL,0,'2022-03-24 01:39:26.0',200,NULL,NULL,0,NULL,0,'50044',NULL),
	 (4,96,'student','961648057171666.out',NULL,NULL,NULL,0,'2022-03-24 01:39:31.0',200,NULL,NULL,0,NULL,0,'49340',NULL),
	 (4,96,'student','961648057177086.out',NULL,NULL,NULL,0,'2022-03-24 01:39:37.0',200,NULL,NULL,0,NULL,0,'48988',NULL),
	 (4,96,'student','961648057182515.out',NULL,NULL,NULL,0,'2022-03-24 01:39:42.0',200,NULL,NULL,0,NULL,0,'48292',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057187952.out',NULL,NULL,NULL,0,'2022-03-24 01:39:48.0',200,NULL,NULL,0,NULL,0,'46664',NULL),
	 (4,96,'student','961648057193360.out',NULL,NULL,NULL,0,'2022-03-24 01:39:53.0',200,NULL,NULL,0,NULL,0,'48644',NULL),
	 (4,96,'student','961648057198764.out',NULL,NULL,NULL,0,'2022-03-24 01:39:58.0',200,NULL,NULL,0,NULL,0,'46796',NULL),
	 (4,96,'student','961648057204160.out',NULL,NULL,NULL,0,'2022-03-24 01:40:04.0',200,NULL,NULL,0,NULL,0,'46440',NULL),
	 (4,96,'student','961648057209563.out',NULL,NULL,NULL,0,'2022-03-24 01:40:09.0',200,NULL,NULL,0,NULL,0,'49388',NULL),
	 (4,96,'student','961648057214965.out',NULL,NULL,NULL,0,'2022-03-24 01:40:15.0',200,NULL,NULL,0,NULL,0,'47708',NULL),
	 (4,96,'student','961648057220377.out',NULL,NULL,NULL,0,'2022-03-24 01:40:20.0',200,NULL,NULL,0,NULL,0,'50000',NULL),
	 (4,96,'student','961648057225777.out',NULL,NULL,NULL,0,'2022-03-24 01:40:25.0',200,NULL,NULL,0,NULL,0,'25512',NULL),
	 (4,96,'student','961648057231202.out',NULL,NULL,NULL,0,'2022-03-24 01:40:31.0',200,NULL,NULL,0,NULL,0,'44424',NULL),
	 (4,96,'student','961648057236599.out',NULL,NULL,NULL,0,'2022-03-24 01:40:36.0',200,NULL,NULL,0,NULL,0,'45668',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057242023.out',NULL,NULL,NULL,0,'2022-03-24 01:40:42.0',200,NULL,NULL,0,NULL,0,'42448',NULL),
	 (4,96,'student','961648057247421.out',NULL,NULL,NULL,0,'2022-03-24 01:40:47.0',200,NULL,NULL,0,NULL,0,'21932',NULL),
	 (4,96,'student','961648057252859.out',NULL,NULL,NULL,0,'2022-03-24 01:40:52.0',200,NULL,NULL,0,NULL,0,'5604',NULL),
	 (4,96,'student','961648057258260.out',NULL,NULL,NULL,0,'2022-03-24 01:40:58.0',200,NULL,NULL,0,NULL,0,'49504',NULL),
	 (4,96,'student','961648057263658.out',NULL,NULL,NULL,0,'2022-03-24 01:41:03.0',200,NULL,NULL,0,NULL,0,'49292',NULL),
	 (4,96,'student','961648057269058.out',NULL,NULL,NULL,0,'2022-03-24 01:41:09.0',200,NULL,NULL,0,NULL,0,'47576',NULL),
	 (4,96,'student','961648057274457.out',NULL,NULL,NULL,0,'2022-03-24 01:41:14.0',200,NULL,NULL,0,NULL,0,'46372',NULL),
	 (4,96,'student','961648057279857.out',NULL,NULL,NULL,0,'2022-03-24 01:41:19.0',200,NULL,NULL,0,NULL,0,'48844',NULL),
	 (4,96,'student','961648057285266.out',NULL,NULL,NULL,0,'2022-03-24 01:41:25.0',200,NULL,NULL,0,NULL,0,'21340',NULL),
	 (4,96,'student','961648057290665.out',NULL,NULL,NULL,0,'2022-03-24 01:41:30.0',200,NULL,NULL,0,NULL,0,'49556',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057296080.out',NULL,NULL,NULL,0,'2022-03-24 01:41:36.0',200,NULL,NULL,0,NULL,0,'46256',NULL),
	 (4,96,'student','961648057301476.out',NULL,NULL,NULL,0,'2022-03-24 01:41:41.0',200,NULL,NULL,0,NULL,0,'48624',NULL),
	 (4,96,'student','961648057306905.out',NULL,NULL,NULL,0,'2022-03-24 01:41:46.0',200,NULL,NULL,0,NULL,0,'41048',NULL),
	 (4,96,'student','961648057312315.out',NULL,NULL,NULL,0,'2022-03-24 01:41:52.0',200,NULL,NULL,0,NULL,0,'48876',NULL),
	 (4,96,'student','961648057317723.out',NULL,NULL,NULL,0,'2022-03-24 01:41:57.0',200,NULL,NULL,0,NULL,0,'44916',NULL),
	 (4,96,'student','961648057323129.out',NULL,NULL,NULL,0,'2022-03-24 01:42:03.0',200,NULL,NULL,0,NULL,0,'5364',NULL),
	 (4,96,'student','961648057328543.out',NULL,NULL,NULL,0,'2022-03-24 01:42:08.0',200,NULL,NULL,0,NULL,0,'49692',NULL),
	 (4,96,'student','961648057333940.out',NULL,NULL,NULL,0,'2022-03-24 01:42:14.0',200,NULL,NULL,0,NULL,0,'46212',NULL),
	 (4,96,'student','961648057339376.out',NULL,NULL,NULL,0,'2022-03-24 01:42:19.0',200,NULL,NULL,0,NULL,0,'47508',NULL),
	 (4,96,'student','961648057344778.out',NULL,NULL,NULL,0,'2022-03-24 01:42:24.0',200,NULL,NULL,0,NULL,0,'48136',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057350177.out',NULL,NULL,NULL,0,'2022-03-24 01:42:30.0',200,NULL,NULL,0,NULL,0,'44260',NULL),
	 (4,96,'student','961648057355583.out',NULL,NULL,NULL,0,'2022-03-24 01:42:35.0',200,NULL,NULL,0,NULL,0,'49580',NULL),
	 (4,96,'student','961648057361016.out',NULL,NULL,NULL,0,'2022-03-24 01:42:41.0',200,NULL,NULL,0,NULL,0,'47932',NULL),
	 (4,96,'student','961648057366410.out',NULL,NULL,NULL,0,'2022-03-24 01:42:46.0',200,NULL,NULL,0,NULL,0,'48972',NULL),
	 (4,96,'student','961648057371806.out',NULL,NULL,NULL,0,'2022-03-24 01:42:51.0',200,NULL,NULL,0,NULL,0,'48460',NULL),
	 (4,96,'student','961648057377225.out',NULL,NULL,NULL,0,'2022-03-24 01:42:57.0',200,NULL,NULL,0,NULL,0,'48272',NULL),
	 (4,96,'student','961648057382653.out',NULL,NULL,NULL,0,'2022-03-24 01:43:02.0',200,NULL,NULL,0,NULL,0,'48184',NULL),
	 (4,96,'student','961648057388048.out',NULL,NULL,NULL,0,'2022-03-24 01:43:08.0',200,NULL,NULL,0,NULL,0,'48444',NULL),
	 (4,96,'student','961648057393442.out',NULL,NULL,NULL,0,'2022-03-24 01:43:13.0',200,NULL,NULL,0,NULL,0,'44340',NULL),
	 (4,96,'student','961648057398832.out',NULL,NULL,NULL,0,'2022-03-24 01:43:18.0',200,NULL,NULL,0,NULL,0,'40032',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057404236.out',NULL,NULL,NULL,0,'2022-03-24 01:43:24.0',200,NULL,NULL,0,NULL,0,'49800',NULL),
	 (4,96,'student','961648057409631.out',NULL,NULL,NULL,0,'2022-03-24 01:43:29.0',200,NULL,NULL,0,NULL,0,'45028',NULL),
	 (4,96,'student','961648057415032.out',NULL,NULL,NULL,0,'2022-03-24 01:43:35.0',200,NULL,NULL,0,NULL,0,'47684',NULL),
	 (4,96,'student','961648057420426.out',NULL,NULL,NULL,0,'2022-03-24 01:43:40.0',200,NULL,NULL,0,NULL,0,'48428',NULL),
	 (4,96,'student','961648057425840.out',NULL,NULL,NULL,0,'2022-03-24 01:43:45.0',200,NULL,NULL,0,NULL,0,'38876',NULL),
	 (4,96,'student','961648057431240.out',NULL,NULL,NULL,0,'2022-03-24 01:43:51.0',200,NULL,NULL,0,NULL,0,'49224',NULL),
	 (4,96,'student','961648057436641.out',NULL,NULL,NULL,0,'2022-03-24 01:43:56.0',200,NULL,NULL,0,NULL,0,'48824',NULL),
	 (4,96,'student','961648057442038.out',NULL,NULL,NULL,0,'2022-03-24 01:44:02.0',200,NULL,NULL,0,NULL,0,'46688',NULL),
	 (4,96,'student','961648057447473.out',NULL,NULL,NULL,0,'2022-03-24 01:44:07.0',200,NULL,NULL,0,NULL,0,'47292',NULL),
	 (4,96,'student','961648057452870.out',NULL,NULL,NULL,0,'2022-03-24 01:44:12.0',200,NULL,NULL,0,NULL,0,'42132',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057458284.out',NULL,NULL,NULL,0,'2022-03-24 01:44:18.0',200,NULL,NULL,0,NULL,0,'49540',NULL),
	 (4,96,'student','961648057463678.out',NULL,NULL,NULL,0,'2022-03-24 01:44:23.0',200,NULL,NULL,0,NULL,0,'50040',NULL),
	 (4,96,'student','961648057469109.out',NULL,NULL,NULL,0,'2022-03-24 01:44:29.0',200,NULL,NULL,0,NULL,0,'49016',NULL),
	 (4,96,'student','961648057474502.out',NULL,NULL,NULL,0,'2022-03-24 01:44:34.0',200,NULL,NULL,0,NULL,0,'49208',NULL),
	 (4,96,'student','961648057479895.out',NULL,NULL,NULL,0,'2022-03-24 01:44:39.0',200,NULL,NULL,0,NULL,0,'47868',NULL),
	 (4,96,'student','961648057485322.out',NULL,NULL,NULL,0,'2022-03-24 01:44:45.0',200,NULL,NULL,0,NULL,0,'41088',NULL),
	 (4,96,'student','961648057490728.out',NULL,NULL,NULL,0,'2022-03-24 01:44:50.0',200,NULL,NULL,0,NULL,0,'48740',NULL),
	 (4,96,'student','961648057496127.out',NULL,NULL,NULL,0,'2022-03-24 01:44:56.0',200,NULL,NULL,0,NULL,0,'42160',NULL),
	 (4,96,'student','961648057501524.out',NULL,NULL,NULL,0,'2022-03-24 01:45:01.0',200,NULL,NULL,0,NULL,0,'46912',NULL),
	 (4,96,'student','961648057506943.out',NULL,NULL,NULL,0,'2022-03-24 01:45:07.0',200,NULL,NULL,0,NULL,0,'45684',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057512347.out',NULL,NULL,NULL,0,'2022-03-24 01:45:12.0',200,NULL,NULL,0,NULL,0,'49168',NULL),
	 (4,96,'student','961648057517757.out',NULL,NULL,NULL,0,'2022-03-24 01:45:17.0',200,NULL,NULL,0,NULL,0,'48744',NULL),
	 (4,96,'student','961648057523147.out',NULL,NULL,NULL,0,'2022-03-24 01:45:23.0',200,NULL,NULL,0,NULL,0,'48256',NULL),
	 (4,96,'student','961648057528540.out',NULL,NULL,NULL,0,'2022-03-24 01:45:28.0',200,NULL,NULL,0,NULL,0,'46660',NULL),
	 (4,96,'student','961648057533950.out',NULL,NULL,NULL,0,'2022-03-24 01:45:34.0',200,NULL,NULL,0,NULL,0,'49056',NULL),
	 (4,96,'student','961648057539353.out',NULL,NULL,NULL,0,'2022-03-24 01:45:39.0',200,NULL,NULL,0,NULL,0,'46784',NULL),
	 (4,96,'student','961648057544750.out',NULL,NULL,NULL,0,'2022-03-24 01:45:44.0',200,NULL,NULL,0,NULL,0,'45644',NULL),
	 (4,96,'student','961648057550156.out',NULL,NULL,NULL,0,'2022-03-24 01:45:50.0',200,NULL,NULL,0,NULL,0,'43292',NULL),
	 (4,96,'student','961648057555548.out',NULL,NULL,NULL,0,'2022-03-24 01:45:55.0',200,NULL,NULL,0,NULL,0,'48280',NULL),
	 (4,96,'student','961648057560975.out',NULL,NULL,NULL,0,'2022-03-24 01:46:01.0',200,NULL,NULL,0,NULL,0,'46488',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057566408.out',NULL,NULL,NULL,0,'2022-03-24 01:46:06.0',200,NULL,NULL,0,NULL,0,'37772',NULL),
	 (4,96,'student','961648057571825.out',NULL,NULL,NULL,0,'2022-03-24 01:46:11.0',200,NULL,NULL,0,NULL,0,'42356',NULL),
	 (4,96,'student','961648057577231.out',NULL,NULL,NULL,0,'2022-03-24 01:46:17.0',200,NULL,NULL,0,NULL,0,'47016',NULL),
	 (4,96,'student','961648057582639.out',NULL,NULL,NULL,0,'2022-03-24 01:46:22.0',200,NULL,NULL,0,NULL,0,'49840',NULL),
	 (4,96,'student','961648057588046.out',NULL,NULL,NULL,0,'2022-03-24 01:46:28.0',200,NULL,NULL,0,NULL,0,'48932',NULL),
	 (4,96,'student','961648057593450.out',NULL,NULL,NULL,0,'2022-03-24 01:46:33.0',200,NULL,NULL,0,NULL,0,'47936',NULL),
	 (4,96,'student','961648057598860.out',NULL,NULL,NULL,0,'2022-03-24 01:46:38.0',200,NULL,NULL,0,NULL,0,'46288',NULL),
	 (4,96,'student','961648057604257.out',NULL,NULL,NULL,0,'2022-03-24 01:46:44.0',200,NULL,NULL,0,NULL,0,'48836',NULL),
	 (4,96,'student','961648057609649.out',NULL,NULL,NULL,0,'2022-03-24 01:46:49.0',200,NULL,NULL,0,NULL,0,'48400',NULL),
	 (4,96,'student','961648057615085.out',NULL,NULL,NULL,0,'2022-03-24 01:46:55.0',200,NULL,NULL,0,NULL,0,'44160',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057620484.out',NULL,NULL,NULL,0,'2022-03-24 01:47:00.0',200,NULL,NULL,0,NULL,0,'48636',NULL),
	 (4,96,'student','961648057625884.out',NULL,NULL,NULL,0,'2022-03-24 01:47:05.0',200,NULL,NULL,0,NULL,0,'49992',NULL),
	 (4,96,'student','961648057631279.out',NULL,NULL,NULL,0,'2022-03-24 01:47:11.0',200,NULL,NULL,0,NULL,0,'46312',NULL),
	 (4,96,'student','961648057636676.out',NULL,NULL,NULL,0,'2022-03-24 01:47:16.0',200,NULL,NULL,0,NULL,0,'49364',NULL),
	 (4,96,'student','961648057642088.out',NULL,NULL,NULL,0,'2022-03-24 01:47:22.0',200,NULL,NULL,0,NULL,0,'50084',NULL),
	 (4,96,'student','961648057647502.out',NULL,NULL,NULL,0,'2022-03-24 01:47:27.0',200,NULL,NULL,0,NULL,0,'48572',NULL),
	 (4,96,'student','961648057652897.out',NULL,NULL,NULL,0,'2022-03-24 01:47:32.0',200,NULL,NULL,0,NULL,0,'49664',NULL),
	 (4,96,'student','961648057658292.out',NULL,NULL,NULL,0,'2022-03-24 01:47:38.0',200,NULL,NULL,0,NULL,0,'47396',NULL),
	 (4,96,'student','961648057663720.out',NULL,NULL,NULL,0,'2022-03-24 01:47:43.0',200,NULL,NULL,0,NULL,0,'38500',NULL),
	 (4,96,'student','961648057669120.out',NULL,NULL,NULL,0,'2022-03-24 01:47:49.0',200,NULL,NULL,0,NULL,0,'49684',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057674541.out',NULL,NULL,NULL,0,'2022-03-24 01:47:54.0',200,NULL,NULL,0,NULL,0,'40196',NULL),
	 (4,96,'student','961648057679936.out',NULL,NULL,NULL,0,'2022-03-24 01:48:00.0',200,NULL,NULL,0,NULL,0,'48452',NULL),
	 (4,96,'student','961648057685354.out',NULL,NULL,NULL,0,'2022-03-24 01:48:05.0',200,NULL,NULL,0,NULL,0,'50064',NULL),
	 (4,96,'student','961648057690759.out',NULL,NULL,NULL,0,'2022-03-24 01:48:10.0',200,NULL,NULL,0,NULL,0,'27192',NULL),
	 (4,96,'student','961648057696179.out',NULL,NULL,NULL,0,'2022-03-24 01:48:16.0',200,NULL,NULL,0,NULL,0,'46432',NULL),
	 (4,96,'student','961648057701577.out',NULL,NULL,NULL,0,'2022-03-24 01:48:21.0',200,NULL,NULL,0,NULL,0,'46996',NULL),
	 (4,96,'student','961648057706996.out',NULL,NULL,NULL,0,'2022-03-24 01:48:27.0',200,NULL,NULL,0,NULL,0,'50088',NULL),
	 (4,96,'student','961648057712392.out',NULL,NULL,NULL,0,'2022-03-24 01:48:32.0',200,NULL,NULL,0,NULL,0,'46476',NULL),
	 (4,96,'student','961648057717807.out',NULL,NULL,NULL,0,'2022-03-24 01:48:37.0',200,NULL,NULL,0,NULL,0,'49828',NULL),
	 (4,96,'student','961648057723211.out',NULL,NULL,NULL,0,'2022-03-24 01:48:43.0',200,NULL,NULL,0,NULL,0,'47156',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057728606.out',NULL,NULL,NULL,0,'2022-03-24 01:48:48.0',200,NULL,NULL,0,NULL,0,'49888',NULL),
	 (4,96,'student','961648057734015.out',NULL,NULL,NULL,0,'2022-03-24 01:48:54.0',200,NULL,NULL,0,NULL,0,'48576',NULL),
	 (4,96,'student','961648057739411.out',NULL,NULL,NULL,0,'2022-03-24 01:48:59.0',200,NULL,NULL,0,NULL,0,'47788',NULL),
	 (4,96,'student','961648057744801.out',NULL,NULL,NULL,0,'2022-03-24 01:49:04.0',200,NULL,NULL,0,NULL,0,'11524',NULL),
	 (4,96,'student','961648057750194.out',NULL,NULL,NULL,0,'2022-03-24 01:49:10.0',200,NULL,NULL,0,NULL,0,'49572',NULL),
	 (4,96,'student','961648057755600.out',NULL,NULL,NULL,0,'2022-03-24 01:49:15.0',200,NULL,NULL,0,NULL,0,'48292',NULL),
	 (4,96,'student','961648057761029.out',NULL,NULL,NULL,0,'2022-03-24 01:49:21.0',200,NULL,NULL,0,NULL,0,'42908',NULL),
	 (4,96,'student','961648057766420.out',NULL,NULL,NULL,0,'2022-03-24 01:49:26.0',200,NULL,NULL,0,NULL,0,'48916',NULL),
	 (4,96,'student','961648057771827.out',NULL,NULL,NULL,0,'2022-03-24 01:49:31.0',200,NULL,NULL,0,NULL,0,'49984',NULL),
	 (4,96,'student','961648057777221.out',NULL,NULL,NULL,0,'2022-03-24 01:49:37.0',200,NULL,NULL,0,NULL,0,'49188',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057782617.out',NULL,NULL,NULL,0,'2022-03-24 01:49:42.0',200,NULL,NULL,0,NULL,0,'47512',NULL),
	 (4,96,'student','961648057788024.out',NULL,NULL,NULL,0,'2022-03-24 01:49:48.0',200,NULL,NULL,0,NULL,0,'33708',NULL),
	 (4,96,'student','961648057793418.out',NULL,NULL,NULL,0,'2022-03-24 01:49:53.0',200,NULL,NULL,0,NULL,0,'49452',NULL),
	 (4,96,'student','961648057798824.out',NULL,NULL,NULL,0,'2022-03-24 01:49:58.0',200,NULL,NULL,0,NULL,0,'10844',NULL),
	 (4,96,'student','961648057804221.out',NULL,NULL,NULL,0,'2022-03-24 01:50:04.0',200,NULL,NULL,0,NULL,0,'47372',NULL),
	 (4,96,'student','961648057809630.out',NULL,NULL,NULL,0,'2022-03-24 01:50:09.0',200,NULL,NULL,0,NULL,0,'47624',NULL),
	 (4,96,'student','961648057815025.out',NULL,NULL,NULL,0,'2022-03-24 01:50:15.0',200,NULL,NULL,0,NULL,0,'48764',NULL),
	 (4,96,'student','961648057820404.out',NULL,NULL,NULL,0,'2022-03-24 01:50:20.0',200,NULL,NULL,0,NULL,0,'48852',NULL),
	 (4,96,'student','961648057825804.out',NULL,NULL,NULL,0,'2022-03-24 01:50:25.0',200,NULL,NULL,0,NULL,0,'18268',NULL),
	 (4,96,'student','961648057831213.out',NULL,NULL,NULL,0,'2022-03-24 01:50:31.0',200,NULL,NULL,0,NULL,0,'48580',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057836614.out',NULL,NULL,NULL,0,'2022-03-24 01:50:36.0',200,NULL,NULL,0,NULL,0,'48492',NULL),
	 (4,96,'student','961648057842007.out',NULL,NULL,NULL,0,'2022-03-24 01:50:42.0',200,NULL,NULL,0,NULL,0,'45608',NULL),
	 (4,96,'student','961648057847399.out',NULL,NULL,NULL,0,'2022-03-24 01:50:47.0',200,NULL,NULL,0,NULL,0,'47784',NULL),
	 (4,96,'student','961648057852807.out',NULL,NULL,NULL,0,'2022-03-24 01:50:52.0',200,NULL,NULL,0,NULL,0,'36484',NULL),
	 (4,96,'student','961648057858218.out',NULL,NULL,NULL,0,'2022-03-24 01:50:58.0',200,NULL,NULL,0,NULL,0,'48808',NULL),
	 (4,96,'student','961648057863617.out',NULL,NULL,NULL,0,'2022-03-24 01:51:03.0',200,NULL,NULL,0,NULL,0,'47716',NULL),
	 (4,96,'student','961648057869015.out',NULL,NULL,NULL,0,'2022-03-24 01:51:09.0',200,NULL,NULL,0,NULL,0,'39820',NULL),
	 (4,96,'student','961648057874411.out',NULL,NULL,NULL,0,'2022-03-24 01:51:14.0',200,NULL,NULL,0,NULL,0,'39216',NULL),
	 (4,96,'student','961648057879809.out',NULL,NULL,NULL,0,'2022-03-24 01:51:19.0',200,NULL,NULL,0,NULL,0,'49340',NULL),
	 (4,96,'student','961648057885198.out',NULL,NULL,NULL,0,'2022-03-24 01:51:25.0',200,NULL,NULL,0,NULL,0,'14016',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057890591.out',NULL,NULL,NULL,0,'2022-03-24 01:51:30.0',200,NULL,NULL,0,NULL,0,'45428',NULL),
	 (4,96,'student','961648057895985.out',NULL,NULL,NULL,0,'2022-03-24 01:51:36.0',200,NULL,NULL,0,NULL,0,'50036',NULL),
	 (4,96,'student','961648057901394.out',NULL,NULL,NULL,0,'2022-03-24 01:51:41.0',200,NULL,NULL,0,NULL,0,'47796',NULL),
	 (4,96,'student','961648057906796.out',NULL,NULL,NULL,0,'2022-03-24 01:51:46.0',200,NULL,NULL,0,NULL,0,'49116',NULL),
	 (4,96,'student','961648057912204.out',NULL,NULL,NULL,0,'2022-03-24 01:51:52.0',200,NULL,NULL,0,NULL,0,'14224',NULL),
	 (4,96,'student','961648057917618.out',NULL,NULL,NULL,0,'2022-03-24 01:51:57.0',200,NULL,NULL,0,NULL,0,'49672',NULL),
	 (4,96,'student','961648057923017.out',NULL,NULL,NULL,0,'2022-03-24 01:52:03.0',200,NULL,NULL,0,NULL,0,'48664',NULL),
	 (4,96,'student','961648057928423.out',NULL,NULL,NULL,0,'2022-03-24 01:52:08.0',200,NULL,NULL,0,NULL,0,'47084',NULL),
	 (4,96,'student','961648057933831.out',NULL,NULL,NULL,0,'2022-03-24 01:52:13.0',200,NULL,NULL,0,NULL,0,'47088',NULL),
	 (4,96,'student','961648057939241.out',NULL,NULL,NULL,0,'2022-03-24 01:52:19.0',200,NULL,NULL,0,NULL,0,'45152',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057944640.out',NULL,NULL,NULL,0,'2022-03-24 01:52:24.0',200,NULL,NULL,0,NULL,0,'45884',NULL),
	 (4,96,'student','961648057950063.out',NULL,NULL,NULL,0,'2022-03-24 01:52:30.0',200,NULL,NULL,0,NULL,0,'11072',NULL),
	 (4,96,'student','961648057955468.out',NULL,NULL,NULL,0,'2022-03-24 01:52:35.0',200,NULL,NULL,0,NULL,0,'44244',NULL),
	 (4,96,'student','961648057960846.out',NULL,NULL,NULL,0,'2022-03-24 01:52:40.0',200,NULL,NULL,0,NULL,0,'45300',NULL),
	 (4,96,'student','961648057966247.out',NULL,NULL,NULL,0,'2022-03-24 01:52:46.0',200,NULL,NULL,0,NULL,0,'49600',NULL),
	 (4,96,'student','961648057971642.out',NULL,NULL,NULL,0,'2022-03-24 01:52:51.0',200,NULL,NULL,0,NULL,0,'39476',NULL),
	 (4,96,'student','961648057977038.out',NULL,NULL,NULL,0,'2022-03-24 01:52:57.0',200,NULL,NULL,0,NULL,0,'45764',NULL),
	 (4,96,'student','961648057982450.out',NULL,NULL,NULL,0,'2022-03-24 01:53:02.0',200,NULL,NULL,0,NULL,0,'22376',NULL),
	 (4,96,'student','961648057987860.out',NULL,NULL,NULL,0,'2022-03-24 01:53:07.0',200,NULL,NULL,0,NULL,0,'13480',NULL),
	 (4,96,'student','961648057993267.out',NULL,NULL,NULL,0,'2022-03-24 01:53:13.0',200,NULL,NULL,0,NULL,0,'48436',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648057998678.out',NULL,NULL,NULL,0,'2022-03-24 01:53:18.0',200,NULL,NULL,0,NULL,0,'48720',NULL),
	 (4,96,'student','961648058004073.out',NULL,NULL,NULL,0,'2022-03-24 01:53:24.0',200,NULL,NULL,0,NULL,0,'49568',NULL),
	 (4,96,'student','961648058009496.out',NULL,NULL,NULL,0,'2022-03-24 01:53:29.0',200,NULL,NULL,0,NULL,0,'44164',NULL),
	 (4,96,'student','961648058014894.out',NULL,NULL,NULL,0,'2022-03-24 01:53:34.0',200,NULL,NULL,0,NULL,0,'47596',NULL),
	 (4,96,'student','961648058020289.out',NULL,NULL,NULL,0,'2022-03-24 01:53:40.0',200,NULL,NULL,0,NULL,0,'27348',NULL),
	 (4,96,'student','961648058025690.out',NULL,NULL,NULL,0,'2022-03-24 01:53:45.0',200,NULL,NULL,0,NULL,0,'48912',NULL),
	 (4,96,'student','961648058031085.out',NULL,NULL,NULL,0,'2022-03-24 01:53:51.0',200,NULL,NULL,0,NULL,0,'49548',NULL),
	 (4,96,'student','961648058036479.out',NULL,NULL,NULL,0,'2022-03-24 01:53:56.0',200,NULL,NULL,0,NULL,0,'49652',NULL),
	 (4,96,'student','961648058041889.out',NULL,NULL,NULL,0,'2022-03-24 01:54:01.0',200,NULL,NULL,0,NULL,0,'47136',NULL),
	 (4,96,'student','961648058047310.out',NULL,NULL,NULL,0,'2022-03-24 01:54:07.0',200,NULL,NULL,0,NULL,0,'48944',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648058052700.out',NULL,NULL,NULL,0,'2022-03-24 01:54:12.0',200,NULL,NULL,0,NULL,0,'9144',NULL),
	 (4,96,'student','961648058058116.out',NULL,NULL,NULL,0,'2022-03-24 01:54:18.0',200,NULL,NULL,0,NULL,0,'37312',NULL),
	 (4,96,'student','961648058063519.out',NULL,NULL,NULL,0,'2022-03-24 01:54:23.0',200,NULL,NULL,0,NULL,0,'48612',NULL),
	 (4,96,'student','961648058068921.out',NULL,NULL,NULL,0,'2022-03-24 01:54:28.0',200,NULL,NULL,0,NULL,0,'50220',NULL),
	 (4,96,'student','961648058074328.out',NULL,NULL,NULL,0,'2022-03-24 01:54:34.0',200,NULL,NULL,0,NULL,0,'50908',NULL),
	 (4,96,'student','961648058079720.out',NULL,NULL,NULL,0,'2022-03-24 01:54:39.0',200,NULL,NULL,0,NULL,0,'51104',NULL),
	 (4,96,'student','961648058085148.out',NULL,NULL,NULL,0,'2022-03-24 01:54:45.0',200,NULL,NULL,0,NULL,0,'50576',NULL),
	 (4,96,'student','961648058090552.out',NULL,NULL,NULL,0,'2022-03-24 01:54:50.0',200,NULL,NULL,0,NULL,0,'48192',NULL),
	 (4,96,'student','961648058095947.out',NULL,NULL,NULL,0,'2022-03-24 01:54:56.0',200,NULL,NULL,0,NULL,0,'48276',NULL),
	 (4,96,'student','961648058101360.out',NULL,NULL,NULL,0,'2022-03-24 01:55:01.0',200,NULL,NULL,0,NULL,0,'50876',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648058106792.out',NULL,NULL,NULL,0,'2022-03-24 01:55:06.0',200,NULL,NULL,0,NULL,0,'50356',NULL),
	 (4,96,'student','961648058112192.out',NULL,NULL,NULL,0,'2022-03-24 01:55:12.0',200,NULL,NULL,0,NULL,0,'50944',NULL),
	 (4,96,'student','961648058117591.out',NULL,NULL,NULL,0,'2022-03-24 01:55:17.0',200,NULL,NULL,0,NULL,0,'50636',NULL),
	 (4,96,'student','961648058122995.out',NULL,NULL,NULL,0,'2022-03-24 01:55:23.0',200,NULL,NULL,0,NULL,0,'49832',NULL),
	 (4,96,'student','961648058128432.out',NULL,NULL,NULL,0,'2022-03-24 01:55:28.0',200,NULL,NULL,0,NULL,0,'50888',NULL),
	 (4,96,'student','961648058133838.out',NULL,NULL,NULL,0,'2022-03-24 01:55:33.0',200,NULL,NULL,0,NULL,0,'50456',NULL),
	 (4,96,'student','961648058139251.out',NULL,NULL,NULL,0,'2022-03-24 01:55:39.0',200,NULL,NULL,0,NULL,0,'47516',NULL),
	 (4,96,'student','961648058144657.out',NULL,NULL,NULL,0,'2022-03-24 01:55:44.0',200,NULL,NULL,0,NULL,0,'44216',NULL),
	 (4,96,'student','961648058150069.out',NULL,NULL,NULL,0,'2022-03-24 01:55:50.0',200,NULL,NULL,0,NULL,0,'50648',NULL),
	 (4,96,'student','961648058155464.out',NULL,NULL,NULL,0,'2022-03-24 01:55:55.0',200,NULL,NULL,0,NULL,0,'50672',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648058160860.out',NULL,NULL,NULL,0,'2022-03-24 01:56:00.0',200,NULL,NULL,0,NULL,0,'49872',NULL),
	 (4,96,'student','961648058166269.out',NULL,NULL,NULL,0,'2022-03-24 01:56:06.0',200,NULL,NULL,0,NULL,0,'48316',NULL),
	 (4,96,'student','961648058171704.out',NULL,NULL,NULL,0,'2022-03-24 01:56:11.0',200,NULL,NULL,0,NULL,0,'50348',NULL),
	 (4,96,'student','961648058177096.out',NULL,NULL,NULL,0,'2022-03-24 01:56:17.0',200,NULL,NULL,0,NULL,0,'50080',NULL),
	 (4,96,'student','961648058182496.out',NULL,NULL,NULL,0,'2022-03-24 01:56:22.0',200,NULL,NULL,0,NULL,0,'47300',NULL),
	 (4,96,'student','961648058187894.out',NULL,NULL,NULL,0,'2022-03-24 01:56:27.0',200,NULL,NULL,0,NULL,0,'48012',NULL),
	 (4,96,'student','961648058193333.out',NULL,NULL,NULL,0,'2022-03-24 01:56:33.0',200,NULL,NULL,0,NULL,0,'51160',NULL),
	 (4,96,'student','961648058198732.out',NULL,NULL,NULL,0,'2022-03-24 01:56:38.0',200,NULL,NULL,0,NULL,0,'50676',NULL),
	 (4,96,'student','961648058204131.out',NULL,NULL,NULL,0,'2022-03-24 01:56:44.0',200,NULL,NULL,0,NULL,0,'50272',NULL),
	 (4,96,'student','961648058209550.out',NULL,NULL,NULL,0,'2022-03-24 01:56:49.0',200,NULL,NULL,0,NULL,0,'49780',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648058214944.out',NULL,NULL,NULL,0,'2022-03-24 01:56:55.0',200,NULL,NULL,0,NULL,0,'46900',NULL),
	 (4,96,'student','961648058220338.out',NULL,NULL,NULL,0,'2022-03-24 01:57:00.0',200,NULL,NULL,0,NULL,0,'50300',NULL),
	 (4,96,'student','961648058225733.out',NULL,NULL,NULL,0,'2022-03-24 01:57:05.0',200,NULL,NULL,0,NULL,0,'50784',NULL),
	 (4,96,'student','961648058231132.out',NULL,NULL,NULL,0,'2022-03-24 01:57:11.0',200,NULL,NULL,0,NULL,0,'49420',NULL),
	 (4,96,'student','961648058236537.out',NULL,NULL,NULL,0,'2022-03-24 01:57:16.0',200,NULL,NULL,0,NULL,0,'50320',NULL),
	 (4,96,'student','961648058241927.out',NULL,NULL,NULL,0,'2022-03-24 01:57:21.0',200,NULL,NULL,0,NULL,0,'51008',NULL),
	 (4,96,'student','961648058247325.out',NULL,NULL,NULL,0,'2022-03-24 01:57:27.0',200,NULL,NULL,0,NULL,0,'51136',NULL),
	 (4,96,'student','961648058252721.out',NULL,NULL,NULL,0,'2022-03-24 01:57:32.0',200,NULL,NULL,0,NULL,0,'49744',NULL),
	 (4,96,'student','961648058258119.out',NULL,NULL,NULL,0,'2022-03-24 01:57:38.0',200,NULL,NULL,0,NULL,0,'48368',NULL),
	 (4,96,'student','961648058263562.out',NULL,NULL,NULL,0,'2022-03-24 01:57:43.0',200,NULL,NULL,0,NULL,0,'29304',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648058268958.out',NULL,NULL,NULL,0,'2022-03-24 01:57:49.0',200,NULL,NULL,0,NULL,0,'50200',NULL),
	 (4,96,'student','961648058274343.out',NULL,NULL,NULL,0,'2022-03-24 01:57:54.0',200,NULL,NULL,0,NULL,0,'50260',NULL),
	 (4,96,'student','961648058279752.out',NULL,NULL,NULL,0,'2022-03-24 01:57:59.0',200,NULL,NULL,0,NULL,0,'49740',NULL),
	 (4,96,'student','961648058285171.out',NULL,NULL,NULL,0,'2022-03-24 01:58:05.0',200,NULL,NULL,0,NULL,0,'50396',NULL),
	 (4,96,'student','961648058290587.out',NULL,NULL,NULL,0,'2022-03-24 01:58:10.0',200,NULL,NULL,0,NULL,0,'50812',NULL),
	 (4,96,'student','961648058296009.out',NULL,NULL,NULL,0,'2022-03-24 01:58:16.0',200,NULL,NULL,0,NULL,0,'45368',NULL),
	 (4,96,'student','961648058301414.out',NULL,NULL,NULL,0,'2022-03-24 01:58:21.0',200,NULL,NULL,0,NULL,0,'49596',NULL),
	 (4,96,'student','961648058306823.out',NULL,NULL,NULL,0,'2022-03-24 01:58:26.0',200,NULL,NULL,0,NULL,0,'6960',NULL),
	 (4,96,'student','961648058312234.out',NULL,NULL,NULL,0,'2022-03-24 01:58:32.0',200,NULL,NULL,0,NULL,0,'50332',NULL),
	 (4,96,'student','961648058317671.out',NULL,NULL,NULL,0,'2022-03-24 01:58:37.0',200,NULL,NULL,0,NULL,0,'49820',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648058323081.out',NULL,NULL,NULL,0,'2022-03-24 01:58:43.0',200,NULL,NULL,0,NULL,0,'38052',NULL),
	 (4,96,'student','961648058328500.out',NULL,NULL,NULL,0,'2022-03-24 01:58:48.0',200,NULL,NULL,0,NULL,0,'3016',NULL),
	 (4,96,'student','961648058333913.out',NULL,NULL,NULL,0,'2022-03-24 01:58:53.0',200,NULL,NULL,0,NULL,0,'47188',NULL),
	 (4,96,'student','961648058339325.out',NULL,NULL,NULL,0,'2022-03-24 01:58:59.0',200,NULL,NULL,0,NULL,0,'50872',NULL),
	 (4,96,'student','961648058344730.out',NULL,NULL,NULL,0,'2022-03-24 01:59:04.0',200,NULL,NULL,0,NULL,0,'49936',NULL),
	 (4,96,'student','961648058350142.out',NULL,NULL,NULL,0,'2022-03-24 01:59:10.0',200,NULL,NULL,0,NULL,0,'4752',NULL),
	 (4,96,'student','961648058355552.out',NULL,NULL,NULL,0,'2022-03-24 01:59:15.0',200,NULL,NULL,0,NULL,0,'51100',NULL),
	 (4,96,'student','961648058360966.out',NULL,NULL,NULL,0,'2022-03-24 01:59:21.0',200,NULL,NULL,0,NULL,0,'49364',NULL),
	 (4,96,'student','961648058366394.out',NULL,NULL,NULL,0,'2022-03-24 01:59:26.0',200,NULL,NULL,0,NULL,0,'49220',NULL),
	 (4,96,'student','961648058371800.out',NULL,NULL,NULL,0,'2022-03-24 01:59:31.0',200,NULL,NULL,0,NULL,0,'49748',NULL);
INSERT INTO web.job_log (job_group,job_id,job_desc,executor_address,executor_handler,executor_param,executor_sharding_param,executor_fail_retry_count,trigger_time,trigger_code,trigger_msg,handle_time,handle_code,handle_msg,alarm_status,process_id,max_id) VALUES
	 (4,96,'student','961648058377212.out',NULL,NULL,NULL,0,'2022-03-24 01:59:37.0',200,NULL,NULL,0,NULL,0,'48460',NULL),
	 (4,96,'student','961648058382636.out',NULL,NULL,NULL,0,'2022-03-24 01:59:42.0',200,NULL,NULL,0,NULL,0,'48028',NULL),
	 (4,96,'student','961648058388049.out',NULL,NULL,NULL,0,'2022-03-24 01:59:48.0',200,NULL,NULL,0,NULL,0,'49556',NULL),
	 (4,96,'student','961648058393430.out',NULL,NULL,NULL,0,'2022-03-24 01:59:53.0',200,NULL,NULL,0,NULL,0,'47496',NULL),
	 (4,96,'student','961648058398840.out',NULL,NULL,NULL,0,'2022-03-24 01:59:58.0',200,NULL,NULL,0,NULL,0,'49564',NULL),
	 (4,96,'student','961648058404258.out',NULL,NULL,NULL,0,'2022-03-24 02:00:04.0',200,NULL,NULL,0,NULL,0,'10844',NULL),
	 (4,97,'test.table_name',NULL,NULL,NULL,NULL,0,'2022-03-24 22:56:36.0',200,NULL,NULL,0,NULL,0,'1733',NULL),
	 (4,97,'test.table_name',NULL,NULL,NULL,NULL,0,'2022-03-25 03:13:02.0',200,NULL,NULL,0,NULL,0,'4797',NULL),
	 (4,97,'test.table_name',NULL,NULL,NULL,NULL,0,'2022-03-26 03:13:03.0',200,NULL,NULL,0,NULL,0,'9563',NULL);INSERT INTO web.job_log_report (trigger_day,running_count,suc_count,fail_count) VALUES
	 ('2019-12-07 00:00:00.0',0,0,0),
	 ('2019-12-10 00:00:00.0',77,52,23),
	 ('2019-12-11 00:00:00.0',9,2,11),
	 ('2019-12-13 00:00:00.0',9,48,74),
	 ('2019-12-12 00:00:00.0',10,8,30),
	 ('2019-12-14 00:00:00.0',78,45,66),
	 ('2019-12-15 00:00:00.0',24,76,9),
	 ('2019-12-16 00:00:00.0',23,85,10),
	 ('2022-01-20 00:00:00.0',0,0,0),
	 ('2022-01-19 00:00:00.0',0,0,0);
INSERT INTO web.job_log_report (trigger_day,running_count,suc_count,fail_count) VALUES
	 ('2022-01-18 00:00:00.0',0,0,0),
	 ('2022-01-21 00:00:00.0',0,0,0),
	 ('2021-12-11 00:00:00.0',0,0,0),
	 ('2021-12-10 00:00:00.0',0,0,0),
	 ('2021-12-09 00:00:00.0',0,0,0),
	 ('2022-01-22 00:00:00.0',0,0,0),
	 ('2022-01-24 00:00:00.0',0,0,0),
	 ('2022-01-23 00:00:00.0',0,0,0),
	 ('2022-01-27 00:00:00.0',0,0,0),
	 ('2022-01-26 00:00:00.0',0,0,0);
INSERT INTO web.job_log_report (trigger_day,running_count,suc_count,fail_count) VALUES
	 ('2022-01-25 00:00:00.0',0,0,0),
	 ('2022-01-28 00:00:00.0',0,0,0),
	 ('2022-01-29 00:00:00.0',0,0,0),
	 ('2022-01-30 00:00:00.0',0,0,0),
	 ('2022-01-31 00:00:00.0',0,0,0),
	 ('2022-02-01 00:00:00.0',0,0,0),
	 ('2022-02-02 00:00:00.0',0,0,0),
	 ('2022-02-03 00:00:00.0',0,0,0),
	 ('2022-02-04 00:00:00.0',0,0,0),
	 ('2022-02-05 00:00:00.0',0,0,0);
INSERT INTO web.job_log_report (trigger_day,running_count,suc_count,fail_count) VALUES
	 ('2022-02-07 00:00:00.0',0,0,0),
	 ('2022-02-06 00:00:00.0',0,0,0),
	 ('2022-02-08 00:00:00.0',0,0,0),
	 ('2022-02-09 00:00:00.0',0,0,0),
	 ('2022-02-10 00:00:00.0',0,0,0),
	 ('2022-02-12 00:00:00.0',0,0,0),
	 ('2022-02-11 00:00:00.0',0,0,0),
	 ('2022-02-13 00:00:00.0',0,0,0),
	 ('2022-02-15 00:00:00.0',0,0,0),
	 ('2022-02-14 00:00:00.0',0,0,0);
INSERT INTO web.job_log_report (trigger_day,running_count,suc_count,fail_count) VALUES
	 ('2022-02-16 00:00:00.0',0,0,0),
	 ('2022-02-20 00:00:00.0',0,0,0),
	 ('2022-02-19 00:00:00.0',0,0,0),
	 ('2022-02-18 00:00:00.0',0,0,0),
	 ('2022-02-21 00:00:00.0',0,0,0),
	 ('2022-02-23 00:00:00.0',0,0,0),
	 ('2022-02-22 00:00:00.0',0,0,0),
	 ('2022-02-24 00:00:00.0',0,0,0),
	 ('2022-02-25 00:00:00.0',0,0,0),
	 ('2022-02-26 00:00:00.0',0,0,0);
INSERT INTO web.job_log_report (trigger_day,running_count,suc_count,fail_count) VALUES
	 ('2022-03-01 00:00:00.0',0,0,0),
	 ('2022-02-28 00:00:00.0',0,0,0),
	 ('2022-02-27 00:00:00.0',0,0,0),
	 ('2022-03-02 00:00:00.0',0,0,0),
	 ('2022-03-05 00:00:00.0',0,0,0),
	 ('2022-03-04 00:00:00.0',0,0,0),
	 ('2022-03-03 00:00:00.0',0,0,0),
	 ('2022-03-06 00:00:00.0',1,0,0),
	 ('2022-03-09 00:00:00.0',0,0,0),
	 ('2022-03-08 00:00:00.0',0,0,0);
INSERT INTO web.job_log_report (trigger_day,running_count,suc_count,fail_count) VALUES
	 ('2022-03-07 00:00:00.0',0,0,0),
	 ('2022-03-10 00:00:00.0',1,0,0),
	 ('2022-03-11 00:00:00.0',0,0,0),
	 ('2022-03-12 00:00:00.0',0,0,0),
	 ('2022-03-13 00:00:00.0',0,0,0),
	 ('2022-03-14 00:00:00.0',1,0,0),
	 ('2022-03-15 00:00:00.0',0,0,0),
	 ('2022-03-16 00:00:00.0',0,0,0),
	 ('2022-03-17 00:00:00.0',0,0,0),
	 ('2022-03-18 00:00:00.0',0,0,0);INSERT INTO web.job_project (name,description,user_id,flag,create_time,update_time) VALUES
	 ('客户分析系统','分析用户客户行为系统',3,1,'2022-03-06 14:53:53.0',NULL),
	 ('前端活动大屏','前端活动大屏的展示',3,1,'2022-03-06 14:54:12.0',NULL),
	 ('活动营销系统','房地产营销策略',3,1,'2022-03-06 14:54:45.0',NULL),
	 ('产品定价系统','产品定价管理',3,1,'2022-03-06 14:56:22.0',NULL),
	 ('供应商评估系统','供应商评估系统',3,1,'2022-03-06 14:56:42.0',NULL);INSERT INTO web.job_template (job_group,job_cron,job_desc,add_time,update_time,user_id,alarm_email,executor_route_strategy,executor_handler,executor_param,executor_block_strategy,executor_timeout,executor_fail_retry_count,glue_type,glue_source,glue_remark,glue_updatetime,child_jobid,trigger_last_time,trigger_next_time,job_json,jvm_param,project_id) VALUES
	 (1,'* 05 * ? * * *','flinkx测试','2022-03-02 22:01:59.0','2022-03-02 22:01:59.0',1,'','RANDOM','executorJobHandler','','DISCARD_LATER',0,0,'flinkx',NULL,NULL,'2022-03-02 22:01:59.0','',0,0,NULL,'',2),
	 (1,'* 05 * ? * * *','将客户系统Mysql表迁移到PG中','2022-03-06 15:10:41.0','2022-03-06 15:10:41.0',1,'','RANDOM','executorJobHandler','','DISCARD_LATER',0,0,'flinkx',NULL,NULL,'2022-03-06 15:10:41.0','',0,0,NULL,'',3),
	 (4,'* * 01 ? * * *','迁移数据到sink数据库','2022-03-23 23:14:41.0','2022-03-23 23:14:41.0',3,'','RANDOM','executorJobHandler','','DISCARD_LATER',0,0,'flinkx',NULL,NULL,'2022-03-23 23:14:41.0','70',0,0,NULL,'',9),
	 (4,'00 13 03 * * ? *','测试','2022-03-24 20:34:01.0','2022-03-24 20:34:01.0',3,'','RANDOM','executorJobHandler','','DISCARD_LATER',0,0,'flinkx',NULL,NULL,'2022-03-24 20:34:01.0','',0,0,NULL,'',9);INSERT INTO web.job_user (username,password,`role`,permission) VALUES
	 ('admin','$2a$10$gnMxMSCMPBrWIU8FHRhAQuRiwNIMbPhXXjWrkU16hWa76vXDq4Fs6','ROLE_ADMIN',NULL),
	 ('yoyo','$2a$10$yr4ZOZwoGpsXYPyalJMzVO5NsB9ydlvXZBdm0KwvAHZI.ZCiJ7xaK','ROLE_USER','');INSERT INTO web.lark_api_auth (token_id,group_id,update_time) VALUES
	 ('Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxLGFkbWluIiwiaXNzIjoiYWRtaW4iLCJleHAiOjE2NDU5NTIwNjEsImlhdCI6MTY0NTM0NzI2MSwicm9sIjoiUk9MRV9BRE1JTiJ9.yYik6il-yfp43fJxCDVz2f7-FyP5p-nZQ8yKqIA_sb7zafo0NeJu9x51hEVmR4e8mpwzguQzACEjvb1IHnz4nA','开发组','Tue Feb 01 12:08:54 CST 2022'),
	 ('Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxLGFkbWluIiwiaXNzIjoiYWRtaW4iLCJleHAiOjE2NDU5NTIwNjEsImlhdCI6MTY0NTM0NzI2MSwicm9sIjoiUk9MRV9BRE1JTiJ9.yYik6il-yfp43fJxCDVz2f7-FyP5p-nZQ8yKqIA_sb7zafo0NeJu9x51hEVmR4e8mpwzguQzACEjvb1IHnz4nA','业务组','2022-01-01 11:00:00'),
	 ('Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxLGFkbWluIiwiaXNzIjoiYWRtaW4iLCJleHAiOjE2NDU5NTIwNjEsImlhdCI6MTY0NTM0NzI2MSwicm9sIjoiUk9MRV9BRE1JTiJ9.yYik6il-yfp43fJxCDVz2f7-FyP5p-nZQ8yKqIA_sb7zafo0NeJu9x51hEVmR4e8mpwzguQzACEjvb1IHnz4nA','大数据组','Tue Feb 01 10:01:02 CST 2022');INSERT INTO web.lark_api_config (`path`,name,group_id,`describe`,datasource_id,params,create_time,update_time,sql_text) VALUES
	 ('/api/apiConfig/add','新增','开发组','数据服务-可视化API新增构建','121','name:zhangsan',NULL,'Tue Feb 01 12:09:56 CST 2022',NULL),
	 ('/student','学生列表','开发组','查询学生列表','21','{}','Sat Mar 19 11:50:13 CST 2022',NULL,'select * from student'),
	 ('/api/getUserList','获取用户列表','用户组','无','6','{}','Fri Mar 25 15:47:07 CST 2022',NULL,'select * from student where id=1');INSERT INTO web.lark_base_resource (name,resource_address,update_time) VALUES
	 ('商品成交总额（GMV）',NULL,'2022-03-25 15:47:36'),
	 ('Flink集群地址',' http://localhost:8081/','2022-03-22 15:08:37'),
	 ('Spark集群地址',' http://localhost:8080/','2022-03-22 15:07:31');INSERT INTO web.lark_dev_tasklist (name,tasktype,runtype,sql_text,run_param,jarpath,task_describe,update_time,create_time,`type`,status) VALUES
	 ('访问人数PV','Spark','yarn-session','','',NULL,'访问人数PV',NULL,'2022-02-20 14:26:10','JAR',0),
	 ('商品成交总额（GMV）','Spark','yarn-session','select * from order','',NULL,'商品成交总额（GMV）',NULL,'2022-02-20 14:26:10','SQL',0),
	 ('FUTURE测试sql','SQL','yarn-session','','',NULL,NULL,NULL,'2022-03-24 20:34:38',NULL,NULL);