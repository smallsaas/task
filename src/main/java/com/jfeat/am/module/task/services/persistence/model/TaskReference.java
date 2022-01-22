package com.jfeat.am.module.task.services.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Code Generator
 * @since 2018-11-27
 */
@TableName("task_reference")
public class TaskReference extends Model<TaskReference> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 事件ID
     */
	private Long taskId;
    /**
     * 参考名称
     */
	private String referenceName;
    /**
     * 参考事件ID
     */
	private String referenceId;
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

	public TaskReference setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getTaskId() {
		return taskId;
	}

	public TaskReference setTaskId(Long taskId) {
		this.taskId = taskId;
		return this;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public TaskReference setReferenceName(String referenceName) {
		this.referenceName = referenceName;
		return this;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public TaskReference setReferenceId(String referenceId) {
		this.referenceId = referenceId;
		return this;
	}

	public Long getOrgId() {
		return orgId;
	}

	public TaskReference setOrgId(Long orgId) {
		this.orgId = orgId;
		return this;
	}

	public static final String ID = "id";

	public static final String TASKID = "taskId";

	public static final String REFERENCENAME = "referenceName";

	public static final String REFERENCEID = "referenceId";

	public static final String ORG_ID = "org_id";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TaskReference{" +
			"id=" + id +
			", taskId=" + taskId +
			", referenceName=" + referenceName +
			", referenceId=" + referenceId +
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
