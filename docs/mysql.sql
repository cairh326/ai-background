/*
Navicat MySQL Data Transfer

Source Server         : 本机sa
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : dream

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2019-08-26 14:04:28
*/

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(10) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `email` varchar(36) NOT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `sex` tinyint(2) NOT NULL DEFAULT '0' COMMENT '性别',
  `age` tinyint(2) DEFAULT '0' COMMENT '年龄',
  `user_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '用户类别[0:管理员,1:普通员工]',
  `organization_id` int(11) DEFAULT NULL COMMENT '组织id',
  `locked` tinyint(2) DEFAULT '0' COMMENT '是否锁定[0:正常,1:锁定]',
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态[0:失效,1:正常]',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_admin_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('1', 'admin', '$2a$04$a3MluO3/wNYNtQjACPK63uIG1JgyJhNWhNw0BrEuSI7ciBf5gn.FO', '管理员', '596392912@qq.com', '15321111111', '0', '0', '0', '1', '0', '1', '2018-01-30 10:08:41', '2018-04-16 14:59:38');
INSERT INTO `t_admin` VALUES ('2', 'test', '$2a$04$e97c8S9vHrS6BE1diTj4FO4nrK6X2Vi2jhJLGYBnqpt65u95TcbBK', '测试', '596392912@qq.com', '', '0', '0', '1', '6', '0', '1', '2018-03-28 04:26:31', '2019-08-26 15:00:03');
-- ----------------------------
-- Table structure for t_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_role`;
CREATE TABLE `t_admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `admin_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `idx_user_role_ids` (`admin_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8 COMMENT='用户角色';

-- ----------------------------
-- Records of t_admin_role
-- ----------------------------
INSERT INTO `t_admin_role` VALUES ('90', '1', '1');
INSERT INTO `t_admin_role` VALUES ('91', '1', '2');
INSERT INTO `t_admin_role` VALUES ('92', '1', '7');
INSERT INTO `t_admin_role` VALUES ('93', '1', '8');
INSERT INTO `t_admin_role` VALUES ('89', '2', '8');
INSERT INTO `t_admin_role` VALUES ('63', '13', '2');
INSERT INTO `t_admin_role` VALUES ('64', '14', '7');
INSERT INTO `t_admin_role` VALUES ('53', '15', '8');

-- ----------------------------
-- Table structure for t_camera
-- ----------------------------
DROP TABLE IF EXISTS `t_camera`;
CREATE TABLE `t_camera` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(30) NOT NULL COMMENT '编码',
  `location_name` varchar(30) NOT NULL COMMENT '点位名称',
  `brand_id` int(11) NOT NULL COMMENT '品牌',
  `ip` varchar(20) NOT NULL COMMENT 'ip',
  `user` varchar(20) NOT NULL COMMENT '用户名',
  `passwd` varchar(20) NOT NULL COMMENT '密码',
  `main_stream` varchar(300) NOT NULL COMMENT '主码流',
  `sub_stream` varchar(300) NOT NULL COMMENT '辅码流',
  `longitude` varchar(20) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(20) DEFAULT NULL COMMENT '纬度',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态[0:失效,1:正常]',
  `is_online` tinyint(2) NOT NULL COMMENT '是否在线',
  `heart_time` datetime DEFAULT NULL COMMENT '最后心跳',
  `create_by` varchar(10) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(10) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ind_camera_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摄像机配置';

-- ----------------------------
-- Records of t_camera
-- ----------------------------

-- ----------------------------
-- Table structure for t_camera_brand
-- ----------------------------
DROP TABLE IF EXISTS `t_camera_brand`;
CREATE TABLE `t_camera_brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `brand_code` varchar(20) NOT NULL COMMENT '品牌代码',
  `brand_name` varchar(20) NOT NULL COMMENT '品牌',
  `main_stream` varchar(150) NOT NULL COMMENT '主码流',
  `sub_stream` varchar(150) NOT NULL COMMENT '辅码流',
  `create_by` varchar(10) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(10) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='摄像机品牌';

-- ----------------------------
-- Records of t_camera_brand
-- ----------------------------
INSERT INTO `t_camera_brand` VALUES ('1', 'DH', '大华', 'rtsp://%s:%s@%s:554/cam/realmonitor?channel=1&subtype=0', 'rtsp://%s:%s@%s:554/cam/realmonitor?channel=1&subtype=1', 'admin', '2019-08-13 14:20:01', 'admin', '2019-08-24 16:08:23');
INSERT INTO `t_camera_brand` VALUES ('2', 'HK', '海康', 'rtsp://%s:%s@%s:554/h264/ch1/main/av_stream', 'rtsp://%s:%s@%s:554/h264/ch1/sub/av_stream', 'admin', '2019-08-13 14:20:01', null, '2019-08-13 14:40:45');
INSERT INTO `t_camera_brand` VALUES ('3', 'YS', '宇视', 'rtsp://%s:%s@%s:554/video1', 'rtsp://%s:%s@%s:554/video2', 'admin', '2019-08-13 14:20:01', null, '2019-08-13 14:40:47');
INSERT INTO `t_camera_brand` VALUES ('4', 'HW', '华为', 'rtsp://%s:%s@%s:554/LiveMedia/ch1/Media1', 'rtsp://%s:%s@%s:554/LiveMedia/ch1/Media2', 'admin', '2019-08-13 14:20:01', null, '2019-08-13 14:40:49');

-- ----------------------------
-- Table structure for t_config
-- ----------------------------
DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `c_name` varchar(255) NOT NULL COMMENT '名称',
  `c_value` varchar(500) DEFAULT '' COMMENT '值',
  `c_desc` varchar(500) DEFAULT '' COMMENT '描述',
  `create_by` varchar(10) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(10) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='参数配置';

-- ----------------------------
-- Records of t_config
-- ----------------------------
INSERT INTO `t_config` VALUES ('1', 'netCardPort', '9090', '网卡配置端口', 'admin', '2019-08-22 11:05:08', 'admin', '2019-08-24 16:12:51');
INSERT INTO `t_config` VALUES ('2', 'cloudConfig', 'http://ip:port#key#ps', '中心平台云配置,按#分割', 'admin', '2019-08-24 20:57:16', null, null);

-- ----------------------------
-- Table structure for t_net_card
-- ----------------------------
DROP TABLE IF EXISTS `t_net_card`;
CREATE TABLE `t_net_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `card_name` varchar(20) NOT NULL COMMENT '名称',
  `method` varchar(150) NOT NULL COMMENT 'ip获取方式',
  `dns` varchar(150) DEFAULT NULL COMMENT '首选DNS',
  `dns2` varchar(150) DEFAULT NULL COMMENT '备用DNS',
  `gateway` varchar(150) DEFAULT NULL COMMENT '默认网关',
  `ipv4` varchar(100) DEFAULT NULL COMMENT 'IPV4地址',
  `isAuto` tinyint(1) DEFAULT NULL COMMENT '开机自启动',
  `mac` varchar(100) DEFAULT NULL COMMENT 'mac地址',
  `mask` varchar(100) DEFAULT NULL COMMENT '子网掩码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='网卡信息';

-- ----------------------------
-- Table structure for t_organization
-- ----------------------------
DROP TABLE IF EXISTS `t_organization`;
CREATE TABLE `t_organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(64) NOT NULL COMMENT '组织名',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `code` varchar(64) NOT NULL COMMENT '编号',
  `icon_cls` varchar(32) DEFAULT NULL COMMENT '图标',
  `pid` int(11) DEFAULT NULL COMMENT '父级主键',
  `seq` tinyint(2) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态[0:失效,1:正常]',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='组织机构';

-- ----------------------------
-- Records of t_organization
-- ----------------------------
INSERT INTO `t_organization` VALUES ('1', '总经办', '王家桥', '01', 'glyphicon-apple', null, '0', '1', '2019-08-10 16:00:01', '2019-08-24 18:54:30');
INSERT INTO `t_organization` VALUES ('3', '技术部', '', '02', 'glyphicon-heart', null, '1', '1', '2019-08-10 16:00:01', '2019-08-24 18:54:35');
INSERT INTO `t_organization` VALUES ('5', '产品部', '', '03', 'glyphicon-glass', null, '2', '1', '2019-08-10 16:00:01', '2019-08-24 18:54:37');
INSERT INTO `t_organization` VALUES ('6', '测试组', '', '04', 'glyphicon-retweet ', '3', '0', '1', '2019-08-10 16:00:01', '2019-08-24 18:54:34');

-- ----------------------------
-- Table structure for t_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL COMMENT '资源名称',
  `permissions` varchar(32) DEFAULT NULL COMMENT '资源的权限',
  `url` varchar(100) DEFAULT NULL COMMENT '资源路径',
  `open_mode` varchar(32) DEFAULT NULL COMMENT '打开方式 ajax,iframe',
  `description` varchar(255) DEFAULT NULL COMMENT '资源介绍',
  `icon_cls` varchar(32) DEFAULT NULL COMMENT '资源图标',
  `pid` int(11) DEFAULT NULL COMMENT '父级资源id',
  `seq` tinyint(2) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态[0:失效,1:正常]',
  `opened` tinyint(1) NOT NULL DEFAULT '0' COMMENT '打开状态',
  `resource_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '资源类别',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=257 DEFAULT CHARSET=utf8 COMMENT='资源';

-- ----------------------------
-- Records of t_resource
-- ----------------------------
INSERT INTO `t_resource` VALUES ('1', '权限管理', '', '', null, '系统管理', 'glyphicon-th ', null, '0', '1', '1', '0', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('11', '资源管理', '', '/resource/manager', 'ajax', '资源管理', 'glyphicon-list-alt', '1', '1', '1', '1', '0', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('12', '角色管理', '', '/role/manager', 'ajax', '角色管理', 'glyphicon-lock', '1', '2', '1', '1', '0', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('13', '用户管理', '', '/admin/manager', 'ajax', '用户管理', 'glyphicon-user', '1', '3', '1', '1', '0', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('14', '部门管理', '', '/organization/manager', 'ajax', '部门管理', 'glyphicon-globe', '1', '4', '1', '1', '0', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('111', '列表', '', '/resource/treeGrid', 'ajax', '资源列表', 'glyphicon-th-list', '11', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('112', '添加', 'resource:add', '/resource/add', 'ajax', '资源添加', 'glyphicon-plus', '11', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('113', '编辑', 'resource:edit', '/resource/edit', 'ajax', '资源编辑', 'glyphicon-pencil', '11', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('114', '删除', 'resource:delete', '/resource/delete', 'ajax', '资源删除', 'glyphicon-remove', '11', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('121', '列表', '', '/role/dataGrid', 'ajax', '角色列表', 'glyphicon-th-list', '12', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('122', '添加', 'role:add', '/role/add', 'ajax', '角色添加', 'glyphicon-plus', '12', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('123', '编辑', 'role:edit', '/role/edit', 'ajax', '角色编辑', 'glyphicon-pencil', '12', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('124', '删除', 'role:delete', '/role/delete', 'ajax', '角色删除', 'glyphicon-remove', '12', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('125', '授权', 'role:grant', '/role/grant', 'ajax', '角色授权', 'glyphicon-ok ', '12', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('131', '列表', '', '/admin/dataGrid', 'ajax', '用户列表', 'glyphicon-th-list', '13', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('132', '添加', 'admin:add', '/admin/add', 'ajax', '用户添加', 'glyphicon-plus', '13', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('133', '编辑', 'admin:edit', '/admin/edit', 'ajax', '用户编辑', 'glyphicon-pencil', '13', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('134', '删除', 'admin:delete', '/admin/delete', 'ajax', '用户删除', 'glyphicon-remove', '13', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('141', '列表', '', '/organization/treeGrid', 'ajax', '用户列表', 'glyphicon-th-list', '14', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('142', '添加', 'organization:add', '/organization/add', 'ajax', '部门添加', 'glyphicon-plus', '14', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('143', '编辑', 'organization:edit', '/organization/edit', 'ajax', '部门编辑', 'glyphicon-pencil', '14', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('144', '删除', 'organization:delete', '/organization/delete', 'ajax', '部门删除', 'glyphicon-remove', '14', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('221', '系统管理', '', '', null, null, 'glyphicon-cog ', null, '3', '1', '0', '0', '2019-08-10 16:00:01', '2018-04-15 21:41:32');
INSERT INTO `t_resource` VALUES ('226', '修改密码', 'admin:edit:pwd', '/admin/editPwdPage', 'ajax', null, 'glyphicon-warning-sign', null, '4', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('227', '登录日志', '', '/sysLog/manager', 'ajax', null, 'glyphicon-info-sign', '221', '3', '1', '1', '0', '2019-08-10 16:00:01', '2018-04-15 21:47:58');
INSERT INTO `t_resource` VALUES ('229', '系统图标', '', '/icons.html', 'ajax', null, 'glyphicon-picture', '221', '2', '1', '1', '0', '2019-08-10 16:00:01', '2018-04-15 21:48:18');
INSERT INTO `t_resource` VALUES ('235', '字典管理', 'sysDict:manager', '/sysDict/manager', null, '字典管理', 'glyphicon-list-alt ', '221', '1', '1', '1', '0', '2019-08-10 16:00:01', '2018-04-15 21:48:26');
INSERT INTO `t_resource` VALUES ('236', '列表', 'sysDict:dataGrid', '/sysDict/dataGrid', 'ajax', '资源列表', 'glyphicon-th-list', '235', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-04-15 21:59:40');
INSERT INTO `t_resource` VALUES ('237', '添加', 'sysDict:add', '/sysDict/add', 'ajax', '资源添加', 'glyphicon-plus', '235', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-04-15 21:32:45');
INSERT INTO `t_resource` VALUES ('238', '编辑', 'sysDict:edit', '/sysDict/edit', 'ajax', '资源编辑', 'glyphicon-pencil', '235', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-04-15 21:32:55');
INSERT INTO `t_resource` VALUES ('239', '码流配置', 'cameraBrand:manager', '/cameraBrand/manager', null, '码流配置', 'glyphicon-list-alt ', '221', '4', '1', '1', '0', '2019-08-10 16:00:01', '2018-04-15 21:48:26');
INSERT INTO `t_resource` VALUES ('240', '列表', 'cameraBrand:dataGrid', '/cameraBrand/dataGrid', 'ajax', '码流配置列表', 'glyphicon-th-list', '239', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-04-15 21:59:40');
INSERT INTO `t_resource` VALUES ('241', '添加', 'cameraBrand:add', '/cameraBrand/add', 'ajax', '码流配置添加', 'glyphicon-plus', '239', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-04-15 21:32:45');
INSERT INTO `t_resource` VALUES ('242', '编辑', 'cameraBrand:edit', '/cameraBrand/edit', 'ajax', '码流配置编辑', 'glyphicon-pencil', '239', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-04-15 21:32:55');
INSERT INTO `t_resource` VALUES ('243', '删除', 'cameraBrand:delete', '/cameraBrand/delete', 'ajax', '码流配置删除', 'glyphicon-remove', '239', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('244', '摄像头配置', 'camera:manager', '/camera/manager', null, '摄像头配置', 'glyphicon-list-alt ', '221', '4', '1', '1', '0', '2019-08-10 16:00:01', '2018-04-15 21:48:26');
INSERT INTO `t_resource` VALUES ('245', '列表', 'camera:dataGrid', '/camera/dataGrid', 'ajax', '摄像头配置列表', 'glyphicon-th-list', '244', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-04-15 21:59:40');
INSERT INTO `t_resource` VALUES ('246', '添加', 'camera:add', '/camera/add', 'ajax', '摄像头配置添加', 'glyphicon-plus', '244', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-04-15 21:32:45');
INSERT INTO `t_resource` VALUES ('247', '编辑', 'camera:edit', '/camera/edit', 'ajax', '摄像头配置编辑', 'glyphicon-pencil', '244', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-04-15 21:32:55');
INSERT INTO `t_resource` VALUES ('248', '删除', 'camera:delete', '/camera/delete', 'ajax', '摄像头配置删除', 'glyphicon-remove', '244', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('249', '参数配置', 'config:manager', '/config/manager', null, '参数配置', 'glyphicon-list-alt ', '221', '4', '1', '1', '0', '2019-08-10 16:00:01', '2018-04-15 21:48:26');
INSERT INTO `t_resource` VALUES ('250', '列表', 'config:dataGrid', '/config/dataGrid', 'ajax', '参数配置列表', 'glyphicon-th-list', '249', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-04-15 21:59:40');
INSERT INTO `t_resource` VALUES ('251', '添加', 'config:add', '/config/add', 'ajax', '参数配置添加', 'glyphicon-plus', '249', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-04-15 21:32:45');
INSERT INTO `t_resource` VALUES ('252', '编辑', 'config:edit', '/config/edit', 'ajax', '参数配置编辑', 'glyphicon-pencil', '249', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-04-15 21:32:55');
INSERT INTO `t_resource` VALUES ('253', '删除', 'config:delete', '/config/delete', 'ajax', '参数配置删除', 'glyphicon-remove', '249', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-03-24 04:33:21');
INSERT INTO `t_resource` VALUES ('254', '网卡配置', 'netCard:manager', '/netCard/manager', null, '网卡配置', 'glyphicon-list-alt ', '221', '4', '1', '1', '0', '2019-08-10 16:00:01', '2018-04-15 21:48:26');
INSERT INTO `t_resource` VALUES ('255', '列表', 'netCard:dataGrid', '/netCard/dataGrid', 'ajax', '网卡配置列表', 'glyphicon-th-list', '254', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-04-15 21:59:40');
INSERT INTO `t_resource` VALUES ('256', '编辑', 'netCard:edit', '/netCard/edit', 'ajax', '网卡配置编辑', 'glyphicon-pencil', '254', '0', '1', '1', '1', '2019-08-10 16:00:01', '2018-04-15 21:32:55');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(64) NOT NULL COMMENT '角色名',
  `description` varchar(255) DEFAULT NULL COMMENT '简介',
  `icon_cls` varchar(32) DEFAULT NULL COMMENT '角色图标',
  `seq` tinyint(2) NOT NULL DEFAULT '0' COMMENT '排序号',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态[0:失效,1:正常]',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'admin', '超级管理员', 'glyphicon-lock ', '1', '1', '2018-03-24 04:33:21', '2019-08-24 19:34:53');
INSERT INTO `t_role` VALUES ('2', 'de', '技术部经理', 'glyphicon-plane ', '2', '1', '2018-03-24 04:33:21', '2018-04-14 16:32:42');
INSERT INTO `t_role` VALUES ('7', 'pm', '产品部经理', 'glyphicon-ok-circle ', '3', '1', '2018-03-24 04:33:21', '2018-04-14 16:32:47');
INSERT INTO `t_role` VALUES ('8', 'test', '测试账户', 'glyphicon-calendar ', '4', '1', '2018-03-24 04:33:21', '2018-04-14 16:32:52');

-- ----------------------------
-- Table structure for t_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_role_resource`;
CREATE TABLE `t_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `resource_id` int(11) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`),
  KEY `idx_role_resource_ids` (`role_id`,`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=921 DEFAULT CHARSET=utf8 COMMENT='角色资源';

-- ----------------------------
-- Records of t_role_resource
-- ----------------------------
INSERT INTO `t_role_resource` VALUES ('893', '1', '1');
INSERT INTO `t_role_resource` VALUES ('910', '1', '11');
INSERT INTO `t_role_resource` VALUES ('904', '1', '12');
INSERT INTO `t_role_resource` VALUES ('899', '1', '13');
INSERT INTO `t_role_resource` VALUES ('894', '1', '14');
INSERT INTO `t_role_resource` VALUES ('911', '1', '111');
INSERT INTO `t_role_resource` VALUES ('912', '1', '112');
INSERT INTO `t_role_resource` VALUES ('913', '1', '113');
INSERT INTO `t_role_resource` VALUES ('914', '1', '114');
INSERT INTO `t_role_resource` VALUES ('905', '1', '121');
INSERT INTO `t_role_resource` VALUES ('906', '1', '122');
INSERT INTO `t_role_resource` VALUES ('907', '1', '123');
INSERT INTO `t_role_resource` VALUES ('908', '1', '124');
INSERT INTO `t_role_resource` VALUES ('909', '1', '125');
INSERT INTO `t_role_resource` VALUES ('900', '1', '131');
INSERT INTO `t_role_resource` VALUES ('901', '1', '132');
INSERT INTO `t_role_resource` VALUES ('902', '1', '133');
INSERT INTO `t_role_resource` VALUES ('903', '1', '134');
INSERT INTO `t_role_resource` VALUES ('895', '1', '141');
INSERT INTO `t_role_resource` VALUES ('896', '1', '142');
INSERT INTO `t_role_resource` VALUES ('897', '1', '143');
INSERT INTO `t_role_resource` VALUES ('898', '1', '144');
INSERT INTO `t_role_resource` VALUES ('868', '1', '221');
INSERT INTO `t_role_resource` VALUES ('867', '1', '226');
INSERT INTO `t_role_resource` VALUES ('887', '1', '227');
INSERT INTO `t_role_resource` VALUES ('888', '1', '229');
INSERT INTO `t_role_resource` VALUES ('889', '1', '235');
INSERT INTO `t_role_resource` VALUES ('890', '1', '236');
INSERT INTO `t_role_resource` VALUES ('891', '1', '237');
INSERT INTO `t_role_resource` VALUES ('892', '1', '238');
INSERT INTO `t_role_resource` VALUES ('869', '1', '239');
INSERT INTO `t_role_resource` VALUES ('870', '1', '240');
INSERT INTO `t_role_resource` VALUES ('871', '1', '241');
INSERT INTO `t_role_resource` VALUES ('872', '1', '242');
INSERT INTO `t_role_resource` VALUES ('873', '1', '243');
INSERT INTO `t_role_resource` VALUES ('874', '1', '244');
INSERT INTO `t_role_resource` VALUES ('875', '1', '245');
INSERT INTO `t_role_resource` VALUES ('876', '1', '246');
INSERT INTO `t_role_resource` VALUES ('877', '1', '247');
INSERT INTO `t_role_resource` VALUES ('878', '1', '248');
INSERT INTO `t_role_resource` VALUES ('879', '1', '249');
INSERT INTO `t_role_resource` VALUES ('880', '1', '250');
INSERT INTO `t_role_resource` VALUES ('881', '1', '251');
INSERT INTO `t_role_resource` VALUES ('882', '1', '252');
INSERT INTO `t_role_resource` VALUES ('883', '1', '253');
INSERT INTO `t_role_resource` VALUES ('884', '1', '254');
INSERT INTO `t_role_resource` VALUES ('886', '1', '255');
INSERT INTO `t_role_resource` VALUES ('885', '1', '256');
INSERT INTO `t_role_resource` VALUES ('437', '2', '1');
INSERT INTO `t_role_resource` VALUES ('438', '2', '13');
INSERT INTO `t_role_resource` VALUES ('439', '2', '131');
INSERT INTO `t_role_resource` VALUES ('440', '2', '132');
INSERT INTO `t_role_resource` VALUES ('441', '2', '133');
INSERT INTO `t_role_resource` VALUES ('445', '2', '221');
INSERT INTO `t_role_resource` VALUES ('442', '2', '222');
INSERT INTO `t_role_resource` VALUES ('443', '2', '223');
INSERT INTO `t_role_resource` VALUES ('444', '2', '224');
INSERT INTO `t_role_resource` VALUES ('446', '2', '227');
INSERT INTO `t_role_resource` VALUES ('447', '2', '228');
INSERT INTO `t_role_resource` VALUES ('916', '7', '1');
INSERT INTO `t_role_resource` VALUES ('917', '7', '14');
INSERT INTO `t_role_resource` VALUES ('918', '7', '141');
INSERT INTO `t_role_resource` VALUES ('919', '7', '142');
INSERT INTO `t_role_resource` VALUES ('920', '7', '143');
INSERT INTO `t_role_resource` VALUES ('915', '7', '221');
INSERT INTO `t_role_resource` VALUES ('546', '8', '1');
INSERT INTO `t_role_resource` VALUES ('547', '8', '11');
INSERT INTO `t_role_resource` VALUES ('549', '8', '12');
INSERT INTO `t_role_resource` VALUES ('551', '8', '13');
INSERT INTO `t_role_resource` VALUES ('553', '8', '14');
INSERT INTO `t_role_resource` VALUES ('548', '8', '111');
INSERT INTO `t_role_resource` VALUES ('550', '8', '121');
INSERT INTO `t_role_resource` VALUES ('552', '8', '131');
INSERT INTO `t_role_resource` VALUES ('554', '8', '141');
INSERT INTO `t_role_resource` VALUES ('563', '8', '221');
INSERT INTO `t_role_resource` VALUES ('560', '8', '222');
INSERT INTO `t_role_resource` VALUES ('561', '8', '223');
INSERT INTO `t_role_resource` VALUES ('562', '8', '224');
INSERT INTO `t_role_resource` VALUES ('564', '8', '227');
INSERT INTO `t_role_resource` VALUES ('565', '8', '228');
INSERT INTO `t_role_resource` VALUES ('566', '8', '229');
INSERT INTO `t_role_resource` VALUES ('555', '8', '230');
INSERT INTO `t_role_resource` VALUES ('556', '8', '231');
INSERT INTO `t_role_resource` VALUES ('557', '8', '232');
INSERT INTO `t_role_resource` VALUES ('558', '8', '233');
INSERT INTO `t_role_resource` VALUES ('559', '8', '234');

-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编码ID',
  `dict_type` varchar(64) DEFAULT NULL COMMENT '类别',
  `dict_desc` varchar(128) DEFAULT NULL COMMENT '描述',
  `dict_key` varchar(64) NOT NULL DEFAULT '' COMMENT '键',
  `dict_value` varchar(128) NOT NULL DEFAULT '' COMMENT '值',
  `seq` tinyint(2) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='字典';

-- ----------------------------
-- Records of t_sys_dict
-- ----------------------------
INSERT INTO `t_sys_dict` VALUES ('1', 'adminSex', '性别-男', '0', '男', '0', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('2', 'adminSex', '性别-女', '1', '女', '1', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('3', 'dbStatus', '数据库状态-失效', '0', '失效', '1', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('4', 'dbStatus', '数据库状态-正常', '1', '正常', '0', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('5', 'adminLocked', '锁定-否', '0', '否', '0', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('6', 'adminLocked', '锁定-是', '1', '是', '1', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('7', 'adminType', 'admin类型-管理员', '0', '管理员', '1', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('8', 'adminType', 'admin类型-用户', '1', '用户', '0', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('9', 'resourceType', '资源类型-菜单', '0', '菜单', '0', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('10', 'resourceType', '资源类型-按钮', '1', '按钮', '1', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('11', 'resourceOpened', '资源菜单打开状态-打开', '1', '打开', '0', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('12', 'resourceOpened', '资源菜单打开状态-关闭', '0', '关闭', '1', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('13', 'resourceOpenMode', '资源打开方式-ajax', 'ajax', 'ajax', '0', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('14', 'resourceOpenMode', '资源打开方式-iframe', 'iframe', 'iframe', '1', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('15', 'netCardAuto', '开机自启动-是', '1', '是', '0', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('16', 'netCardAuto', '开机自启动-否', '0', '否', '0', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('17', 'online', '摄像机是否在线-在线', '1', '在线', '1', '2019-08-10 16:00:01');
INSERT INTO `t_sys_dict` VALUES ('18', 'online', '摄像机是否在线-不在线', '0', '不在线', '0', '2019-08-10 16:00:01');

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `dream`.`persistent_logins` (`username`, `series`, `token`, `last_used`) VALUES ('admin', 'Xs3MX7/oR5IHVkhfPzaU7Q==', 'fBaJukt6mEliHgdISFiM+Q==', '2019-08-26 14:59:54');


-- ----------------------------
-- Table structure for t_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_log`;
CREATE TABLE `t_sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(20) DEFAULT NULL COMMENT '登陆名',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `operation` varchar(64) DEFAULT NULL COMMENT '操作',
  `class_method` varchar(100) NOT NULL COMMENT '类-方法',
  `content` varchar(2000) DEFAULT NULL COMMENT '内容',
  `client_ip` varchar(255) NOT NULL DEFAULT '' COMMENT '客户端ip',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='系统日志';
