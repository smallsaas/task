package com.jfeat.am.module.task.services.domain.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.task.services.domain.dao.QueryTaskFollowerDao;
import com.jfeat.am.module.task.services.domain.service.QueryTaskFollowerService;
import com.jfeat.am.module.task.services.persistence.model.TaskFollower;
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
public class QueryTaskFollowerServiceImpl implements QueryTaskFollowerService {

    @Resource
    QueryTaskFollowerDao queryTaskfollowerDao;

    @Override
    public List<TaskFollower> findTaskfollowerPage(Page<TaskFollower> page, TaskFollower taskFollower) {
        return queryTaskfollowerDao.findTaskfollowerPage(page, taskFollower);
    }
}
