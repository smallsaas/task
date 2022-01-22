package com.jfeat.am.module.team.services.domain.model;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.StaffTeam;
import com.jfeat.am.module.team.services.persistence.model.Team;

/**
 * Created by Code Generator on 2017-11-20
 */
public class StaffTeamModel extends StaffTeam {
    Staff staff;
    Team team;


    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
