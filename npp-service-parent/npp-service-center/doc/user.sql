CREATE DATABASE `user_ds0` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */ ;
-- 执行三遍 npp-user0,npp-user1,npp-user2
CREATE TABLE `npp_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(255) DEFAULT NULL COMMENT 'npp userId，多个平台的UserId对应同一个人，需要用户绑定',
  `platform_id` varchar(255) DEFAULT NULL COMMENT '开放平台id 微信openId 支付宝alipayUserId',
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '寮€鏀惧钩鍙癮ppId',
  `type` smallint(6) NOT NULL COMMENT '类型',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `passwd` varchar(255) DEFAULT NULL COMMENT '密码 （MD5加密）',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像',
  `gender` bit(1) DEFAULT NULL COMMENT '性别',
  `country` varchar(255) DEFAULT NULL COMMENT '国家',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `create_time` varchar(255) NOT NULL COMMENT '创建时间',
  `update_time` varchar(255) NOT NULL COMMENT '更新时间',
  `status` smallint(6) NOT NULL DEFAULT '0' COMMENT '-1：删除 0：默认',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE DATABASE `user_ds1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */ ;
-- 执行三遍 npp-user0,npp-user1,npp-user2
CREATE TABLE `npp_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(255) DEFAULT NULL COMMENT 'npp userId，多个平台的UserId对应同一个人，需要用户绑定',
  `platform_id` varchar(255) DEFAULT NULL COMMENT '开放平台id 微信openId 支付宝alipayUserId',
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '寮€鏀惧钩鍙癮ppId',
  `type` smallint(6) NOT NULL COMMENT '类型',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `passwd` varchar(255) DEFAULT NULL COMMENT '密码 （MD5加密）',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像',
  `gender` bit(1) DEFAULT NULL COMMENT '性别',
  `country` varchar(255) DEFAULT NULL COMMENT '国家',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `create_time` varchar(255) NOT NULL COMMENT '创建时间',
  `update_time` varchar(255) NOT NULL COMMENT '更新时间',
  `status` smallint(6) NOT NULL DEFAULT '0' COMMENT '-1：删除 0：默认',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE DATABASE `user_ds2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */ ;
-- 执行三遍 npp-user0,npp-user1,npp-user2
CREATE TABLE `npp_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(255) DEFAULT NULL COMMENT 'npp userId，多个平台的UserId对应同一个人，需要用户绑定',
  `platform_id` varchar(255) DEFAULT NULL COMMENT '开放平台id 微信openId 支付宝alipayUserId',
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '寮€鏀惧钩鍙癮ppId',
  `type` smallint(6) NOT NULL COMMENT '类型',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `passwd` varchar(255) DEFAULT NULL COMMENT '密码 （MD5加密）',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像',
  `gender` bit(1) DEFAULT NULL COMMENT '性别',
  `country` varchar(255) DEFAULT NULL COMMENT '国家',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `create_time` varchar(255) NOT NULL COMMENT '创建时间',
  `update_time` varchar(255) NOT NULL COMMENT '更新时间',
  `status` smallint(6) NOT NULL DEFAULT '0' COMMENT '-1：删除 0：默认',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
