package com.jfeat.am.module.task.services.domain.dao;

import com.jfeat.am.module.task.services.persistence.model.TaskUpdate;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * Created by Code Generator on 2017-11-21
 */
public interface QueryTaskUpdateDao extends BaseMapper<TaskUpdate> {
    List<TaskUpdate> findTaskupdatePage(Page<TaskUpdate> page, TaskUpdate taskupdate);
}