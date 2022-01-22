package com.jfeat.am.module.task.api.crud;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.common.constant.tips.SuccessTip;
import com.jfeat.am.common.constant.tips.Tip;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.gateway.definetion.NumberType;
import com.jfeat.am.module.gateway.services.domain.service.SnpoolService;
import com.jfeat.am.module.log.annotation.BusinessLog;
import com.jfeat.am.module.task.services.crud.service.TaskService;
import com.jfeat.am.module.task.services.domain.model.TaskModel;
import com.jfeat.am.module.task.services.domain.service.QueryTaskService;
import com.jfeat.am.module.task.services.persistence.model.Task;
import com.jfeat.am.module.team.services.common.StaffUserKit;
import com.jfeat.am.module.team.services.crud.service.StaffService;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.power.base.PowerBusinessCode;
import com.jfeat.am.power.base.PowerBusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * <p>
 * 事件 api
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-21
 */
@Api(value = "工作任务")
@RestController
@RequestMapping("/api/task/tasks")
public class TaskEndpoint {

    @Resource
    TaskService taskService;
    @Resource
    QueryTaskService queryTaskService;

    /**
     * check staff
     */
    @Resource
    StaffService staffService;
    @Resource
    private StaffUserKit staffUserKit;
    @Resource
    private SnpoolService snpoolService;

    @ApiOperation(value = "新建事件",response = Task.class)
    @BusinessLog(name = "事件",value = "新建事件")
    @PostMapping
    public Tip createTask(@Valid @RequestBody TaskModel entity, HttpServletRequest request) {
        entity.setTaskNumber(snpoolService.getSerial(NumberType.TD.getType()));
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId(request));
        if (staff==null){
            throw new PowerBusinessException(PowerBusinessCode.PowerTeamLoginUserNoStaff);
        }
        Long affect = taskService.createMasterByStaff(entity, staff, false);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "新建 ownerByme 事件",response = Task.class)
    @BusinessLog(name = "ownerByme事件",value = "新建ownerByme事件")
    @PostMapping("/create/owner")
    public Tip createTaskOwnerByMe(@Valid @RequestBody TaskModel entity, HttpServletRequest request) {
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId(request));
        if (staff==null){
            throw new PowerBusinessException(PowerBusinessCode.PowerTeamLoginUserNoStaff);
        }
        Long affect = taskService.createMasterByStaff(entity, staff, true);
        return SuccessTip.create(affect);
    }


    @ApiOperation(value = "查询事件   包括事件的关联项",response = Task.class)
    @GetMapping("/{id}")
    public Tip getTask(@PathVariable Long id, HttpServletRequest request) {
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId(request));
        if (staff==null){
            throw new PowerBusinessException(PowerBusinessCode.PowerTeamLoginUserNoStaff);
        }
        return SuccessTip.create(taskService.taskIncludeUpdate(id,staff.getId()));
    }

    @ApiOperation(value = " 修改事件",response = Task.class)
    @BusinessLog(name = "事件",value = "修改事件")
    @PutMapping("/{id}")
    public Tip updateTask(@Valid @PathVariable Long id, @RequestBody TaskModel entity) {
        Staff staff = staffService.queryStaffByUserId(JWTKit.getUserId());
        if (staff==null){
            throw new PowerBusinessException(PowerBusinessCode.PowerTeamLoginUserNoStaff);
        }
        int affect = taskService.updateTask(id, entity, staff);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "删除事件   是否删除 关联的信息 待定",response = Task.class)
    @BusinessLog(name = "事件",value = "删除事件")
    @DeleteMapping("/{id}/{version}")
    public Tip deleteTask(@PathVariable Long id,@PathVariable Integer version) {
        Long staffId = staffUserKit.getStaffIdByUserId(JWTKit.getUserId());
        int affect = taskService.deleteMaster(id, staffId,version);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "查询事件  根据事件的名称(taskName) 跟进员工(ownerByStaffId) 以及 状态(status) 来查找事件",response = Task.class)
    @GetMapping
    public Tip queryTasks(Page page,
                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                          @RequestParam(name = "ownerByStaffId", required = false) Long ownerByStaffId,
                          @RequestParam(name = "taskName", required = false) String taskName,
                          @RequestParam(name = "status", required = false) String status) {

        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(queryTaskService.findTaskPage(page, ownerByStaffId,taskName,status));
        return SuccessTip.create(page);
    }

    /*@ApiOperation(value = "查询当前登录用户的最新代办事项")
    @GetMapping("/new")
    public Tip queryNewTasks(HttpServletRequest httpServletRequest){
        Long staffId = staffUserKit.getStaffIdByUserId(JWTKit.getUserId(httpServletRequest));
        if(staffId == null )
            throw new BusinessException(BusinessCode.EmptyNotAllowed);
        return SuccessTip.create(queryTaskService.findNewTask(staffId));
    }*/
}
