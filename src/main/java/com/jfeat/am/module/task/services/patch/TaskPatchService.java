package com.jfeat.am.module.task.services.patch;

import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.task.services.domain.model.TaskModel;
import com.jfeat.am.module.task.services.domain.model.TeamId;
import com.jfeat.am.module.task.services.persistence.model.Task;
import com.jfeat.am.module.task.services.persistence.model.TaskFollower;
import com.jfeat.am.module.task.services.persistence.model.TaskUpdate;
import com.jfeat.am.module.team.services.domain.model.StaffId;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Code Generator on 2017-11-21
 */
public interface TaskPatchService {

    /**
     * own  login user - >  Task
     */
    List<TaskModel> allTaskThatOwnByMe(Page<TaskModel> page, long staffId, String status, String orderBy);

    /**
     * follower  login user - >  Task
     */
    List<TaskModel> allTaskFollowerByMe(Page<TaskModel> page, long staffId, String status, String orderBy);

    /**
     * own  By Team - >  Task
     */
    List<TaskModel> allTaskThatOwnByTeam(Page<TaskModel> page, long staffId, String status, String orderBy);

    /**
     *   follower  By Team
     * */

    List<TaskModel> allTaskFollowerByTeam(Page<TaskModel> page, long staffId, String status, String orderBy);

    /**
     *  指派 Staff Follower 事件
     *
     * */
    Integer assignStaffFollowerTask(Long userId,long taskId,long staffId);

    /**
     *  转移  事件
     *
     * */
    Integer transferTask(Staff staff, long taskId, TeamId teamId);

    /**
     *  指派 Staff Owner  事件
     *
     * */
    Integer assignStaffOwnerTask(Long userId,long taskId,long staffId);

    /**
     *  添加跟进信息
     * */
    @Transactional
    Integer addTaskUpdate(long taskId,long staffId, TaskUpdate taskUpdate);

    /**
     *  更新事件信息
     * */
    Integer updateTaskUpdate(long id, long staffId, TaskUpdate taskUpdate);

    /**
     *  更新事件信息
     * */
    Integer deleteTaskUpdate(long id, long staffId);

}

