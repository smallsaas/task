package com.jfeat.am.module.task.services.domain.model;

import com.jfeat.am.module.team.services.domain.model.NameModel;
import com.jfeat.am.module.team.services.persistence.model.Team;

import java.util.List;

public class ContainStaff extends Team{
    List<NameModel> staffs;

    public List<NameModel> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<NameModel> staffs) {
        this.staffs = staffs;
    }
}
