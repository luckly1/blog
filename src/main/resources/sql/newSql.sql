
-- 创建数据库
create database `jantent` default character set utf8 collate utf8_general_ci;

use jantent;

DROP TABLE IF EXISTS `t_logs`;

CREATE TABLE `t_logs` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT comment '日志主键---自增主键' ,
  `action` varchar(100) DEFAULT NULL  comment '产生的动作',
  `data` varchar(2000) DEFAULT NULL  comment '产生的数据',
  `author_id` int(10) DEFAULT NULL comment '操作人id' ,
  `ip` varchar(20) DEFAULT NULL comment '所用的ip',
  `created` int(10) DEFAULT NULL comment '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 alter table t_logs comment '日志表';


-- 当使用此类sql时，要将建表语句里的主外键等等写全

-- alter table t_logs modify column id int(11) comment '日志主键---自增主键';----->自增没了
-- alter table t_logs modify column action varchar(100) comment '产生的动作';
-- alter table t_logs modify column data varchar(2000) comment '产生的数据';
-- alter table t_logs modify column author_id int(10) comment '操作人id';
-- alter table t_logs modify column ip varchar(20) comment '所用的ip';
-- alter table t_logs modify column created int(10) comment '创建时间';



DROP TABLE IF EXISTS `t_attach`;

CREATE TABLE `t_attach` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT comment '',
  `fname` varchar(100) NOT NULL DEFAULT '' comment '',
  `ftype` varchar(50) DEFAULT '' comment '',
  `fkey` varchar(100) NOT NULL DEFAULT '' comment '',
  `author_id` int(10) DEFAULT NULL comment '',
  `created` int(10) NOT NULL comment '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_comments`;

