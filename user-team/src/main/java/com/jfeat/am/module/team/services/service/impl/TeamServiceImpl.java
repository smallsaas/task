package com.jfeat.am.module.team.services.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jfeat.am.common.exception.BusinessException;
import com.jfeat.am.module.team.services.service.StaffService;
import com.jfeat.am.module.team.services.service.StaffTeamService;
import com.jfeat.am.module.team.services.service.TeamService;
import com.jfeat.am.module.team.services.domain.dao.QueryTeamDao;
import com.jfeat.am.module.team.services.domain.model.*;
import com.jfeat.am.module.team.services.persistence.dao.StaffMapper;
import com.jfeat.am.module.team.services.persistence.dao.StaffTeamMapper;
import com.jfeat.am.module.team.services.persistence.dao.TeamMapper;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.StaffTeam;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.am.power.base.PowerBusinessCode;
import com.jfeat.am.power.base.PowerBusinessException;
import com.jfeat.am.power.base.naming.UniversalName;
import com.jfeat.crud.plus.CRUD;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 团队 implementation
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */
@Service
public class TeamServiceImpl extends CRUDServiceOnlyImpl<Team> implements TeamService {


    @Resource
    StaffService staffService;
    @Resource
    StaffTeamService staffTeamService;
    @Resource
    StaffTeamMapper staffTeamMapper;
    @Resource
    TeamMapper teamMapper;


    /**
     *  // 判断某个 Staff 在某个 Team 中是否是该 Team 的 Leader 通过 staffId 以及 TeamId 去查找
     * */
    @Override
    public boolean isStaffTeamLeader(long teamId, long staffId){
        StaffTeam staffTeam = new StaffTeam();
        staffTeam.setTeamId(teamId);
        staffTeam.setStaffId(staffId);

        StaffTeam leader = staffTeamMapper.selectOne(staffTeam);
        if(leader==null || leader.getIsLeader()==null){
            return false;
        }

        return leader.getIsLeader()==1;
    }

/*    //判断某个Team是否有Leader
    public void  isHasTeamLeader(long teamId){
        //查找出属于该Team的所有记录
        List<StaffTeam> staffTeams = staffTeamMapper.selectList(new EntityWrapper<StaffTeam>().eq("teamId",teamId));
        for (StaffTeam staffTeam : staffTeams){
            if((staffTeam.getIsLeader().compareTo(1)) == 0){
                throw new BusinessException(2012,"The team already has Leaders!");
            }
        }
    }*/

    // 判断某个团队是否有 Staff  如果存在 Staff 无法执行删除操作
    /**
     *
     * */
    @Override
    public void judgeWhetherStaffInTeam(long id){
        List<StaffTeam> staffs = staffTeamMapper.selectList(new EntityWrapper<StaffTeam>().eq("teamId",id));
        if(staffs.size() > 0){
            throw new BusinessException(2015,"Can not executed that operation!");
        }
    }

    @Override
    public StaffTeam setStaffToTeamLeader(long teamId, Long staffId) {
        return null;
    }

    /**
     *   批量添加Staff到某个team  批量提交的时候，所有的Staff默认都不是领导
     * */
    @Override
    public Integer batchAddStaffToTeam(long teamId, List<Long> ids){
        int result = 0 ;
        for(Long id :ids){

            //通过TeamId 以及 StaffId 判断是否已经在该Team内
            Integer isInTeam = staffTeamMapper.selectCount(new EntityWrapper<StaffTeam>().
                    eq("teamId",teamId).eq("staffId",id));
            if((isInTeam.compareTo(0) == 0)){
                // 不在该组的时候，允许插入该Team
                StaffTeam staffTeam = new StaffTeam();
                staffTeam.setStaffId(id);
                staffTeam.setTeamId(teamId);
                staffTeam.setIsLeader(0);
                staffTeamMapper.insert(staffTeam);
                result++;
            }else{
            }
        }
        return result;
    }

    /**
     *某个Team批量删除Staff
     */
    @Override
    public Integer batchDeleteStaffToTeam(Long teamId, List<Long> ids){
        int result = 0 ;
        for(Long id :ids){

            //通过TeamId 以及 StaffId 判断是否已经在改Team内
            Integer isInTeam = staffTeamMapper.selectCount(new EntityWrapper<StaffTeam>().
                    eq("teamId",teamId).eq("staffId",id));
            if((isInTeam.compareTo(1) == 0)){
                Map<String,Object> map = new HashMap<>();
                map.put("teamId",teamId);
                map.put("staffId",id);
                staffTeamMapper.deleteByMap(map);
                result++;
            }else{

            }
        }
        return result;
    }

