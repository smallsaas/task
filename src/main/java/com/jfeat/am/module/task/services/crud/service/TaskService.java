package com.jfeat.am.module.task.services.crud.service;

import com.jfeat.am.module.task.services.domain.model.TaskModel;
import com.jfeat.am.module.task.services.persistence.model.Task;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.crud.plus.CRUDServiceOnly;

import java.util.List;


/**
 * <p>
 * 事件 service interface
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-21
 */

public interface TaskService extends CRUDServiceOnly<Task> {

    ///
    Long createMasterByStaff(TaskModel entity, Staff byStaff, boolean owner);

    /**
     *  更新 事件
     * */

    Integer updateTask(long taskId,TaskModel entity, Staff staff);


    List<Task> tasksFromCommunicationRecord(long id);


    /**
     *   事件 详情
     * */
    TaskModel taskIncludeUpdate(long id,Long staffId);

    Integer deleteMaster(long id,Long staffId,Integer version);
    
}
