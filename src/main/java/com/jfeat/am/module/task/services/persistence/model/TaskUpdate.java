package com.jfeat.am.module.task.services.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
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
@TableName("task_update")
public class TaskUpdate extends Model<TaskUpdate> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 事件ID
     */
	private Long taskId;
    /**
     * 员工ID
     */
	private Long staffId;
    /**
     * 备注
     */
	private String note;
    /**
     * 附件
     */
	private String attachment;
    /**
     * 更新时间
     */
	private Date updateTime;
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

	public TaskUpdate setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getTaskId() {
		return taskId;
	}

	public TaskUpdate setTaskId(Long taskId) {
		this.taskId = taskId;
		return this;
	}

	public Long getStaffId() {
		return staffId;
	}

	public TaskUpdate setStaffId(Long staffId) {
		this.staffId = staffId;
		return this;
	}

	public String getNote() {
		return note;
	}

	public TaskUpdate setNote(String note) {
		this.note = note;
		return this;
	}

	public String getAttachment() {
		return attachment;
	}

	public TaskUpdate setAttachment(String attachment) {
		this.attachment = attachment;
		return this;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public TaskUpdate setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	public Long getOrgId() {
		return orgId;
	}

	public TaskUpdate setOrgId(Long orgId) {
		this.orgId = orgId;
		return this;
	}

	public static final String ID = "id";

	public static final String TASKID = "taskId";

	public static final String STAFFID = "staffId";

	public static final String NOTE = "note";

	public static final String ATTACHMENT = "attachment";

	public static final String UPDATETIME = "updateTime";

	public static final String ORG_ID = "org_id";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TaskUpdate{" +
			"id=" + id +
			", taskId=" + taskId +
			", staffId=" + staffId +
			", note=" + note +
			", attachment=" + attachment +
			", updateTime=" + updateTime +
			", orgId=" + orgId +
			"}";
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
