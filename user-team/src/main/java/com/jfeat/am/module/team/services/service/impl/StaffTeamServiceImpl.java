package com.jfeat.am.module.team.services.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jfeat.am.module.team.services.service.StaffTeamService;
import com.jfeat.am.module.team.services.service.TeamService;
import com.jfeat.am.module.team.services.persistence.dao.StaffMapper;
import com.jfeat.am.module.team.services.persistence.dao.StaffTeamMapper;
import com.jfeat.am.module.team.services.persistence.dao.TeamMapper;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.StaffTeam;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.am.power.base.PowerBusinessException;
import com.jfeat.crud.plus.impl.CRUDServicePeerImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.jfeat.am.power.base.PowerBusinessCode.PowerBusinessBadRequest;

/**
 * <p>
 * 团队 implementation
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */
@Service
public class StaffTeamServiceImpl extends CRUDServicePeerImpl<Staff,Team,StaffTeam> implements StaffTeamService {
    @Resource
    private StaffMapper staffMapper;
    @Resource
    private TeamMapper teamMapper;
    @Resource
    private StaffTeamMapper staffTeamMapper;
    @Resource
    TeamService teamService;

    @Override
    protected BaseMapper<Staff> getMasterMapper() {
        return staffMapper;
    }

    @Override
    protected BaseMapper<Team> getMasterPeerMapper() {
        return teamMapper;
    }

    @Override
    protected BaseMapper<StaffTeam> getRelationMapper() {
        return staffTeamMapper;
    }

    @Override
    protected String[] relationMatches() {
        return new String[]{StaffTeam.STAFFID, StaffTeam.TEAMID};
    }

    @Override
    public Boolean checkIsTeamLeader(long teamId,long staffId){
        StaffTeam leader = getRelation(staffId, teamId);

        if(leader != null && leader.getIsLeader().compareTo(1) == 0){
            return true;
        }
        return false;
    }





    //指派多个个Staff为TeamLeader
    @Override
    public Integer setStaffToTeamLeader(long teamId, List<Long> ids){
        if(ids == null)
            throw new PowerBusinessException(PowerBusinessBadRequest,"POST参数ids缺失");

        // 影响条数
        Integer affected = ids.size();

        for(Long id : ids){

            //判断该员工是否在该组里面，如果没有，插入该员工到改组里面
            StaffTeam relation = getRelation(id, teamId);
            if(relation==null){
                StaffTeam staffTeam = new StaffTeam();
                staffTeam.setStaffId(id);
                staffTeam.setTeamId(teamId);
                staffTeam.setIsLeader(1);
                staffTeamMapper.insert(staffTeam);
            }
            else if((relation.getIsLeader()) != 1) {
                //teamPatchService.isHasTeamLeader(teamId);
                relation.setIsLeader(1);
                getRelationMapper().updateById(relation);
            }
            // 如果既在里面，又是Leader ，那么，成功条数 -1.
            else {
                affected = ids.size() -1;
            }
        }
        /// 如果员工不是Leader, 更新数据库
        return affected;
    }

    /**
    *   批量添加 Staff 到指定的 Team 中
    * */
    @Override
    public Integer addStaffToTeam(long teamId, List<Long> ids){

        // 影响条数
        Integer affected = ids.size();

        for(Long id : ids){

            //判断该员工是否在该组里面，如果没有，插入该员工到改组里面
            StaffTeam relation = getRelation(id, teamId);
            if(relation==null){
                StaffTeam staffTeam = new StaffTeam();
                staffTeam.setStaffId(id);
                staffTeam.setTeamId(teamId);
                staffTeam.setIsLeader(0);
                staffTeamMapper.insert(staffTeam);
            }
            // 如果既在里面，又是Leader ，那么，成功条数 -1.
            else {
                affected = ids.size() -1;
            }
        }

        /// 如果员工不是Leader, 更新数据库
        return affected;
    }


    /**
    *   删除 批量删除 Staff 可以 复用于  批量删除 Leader  并且 从该组中删除
    * */
    @Override
    public Integer deleteStaffTeamLeader(long teamId, List<Long> ids){

        // 影响条数
        Integer affected = ids.size();

        for(Long id : ids){

            //判断该员工是否在该组里面，如果没有，插入该员工到改组里面
            StaffTeam relation = getRelation(id, teamId);
            if(relation==null){
                // 如果 查找不到 数据， 则数量 -1 ， 即理解为提交的数据 出错
                affected = ids.size() -1;
            }

            else {
                staffTeamMapper.deleteById(relation.getId());
            }
        }

        /// 如果员工不是Leader, 更新数据库
        return affected;
    }

    /**
     *      执行 删除操作的时候  会把所有的关于该 Team 的记录全部删除
     */
    @Override
    public Integer deleteTeam(long id){
        List<StaffTeam> staffTeams = staffTeamMapper.selectList(new EntityWrapper<StaffTeam>().eq(StaffTeam.TEAMID,id));
        for(int i = 0 ; i < staffTeams.size(); i++){

            // 删除所有的 关于该 Team 的记录
            staffTeamMapper.deleteById(staffTeams.get(i).getId());
        }
        return teamMapper.deleteById(id);
    }
}


