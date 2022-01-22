package com.jfeat.am.module.team.services.domain.dao;

import com.jfeat.am.module.team.services.domain.model.TeamModel;
import com.jfeat.am.module.team.services.persistence.model.Team;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Code Generator on 2017-11-20
 */
public interface QueryTeamDao extends BaseMapper<Team> {
    List<TeamModel> findTeamPage(Page<TeamModel> page, Team team);

    List<Team> teamsLeader(Page<Team> page, @Param("staffId")long staffId);

    @Select("SELECT count(id) FROM team WHERE id = #{teamId}")
    Integer checkTeamIsExist(@Param("teamId") Long teamId);

    @Select("SELECT COUNT(id) FROM task_follower WHERE teamId = #{teamId}")
    Integer checkTeamWithFollow(@Param("teamId") Long teamId);
}