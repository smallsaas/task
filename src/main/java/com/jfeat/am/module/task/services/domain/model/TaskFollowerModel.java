package com.jfeat.am.module.task.services.domain.model;
import com.jfeat.am.module.task.services.persistence.model.TaskFollower;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.Team;

/**
 * Created by Code Generator on 2017-11-21
 */
public class TaskFollowerModel extends TaskFollower {
    Staff staff;
    Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
