package com.jfeat.am.module.task.services.domain.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.task.services.persistence.model.TaskFollower;

import java.util.List;

/**
 * Created by vincent on 2017/10/19.
 */
public interface QueryTaskFollowerService {
    List<TaskFollower> findTaskfollowerPage(Page<TaskFollower> page, TaskFollower taskFollower);
}