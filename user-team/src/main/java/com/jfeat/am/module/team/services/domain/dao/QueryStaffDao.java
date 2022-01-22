package com.jfeat.am.module.team.services.domain.dao;

import com.jfeat.am.module.team.services.domain.model.NameModel;
import com.jfeat.am.module.team.services.domain.model.SelfCenterRecord;
import com.jfeat.am.module.team.services.domain.model.StaffModel;
import com.jfeat.am.module.team.services.persistence.model.Staff;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jfeat.am.uaas.perm.services.persistence.model.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Code Generator on 2017-11-20
 */
public interface QueryStaffDao extends BaseMapper<Staff> {
    List<StaffModel> findStaffPage(Page<StaffModel> page, Staff staff);

    List<NameModel> searchStaffInTeam(long teamId);

    @Select("select count(id) from task_follower where task_follower.staffId=#{staffId}")
    Integer selectFollowerCount(@Param("staffId")Long staffId);

    @Select("select staff.* from staff where staff.userId=#{userId}")
    StaffModel getStaffModelByuserId(@Param("userId")Long userId);

    SelfCenterRecord selfCenter(@Param("id")Long id);

    List<SysRole> findSysRoleByUserId(@Param("userId") Long id);
}