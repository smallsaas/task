package com.jfeat.am.module.team.services.domain.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.team.services.persistence.model.StaffTeam;
import com.jfeat.am.module.team.services.persistence.model.Team;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by Code Generator on 2017-11-20
 */
public interface QueryStaffTeamDao extends BaseMapper<StaffTeam> {
    List<StaffTeam> findStaffteamPage(Page<StaffTeam> page, StaffTeam staffTeam);

    @Update("UPDATE user_role set roleid = #{roleId} WHERE userid = #{userId}")
    Integer updateUserRole(@Param("userId") long userId,@Param("roleId") long roleId);
    List<Team> getTeamsLedByStaff(@Param("staffId") Long staffId);
    List<Team> getTeamsByStaffId(@Param("staffId") Long staffId);
}