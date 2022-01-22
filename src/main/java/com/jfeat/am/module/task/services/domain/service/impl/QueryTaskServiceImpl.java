package com.jfeat.am.module.task.services.domain.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.task.services.crud.service.TaskService;
import com.jfeat.am.module.task.services.domain.dao.QueryTaskDao;
import com.jfeat.am.module.task.services.domain.model.TaskModel;
import com.jfeat.am.module.task.services.domain.service.QueryTaskService;
import com.jfeat.am.module.task.services.persistence.model.Task;
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
public class QueryTaskServiceImpl implements QueryTaskService {

    @Resource
    QueryTaskDao queryTaskDao;
    @Resource
    TaskService taskService;

    @Override
    public List<Task> findTaskPage(Page<Task> page,
                                   Long ownerByStaffId,
                                   String taskName,
                                   String status) {
        return queryTaskDao.findTaskPage(page, ownerByStaffId,taskName,status);
    }


    @Override
    public List<Task> searchTaskOwnerByTeam(Page<TaskModel> page,
                                            Long staffId,
                                            String status,
                                            String orderBy) {
        return queryTaskDao.searchTaskOwnerByTeam(page,staffId ,status, orderBy);

    }

    /*@Override
    public List<Task> findNewTask(Long staffId) {
        return queryTaskDao.findNewTask(staffId);
    }*/
}
