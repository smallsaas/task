package com.jfeat.am.module.team.services.domain.model;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.Team;

import java.util.List;
/**
 * Created by Code Generator on 2017-11-20
 */
public class TeamModel extends Team {
    List<Staff> staffs;
    List<String> leaders;

    List<NameModel> teamStaffs;
    List<NameModel> teamLeaders;

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }

    public List<String> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<String> leaders) {
        this.leaders = leaders;
    }

    public List<NameModel> getTeamStaffs() {
        return teamStaffs;
    }

    public void setTeamStaffs(List<NameModel> teamStaffs) {
        this.teamStaffs = teamStaffs;
    }

    public List<NameModel> getTeamLeaders() {
        return teamLeaders;
    }

    public void setTeamLeaders(List<NameModel> teamLeaders) {
        this.teamLeaders = teamLeaders;
    }
}
