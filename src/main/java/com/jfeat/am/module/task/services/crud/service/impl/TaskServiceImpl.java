package com.jfeat.am.module.task.services.crud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jfeat.am.common.exception.BusinessException;
import com.jfeat.am.core.support.DateTimeKit;
import com.jfeat.am.module.attachments.services.domain.service.AttachmentsService;
import com.jfeat.am.module.message.services.domain.model.MessageType;
import com.jfeat.am.module.message.services.domain.service.MessageService;
import com.jfeat.am.module.message.services.gen.persistence.model.Message;
import com.jfeat.am.module.task.api.patch.TaskStatus;
import com.jfeat.am.module.task.services.crud.service.TaskReferenceService;
import com.jfeat.am.module.task.services.crud.service.TaskService;
import com.jfeat.am.module.task.services.domain.dao.QueryTaskDao;
import com.jfeat.am.module.task.services.domain.model.ContainStaff;
import com.jfeat.am.module.task.services.domain.model.TaskModel;
import com.jfeat.am.module.task.services.domain.model.TaskReferenceModel;
import com.jfeat.am.module.task.services.domain.model.TaskUpdateModel;
import com.jfeat.am.module.task.services.persistence.dao.TaskFollowerMapper;
import com.jfeat.am.module.task.services.persistence.dao.TaskMapper;
import com.jfeat.am.module.task.services.persistence.dao.TaskReferenceMapper;
import com.jfeat.am.module.task.services.persistence.dao.TaskUpdateMapper;
import com.jfeat.am.module.task.services.persistence.model.Task;
import com.jfeat.am.module.task.services.persistence.model.TaskFollower;
import com.jfeat.am.module.task.services.persistence.model.TaskReference;
import com.jfeat.am.module.task.services.persistence.model.TaskUpdate;
import com.jfeat.am.module.team.services.domain.model.NameModel;
import com.jfeat.am.module.team.services.persistence.dao.StaffMapper;
import com.jfeat.am.module.team.services.persistence.dao.TeamMapper;
import com.jfeat.am.module.team.services.persistence.model.Staff;
import com.jfeat.am.module.team.services.persistence.model.Team;
import com.jfeat.am.power.base.PowerBusinessCode;
import com.jfeat.am.power.base.PowerBusinessException;
import com.jfeat.am.power.base.naming.UniversalName;
import com.jfeat.am.uaas.system.modular.system.service.SysUserService;
import com.jfeat.am.uaas.system.module.services.persistence.model.SysUser;
import com.jfeat.crud.plus.CRUD;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;
import com.jfeat.crud.plus.model.IdVersion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * ?????? implementation
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-21
 */
@Service
public class TaskServiceImpl extends CRUDServiceOnlyImpl<Task> implements TaskService {

    @Resource
    private TaskMapper taskMapper;
    @Resource
    private MessageService messageService;
    @Resource
    QueryTaskDao queryTaskDao;
    @Resource
    TaskUpdateMapper taskUpdateMapper;
    @Resource
    TaskFollowerMapper taskFollowerMapper;
    @Resource
    TaskReferenceMapper taskReferenceMapper;
    @Resource
    StaffMapper staffMapper;
    @Resource
    TeamMapper teamMapper;
    @Resource
    SysUserService userService;
    @Resource
    TaskReferenceService taskReferenceService;
    @Resource
    private AttachmentsService attachmentsService;

    @Override
    protected BaseMapper<Task> getMasterMapper() {
        return taskMapper;
    }

    protected boolean checkDateValidation(Task entity) {

        int ok = 0;
        if (entity.getNoticeTime() != null) {
            if (DateTimeKit.diff(entity.getStartTime(), entity.getNoticeTime(), DateTimeKit.SECOND_MS) > 0) {
                ok++;
            }

            if (DateTimeKit.diff(entity.getNoticeTime(), entity.getDeadline(), DateTimeKit.SECOND_MS) >= 0) {
                ok++;
            }
            return ok == 2;
        } else {
            if (DateTimeKit.diff(entity.getStartTime(), entity.getDeadline(), DateTimeKit.SECOND_MS) > 0) {
                ok++;
            }
            return ok == 1;
        }
    }

