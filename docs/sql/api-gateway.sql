SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `application_interface`;
CREATE TABLE `application_interface` (
                                         `id` int(11) NOT NULL AUTO_INCREMENT,
                                         `system_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
                                         `interface_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
                                         `interface_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
                                         `interface_version` varchar(16) COLLATE utf8_bin DEFAULT NULL,
                                         `create_time` datetime DEFAULT NULL,
                                         `update_time` datetime DEFAULT NULL,
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `idx` (`system_id`,`interface_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
BEGIN;
COMMIT;

DROP TABLE IF EXISTS `application_interface_method`;
CREATE TABLE `application_interface_method` (
                                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                                `system_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
                                                `interface_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
                                                `method_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
                                                `method_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
                                                `parameter_type` varchar(256) COLLATE utf8_bin DEFAULT NULL,
                                                `uri` varchar(126) COLLATE utf8_bin DEFAULT NULL,
                                                `http_command_type` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'GET, POST, PUT, DELETE',
                                                `auth` int(4) DEFAULT NULL COMMENT 'true = 1, false = 0',
                                                `create_time` datetime DEFAULT NULL,
                                                `update_time` datetime DEFAULT NULL,
                                                PRIMARY KEY (`id`),
                                                UNIQUE KEY `idx` (`system_id`,`interface_id`,`method_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
BEGIN;
COMMIT;

DROP TABLE IF EXISTS `application_system`;
CREATE TABLE `application_system` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `system_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
                                      `system_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
                                      `system_type` varchar(4) COLLATE utf8_bin DEFAULT NULL COMMENT 'RPC, HTTP',
                                      `system_registry` varchar(128) COLLATE utf8_bin DEFAULT NULL,
                                      `create_time` datetime DEFAULT NULL,
                                      `update_time` datetime DEFAULT NULL,
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `idx_systemId` (`system_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
BEGIN;
COMMIT;

DROP TABLE IF EXISTS `gateway_distribution`;
CREATE TABLE `gateway_distribution` (
                                        `id` int(11) NOT NULL,
                                        `group_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
                                        `gateway_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
                                        `system_id` varchar(64) COLLATE utf8_bin DEFAULT NULL,
                                        `system_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
                                        `create_time` datetime DEFAULT NULL COMMENT,
                                        `update_time` datetime DEFAULT NULL COMMENT,
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
BEGIN;
COMMIT;

DROP TABLE IF EXISTS `gateway_server`;
CREATE TABLE `gateway_server` (
                                  `id` int(11) NOT NULL AUTO_INCREMENT,
                                  `group_id` varchar(32) COLLATE utf8_bin DEFAULT NULL,
                                  `group_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
BEGIN;
COMMIT;

DROP TABLE IF EXISTS `gateway_server_detail`;
CREATE TABLE `gateway_server_detail` (
                                         `id` int(11) NOT NULL AUTO_INCREMENT,
                                         `group_id` varchar(32) COLLATE utf8_bin,
                                         `gateway_id` varchar(32) COLLATE utf8_bin DEFAULT NULL,
                                         `gateway_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
                                         `gateway_address` varchar(64) COLLATE utf8_bin DEFAULT NULL,
                                         `status` varchar(4) COLLATE utf8_bin DEFAULT NULL COMMENT '0 unavailable、1 available',
                                         `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                         `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `idx_gateway_id` (`gateway_id`,`gateway_address`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
BEGIN;
COMMIT;

DROP TABLE IF EXISTS `http_statement`;
CREATE TABLE `http_statement` (
                                  `id` bigint(11) NOT NULL AUTO_INCREMENT,
                                  `application` varchar(128) COLLATE utf8_bin NOT NULL,
                                  `interface_name` varchar(256) COLLATE utf8_bin NOT NULL COMMENT 'RPC, Others',
                                  `method_name` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'RPC#method',
                                  `parameter_type` varchar(256) COLLATE utf8_bin NOT NULL,
                                  `uri` varchar(128) COLLATE utf8_bin NOT NULL,
                                  `http_command_type` varchar(32) COLLATE utf8_bin NOT NULL COMMENT 'GET, POST, PUT, DELETE',
                                  `auth` int(4) NOT NULL DEFAULT '0' COMMENT 'true = 1, false = 0',
                                  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

