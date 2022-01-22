package com.jfeat.am.module.task.services.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Code Generator
 * @since 2018-11-27
 */
public class Task extends Model<Task> {
	public static final String tableName = "task";
    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 事件类型
     */
	private String taskType;
    /**
     * 创建者ID
     */
	private Long createByStaffId;
    /**
     * 事件编号
     */
	private String taskNumber;
    /**
     * ownerId
     */
	private Long ownerByStaffId;
    /**
     * ownByTeamId
     */
	private Long ownerByTeamId;
    /**
     * 事件名称
     */
	private String taskName;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 关闭时间
     */
	private Date closeTime;
    /**
     * 状态
     */
	private String status;
    /**
     * 优先级 0/1/2 低 中 高
     */
	private Integer priority;
    /**
     * 描述
     */
	private String desc;
    /**
     * 开始时间
     */
	private Date startTime;
    /**
     * 提醒时间
     */
	private Date noticeTime;
    /**
     * 到期时间
     */
	private Date deadline;
    /**
     * 客服记录
     */
	private Long communicationRecordId;
    /**
     * 组织(部门)ID
     */
	@TableField("org_id")
	private Long orgId;
	/**
	 * 版本控制
	 */
	@Version
	private Integer version;

	public Long getId() {
		return id;
	}

	public Task setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTaskType() {
		return taskType;
	}

	public Task setTaskType(String taskType) {
		this.taskType = taskType;
		return this;
	}

	public Long getCreateByStaffId() {
		return createByStaffId;
	}

	public Task setCreateByStaffId(Long createByStaffId) {
		this.createByStaffId = createByStaffId;
		return this;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public Task setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
		return this;
	}

	public Long getOwnerByStaffId() {
		return ownerByStaffId;
	}

	public Task setOwnerByStaffId(Long ownerByStaffId) {
		this.ownerByStaffId = ownerByStaffId;
		return this;
	}

	public Long getOwnerByTeamId() {
		return ownerByTeamId;
	}

	public Task setOwnerByTeamId(Long ownerByTeamId) {
		this.ownerByTeamId = ownerByTeamId;
		return this;
	}

	public String getTaskName() {
		return taskName;
	}

	public Task setTaskName(String taskName) {
		this.taskName = taskName;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Task setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public Task setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public Task setStatus(String status) {
		this.status = status;
		return this;
	}

	public Integer getPriority() {
		return priority;
	}

	public Task setPriority(Integer priority) {
		this.priority = priority;
		return this;
	}

	public String getDesc() {
		return desc;
	}

	public Task setDesc(String desc) {
		this.desc = desc;
		return this;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Task setStartTime(Date startTime) {
		this.startTime = startTime;
		return this;
	}

	public Date getNoticeTime() {
		return noticeTime;
	}

	public Task setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
		return this;
	}

	public Date getDeadline() {
		return deadline;
	}

	public Task setDeadline(Date deadline) {
		this.deadline = deadline;
		return this;
	}

	public Long getCommunicationRecordId() {
		return communicationRecordId;
	}

	public Task setCommunicationRecordId(Long communicationRecordId) {
		this.communicationRecordId = communicationRecordId;
		return this;
	}


	public Long getOrgId() {
		return orgId;
	}

	public Task setOrgId(Long orgId) {
		this.orgId = orgId;
		return this;
	}

	public static final String ID = "id";

	public static final String TASKTYPE = "taskType";

	public static final String CREATEBYSTAFFID = "createByStaffId";

	public static final String TASKNUMBER = "taskNumber";

	public static final String OWNERBYSTAFFID = "ownerByStaffId";

	public static final String OWNERBYTEAMID = "ownerByTeamId";

	public static final String TASKNAME = "taskName";

	public static final String CREATETIME = "createTime";

	public static final String CLOSETIME = "closeTime";

	public static final String STATUS = "status";

	public static final String PRIORITY = "priority";

	public static final String DESC = "desc";

	public static final String STARTTIME = "startTime";

	public static final String NOTICETIME = "noticeTime";

	public static final String DEADLINE = "deadline";

	public static final String COMMUNICATIONRECORDID = "communicationRecordId";

	public static final String ATTACHMENT = "attachment";

	public static final String ORG_ID = "org_id";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Task{" +
			"id=" + id +
			", taskType=" + taskType +
			", createByStaffId=" + createByStaffId +
			", taskNumber=" + taskNumber +
			", ownerByStaffId=" + ownerByStaffId +
			", ownerByTeamId=" + ownerByTeamId +
			", taskName=" + taskName +
			", createTime=" + createTime +
			", closeTime=" + closeTime +
			", status=" + status +
			", priority=" + priority +
			", desc=" + desc +
			", startTime=" + startTime +
			", noticeTime=" + noticeTime +
			", deadline=" + deadline +
			", communicationRecordId=" + communicationRecordId +
			", orgId=" + orgId +
			"}";
	}

	public Integer getVersion() {
		return version;
	}

	public Task setVersion(Integer version) {
		this.version = version;
		return this;
	}
}
