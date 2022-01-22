package com.jfeat.am.module.task.services.crud.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jfeat.am.module.message.services.domain.model.MessageType;
import com.jfeat.am.module.message.services.domain.service.MessageService;
import com.jfeat.am.module.message.services.gen.persistence.model.Message;
import com.jfeat.am.module.task.services.crud.service.TaskFollowerService;
import com.jfeat.am.module.task.services.persistence.dao.TaskFollowerMapper;
import com.jfeat.am.module.task.services.persistence.dao.TaskMapper;
import com.jfeat.am.module.task.services.persistence.model.Task;
import com.jfeat.am.module.task.services.persistence.model.TaskFollower;
import com.jfeat.am.module.team.services.crud.service.StaffTeamService;
import com.jfeat.am.module.team.services.domain.dao.QueryStaffTeamDao;
import com.jfeat.am.module.team.services.persistence.dao.StaffMapper;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.am.power.base.PowerBusinessCode;
import com.jfeat.am.power.base.PowerBusinessException;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 事件跟进表 implementation
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-21
 */
@Service
public class TaskFollowerServiceImpl extends CRUDServiceOnlyImpl<TaskFollower> implements TaskFollowerService {


    @Resource
    private TaskFollowerMapper taskFollowerMapper;
    @Resource
    TaskMapper taskMapper;
    @Resource
    StaffTeamService staffTeamService;
    @Resource
    private QueryStaffTeamDao queryStaffTeamDao;
    @Resource
    private StaffMapper staffMapper;
    @Resource
    private MessageService messageService;

    @Override
    protected BaseMapper<TaskFollower> getMasterMapper() {
        return taskFollowerMapper;
    }

    /**
     *       批量 提交关联人员(跟进)
     * */
    @Override
    public Integer addFollowerStaff(long taskId,long staffId, List<Long> ids){
        int affect = ids.size();

        // 判断权限
        Task task = taskMapper.selectById(taskId);
        /**
         *  1. 判断是不是 事件的 Owner
         *  2. 判断是不是 Owner Team 的 Leader
         *  3. 判断是否有 Task 的修改权限 即为管理员之类的权限
         *  4. 覆盖 操作 先将原有的记录删除 然后再执行插入操作
         * */
        if (task.getOwnerByStaffId().equals(staffId)
                || staffTeamService.checkIsTeamLeader(task.getOwnerByTeamId(),staffId)
                 ){
            // 先执行抹去 操作  再进行插入
            List<Long> originIds = new ArrayList<>();
            List<TaskFollower> followers = taskFollowerMapper.selectList(new EntityWrapper<TaskFollower>().eq("taskId",taskId));
            for (TaskFollower taskFollower : followers){
                originIds.add(taskFollower.getId());
                taskFollowerMapper.deleteBatchIds(originIds);
            }


            // 返回 影响条数
            if (ids==null || ids.size()==0){
                return ids.size();
            }

            for (Long id : ids){
                TaskFollower newFollower = new TaskFollower();
                newFollower.setStaffId(id);
                newFollower.setTaskId(taskId);
                TaskFollower follower = taskFollowerMapper.selectOne(newFollower);
                if(follower == null){
                    newFollower.setTeamId(task.getOwnerByTeamId());
                    taskFollowerMapper.insert(newFollower);
                }else{
                    affect--;
                }
            }
            return affect;
        }else {
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessNoPermission);
        }
    }

    /**
     *       批量 提交关联人员(跟进)
     * */
    @Override
    public Integer bulkAddStaffFollowerTask(long taskId, Staff staff, List<TaskFollower> followers){
        int affect=0;
        if (followers.size()==0){
            return followers.size();
        }
        Task task = taskMapper.selectById(taskId);
        List<Team> teams = queryStaffTeamDao.getTeamsLedByStaff(staff.getId());
        for (Team team : teams){
            List<TaskFollower> taskFollowers = taskFollowerMapper.selectList(new EntityWrapper<TaskFollower>().eq("taskId", taskId).eq("teamId", team.getId()));
            for (TaskFollower taskFollower : taskFollowers){
                if(staffMapper.selectById(taskFollower.getStaffId()).getIsValid()==1){
                    //删除消息
                    messageService.deleteMessage(new Message().setNoticeStaffId(taskFollower.getStaffId()).setReferenceId(taskId).setMessageType(MessageType.TASKFOLLOWER.getType()));
                    taskFollowerMapper.deleteById(taskFollower.getId());
                }
            }
        }
        for (TaskFollower follower : followers){
            /*if (!(staffTeamService.checkIsTeamLeader(follower.getTeamId(),staff.getId()))){
                // Do nothing
            }else {

                }*/
            follower.setTaskId(taskId);
            Staff followStaff = staffMapper.selectById(follower.getStaffId());
            if(followStaff.getIsValid()==1){
                taskFollowerMapper.insert(follower);
                //增加消息
                if(task.getNoticeTime()!=null){
                    Message message = new Message();
                    message.setTitle("代办事项提醒");
                    message.setDesc("您有一个代办事项未处理,编号为"+task.getTaskNumber());
                    message.setNoticeStaffId(follower.getStaffId());
                    message.setMessageType(MessageType.TASKFOLLOWER.getType());
                    message.setReferenceId(taskId);
                    message.setNoticeDate(task.getNoticeTime());
                    messageService.createMessage(message);
                }
                affect++;
            }
        }
        return affect;
    }
}


