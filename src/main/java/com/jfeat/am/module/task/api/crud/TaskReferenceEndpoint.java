package com.jfeat.am.module.task.api.crud;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.common.constant.tips.SuccessTip;
import com.jfeat.am.common.constant.tips.Tip;
import com.jfeat.am.module.log.annotation.BusinessLog;
import com.jfeat.am.module.task.services.crud.service.TaskReferenceService;
import com.jfeat.am.module.task.services.domain.service.QueryTaskReferenceService;
import com.jfeat.am.module.task.services.persistence.model.TaskReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 * 事件参考表 api
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-21
 */
@Api(value = "工作任务")
@RestController
@RequestMapping("/api/task")
public class TaskReferenceEndpoint {

    @Resource
    TaskReferenceService taskReferenceService;

    @Resource
    QueryTaskReferenceService queryTaskReferenceService;

    @ApiOperation(value = "添加关联事件  参考事件",response = TaskReference.class)
    @BusinessLog(name = "关联事件",value = "添加关联事件")
    @PostMapping("/tasks/{taskId}/taskreferences")
    public Tip createTaskReference(@PathVariable long taskId,@RequestBody TaskReference entity) {
        int affect = taskReferenceService.addReference(taskId, entity);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "查看参考事件",response = TaskReference.class)
    @GetMapping("/tasks/{taskId}/taskreferences/{id}")
    public Tip getTaskReference(@PathVariable Long id) {
        return SuccessTip.create(taskReferenceService.retrieveMaster(id));
    }

    /// TODO,  change to /taskreferences/{id}
    /// todo, 或移到 task Endpoint

    @ApiOperation(value = "修改 参考事件",response = TaskReference.class)
    @BusinessLog(name = "参考事件",value = "修改参考事件")
    @PutMapping("/tasks/{id}/taskreferences")
    public Tip updateTaskReference(@PathVariable Long id, @RequestBody TaskReference entity) {
        entity.setId(id);
        int affect = taskReferenceService.updateMaster(entity);
        return SuccessTip.create(affect);
    }

    @ApiOperation(value = "删除参考事件",response = TaskReference.class)
    @BusinessLog(name = "参考事件",value = "删除参考事件")
    @PostMapping("/tasks/{taskId}/{version}/taskreferences/{id}")
    public Tip deleteTaskReference(@PathVariable Long id,@PathVariable Integer version) {
        int affect = taskReferenceService.deleteMaster(id,version);
        return SuccessTip.create(affect);
    }


    /// TODO,  change to /taskreferences

    @ApiOperation(value = " 搜索参考事件 根据参考事件名称 参考事件的 ID",response = TaskReference.class)
    @GetMapping("/taskreferences/options")
    public Tip queryTaskReferences(Page<TaskReference> page,
                                   @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                   @RequestParam(name = "id", required = false) Long id,
                                   @RequestParam(name = "TaskID", required = false) Long TaskID,
                                   @RequestParam(name = "ReferenceName", required = false) String ReferenceName,
                                   @RequestParam(name = "ReferenceId", required = false) String ReferenceId) {
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        TaskReference taskreference = new TaskReference();
        taskreference.setId(id);
        taskreference.setTaskId(TaskID);
        taskreference.setReferenceName(ReferenceName);
        taskreference.setReferenceId(ReferenceId);

        page.setRecords(queryTaskReferenceService.findTaskreferencePage(page, taskreference));

        return SuccessTip.create(page);
    }
}
