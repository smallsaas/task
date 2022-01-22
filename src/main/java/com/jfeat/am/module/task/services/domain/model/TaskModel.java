package com.jfeat.am.module.task.services.domain.model;

import com.jfeat.am.module.attachments.services.gen.persistence.model.Attachments;
import com.jfeat.am.module.task.services.persistence.model.Task;
import com.jfeat.am.module.task.services.persistence.model.TaskFollower;
import com.jfeat.am.module.task.services.persistence.model.TaskReference;
import com.jfeat.am.module.team.services.domain.model.NameModel;
import com.jfeat.am.module.team.services.domain.model.StaffIdName;
import com.jfeat.am.module.team.services.fix.model.TeamStaffModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Code Generator on 2017-11-21
 */
public class TaskModel extends Task {
    List<Attachments> attachments;
    List<TaskUpdateModel> updateRecords;
    List<TaskFollower> followers;
    List<TaskReference> references;
    String createByStaffName;
    List<String> containStaffName;
    List<NameModel> followerRecords;
    List<String> containTeamName;
    private TaskDeletionModel deletion;

    //// fix followerRecords to Team including Staffs data
    List<TeamStaffModel> followerList;

    public List<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachments> attachments) {
        this.attachments = attachments;
    }
    public void fixCopyFollowerRecordsToFollowerList() {
        if (followerRecords != null && followerRecords.size() > 0) {

            if (followerList == null) {
                followerList = new ArrayList<>();
            }

            /// check team hash
            HashMap<Long, TeamStaffModel> hash = new HashMap<>();

            for (NameModel name : followerRecords) {
                Long key = name.getTeamId();

                // add new team
                if (!hash.containsKey(key)) {
                    TeamStaffModel it = new TeamStaffModel();
                    it.setId(key);
                    it.setTeamName(name.getTeamName());
                    hash.put(key, it);
                }


                if (name.getStaffId() != null && name.getStaffName() != null && name.getStaffName().length() > 0) {

                    TeamStaffModel team = hash.get(key);
                    if (team.getStaffs() == null) {
                        team.setStaffs(new ArrayList<>());
                    }

                    List<StaffIdName> staffs = team.getStaffs();
                    StaffIdName staff = new StaffIdName();
                    staff.setStaffId(name.getStaffId());
                    staff.setStaffName(name.getStaffName());

                    staffs.add(staff);
                }
            }

            Collection<TeamStaffModel> collection = hash.values();
            if(collection!=null && collection.size()>0){
                followerList.addAll(collection);
            }
        }
    }

    public List<TeamStaffModel> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(List<TeamStaffModel> followerList) {
        this.followerList = followerList;
    }
    // end fix


    String  ownerStaffName;

    public String getOwnerStaffName() {
        return ownerStaffName;
    }

    public void setOwnerStaffName(String ownerStaffName) {
        this.ownerStaffName = ownerStaffName;
    }

    public List<NameModel> getFollowerRecords() {
        return followerRecords;
    }

    public void setFollowerRecords(List<NameModel> followerRecords) {
        this.followerRecords = followerRecords;
    }

    public String getCreateByStaffName() {
        return createByStaffName;
    }

    public List<String> getContainStaffName() {
        return containStaffName;
    }

    public void setContainStaffName(List<String> containStaffName) {
        this.containStaffName = containStaffName;
    }

    public List<String> getContainTeamName() {
        return containTeamName;
    }

    public void setContainTeamName(List<String> containTeamName) {
        this.containTeamName = containTeamName;
    }

    public void setCreateByStaffName(String createByStaffName) {
        this.createByStaffName = createByStaffName;
    }

    public List<TaskFollower> getFollowers() {
        return followers;
    }

    public void setFollowers(List<TaskFollower> followers) {
        this.followers = followers;
    }

    public List<TaskReference> getReferences() {
        return references;
    }

    public void setReferences(List<TaskReference> references) {
        this.references = references;
    }

    public List<TaskUpdateModel> getUpdateRecords() {
        return updateRecords;
    }

    public void setUpdateRecords(List<TaskUpdateModel> updateRecords) {
        this.updateRecords = updateRecords;
    }

    @Override
    public String toString(){
        return getTaskNumber();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        TaskModel taskModel = (TaskModel)obj;
        if(this.getId().longValue()==taskModel.getId().longValue())
            return true;
        else
            return false;
    }

    public TaskDeletionModel getDeletion() {
        return deletion;
    }

    public void setDeletion(TaskDeletionModel deletion) {
        this.deletion = deletion;
    }
}
