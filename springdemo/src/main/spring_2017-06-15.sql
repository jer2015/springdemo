# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 172.16.6.103 (MySQL 5.7.11-log)
# Database: spring
# Generation Time: 2017-06-15 07:12:05 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

# Dump of table crontab
# ------------------------------------------------------------

DROP TABLE IF EXISTS `crontab`;

CREATE TABLE `crontab` (
  `id`         INT(3) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键id',
  `job_name`   VARCHAR(20)              DEFAULT NULL
  COMMENT '任务名称',
  `job_group`  VARCHAR(20)              DEFAULT NULL
  COMMENT '任务组',
  `job_status` INT(1)                   DEFAULT NULL
  COMMENT '任务状态:0禁用,1启用,2删除',
  `job_cron`   VARCHAR(20)              DEFAULT NULL
  COMMENT '任务运行时间表达式',
  `job_desc`   VARCHAR(20)              DEFAULT NULL
  COMMENT '任务描述',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

LOCK TABLES `crontab` WRITE;
/*!40000 ALTER TABLE `crontab`
  DISABLE KEYS */;

INSERT INTO `crontab` (`id`, `job_name`, `job_group`, `job_status`, `job_cron`, `job_desc`)
VALUES
  (1, 'TEST_JOB', 'task group', 1, '0/10 * * * * ?', '测试任务');

/*!40000 ALTER TABLE `crontab`
  ENABLE KEYS */;
UNLOCK TABLES;

# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id`          INT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(10)              DEFAULT NULL,
  `age`         INT(3)                   DEFAULT NULL,
  `birthday`    DATE                     DEFAULT NULL,
  `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME                 DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user`
  DISABLE KEYS */;

INSERT INTO `user` (`id`, `name`, `age`, `birthday`, `create_time`, `update_time`)
VALUES
  (1, '用户A', 18, '2000-01-01', '2017-06-15 11:16:28', '2017-06-15 15:04:40');

/*!40000 ALTER TABLE `user`
  ENABLE KEYS */;
UNLOCK TABLES;


/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
