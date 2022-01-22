package com.jfeat.am.module.team.services.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.team.services.domain.dao.QueryTeamDao;
import com.jfeat.am.module.team.services.domain.model.StaffTeamModel;
import com.jfeat.am.module.team.services.domain.model.TeamModel;
import com.jfeat.am.module.team.services.domain.service.QueryTeamService;
import com.jfeat.am.module.team.services.persistence.dao.StaffMapper;
import com.jfeat.am.module.team.services.persistence.dao.StaffTeamMapper;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.StaffTeam;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.am.power.base.naming.UniversalName;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class QueryTeamServiceImpl implements QueryTeamService {

    @Resource
    QueryTeamDao queryTeamDao;
    @Resource
    StaffTeamMapper staffTeamMapper;
    @Resource
    StaffMapper staffMapper;

    @Override
    public List<TeamModel> findTeamPage(Page<TeamModel> page, Team team) {

        // 通过条件查找出需要的Team   不传入参数默认是返回所有的 Team 信息
        List<TeamModel> teams = queryTeamDao.findTeamPage(page, team);

        List<TeamModel> models = new ArrayList<>();
        for(Team t :teams){
            // Team 转化为 Json数据
            JSONObject teamObj = JSON.parseObject(JSONObject.toJSONString(t));

            // 关键  Staff 跟 Team 是 多对多的关系 查找出中间的关联表的信息
            List<StaffTeam> staffs = staffTeamMapper.selectList(new EntityWrapper<StaffTeam>().eq("teamId",t.getId()).eq("isLeader",1));
            //  Leader 可以是多个 使用 List 数组去接收查找出来的 Leader
            List<String> leader = new ArrayList<>();

            for(StaffTeam staffTeam :staffs){
                // 遍历已经查找出来的 TeamStaff 使用是 Leader 的 Staff ID 找出该 Staff  的 所有的属性
                Staff staff = staffMapper.selectById(staffTeam.getStaffId());
                // 将 Staff  的姓以及名字 赋值给 Leader
                leader.add(staff==null?null:new UniversalName(staff.getFirstName(),staff.getLastName()).toString());
            }
            teamObj.put("leaders",leader);
            TeamModel model = JSONObject.parseObject(JSONObject.toJSONString(teamObj),TeamModel.class);
            models.add(model);
        }
        return models;
    }

    /**
     *      所有我领导的 Team。
     * */
    @Override
    public List<Team> teamsLeader(Page<Team> page, long staffId){
        return queryTeamDao.teamsLeader(page,staffId);
    }
}
