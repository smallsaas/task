package com.jfeat.am.module.task.api.crud;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.common.constant.tips.SuccessTip;
import com.jfeat.am.common.constant.tips.Tip;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.log.annotation.BusinessLog;
import com.jfeat.am.module.task.services.crud.service.TaskFollowerService;
import com.jfeat.am.module.task.services.domain.service.QueryTaskFollowerService;
import com.jfeat.am.module.task.services.persistence.model.TaskFollower;
import com.jfeat.am.module.team.services.crud.service.StaffService;
import com.jfeat.am.module.team.services.domain.model.StaffId;
import com.jfeat.am.module.team.services.patch.TeamPatchService;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.power.base.PowerBusinessCode;
import com.jfeat.am.power.base.PowerBusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 事件跟进表 api
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-21
 */
@Api(value = "工作任务")
@RestController
@RequestMapping("/api/task/taskfollower")
public class TaskFollowerEndpoint {

    @Resource
    TaskFollowerService taskFollowerService;
    @Resource
    QueryTaskFollowerService queryTaskFollowerService;

    @Resource
    TeamPatchService teamPatchService;

    /**
     *  Servlet 通过登陆信息查询当前用户的信息
     * */
    @Resource
    StaffService staffService;

    @ApiOperation(value = "创建跟进信息",response = TaskFollower.class)
    @BusinessLog(name = "跟进信息",value = "创建跟进信息")
    @PostMapping
    public Tip createTaskFollower(@RequestBody TaskFollower entity) {
        int affect = taskFollowerService.createMaster(entity);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "查询跟进信息 /   无太大的意义/该API",response = TaskFollower.class)
    @GetMapping("/{id}")
    public Tip getTaskFollower(@PathVariable Long id) {
        return SuccessTip.create(taskFollowerService.retrieveMaster(id));
    }


    @ApiOperation(value = "修改TaskFollower的信息",response = TaskFollower.class)
    @BusinessLog(name = "TaskFollower的信息",value = "修改TaskFollower的信息")
    @PutMapping("/{id}")
    public Tip updateTaskFollower(@PathVariable Long id, @RequestBody TaskFollower entity) {

        throw new PowerBusinessException(PowerBusinessCode.PowerBusinessNotSupport);

        //entity.setId(id);
        //return SuccessTip.create(taskFollowerService.updateMaster(entity));
    }


    @ApiOperation(value = "指派 Staff 跟进某个 Task 事件",response = TaskFollower.class)
    @BusinessLog(name = "跟进事件",value = "指派Staff跟进某个Task事件")
    @PutMapping("/{id}/assign")
    public Tip assignStaffFollowTask(@PathVariable Long id, @RequestBody StaffId entity, HttpServletRequest request) {

        // TODO,  assignStaffFollowTask 应该由  Service 提供, 以下代码放入 Service 方法 assignStaffFollowTask

        /// TODO, to check
        /// blew line is ugly code

        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId(request));
        if (staff==null){
            throw new PowerBusinessException(PowerBusinessCode.PowerTeamLoginUserNoStaff);
        }

        TaskFollower follower = taskFollowerService.retrieveMaster(id);

        // TODO, to check
        /// blow line is also ugly code, 代码本身表达清晰，就是最好的 comment,
        /// 以下方法名代表判断， 但这里没有判断， 表达不清晰，即使以下代码抛出异常。
        if(!teamPatchService.isStaffTeamLeader(follower.getTeamId(),staff.getId())){
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessCodeBase,"You are not the leader in the team!");
        }
        follower.setStaffId(entity.getStaffId());
        int affect = taskFollowerService.updateMaster(follower);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "删除 某个事件跟进",response = TaskFollower.class)
    @BusinessLog(name = "某个事件跟进",value = "删除某个事件跟进")
    @DeleteMapping("/{id}/{version}")
    public Tip deleteTaskFollower(@PathVariable Long id,@PathVariable Integer version) {
        int affect = taskFollowerService.deleteMaster(id,version);
        return SuccessTip.create(affect);
    }

    /// TODO,  without /options

    @ApiOperation(value = "//此方法可能需要自行添加需要的参数,按需要使用  参数为 事件的ID Team 的ID 以及 员工的 ID",response = TaskFollower.class)
    @GetMapping("/options")
    public Tip queryTaskFollowers(Page<TaskFollower> page,
                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  @RequestParam(name = "id", required = false) Long id,
                                  @RequestParam(name = "TeamId", required = false) Long TeamId,
                                  @RequestParam(name = "TaskID", required = false) Long TaskID,
                                  @RequestParam(name = "StaffId", required = false) Long StaffId) {
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        TaskFollower taskFollower = new TaskFollower();
        taskFollower.setId(id);
        taskFollower.setTeamId(TeamId);
        taskFollower.setTaskId(TaskID);
        taskFollower.setStaffId(StaffId);
        page.setRecords(queryTaskFollowerService.findTaskfollowerPage(page, taskFollower));

        return SuccessTip.create(page);
    }
}
