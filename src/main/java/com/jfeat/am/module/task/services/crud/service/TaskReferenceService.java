package com.jfeat.am.module.task.services.crud.service;

import com.jfeat.am.module.task.services.persistence.model.TaskReference;
import com.jfeat.crud.plus.CRUDServiceOnly;

/**
 * <p>
 * 事件参考表 service interface
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-21
 */

public interface TaskReferenceService extends CRUDServiceOnly<TaskReference> {

    /**
     *  判断关联的信息，是否已经存在  如果存在的话，不再插入数据
     * */
    Integer addReference(long taskId,TaskReference entity);

}
