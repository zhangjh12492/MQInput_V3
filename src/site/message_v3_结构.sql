/*
SQLyog 企业版 - MySQL GUI v7.14 
MySQL - 5.5.38 : Database - message_v3
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`message_v3` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `message_v3`;

/*Table structure for table `dic_items` */

DROP TABLE IF EXISTS `dic_items`;

CREATE TABLE `dic_items` (
  `sid` int(10) NOT NULL AUTO_INCREMENT,
  `dic_name` varchar(50) DEFAULT NULL,
  `dic_item` varchar(50) DEFAULT NULL,
  `dic_value` varchar(50) DEFAULT NULL,
  `dic_value_second` int(10) DEFAULT NULL,
  `status` int(10) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

/*Table structure for table `dic_names` */

DROP TABLE IF EXISTS `dic_names`;

CREATE TABLE `dic_names` (
  `sid` int(10) NOT NULL AUTO_INCREMENT,
  `dic_name` varchar(50) DEFAULT NULL,
  `dic_type` int(10) DEFAULT NULL,
  `dic_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Table structure for table `exchange_conf` */

DROP TABLE IF EXISTS `exchange_conf`;

CREATE TABLE `exchange_conf` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `exchange_name` varchar(50) DEFAULT NULL,
  `exchange_desc` varchar(50) DEFAULT NULL,
  `exchange_type` int(10) DEFAULT NULL,
  `status` int(10) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `group_conf` */

DROP TABLE IF EXISTS `group_conf`;

CREATE TABLE `group_conf` (
  `sid` int(10) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) DEFAULT NULL,
  `group_desc` varchar(100) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `inbound_conf` */

DROP TABLE IF EXISTS `inbound_conf`;

CREATE TABLE `inbound_conf` (
  `sid` int(10) NOT NULL AUTO_INCREMENT,
  `system_no` varchar(20) DEFAULT NULL,
  `service_no` varchar(20) DEFAULT NULL,
  `exchange_type` int(10) DEFAULT NULL,
  `route_key` varchar(50) DEFAULT NULL,
  `inbound_desc` varchar(50) DEFAULT NULL,
  `group_sid` int(10) DEFAULT NULL,
  `max_length` int(10) DEFAULT NULL,
  `exchange_conf_sid` int(10) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

/*Table structure for table `inbound_queue_ref` */

DROP TABLE IF EXISTS `inbound_queue_ref`;

CREATE TABLE `inbound_queue_ref` (
  `sid` int(10) NOT NULL AUTO_INCREMENT,
  `inbound_conf_sid` int(10) DEFAULT NULL,
  `queue_conf_sid` int(10) DEFAULT NULL,
  `route_key` varchar(50) DEFAULT NULL,
  `memo` varchar(100) DEFAULT NULL,
  `exchange_conf_sid` int(10) DEFAULT NULL,
  `status` int(10) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

/*Table structure for table `menu_list` */

DROP TABLE IF EXISTS `menu_list`;

CREATE TABLE `menu_list` (
  `sid` int(10) NOT NULL AUTO_INCREMENT,
  `parent_id` int(10) NOT NULL,
  `menu_name` varchar(50) NOT NULL,
  `menu_desc` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `message_log` */

DROP TABLE IF EXISTS `message_log`;

CREATE TABLE `message_log` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` varchar(50) DEFAULT NULL,
  `message_type` int(10) DEFAULT NULL,
  `content` mediumtext,
  `create_time` datetime DEFAULT NULL,
  `tx_info_sid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=2009527 DEFAULT CHARSET=utf8;

/*Table structure for table `outbound_conf` */

DROP TABLE IF EXISTS `outbound_conf`;

CREATE TABLE `outbound_conf` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `outbound_name` varchar(50) DEFAULT NULL,
  `outbound_desc` varchar(50) DEFAULT NULL,
  `ws_type` int(10) DEFAULT NULL,
  `outbound_url` varchar(100) DEFAULT NULL,
  `status` int(10) DEFAULT NULL,
  `timeout` int(10) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Table structure for table `permit` */

DROP TABLE IF EXISTS `permit`;

CREATE TABLE `permit` (
  `sid` int(10) NOT NULL,
  `process` int(5) NOT NULL,
  `per_desc` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `queue_conf` */

DROP TABLE IF EXISTS `queue_conf`;

CREATE TABLE `queue_conf` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `queue_name` varchar(50) DEFAULT NULL,
  `queue_desc` varchar(50) DEFAULT NULL,
  `outbound_conf_sid` bigint(20) DEFAULT NULL,
  `status` int(10) DEFAULT NULL,
  `max_num` int(10) DEFAULT NULL,
  `group_sid` int(10) DEFAULT NULL,
  `queue_type` int(10) DEFAULT NULL COMMENT '0-默认点对点 1-topic类型',
  `queue_listen_type` int(10) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  KEY `NewIndex1` (`queue_name`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `sid` int(10) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  `role_desc` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `role_menu_ref` */

DROP TABLE IF EXISTS `role_menu_ref`;

CREATE TABLE `role_menu_ref` (
  `sid` int(10) NOT NULL AUTO_INCREMENT,
  `role_id` int(10) NOT NULL,
  `menu_id` int(10) NOT NULL,
  `status` int(10) NOT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Table structure for table `role_permit_ref` */

DROP TABLE IF EXISTS `role_permit_ref`;

CREATE TABLE `role_permit_ref` (
  `sid` int(10) NOT NULL,
  `role_id` int(10) NOT NULL,
  `per_id` int(10) NOT NULL,
  `ref_desc` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `role_user_ref` */

DROP TABLE IF EXISTS `role_user_ref`;

CREATE TABLE `role_user_ref` (
  `sid` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `role_id` int(10) NOT NULL,
  `status` int(1) DEFAULT NULL,
  `ref_desc` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Table structure for table `server_conf` */

DROP TABLE IF EXISTS `server_conf`;

CREATE TABLE `server_conf` (
  `sid` int(10) NOT NULL AUTO_INCREMENT,
  `group_sid` int(10) DEFAULT NULL,
  `server_ip` varchar(20) DEFAULT NULL,
  `server_port` int(11) DEFAULT NULL,
  `type` int(10) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tx_info` */

DROP TABLE IF EXISTS `tx_info`;

CREATE TABLE `tx_info` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` varchar(50) DEFAULT NULL,
  `queue_conf_sid` int(10) DEFAULT NULL,
  `status` int(10) DEFAULT NULL,
  `in_time` datetime DEFAULT NULL,
  `out_time` datetime DEFAULT NULL,
  `system_no` varchar(50) DEFAULT NULL,
  `service_no` varchar(50) DEFAULT NULL,
  `retry_times` int(10) DEFAULT NULL,
  `exchange_type` int(10) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  KEY `NewIndex1` (`message_id`),
  KEY `NewIndex2` (`queue_conf_sid`),
  KEY `NewIndex3` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=2006967 DEFAULT CHARSET=utf8;

/*Table structure for table `tx_info_log` */

DROP TABLE IF EXISTS `tx_info_log`;

CREATE TABLE `tx_info_log` (
  `sid` bigint(20) NOT NULL,
  `content` text,
  `message_id` varchar(50) DEFAULT NULL,
  `queue_conf_sid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `sid` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
