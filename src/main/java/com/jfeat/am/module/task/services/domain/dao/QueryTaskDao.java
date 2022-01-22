package com.jfeat.am.module.task.services.domain.dao;

import com.jfeat.am.module.task.services.domain.model.TaskModel;
import com.jfeat.am.module.task.services.persistence.model.Task;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Code Generator on 2017-11-21
 */
public interface QueryTaskDao extends BaseMapper<Task> {
    List<Task> findTaskPage(Page<Task> page,
                            @Param("ownerByStaffId")Long ownerByStaffId,
                            @Param("taskName")String taskName,
                            @Param("status")String status);


    List<Task> searchTaskOwnerByTeam(Page<TaskModel> page,@Param("staffId")Long staffId,@Param("status")String status,@Param("orderBy")String orderBy);

    List<Task> searchTaskFollowerByTeam(Page<TaskModel> page,@Param("staffId")Long staffId,@Param("status")String status,@Param("orderBy")String orderBy);

    List<Task> allTaskOwnerByMe(Page<TaskModel> page, @Param("staffId")Long staffId, @Param("status")String status,@Param("orderBy")String orderBy);

    List<Task> allTaskFollowerByMe(Page<TaskModel> page, @Param("staffId")Long staffId, @Param("status")String status,@Param("orderBy")String orderBy);

    @Select("SELECT count(id) FROM task_reference WHERE referenceId = #{referenceId}")
    Integer checkTaskReferenceDependency(@Param("referenceId") Long referenceId);

    @Select("SELECT count(id) FROM task_follower WHERE taskId = #{taskId}")
    Integer checkTaskFollowerDependency(@Param("taskId") Long taskId);

    @Select("SELECT count(id) FROM task_update WHERE taskId = #{taskId}")
    Integer checkTaskUpdateDependency(@Param("taskId") Long taskId);

}