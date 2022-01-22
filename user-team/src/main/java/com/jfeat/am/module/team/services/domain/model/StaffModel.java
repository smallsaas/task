package com.jfeat.am.module.team.services.domain.model;

import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.am.uaas.perm.services.persistence.model.SysRole;
import com.jfeat.am.uaas.system.module.services.persistence.model.SysUser;

import java.util.List;

/**
 * Created by Code Generator on 2017-11-20
 */
public class StaffModel extends Staff {
    List<Team> teams;
    List<SysRole> roles;
    private SysUser sysUser;
    private String universalName;

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public SysUser getSysUser() { return sysUser; }

    public void setSysUser(SysUser sysUser) { this.sysUser = sysUser; }

    public String getUniversalName() {
        return universalName;
    }

    public void setUniversalName(String universalName) {
        this.universalName = universalName;
    }
}
