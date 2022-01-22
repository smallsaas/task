package com.jfeat.am.module.task.services.crud.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.task.services.domain.model.TaskUpdateModel;
import com.jfeat.am.module.task.services.persistence.model.TaskUpdate;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.crud.plus.CRUDServiceOnly;

import java.util.List;


/**
 * <p>
 * 事件更新表 service interface
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-21
 */

public interface TaskUpdateService extends CRUDServiceOnly<TaskUpdate> {

    //include Staff details
    TaskUpdateModel showTaskUpdateIncludeStaff(long id);
    // only show records belongs to login user

    List<TaskUpdateModel> loginStaffTaskUpdateRecords(Page<TaskUpdateModel> page, long staffId, String status);

    // only show records belongs to Team
    List<TaskUpdateModel> teamTaskUpdateRecords(Page<TaskUpdateModel> page, long teamId,String status);

    // rebuild  update taskUpdate
    Integer updateTaskUpdate(long taskId, long id, Staff staff, TaskUpdate entity);
}
