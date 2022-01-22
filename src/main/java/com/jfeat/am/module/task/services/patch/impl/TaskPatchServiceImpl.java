package com.jfeat.am.module.task.services.patch.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jfeat.am.module.message.services.domain.model.MessageType;
import com.jfeat.am.module.message.services.domain.service.MessageService;
import com.jfeat.am.module.message.services.gen.persistence.model.Message;
import com.jfeat.am.module.task.api.patch.TaskStatus;
import com.jfeat.am.module.task.services.crud.service.TaskService;
import com.jfeat.am.module.task.services.domain.dao.QueryTaskDao;
import com.jfeat.am.module.task.services.domain.model.TaskModel;
import com.jfeat.am.module.task.services.domain.model.TeamId;
import com.jfeat.am.module.task.services.patch.TaskPatchService;
import com.jfeat.am.module.task.services.persistence.dao.TaskFollowerMapper;
import com.jfeat.am.module.task.services.persistence.dao.TaskUpdateMapper;
import com.jfeat.am.module.task.services.persistence.model.Task;
import com.jfeat.am.module.task.services.persistence.model.TaskFollower;
import com.jfeat.am.module.task.services.persistence.model.TaskUpdate;
import com.jfeat.am.module.team.services.crud.service.StaffTeamService;
import com.jfeat.am.module.team.services.persistence.dao.StaffMapper;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.power.base.PowerBusinessCode;
import com.jfeat.am.power.base.PowerBusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by J4cob on 2018/1/23.
 */
@Service
public class TaskPatchServiceImpl implements TaskPatchService {
    @Resource
    QueryTaskDao queryTaskDao;
    @Resource
    TaskService taskService;
    @Resource
    StaffTeamService staffTeamService;
    @Resource
    TaskFollowerMapper taskFollowerMapper;
    @Resource
    TaskUpdateMapper taskUpdateMapper;
    @Resource
    private StaffMapper staffMapper;
    @Resource
    private MessageService messageService;

    /**
     * own  login user - >  Task
     */
    @Override
    public List<TaskModel> allTaskThatOwnByMe(Page<TaskModel> page, long staffId, String status, String orderBy){
        List<TaskModel>  models = new ArrayList<>();
        List<Task> teamTasks = queryTaskDao.allTaskOwnerByMe(page,staffId, status==null?"":status.toUpperCase(), orderBy);
        if (teamTasks == null || teamTasks.size() <= 0 ){

        }else{
            for (Task task : teamTasks){
                TaskModel model = taskService.taskIncludeUpdate(task.getId(),staffId);

                /// fix response data format
                models.add(model);
            }

        }
        return models;
    }

    /**
     * follower  login user - >  Task
     */
    @Override
    public List<TaskModel> allTaskFollowerByMe(Page<TaskModel> page, long staffId, String status, String orderBy){
        List<TaskModel>  models = new ArrayList<>();
        List<Task> teamTasks = queryTaskDao.allTaskFollowerByMe(page,staffId, status==null?"":status.toUpperCase(), orderBy);
        if (teamTasks == null || teamTasks.size() <= 0 ){

        }else{
            for (Task task : teamTasks){
                TaskModel model = taskService.taskIncludeUpdate(task.getId(),staffId);

                /// fix response data format
                if(!models.contains(model)) {
                    models.add(model);
                }
            }

        }
        return models;
    }

    /**
     * own  By Team - >  Task
     */
    @Override
    public List<TaskModel> allTaskThatOwnByTeam(Page<TaskModel> page, long staffId, String status, String orderBy){
        List<TaskModel>  models = new ArrayList<>();
        List<Task> teamTasks = queryTaskDao.searchTaskOwnerByTeam(page,staffId, status==null?"":status.toUpperCase(), orderBy);
        if (teamTasks == null || teamTasks.size() <= 0 ){

        }else{
            for (Task task : teamTasks){
                TaskModel model = taskService.taskIncludeUpdate(task.getId(),staffId);

                /// fix response data format
                    models.add(model);

            }
        }

        return models;
    }


    /**
    *   follower  By Team
    * */

    @Override
    public List<TaskModel> allTaskFollowerByTeam(Page<TaskModel> page, long staffId, String status, String orderBy){
        List<TaskModel>  models = new ArrayList<>();
        List<Task> teamTasks = queryTaskDao.searchTaskFollowerByTeam(page,staffId, status==null?"":status.toUpperCase(), orderBy);
        if (teamTasks == null || teamTasks.size() <= 0 ){

        }else{
            for (Task task : teamTasks){
                TaskModel model = taskService.taskIncludeUpdate(task.getId(),staffId);

                /// fix response data format
                models.add(model);
            }
        }
        return models;

    }

    /**
     *  指派 或重新指派 某人 跟进 某事件
     * */
    public Integer assignStaffOwnerTask(long taskId,long staffId) {
        Task task = taskService.retrieveMaster(taskId);
        if (task.getOwnerByStaffId().equals(staffId)) {
            return null;
        }
        // 如果 OwnerByStaffId 与传进来的 StaffId 不相等 且不为空的时候 设置为传入来的StaffId 并且task.status -> WIP
        else {
            if (!(task.getOwnerByStaffId().equals(staffId)) || task.getOwnerByStaffId().equals(null)) {
                task.setStatus(TaskStatus.WIP.toString());
                task.setOwnerByStaffId(staffId);
            }
            return taskService.updateMaster(task);
        }
    }

