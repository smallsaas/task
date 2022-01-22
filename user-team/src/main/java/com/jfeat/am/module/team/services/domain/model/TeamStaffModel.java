package com.jfeat.am.module.team.services.domain.model;

import com.jfeat.am.module.team.services.domain.model.StaffIdName;
import com.jfeat.am.module.team.services.persistence.model.Team;

import java.util.List;

/**
 * Created by vincenthuang on 02/03/2018.
 */
public class TeamStaffModel extends Team{
    private List<StaffIdName> staffs;

    public List<StaffIdName> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<StaffIdName> staffs) {
        this.staffs = staffs;
    }
}