    /**
     * 判断某个Staff是否在某个Team
     * */
    @Override
    public Boolean isStaffInTeam(long teamId, long staffId) {
        //判断该员工是否在该组里面，如果没有，插入该员工到改组里面
        Integer isInTeam = staffTeamMapper.selectCount(new EntityWrapper<StaffTeam>().
                eq("teamId", teamId).eq("staffId", staffId));
        if (isInTeam.compareTo(0) != 0) {
            return true;
        }
        return false;
    }



    /**
     * //判断某个Staff是否在某个Team
     * */
    @Override
    public Integer deleteStaffInTeam(long teamId, long staffId) {
        //判断该员工是否在该组里面  如果没有，不能执行删除操作
        Integer isInTeam = staffTeamMapper.selectCount(new EntityWrapper<StaffTeam>().
                eq("teamId", teamId).eq("staffId", staffId));
        if (isInTeam.compareTo(0) == 0) {
            return null;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("teamId",teamId);
        map.put("staffId",staffId);
        return staffTeamMapper.deleteByMap(map);
    }

    /**
     *    // 我的所有团队
     * */
    @Override
    public List<StaffTeamModel> allMyTeam(long staffId){
        // 找出所有与我有关的团队
        List<StaffTeam> staffTeams = staffTeamMapper.selectList(new EntityWrapper<StaffTeam>().eq("staffId",staffId));
        List<StaffTeamModel> models = new ArrayList<StaffTeamModel>();
        for(StaffTeam staffTeam : staffTeams){
            JSONObject staffTeamObj = JSON.parseObject(JSON.toJSONString(staffTeam));
            Team team = teamMapper.selectById(staffTeam.getTeamId());
            staffTeamObj.put("team",team);
            StaffTeamModel model = JSONObject.parseObject(JSON.toJSONString(staffTeamObj),StaffTeamModel.class);
            models.add(model);
        }
        return models;
    }

    @Resource
    private TeamMapper teamMapper;
    @Resource
    StaffMapper staffMapper;
    @Resource
    StaffTeamMapper staffTeamMapper;
    @Resource
    StaffTeamService staffTeamService;
    @Resource
    QueryTeamDao queryTeamDao;

    @Override
    protected BaseMapper<Team> getMasterMapper() {
        return teamMapper;
    }

    // 重构 新建 Team Service

    @Transactional
    public Integer createTeamIncludeStaff(TeamAndStaffModel model){
        try {
            teamMapper.insert(model);
        }catch (Exception e){
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessNameRepetione);
        }
        // 插入 Leader 指派多个个Staff为TeamLeader
        if (model.getLeaders()!=null){
            staffTeamService.setStaffToTeamLeader(model.getId(),model.getLeaders());
        }else{
            // do nothings
        }
        // 插入 staff 批量添加 Staff 到指定的 Team 中
        if (model.getStaffs()!=null){
            staffTeamService.addStaffToTeam(model.getId(),model.getStaffs());
        }else{
            // do nothings
        }
        return 1;
    }

    // 重构 修改 Team Service

    @Transactional
    public Integer updateTeamIncludeStaff(long id,TeamAndStaffModel model){
        Team originTeam = teamMapper.selectById(id);
        model.setId(id);
        if (((originTeam.getTeamName()).compareTo(model.getTeamName()) != 0 )) {
            try {
                teamMapper.updateById(CRUD.castObject(model,Team.class));
            }catch (Exception e){
                throw new PowerBusinessException(PowerBusinessCode.PowerBusinessNameRepetione);
            }
        }
        teamMapper.updateById(CRUD.castObject(model,Team.class));
        // 抹去 之前的数据
        staffTeamMapper.delete(new EntityWrapper<StaffTeam>().eq("teamId",id));

        // 插入 staff 批量添加 Staff 到指定的 Team 中
        if (model.getStaffs()!=null){
            staffTeamService.addStaffToTeam(id,model.getStaffs());
        }else{
            // do nothings
        }
        // 插入 Leader 指派多个个Staff为TeamLeader
        if (model.getLeaders()!=null){
            staffTeamService.setStaffToTeamLeader(id,model.getLeaders());
        }else{
            // do nothings
        }
        return 1;
    }

    /**
    *  删除 该 Team的所有成员，包括 Leader  提供 给 更新 Team的staff 以及 Leader  的时候使用
    *  1. 删除 某个 Team 下面的 所有的 staff  包括 Leader
    *  2. 该方法仅仅是提供给 修改Team信息的时候 先抹去该Team的所有的信息 在执行插入的操作
    * */
    @Override
    public Integer deleteALlStaff(long id){
        return staffTeamMapper.delete(new EntityWrapper<StaffTeam>().eq("teamId",id));
    }


