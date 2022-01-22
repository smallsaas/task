package com.jfeat.am.module.task.services.crud.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jfeat.am.module.task.services.crud.service.TaskReferenceService;
import com.jfeat.am.module.task.services.persistence.dao.TaskReferenceMapper;
import com.jfeat.am.module.task.services.persistence.model.TaskReference;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 事件参考表 implementation
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-21
 */
@Service
public class TaskReferenceServiceImpl extends CRUDServiceOnlyImpl<TaskReference> implements TaskReferenceService {


    @Resource
    private TaskReferenceMapper taskreferenceMapper;

    @Override
    protected BaseMapper<TaskReference> getMasterMapper() {
        return taskreferenceMapper;
    }


    /**
     *  判断关联的信息，是否已经存在  如果存在的话，不再插入数据
     * */
    @Override
    public Integer addReference(long taskId, TaskReference entity){
        TaskReference taskReference = new TaskReference();
        taskReference.setTaskId(taskId);
        taskReference.setReferenceId(entity.getReferenceId());
        taskReference.setReferenceName(entity.getReferenceName());
        if(taskreferenceMapper.selectOne(taskReference)==null){
            return taskreferenceMapper.insert(taskReference);
        }else{
            return 0;
        }
    }
}


