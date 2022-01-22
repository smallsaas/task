package com.jfeat.am.module.team.services.domain.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.team.services.domain.dao.QueryStaffDao;
import com.jfeat.am.module.team.services.domain.dao.QueryStaffTeamDao;
import com.jfeat.am.module.team.services.domain.model.NameModel;
import com.jfeat.am.module.team.services.domain.model.SelfCenterRecord;
import com.jfeat.am.module.team.services.domain.model.StaffModel;
import com.jfeat.am.module.team.services.domain.service.QueryStaffService;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.am.uaas.perm.services.persistence.model.SysRole;
import com.jfeat.am.uaas.system.module.services.persistence.dao.SysUserMapper;
import com.jfeat.am.uaas.system.module.services.persistence.model.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */
@Service
public class QueryStaffServiceImpl implements QueryStaffService {

    @Resource
    QueryStaffDao queryStaffDao;
    @Resource
    QueryStaffTeamDao queryStaffTeamDao;
    @Resource
    SysUserMapper userMapper;

    @Override
    public List<StaffModel> findStaffPage(Page<StaffModel> page, Staff staff) {
        List<StaffModel> staffPage = queryStaffDao.findStaffPage(page, staff);
        for(StaffModel staffModel : staffPage){
            List<Team> teams = queryStaffTeamDao.getTeamsByStaffId(staffModel.getId());
            staffModel.setTeams(teams);
            SysUser sysUser = userMapper.selectById(staffModel.getUserId());
            staffModel.setSysUser(sysUser);
            List<SysRole> sysRoles = queryStaffDao.findSysRoleByUserId(sysUser.getId());
            staffModel.setRoles(sysRoles);
        }
        return staffPage;
    }

    /**
    *  查找某个组中的所有成员
     *  @param teamId
    * */
    @Override
    public List<NameModel> searchStaffInTeam(long teamId) {
        return queryStaffDao.searchStaffInTeam(teamId);
    }

    // ID 返回 staff 的信息

    public SelfCenterRecord selfCenterRecord(long id){
        return queryStaffDao.selfCenter(id);
    }

}
