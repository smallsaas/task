package com.jfeat.am.module.team.services.domain.model;

import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.Team;

import java.util.List;

public class TeamAndStaffModel extends Team{
    List<Long> staffs;
    List<Long> leaders;

    public List<Long> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Long> staffs) {
        this.staffs = staffs;
    }

    public List<Long> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<Long> leaders) {
        this.leaders = leaders;
    }
}
