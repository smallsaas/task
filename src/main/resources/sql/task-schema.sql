SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`taskType` varchar(255) NOT NULL COMMENT '事件类型',
`createByStaffId` bigint NOT NULL comment '创建者ID',
`taskNumber` varchar(26) not null comment '事件编号',
`ownerByStaffId` bigint not NULL COMMENT 'ownerId',
`ownerByTeamId` bigint default NULL COMMENT 'ownByTeamId',
`taskName` varchar(255) NOT NULL COMMENT '事件名称',
`createTime` datetime NOT NULL COMMENT '创建时间',
`closeTime` datetime default NULL COMMENT '关闭时间',
`status` VARCHAR(20) default NULL COMMENT '状态',
`priority` INT NOT NULL comment  '优先级 0/1/2 低 中 高',
`desc` text default NULL COMMENT '描述',
`startTime` datetime default NULL COMMENT '开始时间',
`noticeTime` datetime default NULL COMMENT '提醒时间',
`deadline` datetime default NULL COMMENT '到期时间',
`communicationRecordId` bigint default NULL COMMENT '客服记录',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
`version` int(11) NOT NULL DEFAULT 1 COMMENT '版本控制',
 unique(`taskNumber`),
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `task_reference`;
CREATE TABLE `task_reference` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`taskId` bigint NOT NULL COMMENT '事件ID',
`referenceName` varchar(255) NOT NULL COMMENT '参考名称',
`referenceId` varchar(26)  NOT NULL COMMENT '参考事件ID',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
`version` int(11) NOT NULL DEFAULT 1 COMMENT '版本控制',
UNIQUE KEY (`taskId`,`referenceName`,`referenceId`),
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `task_update`;
CREATE TABLE `task_update` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`taskId` bigint NOT NULL COMMENT '事件ID',
`staffId` bigint NOT NULL COMMENT '员工ID',
`note` text default NULL COMMENT '备注',
`attachment` varchar(255) default NULL COMMENT '附件',
`updateTime` datetime default NULL COMMENT '更新时间',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
`version` int(11) NOT NULL DEFAULT 1 COMMENT '版本控制',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `task_follower`;
CREATE TABLE `task_follower` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`teamId` bigint NOT NULL COMMENT '团队Id',
`taskId` bigint NOT NULL COMMENT '事件ID',
`staffId` bigint default NULL COMMENT '员工ID',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
`version` int(11) NOT NULL DEFAULT 1 COMMENT '版本控制',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
