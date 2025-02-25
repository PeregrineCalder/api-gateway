SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `http_statement`;
CREATE TABLE `http_statement` (
                                  `id` bigint(11) NOT NULL AUTO_INCREMENT,
                                  `application` varchar(128) COLLATE utf8_bin NOT NULL,
                                  `interface_name` varchar(256) COLLATE utf8_bin NOT NULL COMMENT 'RPC, Others',
                                  `method_name` varchar(128) COLLATE utf8_bin NOT NULL COMMENT ' RPC#method',
                                  `parameter_type` varchar(256) COLLATE utf8_bin NOT NULL,
                                  `uri` varchar(128) COLLATE utf8_bin NOT NULL,
                                  `http_command_type` varchar(32) COLLATE utf8_bin NOT NULL COMMENT 'GET, POST, PUT, DELETE',
                                  `auth` int(4) NOT NULL DEFAULT '0' COMMENT 'true = 1, false = 0',
                                  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

BEGIN;
INSERT INTO `http_statement` VALUES (1, 'api-gateway-test', 'gateway.rpc.IActivityBooth', 'sayHi', 'java.lang.String', '/wg/activity/sayHi', 'GET', 0);
INSERT INTO `http_statement` VALUES (2, 'api-gateway-test', 'gateway.rpc.IActivityBooth', 'insert', 'gateway.rpc.dto.XReq', '/wg/activity/insert', 'POST', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