    @Override
    @Transactional
    public Long createMasterByStaff(TaskModel entity, Staff staff, boolean owner) {
        //int affect = 1;
        if (!checkDateValidation(entity)) {
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessBadRequest, "???????????????????????????????????????????????????");
        }

        entity.setCreateByStaffId(staff.getId());
        entity.setCreateTime(new Date());
        //
        if (owner) {
            entity.setOwnerByStaffId(staff.getId());
        }
        entity.setStatus(TaskStatus.Undefined.toString());
        entity.setCreateByStaffId(staff.getId());

        taskMapper.insert(entity);
        //????????????
        if(!CollectionUtils.isEmpty(entity.getAttachments())){
            attachmentsService.saveAttachments(entity.getAttachments(),Task.tableName,entity.getId());
        }
        if(entity.getNoticeTime()!=null){
            Message message = new Message();
            message.setTitle("??????????????????");
            message.setDesc("?????????????????????????????????,?????????"+entity.getTaskNumber());
            message.setMessageType(MessageType.TASK.getType());
            message.setReferenceId(entity.getId());
            message.setNoticeDate(entity.getNoticeTime());
            message.setNoticeStaffId(entity.getOwnerByStaffId());
            messageService.createMessage(message);
        }


        if (entity.getReferences() != null) {
            taskReferenceMapper.delete(new EntityWrapper<TaskReference>().eq("taskId", entity.getId()));
            for (TaskReference reference : entity.getReferences()) {
                reference.setTaskId(entity.getId());
                taskReferenceService.addReference(entity.getId(), reference);
                //affect++;
            }
        }

        if (entity.getFollowers() != null && entity.getFollowers().size() > 0) {
            taskFollowerMapper.delete(new EntityWrapper<TaskFollower>().eq("taskId", entity.getId()));
            for (TaskFollower follower : entity.getFollowers()) {
                if (follower.getStaffId() != null) {
                    follower.setStaffId(follower.getStaffId());
                    if(entity.getNoticeTime()!=null){
                        Message message = new Message();
                        message.setTitle("??????????????????");
                        message.setDesc("?????????????????????????????????,?????????"+entity.getTaskNumber());
                        message.setMessageType(MessageType.TASKFOLLOWER.getType());
                        message.setReferenceId(entity.getId());
                        message.setNoticeDate(entity.getNoticeTime());
                        message.setNoticeStaffId(follower.getStaffId());
                        messageService.createMessage(message);
                    }
                }
                follower.setTaskId(entity.getId());
                taskFollowerMapper.insert(follower);
                //affect++;
            }
        }

