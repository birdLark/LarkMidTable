/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.204
 Source Server Type    : MySQL
 Source Server Version : 50173
 Source Host           : 192.168.1.204:3306
 Source Schema         : flink-test

 Target Server Type    : MySQL
 Target Server Version : 50173
 File Encoding         : 65001

 Date: 09/06/2021 21:44:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pvuv_sink
-- ----------------------------
DROP TABLE IF EXISTS `pvuv_sink`;
CREATE TABLE `pvuv_sink`  (
  `dt` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `pv` bigint(255) NULL DEFAULT NULL,
  `uv` bigint(255) NULL DEFAULT NULL
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
