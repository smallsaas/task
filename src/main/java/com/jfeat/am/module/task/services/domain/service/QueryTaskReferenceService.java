package com.jfeat.am.module.task.services.domain.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.task.services.persistence.model.TaskReference;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface QueryTaskReferenceService {
    List<TaskReference> findTaskreferencePage(Page<TaskReference> page, TaskReference taskreference );
}