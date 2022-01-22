package com.jfeat.am.module.team.services.persistence.model;

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
 * @since 2018-11-26
 */
public class Staff extends Model<Staff> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 名字
     */
	private String firstName;
    /**
     * 姓
     */
	private String lastName;
    /**
     * 出身年月
     */
	@TableField("DOB")
	private Date dob;
    /**
     * 性别(F/M)
     */
	private String sex;
    /**
     * 职位
     */
	private String postPosition;
    /**
     * 电话
     */
	private String phone;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 微信
     */
	private String weChat;
    /**
     * 企鹅
     */
	private String qq;
    /**
     * 微博
     */
	private String microBlog;
    /**
     * 推特
     */
	private String twitter;
    /**
     * 备注
     */
	private String note;
    /**
     * 头像
     */
	private String avatar;
    /**
     * 保留字段
     */
	private String field2;
    /**
     * UserId
     */
	private Long userId;
	/**
	 * 是否启用 0不启用 1启用
	 */
	private Integer isValid;
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

	public Staff setId(Long id) {
		this.id = id;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public Staff setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Staff setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public Date getDob() {
		return dob;
	}

	public Staff setDob(Date dob) {
		this.dob = dob;
		return this;
	}

	public String getSex() {
		return sex;
	}

	public Staff setSex(String sex) {
		this.sex = sex;
		return this;
	}

	public String getPostPosition() {
		return postPosition;
	}

	public Staff setPostPosition(String postPosition) {
		this.postPosition = postPosition;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public Staff setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Staff setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getWeChat() {
		return weChat;
	}

	public Staff setWeChat(String weChat) {
		this.weChat = weChat;
		return this;
	}

	public String getQq() {
		return qq;
	}

	public Staff setQq(String qq) {
		this.qq = qq;
		return this;
	}

	public String getMicroBlog() {
		return microBlog;
	}

	public Staff setMicroBlog(String microBlog) {
		this.microBlog = microBlog;
		return this;
	}

	public String getTwitter() {
		return twitter;
	}

	public Staff setTwitter(String twitter) {
		this.twitter = twitter;
		return this;
	}

	public String getNote() {
		return note;
	}

	public Staff setNote(String note) {
		this.note = note;
		return this;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getField2() {
		return field2;
	}

	public Staff setField2(String field2) {
		this.field2 = field2;
		return this;
	}

	public Long getUserId() {
		return userId;
	}

	public Staff setUserId(Long userId) {
		this.userId = userId;
		return this;
	}

	public Long getOrgId() {
		return orgId;
	}

	public Staff setOrgId(Long orgId) {
		this.orgId = orgId;
		return this;
	}

	public static final String ID = "id";

	public static final String FIRSTNAME = "firstName";

	public static final String LASTNAME = "lastName";

	public static final String DOB = "DOB";

	public static final String SEX = "sex";

	public static final String POSTPOSITION = "postPosition";

	public static final String PHONE = "phone";

	public static final String EMAIL = "email";

	public static final String WECHAT = "weChat";

	public static final String QQ = "qq";

	public static final String MICROBLOG = "microBlog";

	public static final String TWITTER = "twitter";

	public static final String NOTE = "note";

	public static final String FIELD2 = "field2";

	public static final String USERID = "userId";

	public static final String ORG_ID = "org_id";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Staff{" +
			"id=" + id +
			", firstName=" + firstName +
			", lastName=" + lastName +
			", dob=" + dob +
			", sex=" + sex +
			", postPosition=" + postPosition +
			", phone=" + phone +
			", email=" + email +
			", weChat=" + weChat +
			", qq=" + qq +
			", microBlog=" + microBlog +
			", twitter=" + twitter +
			", note=" + note +
			", avatar=" + avatar +
			", field2=" + field2 +
			", userId=" + userId +
			", orgId=" + orgId +
			"}";
	}

	public Integer getIsValid() {
		return isValid;
	}

	public Staff setIsValid(Integer isValid) {
		this.isValid = isValid;
		return this;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
