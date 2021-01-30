/*
 Navicat Premium Data Transfer

 Source Server         : xampp
 Source Server Type    : MySQL
 Source Server Version : 100411
 Source Host           : localhost:3306
 Source Schema         : pb

 Target Server Type    : MySQL
 Target Server Version : 100411
 File Encoding         : 65001

 Date: 30/01/2021 10:59:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for channels
-- ----------------------------
DROP TABLE IF EXISTS `channels`;
CREATE TABLE `channels`  (
  `id` int(11) NOT NULL,
  `gameserver` int(255) NULL DEFAULT NULL,
  `announce` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of channels
-- ----------------------------
INSERT INTO `channels` VALUES (0, 1, 'Point Blank Development', 'BEGIN');
INSERT INTO `channels` VALUES (1, 1, 'Point Blank Development', 'NORMAL');
INSERT INTO `channels` VALUES (2, 1, 'Point Blank Development', 'NORMAL');
INSERT INTO `channels` VALUES (3, 1, 'Point Blank Development', 'NORMAL');
INSERT INTO `channels` VALUES (4, 1, 'Point Blank Development', 'NORMAL');
INSERT INTO `channels` VALUES (5, 1, 'Point Blank Development', 'NORMAL');
INSERT INTO `channels` VALUES (6, 1, 'Point Blank Development', 'NORMAL');
INSERT INTO `channels` VALUES (7, 1, 'Point Blank Development', 'NORMAL');
INSERT INTO `channels` VALUES (8, 1, 'Point Blank Development', 'NORMAL');
INSERT INTO `channels` VALUES (9, 1, 'Point Blank Development', 'CLAN');

-- ----------------------------
-- Table structure for gameservers
-- ----------------------------
DROP TABLE IF EXISTS `gameservers`;
CREATE TABLE `gameservers`  (
  `id` int(11) NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `port` int(255) NULL DEFAULT NULL,
  `max_players` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gameservers
-- ----------------------------
INSERT INTO `gameservers` VALUES (1, 'NORMAL', '192.168.178.113', 39191, 100);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `item_id` int(11) NULL DEFAULT NULL,
  `points` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `moneys` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `counts` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '',
  `visible` tinyint(1) NULL DEFAULT 1
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (100003036, '11000,20000,40000', '0,0,0', '100,200,500', '', 1);
INSERT INTO `goods` VALUES (100003037, '0,0,0', '250,1250,4500', '86400,259200,2592000', 'NEW', 1);

-- ----------------------------
-- Table structure for items
-- ----------------------------
DROP TABLE IF EXISTS `items`;
CREATE TABLE `items`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `consume` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `specific_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `required_title` int(255) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of items
-- ----------------------------
INSERT INTO `items` VALUES (100003004, 'K-2', 'PERMANENT', 'WEAPON', 'WEAPON', 0);
INSERT INTO `items` VALUES (100003036, 'AUG_A3', 'DURABLE', 'WEAPON', 'WEAPON', 0);
INSERT INTO `items` VALUES (100003037, 'AUG_A3_GOLD', 'TEMPORARY', 'WEAPON', 'WEAPON', 0);
INSERT INTO `items` VALUES (200004006, 'K-1', 'PERMANENT', 'WEAPON', 'WEAPON', 0);
INSERT INTO `items` VALUES (300005003, 'SSG-69', 'PERMANENT', 'WEAPON', 'WEAPON', 0);
INSERT INTO `items` VALUES (400006001, '870MCS', 'PERMANENT', 'WEAPON', 'WEAPON', 0);
INSERT INTO `items` VALUES (601002003, 'K-5', 'PERMANENT', 'WEAPON', 'PISTOL', 0);
INSERT INTO `items` VALUES (702001001, 'M-7', 'PERMANENT', 'WEAPON', 'KNIFE', 0);
INSERT INTO `items` VALUES (803007001, 'K-400', 'PERMANENT', 'WEAPON', 'THROW', 0);
INSERT INTO `items` VALUES (904007002, 'SMOKE', 'PERMANENT', 'WEAPON', 'SPECIAL', 0);
INSERT INTO `items` VALUES (1001001005, 'RED_BULLS', 'PERMANENT', 'CHARACTER', 'RED', 0);
INSERT INTO `items` VALUES (1001002006, 'ACID_POOL', 'PERMANENT', 'CHARACTER', 'BLUE', 0);
INSERT INTO `items` VALUES (1102003001, 'HELMET_BASE', 'PERMANENT', 'CHARACTER', 'HEAD', 0);

-- ----------------------------
-- Table structure for player_items
-- ----------------------------
DROP TABLE IF EXISTS `player_items`;
CREATE TABLE `player_items`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `item_id` int(11) NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `count` int(11) NULL DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of player_items
-- ----------------------------
INSERT INTO `player_items` VALUES (1, 1, 100003004, 'DISABLED', 1, 'INVENTORY');
INSERT INTO `player_items` VALUES (2, 1, 200004006, 'DISABLED', 1, 'INVENTORY');
INSERT INTO `player_items` VALUES (3, 1, 300005003, 'DISABLED', 1, 'INVENTORY');
INSERT INTO `player_items` VALUES (4, 1, 400006001, 'DISABLED', 1, 'INVENTORY');
INSERT INTO `player_items` VALUES (5, 1, 601002003, 'DISABLED', 1, 'EQUIPPED');
INSERT INTO `player_items` VALUES (6, 1, 702001001, 'DISABLED', 1, 'EQUIPPED');
INSERT INTO `player_items` VALUES (7, 1, 803007001, 'DISABLED', 1, 'EQUIPPED');
INSERT INTO `player_items` VALUES (8, 1, 904007002, 'DISABLED', 1, 'EQUIPPED');
INSERT INTO `player_items` VALUES (9, 1, 1001001005, 'DISABLED', 1, 'EQUIPPED');
INSERT INTO `player_items` VALUES (10, 1, 1001002006, 'DISABLED', 1, 'EQUIPPED');
INSERT INTO `player_items` VALUES (11, 1, 1102003001, 'DISABLED', 1, 'EQUIPPED');
INSERT INTO `player_items` VALUES (16, 1, 100003037, 'ACTIVATED', 2101301838, 'INVENTORY');
INSERT INTO `player_items` VALUES (17, 1, 100003036, 'NORMAL', 800, 'EQUIPPED');

-- ----------------------------
-- Table structure for player_stats
-- ----------------------------
DROP TABLE IF EXISTS `player_stats`;
CREATE TABLE `player_stats`  (
  `id` bigint(20) NOT NULL,
  `fights` int(255) NULL DEFAULT 0,
  `wins` int(255) NULL DEFAULT 0,
  `loses` int(255) NULL DEFAULT 0,
  `escapes` int(255) NULL DEFAULT 0,
  `kills` int(255) NULL DEFAULT 0,
  `deaths` int(255) NULL DEFAULT 0,
  `headshots` int(255) NULL DEFAULT 0,
  `total_fights` int(255) NULL DEFAULT 0,
  `total_kills` int(255) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of player_stats
-- ----------------------------
INSERT INTO `player_stats` VALUES (1, 0, 0, 0, 0, 0, 0, 0, 0, 0);

-- ----------------------------
-- Table structure for player_stats_season
-- ----------------------------
DROP TABLE IF EXISTS `player_stats_season`;
CREATE TABLE `player_stats_season`  (
  `id` bigint(20) NOT NULL,
  `fights` int(255) NULL DEFAULT 0,
  `wins` int(255) NULL DEFAULT 0,
  `loses` int(255) NULL DEFAULT 0,
  `escapes` int(255) NULL DEFAULT 0,
  `kills` int(255) NULL DEFAULT 0,
  `deaths` int(255) NULL DEFAULT 0,
  `headshots` int(255) NULL DEFAULT 0,
  `total_fights` int(255) NULL DEFAULT 0,
  `total_kills` int(255) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of player_stats_season
-- ----------------------------
INSERT INTO `player_stats_season` VALUES (1, 0, 0, 0, 0, 0, 0, 0, 0, 0);

-- ----------------------------
-- Table structure for players
-- ----------------------------
DROP TABLE IF EXISTS `players`;
CREATE TABLE `players`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `rank` int(255) NULL DEFAULT NULL,
  `experience` int(255) NULL DEFAULT NULL,
  `points` int(255) NULL DEFAULT NULL,
  `moneys` int(255) NULL DEFAULT NULL,
  `online` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of players
-- ----------------------------
INSERT INTO `players` VALUES (1, 'test', 'test', 'Tester', 53, 0, 689000, 599750, '0');

SET FOREIGN_KEY_CHECKS = 1;
