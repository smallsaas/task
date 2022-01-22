package com.jfeat.am.module.team.services.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jfeat.am.module.team.services.service.StaffService;
import com.jfeat.am.module.team.services.domain.dao.QueryStaffDao;
import com.jfeat.am.module.team.services.domain.dao.QueryStaffTeamDao;
import com.jfeat.am.module.team.services.domain.model.StaffModel;
import com.jfeat.am.module.team.services.persistence.dao.StaffMapper;
import com.jfeat.am.module.team.services.persistence.dao.StaffTeamMapper;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.StaffTeam;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.am.power.base.PowerBusinessCode;
import com.jfeat.am.power.base.PowerBusinessException;
import com.jfeat.am.uaas.perm.services.persistence.model.SysRole;
import com.jfeat.am.uaas.system.modular.system.service.SysUserService;
import com.jfeat.am.uaas.system.module.services.persistence.dao.SysUserMapper;
import com.jfeat.am.uaas.system.module.services.persistence.dao.SysUserRoleMapper;
import com.jfeat.am.uaas.system.module.services.persistence.model.SysUser;
import com.jfeat.am.uaas.system.module.services.persistence.model.SysUserRole;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 员工表 implementation
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */
@Service
public class StaffServiceImpl extends CRUDServiceOnlyImpl<Staff> implements StaffService {

    @Resource
    private StaffMapper staffMapper;
    @Resource
    SysUserMapper userMapper;
    @Resource
    QueryStaffTeamDao queryStaffTeamDao;
    @Resource
    SysUserRoleMapper userRoleMapper;
    @Resource
    QueryStaffDao queryStaffDao;
    @Resource
    StaffTeamMapper staffTeamMapper;
    @Resource
    SysUserService sysUserService;


    @Override
    protected BaseMapper<Staff> getMasterMapper() {
        return staffMapper;
    }

    @Override
    public Staff queryStaffByUserId(long userId) {
        Staff staff = new Staff();
        staff.setUserId(userId);

        return staffMapper.selectOne(staff);
    }

    /**
     * 同时删除 user 表中对应的记录
     */
    @Transactional
    @Override
    public Integer deleteStaff(long id) {
        int affected = 0;
        Staff staff = staffMapper.selectById(id);
        SysUser user = userMapper.selectById(staff.getUserId());
        if (user.getAccount().compareTo("admin") == 0) {
            throw new PowerBusinessException(PowerBusinessCode.PowerTeamDeleteSuperStaffNotSupport);
        } else {
            if (queryStaffDao.selectFollowerCount(id) > 0) {
                throw new PowerBusinessException(PowerBusinessCode.PowerBusinessDeleteAssociatedOne);
            }
            affected += staffTeamMapper.delete(new EntityWrapper<StaffTeam>().eq("staffId", id));
            affected += staffMapper.deleteById(id);
            affected += userMapper.deleteById(user.getId());
            return affected;
        }
    }

    @Override
    @Transactional
    public Integer createStaffModel(StaffModel entity) {
        int affected = 0;
        SysUser sysUser = sysUserService.register(entity.getSysUser().getAccount(), entity.getSysUser().getName(), entity.getSysUser().getPassword());
        sysUser.setBirthday(entity.getDob());
        if(entity.getEmail()!=null && !"".equals(entity.getEmail().trim())){
            sysUser.setEmail(entity.getEmail());
        }
        if(entity.getPhone()!=null && !"".equals(entity.getPhone().trim())){
            sysUser.setPhone(entity.getPhone());
        }
        sysUser.setVersion(1);
        sysUser.setAvatar(entity.getAvatar());
        sysUserService.updateById(sysUser);
        List<SysRole> roles = entity.getRoles();
        if(!CollectionUtils.isEmpty(roles)){
            for (SysRole role : roles){
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId(role.getId());
                userRole.setUserId(sysUser.getId());
                userRoleMapper.insert(userRole);
            }
        }
        entity.setUserId(sysUser.getId());
        affected += staffMapper.insert(entity);
        return affected;
    }

    @Override
    public StaffModel getStaffModelByuserId(Long userId) {
        return queryStaffDao.getStaffModelByuserId(userId);
    }

    @Override
    @Transactional
    public StaffModel retrieveMaster(long id) {
        Staff staff = super.retrieveMaster(id);
        StaffModel staffModel = JSONObject.parseObject(JSONObject.toJSONString(staff), StaffModel.class);
        List<Team> teams = queryStaffTeamDao.getTeamsByStaffId(id);
        staffModel.setTeams(teams);
        SysUser sysUser = userMapper.selectById(staffModel.getUserId());
        staffModel.setSysUser(sysUser);
        List<SysRole> sysRoles = queryStaffDao.findSysRoleByUserId(sysUser.getId());
        staffModel.setRoles(sysRoles);
        return staffModel;
    }

    @Override
    @Transactional
    public Integer updateMaster(Staff staff) {
        int affected = 0;
        affected += super.updateMaster(staff);
        return affected;
    }

    @Override
    @Transactional
    public Integer updateStaffModel(StaffModel entity) {
        Integer affected = 0;
        affected += this.updateMaster(entity);
        // update roles
        Long userId = entity.getUserId();
        userRoleMapper.delete(new EntityWrapper<SysUserRole>().eq("user_id", userId));
        List<SysRole> roles = entity.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            for (SysRole role : roles) {
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId(role.getId());
                userRole.setUserId(userId);
                userRoleMapper.insert(userRole);
            }
        }

        return affected;
    }
}


