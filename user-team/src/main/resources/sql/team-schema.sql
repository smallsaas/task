
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `t_team`;
CREATE TABLE `team` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`team_name` varchar(255) NOT NULL COMMENT '团队名称',
`team_desc` text DEFAULT NULL COMMENT '团队描述',
`pid` bigint DEFAULT NULL COMMENT '父级ID',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
`version` int(11) NOT NULL DEFAULT 1 COMMENT '版本控制',
UNIQUE(`teamName`),
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_staff`;
CREATE TABLE `staff` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`first_name` varchar(255) NOT NULL COMMENT '名字',
`last_name` varchar(255) NOT NULL COMMENT '姓',
`dob` datetime DEFAULT NULL COMMENT '出身年月',
`sex` varchar(26) NOT NULL COMMENT '性别(F/M)',
`position` varchar(255) DEFAULT NULL COMMENT '职位',
`phone` varchar(255) DEFAULT NULL COMMENT '电话',
`email` varchar(255) DEFAULT NULL COMMENT '邮箱',
`wechat` varchar(255) DEFAULT NULL COMMENT '微信',
`qq` varchar(255) DEFAULT NULL COMMENT '企鹅',
`micro_blog` varchar(255) DEFAULT NULL COMMENT '微博',
`twitter` varchar(255) DEFAULT NULL COMMENT '推特',
`note` text DEFAULT NULL COMMENT '备注',
`avatar` varchar(255) DEFAULT NULL COMMENT '头像',
`user_id` bigint NOT NULL COMMENT 'UserId',
`is_valid` smallint(5) NOT NULL COMMENT '是否启用 0不启用 1启用',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
`version` int(11) NOT NULL DEFAULT 1 COMMENT '版本控制',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_team_staff`;
CREATE TABLE `staff_team` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`team_id` bigint NOT NULL COMMENT '团队Id',
`staff_id` bigint NOT NULL COMMENT '员工ID',
`is_leader` smallint NOT NULL COMMENT '领导者(0-N 1-Y)',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
