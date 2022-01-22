package com.jfeat.am.module.task.services.domain.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.task.services.domain.model.TaskModel;
import com.jfeat.am.module.task.services.persistence.model.Task;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface QueryTaskService {

    List<Task> findTaskPage(Page<Task> page,
                            Long ownerByStaffId,
                            String taskName,
                            String status);


    public List<Task> searchTaskOwnerByTeam(Page<TaskModel> page,
                                            Long staffId,
                                            String status,
                                            String orderBy);
    /*List<Task> findNewTask(Long staffId);*/
}