    /**
     *  指派 Staff Follower 事件
     *
     * */
    @Override
    public Integer assignStaffFollowerTask(Long userId, long taskId, long staffId){
        Task task = taskService.retrieveMaster(taskId);
        if(staffTeamService.checkIsTeamLeader(task.getOwnerByTeamId(),userId)){
            TaskFollower follower = new TaskFollower();
            follower.setTaskId(taskId);
            follower.setStaffId(staffId);
            //  判断是否存在记录，存在 不重复插入
            if(taskFollowerMapper.selectOne(follower)==null){
                if (task.getStatus().compareTo(TaskStatus.WIP.toString())!=0){
                    task.setStatus(TaskStatus.WIP.toString());
                    taskService.updateMaster(task);
                }
                return taskFollowerMapper.insert(follower);
            }
            else{
                return null;
            }

        }else{
            throw new PowerBusinessException(PowerBusinessCode.PowerTeamStaffLeaderOnly);
        }
    }


    /**
     *  转移  事件
     *
     * */
    @Override
    public Integer transferTask(Staff staff, long taskId, TeamId teamId){
        Task task = taskService.retrieveMaster(taskId);
        Long ownerByStaffId = task.getOwnerByStaffId();
        Staff ownerStaff = staffMapper.selectById(ownerByStaffId);
        if(ownerStaff.getIsValid()==0){
            return 0;
        }
        if(staffTeamService.checkIsTeamLeader(task.getOwnerByTeamId(),staff.getId())
                /*|| ShiroKit.hasPermission(TaskPermission.TASK_EDIT)*/){
            messageService.deleteMessage(new Message().setMessageType(MessageType.TASK.getType()).setReferenceId(taskId).setNoticeStaffId(ownerByStaffId));
            if(task.getNoticeTime()!=null){
                Message message = new Message();
                message.setTitle("代办事项提醒");
                message.setDesc("您有一个代办事项未处理,编号为"+task.getTaskNumber());
                message.setNoticeStaffId(teamId.getStaffId());
                message.setMessageType(MessageType.TASK.getType());
                message.setReferenceId(taskId);
                message.setNoticeDate(task.getNoticeTime());
                messageService.createMessage(message);
            }
            task.setOwnerByTeamId(teamId.getTeamId());
            task.setOwnerByStaffId(teamId.getStaffId());
            return taskService.updateMaster(task);
        }else{
            throw new PowerBusinessException(PowerBusinessCode.PowerTeamStaffLeaderOnly);
        }
    }


    /**
     *  指派 Staff Owner  事件
     *
     * */
    @Override
    public Integer assignStaffOwnerTask(Long userId, long taskId, long staffId){
        Task task = taskService.retrieveMaster(taskId);
        if(staffTeamService.checkIsTeamLeader(task.getOwnerByTeamId(),userId) ){
            task.setOwnerByStaffId(staffId);
            return taskService.updateMaster(task);
        }else{
            throw new PowerBusinessException(PowerBusinessCode.PowerTeamStaffLeaderOnly);
        }
    }

    /**
    *  添加跟进信息
    * */
    @Override
    @Transactional
    public Integer addTaskUpdate(long taskId, long staffId, TaskUpdate taskUpdate){
        Task task = taskService.retrieveMaster(taskId);

        //通过taskId,staffId,在task_follower中找 找到就可以跟进 判断是否是跟进人，跟进人才可以添加
        List<TaskFollower> taskFollowers = taskFollowerMapper.selectList(new EntityWrapper<TaskFollower>().eq("taskId", taskId).eq("staffId", staffId));
        if (task.getOwnerByStaffId().equals(staffId) || taskFollowers != null && taskFollowers.size()>0
                /*|| ShiroKit.hasPermission(TaskPermission.TASK_EDIT)*/) {
            taskUpdate.setUpdateTime(new Date());
            taskUpdate.setStaffId(staffId);
            taskUpdate.setTaskId(taskId);
            if((task.getStatus().toString()).compareTo(TaskStatus.Undefined.toString())==0){
                task.setStatus(TaskStatus.WIP.toString());
                taskService.updateMaster(task);
            }
            return taskUpdateMapper.insert(taskUpdate);
        }else {
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessNoPermission);
        }
    }

    /**
     *  更新事件信息
     * */
    @Override
    public Integer updateTaskUpdate(long id, long staffId, TaskUpdate taskUpdate){
        TaskUpdate update = taskUpdateMapper.selectById(id);
        if (update.getStaffId().equals(staffId)
               ) {
            taskUpdate.setUpdateTime(new Date());
            return taskUpdateMapper.updateById(taskUpdate);
        }else {
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessNoPermission);
        }
    }

    /**
     *  删除更新事件信息
     * */
    @Override
    public Integer deleteTaskUpdate(long id, long staffId){
        TaskUpdate update = taskUpdateMapper.selectById(id);
        if (update.getStaffId().equals(staffId)
               ) {
            return taskUpdateMapper.deleteById(id);
        }else {
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessNoPermission);
        }
    }


}