CREATE TABLE `t_comments` (
  `coid` int(10) unsigned NOT NULL AUTO_INCREMENT ,
  `cid` int(10) unsigned DEFAULT '0' ,
  `created` int(10) unsigned DEFAULT '0' ,
  `author` varchar(200) DEFAULT NULL ,
  `author_id` int(10) unsigned DEFAULT '0' ,
  `owner_id` int(10) unsigned DEFAULT '0' ,
  `mail` varchar(200) DEFAULT NULL ,
  `url` varchar(200) DEFAULT NULL ,
  `ip` varchar(64) DEFAULT NULL  ,
  `agent` varchar(200) DEFAULT NULL  ,
  `content` text ,
  `type` varchar(16) DEFAULT 'comment' ,
  `status` varchar(16) DEFAULT 'approved' ,
  `parent` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`coid`),
  KEY `cid` (`cid`),
  KEY `created` (`created`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_contents`;

CREATE TABLE `t_contents` (
  `cid` int(10) unsigned NOT NULL AUTO_INCREMENT ,
  `title` varchar(200) DEFAULT NULL ,
  `slug` varchar(200) DEFAULT NULL ,
  `created` int(10) unsigned DEFAULT '0' ,
  `modified` int(10) unsigned DEFAULT '0' ,
  `content` text COMMENT '内容文字',
  `author_id` int(10) unsigned DEFAULT '0' ,
  `type` varchar(16) DEFAULT 'post'  ,
  `status` varchar(16) DEFAULT 'publish' ,
  `tags` varchar(200) DEFAULT NULL ,
  `categories` varchar(200) DEFAULT NULL ,
  `thumbImg` varchar(512) ,
  `hits` int(10) unsigned DEFAULT '0' ,
  `comments_num` int(10) unsigned DEFAULT '0' ,
  `allow_comment` tinyint(1) DEFAULT '1' ,
  `allow_ping` tinyint(1) DEFAULT '1' ,
  `allow_feed` tinyint(1) DEFAULT '1' ,
  PRIMARY KEY (`cid`),
  UNIQUE KEY `slug` (`slug`),
  KEY `created` (`created`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_metas`;

CREATE TABLE `t_metas` (
  `mid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `slug` varchar(200) DEFAULT NULL,
  `type` varchar(32) NOT NULL DEFAULT '' ,
  `description` varchar(200) DEFAULT NULL ,
  `sort` int(10) unsigned DEFAULT '0',
  `parent` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`mid`),
  KEY `slug` (`slug`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `t_options`;

CREATE TABLE `t_options` (
  `name` varchar(32) NOT NULL DEFAULT '',
  `value` varchar(1000) DEFAULT '',
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_relationships`;

CREATE TABLE `t_relationships` (
  `cid` int(10) unsigned NOT NULL comment '内容主键',
  `mid` int(10) unsigned NOT NULL comment '项目主键',
  PRIMARY KEY (`cid`,`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table t_relationships comment '关联表';
-- alter table t_relationships modify column cid int(10) comment '内容主键';
-- alter table t_relationships modify column mid int(10) comment '项目主键';

DROP TABLE IF EXISTS `t_users`;

CREATE TABLE `t_users` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT comment '自增主键',
  `username` varchar(32) DEFAULT NULL comment '用户名称',
  `password` varchar(64) DEFAULT NULL comment '用户密码',
  `email` varchar(200) DEFAULT NULL comment '用户的邮箱',
  `home_url` varchar(200) DEFAULT NULL comment '用户的主页',
  `screen_name` varchar(32) DEFAULT NULL comment '用户显示的名称',
  `created` int(10) unsigned DEFAULT '0' comment '用户注册时的时间',
  `activated` int(10) unsigned DEFAULT '0' comment '最后活动时间' ,
  `logged` int(10) unsigned DEFAULT '0' comment '上次登录最后活跃时间',
  `group_name` varchar(16) DEFAULT 'visitor' comment '用户组' ,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`username`),
  UNIQUE KEY `mail` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


alter table t_users comment '管理人员表';
-- alter table t_users modify column uid int(11) comment 'id---自增主键';
-- alter table t_users modify column username varchar(32) comment '用户名称';
-- alter table t_users modify column password varchar(64) comment '用户密码';
-- alter table t_users modify column email varchar(200) comment '用户的邮箱';
-- alter table t_users modify column home_url varchar(200) comment '用户的主页';
-- alter table t_users modify column screen_name varchar(32) comment '用户显示的名称';
-- alter table t_users modify column created int(10) comment '用户注册时的时间';
-- alter table t_users modify column activated int(10) comment '最后活动时间';
-- alter table t_users modify column logged int(10) comment '上次登录最后活跃时间';
-- alter table t_users modify column group_name varchar(16) comment '用户组';



DROP TABLE IF EXISTS `t_blogusers`;
CREATE TABLE `t_blogusers` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT comment '自增主键',
  `username` varchar(32) DEFAULT NULL comment '用户名称',
  `password` varchar(64) DEFAULT NULL comment '用户密码',
  `email` varchar(200) DEFAULT NULL comment '用户的邮箱',
  `mobile_phone` varchar(32) DEFAULT NULL comment '用户手机',
  `status` int(10) DEFAULT 0  comment '用户状态(1、待通过 2、正常 3、停用)',
  `created` int(10) unsigned DEFAULT '0' comment '用户注册时的时间' ,
  `info_image` varchar(512) comment '用户图片路径',
  `type` varchar(16) comment '用户上传附件类型',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`username`),
  /*  UNIQUE KEY `phone` (`mobile_phone`),*/
  UNIQUE KEY `mail` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table t_blogusers comment '管理人员表';
-- alter table t_blogusers modify column uid int(10) unsigned NOT NULL AUTO_INCREMENT comment 'id---自增主键';
-- alter table t_blogusers modify column username varchar(32) comment '用户名称';
-- alter table t_blogusers modify column password varchar(64) comment '用户密码';
-- alter table t_blogusers modify column email varchar(200) comment '用户的邮箱';
-- alter table t_blogusers modify column mobile_phone varchar(200) comment '用户手机';
-- alter table t_blogusers modify column status varchar(32) comment '用户状态(1、待通过 2、正常 3、停用)';
-- alter table t_blogusers modify column created int(10) comment '用户注册时的时间';
-- alter table t_blogusers modify column info_image int(10) comment '用户图片路径';
-- alter table t_blogusers add column type varchar(16) default 'post' comment '用户上传附件类型';



INSERT INTO `t_users` (`uid`, `username`, `password`, `email`, `home_url`, `screen_name`, `created`, `activated`, `logged`, `group_name`)
VALUES
       (1, 'admin', 'a66abb5684c45962d887564f08346e8d', 'default@qq.com', NULL, 'admin', 1490756162, 0, 0, 'visitor');

