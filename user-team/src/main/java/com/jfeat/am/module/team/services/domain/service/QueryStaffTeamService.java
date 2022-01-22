package com.jfeat.am.module.team.services.domain.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.team.services.domain.model.NameModel;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.StaffTeam;
import com.jfeat.am.module.team.services.persistence.model.Team;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface QueryStaffTeamService {
    List<StaffTeam> findStaffTeamPage(Page<StaffTeam> page, StaffTeam staffTeam);

    /**
     * 取得个人所属的Team
     * @param staffId
     * @return
     */
    List<Team> getTeamsLedByStaff(long staffId);

    List<Staff> getTeamLeaders(long teamId);

    // 工作组 Add Team 复选框中，根据团队名称查找用户，根据用户查找团队名称， 或者根据两者结合查找用户
    List<NameModel> searchByStaffOrTeamName(Page<NameModel> page, String teamName , String staffName);
}