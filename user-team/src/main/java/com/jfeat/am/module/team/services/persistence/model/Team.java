package com.jfeat.am.module.team.services.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Code Generator
 * @since 2018-11-26
 */
public class Team extends Model<Team> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 团队名称
     */
	private String teamName;
    /**
     * 团队描述
     */
	private String teamDesc;
    /**
     * 父级ID
     */
	private Long pid;
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

	public Team setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTeamName() {
		return teamName;
	}

	public Team setTeamName(String teamName) {
		this.teamName = teamName;
		return this;
	}

	public String getTeamDesc() {
		return teamDesc;
	}

	public Team setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc;
		return this;
	}

	public Long getPid() {
		return pid;
	}

	public Team setPid(Long pid) {
		this.pid = pid;
		return this;
	}

	public Long getOrgId() {
		return orgId;
	}

	public Team setOrgId(Long orgId) {
		this.orgId = orgId;
		return this;
	}

	public static final String ID = "id";

	public static final String TEAMNAME = "teamName";

	public static final String TEAMDESC = "teamDesc";

	public static final String PID = "pid";

	public static final String ORG_ID = "org_id";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Team{" +
			"id=" + id +
			", teamName=" + teamName +
			", teamDesc=" + teamDesc +
			", pid=" + pid +
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
