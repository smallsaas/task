package com.jfeat.am.module.team.services.domain.model;

import com.jfeat.am.module.team.services.persistence.model.Staff;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/2/22
 */
public class AllTeamIncludeStaff {
    String teamName;
    Long teamId;
    List<NameModel> staffs;



    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public List<NameModel> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<NameModel> staffs) {
        this.staffs = staffs;
    }
}
