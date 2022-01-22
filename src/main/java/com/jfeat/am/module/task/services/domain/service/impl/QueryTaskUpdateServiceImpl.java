package com.jfeat.am.module.task.services.domain.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.task.services.domain.dao.QueryTaskUpdateDao;
import com.jfeat.am.module.task.services.domain.service.QueryTaskUpdateService;
import com.jfeat.am.module.task.services.persistence.model.TaskUpdate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */
@Service
public class QueryTaskUpdateServiceImpl implements QueryTaskUpdateService {

    @Resource
    QueryTaskUpdateDao queryTaskUpdateDao;

    @Override
    public List<TaskUpdate> findTaskupdatePage(Page<TaskUpdate> page, TaskUpdate taskupdate) {
        return queryTaskUpdateDao.findTaskupdatePage(page, taskupdate);
    }
}