    /**
     *      删除某个Team  ，当 Team下面有记录的时候，不允许删除
     * */
    @Override
    public Integer deleteTeam(long id,Integer version){
        Integer staffTeams = staffTeamMapper.selectCount(new EntityWrapper<StaffTeam>().eq("teamId",id));
        Integer taskFollowers = queryTeamDao.checkTeamWithFollow(id);
        if (staffTeams == 0 && taskFollowers == 0){
            return this.deleteMaster(id,version);
        }
        else{
            if(staffTeams!=0){
                throw new PowerBusinessException(PowerBusinessCode.PowerBusinessDeleteAssociatedOne,"team被staffTeams关联");
            }
            if(taskFollowers!=0){
                throw new PowerBusinessException(PowerBusinessCode.PowerBusinessDeleteAssociatedOne,"team被taskFollowers关联");
            }
            throw new RuntimeException("系统异常");
        }
    }

    @Override
    public TeamModel teamBuilding(long id) {
        Team team = teamMapper.selectById(id);
        TeamModel teamModel = copyProperties(team, TeamModel.class);
        List<StaffTeam> staffTeams = staffTeamMapper.selectList(new EntityWrapper<StaffTeam>().eq("teamId", team.getId()));
        List<NameModel> teamStaffs = new ArrayList<>();
        List<NameModel> teamLeaders = new ArrayList<>();

        for (StaffTeam staffTeam : staffTeams) {
            Staff staff = staffMapper.selectById(staffTeam.getStaffId());
            if (staff != null) {
                // 确定是 Staff  而不是 Leader 加入到 员工数组
                NameModel nameModel = new NameModel();
                nameModel.setStaffId(staff.getId());
                nameModel.setTeamId(id);
                nameModel.setTeamName(team.getTeamName());
                nameModel.setStaffName(new UniversalName(staff.getFirstName(), staff.getLastName()).toString());
                teamStaffs.add(nameModel);
                if (staffTeam.getIsLeader().equals(1)) {
                    teamLeaders.add(copyProperties(nameModel, NameModel.class));
                }
            }
        }

        teamModel.setTeamStaffs(teamStaffs);
        teamModel.setTeamLeaders(teamLeaders);
        return teamModel;
    }


    /**
     *  所有的团队以及团队下面的所有的员工
     * */
    @Override
    public List<AllTeamIncludeStaff> allTeamAndStaff(){
        List<AllTeamIncludeStaff> models = new ArrayList<>();
        List<Team> teams = teamMapper.selectList(new EntityWrapper<Team>());
        for (Team team : teams) {
            AllTeamIncludeStaff model = new AllTeamIncludeStaff();
            model.setTeamName(team.getTeamName());
            model.setTeamId(team.getId());
            List<StaffTeam> staffTeams = staffTeamMapper.selectList(new EntityWrapper<StaffTeam>().eq("teamId",team.getId()));
            List<NameModel> staffs = new ArrayList<>();
            for(StaffTeam staffTeam : staffTeams){
                Staff staff = staffMapper.selectById(staffTeam.getStaffId());
                if(staff.getIsValid()==1){
                    NameModel nameModel = new NameModel();
                    nameModel.setStaffName(new UniversalName(staff.getFirstName(),staff.getLastName()).toString());
                    nameModel.setStaffId(staff.getId());
                    staffs.add(nameModel);
                }
            }
            model.setStaffs(staffs);
            models.add(model);
        }
        return models;
    }

    /**
     *  登录用户作为Leader的所有的Team下面的所有的员工
     * */
    @Override
    public List<AllTeamIncludeStaff> allTeamLeaderAndStaff(long staffId){
        List<AllTeamIncludeStaff> models = new ArrayList<>();
        List<Team> teams = teamMapper.selectList(new EntityWrapper<Team>());
        for (Team team : teams) {
            if(!staffTeamService.checkIsTeamLeader(team.getId(),staffId)){

            }else{
                AllTeamIncludeStaff model = new AllTeamIncludeStaff();
                model.setTeamName(team.getTeamName());
                model.setTeamId(team.getId());
                List<StaffTeam> staffTeams = staffTeamMapper.selectList(new EntityWrapper<StaffTeam>().eq(StaffTeam.TEAMID,team.getId()));
                List<NameModel> staffs = new ArrayList<>();
                for(StaffTeam staffTeam : staffTeams){
                    Staff staff = staffMapper.selectById(staffTeam.getStaffId());
                    if(null !=staff.getIsValid() && staff.getIsValid() ==1){
                        NameModel nameModel = new NameModel();
                        nameModel.setStaffName(staff==null?null:new UniversalName(staff.getFirstName(),staff.getLastName()).toString());
                        nameModel.setStaffId(staff==null?null:staff.getId());
                        staffs.add(nameModel);
                    }
                }
                model.setStaffs(staffs);
                models.add(model);
            }
        }
        return models;
    }

    private <T> T copyProperties(Object source, Class<T> tClass){
        T target;
        Constructor<T> constructor;
        try {
            constructor = tClass.getConstructor(null);
            target = constructor.newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Instance class error! : " + tClass.toString());
        }
        return target;
    }
}