        return entity.getId();
    }


    /**
     * ?????? ??????
     */
    @Override
    public Integer updateTask(long taskId, TaskModel entity, Staff staff) {
        int affect = 1;
        Task originTask = taskMapper.selectOne(new Task().setId(taskId).setVersion(entity.getVersion()));
        if(null == originTask){
            throw new PowerBusinessException(PowerBusinessCode.PowerDateVersionError);
        }
        if (/*!(ShiroKit.hasPermission(TaskPermission.TASK_EDIT)) ||*/ !(originTask.getOwnerByStaffId().equals(staff.getId()))) {
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessNoPermission);
        }
        if (!checkDateValidation(entity)) {
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessBadRequest, "???????????????????????????????????????????????????");
        }
        entity.setId(taskId);
        taskMapper.updateById(CRUD.castObject(entity,Task.class));
        //????????????
        Message message = new Message();
        message.setTitle("??????????????????");
        message.setDesc("?????????????????????????????????,?????????"+originTask.getTaskNumber());
        message.setNoticeStaffId(originTask.getOwnerByStaffId());
        message.setMessageType(MessageType.TASK.getType());
        message.setReferenceId(taskId);
        if(null != entity.getNoticeTime()){
            message.setNoticeDate(entity.getNoticeTime());
            messageService.updateMessage(message);
        }else if(originTask.getOwnerByStaffId()!=null){
            message.setNoticeStaffId(originTask.getOwnerByStaffId());
            messageService.deleteMessage(message);
        }

        if(null != entity.getDeletion()){
            List<IdVersion> references = entity.getDeletion().getReferences();
            List<IdVersion> updateRecords = entity.getDeletion().getUpdateRecords();
            List<IdVersion> followers = entity.getDeletion().getFollowers();
            if(!CollectionUtils.isEmpty(references)){
                for (IdVersion idVersion : references){
                    taskReferenceMapper.delete(new EntityWrapper<TaskReference>().eq("id",idVersion.getId()).eq("version",idVersion.getVersion()));
                }
            }
            if(!CollectionUtils.isEmpty(updateRecords)){
                for (IdVersion idVersion : updateRecords){
                    taskUpdateMapper.delete(new EntityWrapper<TaskUpdate>().eq("id",idVersion.getId()).eq("version",idVersion.getVersion()));
                }
            }
            if(!CollectionUtils.isEmpty(followers)){
                for (IdVersion idVersion : followers){
                    TaskFollower taskFollower = taskFollowerMapper.selectById(idVersion.getId());
                    //????????????
                    Message message2 = new Message();
                    message2.setNoticeStaffId(taskFollower.getStaffId());
                    message2.setMessageType(MessageType.TASKFOLLOWER.getType());
                    message2.setReferenceId(taskId);
                    messageService.deleteMessage(message2);

                    taskFollowerMapper.delete(new EntityWrapper<TaskFollower>().eq("id",idVersion.getId()).eq("version",idVersion.getVersion()));
                }
            }
        }

        if (entity.getReferences() != null) {
            for (TaskReference reference : entity.getReferences()) {
                if(reference.getId() !=null && reference.getId()>0){
                    taskReferenceService.updateMaster(reference);
                }else {
                    reference.setTaskId(taskId);
                    taskReferenceMapper.insert(reference);
                }
                affect++;
            }
        }

        if (entity.getFollowers() != null) {

            for (TaskFollower follower : entity.getFollowers()) {
                follower.setTaskId(taskId);
                follower.setTeamId(follower.getTeamId());

                if(follower.getId()!=null && follower.getId()>0){
                    taskFollowerMapper.updateById(follower);
                }else{
                    taskFollowerMapper.insert(follower);

                    if (follower.getStaffId() != null) {
                        follower.setStaffId(follower.getStaffId());
                        //????????????
                        if(null != entity.getNoticeTime()){
                            Message message2 = new Message();
                            message2.setTitle("??????????????????");
                            message2.setDesc("?????????????????????????????????,?????????"+originTask.getTaskNumber());
                            message2.setNoticeStaffId(follower.getStaffId());
                            message2.setMessageType(MessageType.TASKFOLLOWER.getType());
                            message2.setReferenceId(taskId);
                            message2.setNoticeDate(entity.getNoticeTime());
                            messageService.createMessage(message2);
                        }
                    }
                }
                affect++;
            }
        }

        return affect;
    }


    @Override
    public List<Task> tasksFromCommunicationRecord(long id) {
        return taskMapper.selectList(new EntityWrapper<Task>().eq(
                "communicationRecordId", id)
        );
    }

    /**
     * include TaskUpdate
     */
    @Override
    @Transactional
    public TaskModel taskIncludeUpdate(long id,Long staffId) {
        // ??????????????? Task ??????
        Task task = taskMapper.selectById(id);
        if (task == null) {
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessFileNotFound);
        }

        if(task.getNoticeTime()!=null && task.getNoticeTime().getTime()<=System.currentTimeMillis() && task.getOwnerByStaffId().equals(staffId)){
            messageService.deleteMessage(new Message().setMessageType(MessageType.TASK.getType()).setReferenceId(id).setNoticeStaffId(task.getOwnerByStaffId()));
        }
        JSONObject taskObj = JSON.parseObject(JSONObject.toJSONString(task));
        Staff ownerStaff = staffMapper.selectById(task.getOwnerByStaffId());
        taskObj.put("ownerStaffName", (ownerStaff == null) ? "" :
                new UniversalName(ownerStaff.getFirstName(), ownerStaff.getLastName()).toString());
        List<TaskUpdateModel> updateRecords = new ArrayList<>();
        List<TaskUpdate> updateRecord = taskUpdateMapper.selectList
                (new EntityWrapper<TaskUpdate>().eq("taskId", id));
        for (TaskUpdate update : updateRecord) {
            // ????????????????????? ???????????????????????? Staff  ?????????
            JSONObject updateObj = JSON.parseObject(JSONObject.toJSONString(update));
            Staff staff = staffMapper.selectById(update.getStaffId());
            updateObj.put("userId", staff.getUserId());
            SysUser user = userService.selectById(staff.getUserId());
            updateObj.put("loginName", user.getAccount());
            updateObj.put("staffName", new UniversalName(staff.getFirstName(), staff.getLastName()).toString());
            TaskUpdateModel model = JSON.parseObject(JSON.toJSONString(updateObj), TaskUpdateModel.class);
            updateRecords.add(model);
        }

        List<NameModel> followers = new ArrayList<>();
        //  ???????????????
        List<TaskFollower> taskFollower = taskFollowerMapper.selectList
                (new EntityWrapper<TaskFollower>().eq("taskId", id));

        if (taskFollower != null && taskFollower.size() > 0) {
            List<String> containStaffName = new ArrayList<>();
            for (TaskFollower follower : taskFollower) {
                if(task.getNoticeTime()!=null && task.getNoticeTime().getTime()<=System.currentTimeMillis() && follower.getStaffId().equals(staffId)){
                    messageService.deleteMessage(new Message().setMessageType(MessageType.TASKFOLLOWER.getType()).setReferenceId(id).setNoticeStaffId(follower.getStaffId()));
                }
                NameModel record = new NameModel();
                Team team = (follower.getTeamId() == null) ? null : (teamMapper.selectById(follower.getTeamId()));
                Staff staff = (follower.getStaffId() == null) ? null : (staffMapper.selectById(follower.getStaffId()));
                record.setStaffId(staff == null ? null : staff.getId());
                record.setTeamId(follower.getTeamId());
                record.setStaffName(staff == null ? null : new UniversalName(staff.getFirstName(), staff.getLastName()).toString());
                record.setTeamName((team == null) ? null : team.getTeamName());
                containStaffName.add(staff == null ? null : new UniversalName(staff.getFirstName(), staff.getLastName()).toString());
                followers.add(record);
            }
            taskObj.put("containStaffName", containStaffName);  // ????????????
        } else {

        }
        // ???????????????????????????????????????????????? ???????????????????????????
        List<TaskReference> references = taskReferenceMapper.selectList
                (new EntityWrapper<TaskReference>().eq("taskId", id));
        if (references != null && references.size() > 0) {
            List<TaskReferenceModel> models = new ArrayList<>();
            for (TaskReference reference : references) {
                JSONObject referenceObj = JSON.parseObject(JSONObject.toJSONString(reference));
                TaskReference taskReference = taskReferenceMapper.selectById(reference.getReferenceId());
                referenceObj.put("taskReference", taskReference);
                TaskReferenceModel model = JSON.parseObject(JSON.toJSONString(referenceObj), TaskReferenceModel.class);
                models.add(model);
            }
        } else {

        }
        // ???????????????????????????   ??????  ID  ?????????????????????   getCreateByStaffId
        Staff staff = staffMapper.selectById(task.getCreateByStaffId());
        taskObj.put("createByStaffName",staff == null ? null : new UniversalName(staff.getFirstName(), staff.getLastName()).toString());
        taskObj.put("updateRecords", updateRecords);  //????????????
        taskObj.put("references", references);      //????????????
        taskObj.put("followerRecords", followers);  // ????????????
        taskObj.put("followers", taskFollower);
        TaskModel model = JSON.parseObject(JSON.toJSONString(taskObj), TaskModel.class);
        // ????????????????????????
        model.fixCopyFollowerRecordsToFollowerList();
        //????????????
        model.setAttachments(attachmentsService.getAttachments(Task.tableName,id));
        return model;
    }
    //
    public ContainStaff showContain(long teamId, List<Long> ids) {
        Team team = teamMapper.selectById(teamId);
        JSONObject teamObj = JSON.parseObject(JSON.toJSONString(team));
        List<NameModel> staffs = new ArrayList<>();
        for (Long id : ids) {
            NameModel model = new NameModel();
            Staff staff = staffMapper.selectById(id);
            model.setStaffName(new UniversalName(staff.getFirstName(), staff.getLastName()).toString());
            model.setStaffId(id);
            staffs.add(model);
        }
        teamObj.put("staffs", staffs);
        ContainStaff model = JSON.parseObject(JSON.toJSONString(teamObj), ContainStaff.class);
        return model;
    }

    @Override
    @Transactional
    public Integer deleteMaster(long id,Long staffId,Integer version) {
        int effect=0;
        Task task = taskMapper.selectById(id);
        if( task.getOwnerByStaffId().longValue()==staffId){

            String status = task.getStatus();
            if(status==null || status.equals("Open") || status.equals("Draft") || status.equals("Closed")){
                effect+=super.deleteMaster(id,version);
                //????????????
                attachmentsService.deleteAttachments(Task.tableName,id);
                //??????????????????
                List<TaskFollower> taskFollowerList = taskFollowerMapper.selectList(new EntityWrapper<TaskFollower>().eq("taskId", id));
                for(TaskFollower follower : taskFollowerList){
                    Message message2 = new Message();
                    message2.setNoticeStaffId(follower.getStaffId());
                    message2.setMessageType(MessageType.TASKFOLLOWER.getType());
                    message2.setReferenceId(id);
                    messageService.deleteMessage(message2);
                }
                effect+=taskFollowerMapper.delete(new EntityWrapper<TaskFollower>().eq("taskId",id));
                effect+=taskReferenceMapper.delete(new EntityWrapper<TaskReference>().eq("taskId",id));
                effect+=taskUpdateMapper.delete(new EntityWrapper<TaskUpdate>().eq("taskId",id));
                //????????????
                messageService.deleteMessage(new Message().setMessageType(MessageType.TASK.getType()).setReferenceId(id).setNoticeStaffId(task.getOwnerByStaffId()));
                return effect;
            }else{
                throw new BusinessException(5000,status+"?????????????????????");
            }
        }else{
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessNoPermission);
        }
        /*if (queryTaskDao.checkTaskReferenceDependency(id) > 0)
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessDeleteAssociatedOne, "????????????,task" + id + "?????????(checkTaskReferenceDependency)");
        if (queryTaskDao.checkTaskFollowerDependency(id) > 0)
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessDeleteAssociatedOne, "????????????,task" + id + "?????????(checkTaskFollowerDependency)");
        if (queryTaskDao.checkTaskUpdateDependency(id) > 0)
            throw new PowerBusinessException(PowerBusinessCode.PowerBusinessDeleteAssociatedOne, "????????????,task" + id + "?????????(checkTaskUpdateDependency)");*/

    }

}

