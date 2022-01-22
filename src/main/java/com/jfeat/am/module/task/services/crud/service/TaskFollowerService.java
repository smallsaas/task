package com.jfeat.am.module.task.services.crud.service;


import com.jfeat.am.module.task.services.persistence.model.TaskFollower;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.crud.plus.CRUDServiceOnly;

import java.util.List;


/**
 * <p>
 * 事件跟进表 service interface
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-21
 */

public interface TaskFollowerService extends CRUDServiceOnly<TaskFollower> {

    /**
     *   批量 提交关联人员(跟进)
     * */
    Integer addFollowerStaff(long taskId,long staffId,List<Long> ids);


    /**
     *       批量 提交关联人员(跟进)
     * */

    Integer bulkAddStaffFollowerTask(long taskId, Staff staff, List<TaskFollower> followers);
}
