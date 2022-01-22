package com.jfeat.am.module.team.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.common.constant.tips.Ids;
import com.jfeat.am.common.constant.tips.SuccessTip;
import com.jfeat.am.common.constant.tips.Tip;
import com.jfeat.am.common.controller.BaseController;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.log.annotation.BusinessLog;
import com.jfeat.am.module.team.services.service.StaffService;
import com.jfeat.am.module.team.services.service.StaffTeamService;
import com.jfeat.am.module.team.services.service.TeamService;
import com.jfeat.am.module.team.services.domain.dao.QueryTeamDao;
import com.jfeat.am.module.team.services.domain.model.NameModel;
import com.jfeat.am.module.team.services.domain.model.TeamAndStaffModel;
import com.jfeat.am.module.team.services.domain.service.QueryStaffService;
import com.jfeat.am.module.team.services.domain.service.QueryStaffTeamService;
import com.jfeat.am.module.team.services.domain.service.QueryTeamService;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.StaffTeam;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 团队 api
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-20
 */
@Api(value = "团队信息")
@RestController
@RequestMapping("/api/team/teams")
public class TeamEndpoint extends BaseController {

    @Resource
    TeamService teamService;
    @Resource
    QueryTeamService queryTeamService;
    @Autowired
    QueryTeamDao queryTeamDao;
    @Resource
    StaffTeamService staffTeamService;
    @Resource
    StaffService staffService;
    @Resource
    QueryStaffService queryStaffService;
    @Resource
    QueryStaffTeamService queryStaffTeamService;

    @ApiOperation(value = "新建Team",response = Team.class)
    @BusinessLog(name = "Team",value = "新建Team")
    @PostMapping
    public Tip createTeam(@RequestBody TeamAndStaffModel entity) {
        int affect = teamService.createTeamIncludeStaff(entity);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "获取Team",response = Team.class)
    @GetMapping("/{id}")
    public Tip getTeam(@PathVariable Long id) {
        return SuccessTip.create(teamService.teamBuilding(id));
    }

    @ApiOperation(value = "修改Team的信息",response = Team.class)
    @BusinessLog(name = "Team",value = "修改Team的信息")
    @PutMapping("/{id}")
    public Tip updateTeam(@PathVariable Long id, @RequestBody TeamAndStaffModel entity) {
        int affect = teamService.updateTeamIncludeStaff(id, entity);
        return SuccessTip.create(affect);
    }


    @ApiOperation(value = "删除某个Team",response = Team.class)
    @BusinessLog(name = "Team",value = "删除某个Team")
    @DeleteMapping("/{id}/{version}")
    public Tip deleteTeam(@PathVariable Long id,@PathVariable Integer version) {
        //TODO  删除关联事件 以及其他的关联信息
        Integer result = teamService.deleteTeam(id,version);
        return SuccessTip.create(result);
    }

    @ApiOperation(value = "某个team中的所有的Staff 包括 Leader 的信息")
    @GetMapping("/{teamId}/allstaffs")
    public Tip allMyTeam(@PathVariable long teamId){
        return SuccessTip.create(queryStaffService.searchStaffInTeam(teamId));
    }

    @ApiOperation(value = "查看自己 所在的 所有的Team信息(包括不作为领导人的小组)",response = StaffTeam.class)
    @GetMapping("/owners")
    public Tip allMyTeam(){
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId(getHttpServletRequest()));
        return SuccessTip.create(queryStaffTeamService.getTeamsLedByStaff(staff.getId()));
    }

    @ApiOperation(value = "查看自己作为领导人的所有的Team信息",response = StaffTeam.class)
    @GetMapping("/leader")
    public Tip teamLeader() {
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId(getHttpServletRequest()));
        if (staff==null){
            throw new BusinessException(BusinessCode.PowerTeamLoginUserNoStaff);
        }
        return SuccessTip.create(teamService.allTeamLeaderAndStaff(staff.getId()));
    }

    @ApiOperation(value = "指派Leader",response = StaffTeam.class)
    @PostMapping("/{teamId}/bulk/assignLeader")
    public Tip setTeamLeader(@PathVariable long teamId, @RequestBody Ids ids){
        Integer count = queryTeamDao.checkTeamIsExist(teamId);
        if(count==0) {
            throw new BusinessException(BusinessCode.BadRequest, "team不存在");
        }
        int affect = staffTeamService.setStaffToTeamLeader(teamId, ids.getIds());
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "批量将员工放到某个Team",response = StaffTeam.class)
    @PostMapping("/{teamId}/bulk/add")
    public Tip batchAddStaffToTeam(@PathVariable long teamId, @RequestBody Ids ids){
        int affect = staffTeamService.addStaffToTeam(teamId, ids.getIds());
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "批量将员工以及Leader从某个Team移除",response = StaffTeam.class)
    @PostMapping("/{teamId}/clean")
    public Tip cleanALlStaff(@PathVariable long teamId){
        int affect = teamService.deleteALlStaff(teamId);
        return SuccessTip.create(affect);
    }


//    @ApiOperation(value = "前端复选框API",response = StaffTeam.class)
//    @GetMapping("/staff")
//    public Tip queryTeams(Page<NameModel> page,
//                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
//                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
//                          @RequestParam(name = "teamName", required = false) String teamName,
//                          @RequestParam(name = "staffName", required = false) String staffName) {
//        page.setCurrent(pageNum);
//        page.setSize(pageSize);
//        page.setRecords(queryStaffTeamService.searchByStaffOrTeamName(page,teamName,staffName));
//        return SuccessTip.create(page);
//    }
    @ApiOperation(value = "根据名称或者简介去查找Team",response = Team.class)
    @GetMapping
    public Tip queryTeams(Page page,
                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                          @RequestParam(name = "name", required = false) String name,
                          @RequestParam(name = "desc", required = false) String desc) {
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Team team = new Team();
        team.setTeamName(name);
        team.setTeamDesc(desc);
        page.setRecords(queryTeamService.findTeamPage(page, team));

        return SuccessTip.create(page);
    }

    @ApiOperation(value = "所有的 Team 以及 Team 中的成员 ",response = Team.class)
    @GetMapping("/list")
    public Tip allTeam() {
        return SuccessTip.create(teamService.allTeamAndStaff());
    }


}
