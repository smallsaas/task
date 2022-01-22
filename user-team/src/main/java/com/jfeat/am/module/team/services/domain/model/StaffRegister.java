package com.jfeat.am.module.team.services.domain.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Administrator on 2017/12/1.
 */
public class StaffRegister {
    @NotEmpty
    @Length(min = 1,max = 16)
    private String registerName;
    @NotEmpty
    @Length(min = 6,max = 50)
    private String password;
    @NotEmpty
    @Length(min = 1)
    private String firstName;
    @NotEmpty
    @Length(min = 1)
    private String lastName;
    @NotEmpty
    private String sex;
    @NotNull
    private List<Long> roleId;

    public List<Long> getRoleId() {
        return roleId;
    }

    public void setRoleId(List<Long> roleId) {
        this.roleId = roleId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
