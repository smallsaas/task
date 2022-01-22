package com.jfeat.am.module.task.services.domain.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.task.services.domain.dao.QueryTaskReferenceDao;
import com.jfeat.am.module.task.services.domain.service.QueryTaskReferenceService;
import com.jfeat.am.module.task.services.persistence.model.TaskReference;
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
public class QueryTaskReferenceServiceImpl implements QueryTaskReferenceService {

    @Resource
    QueryTaskReferenceDao queryTaskReferenceDao;

    @Override
    public List<TaskReference> findTaskreferencePage(Page<TaskReference> page, TaskReference taskreference) {
        return queryTaskReferenceDao.findTaskreferencePage(page, taskreference);
    }
}
