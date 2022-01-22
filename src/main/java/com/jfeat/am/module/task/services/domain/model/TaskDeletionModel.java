package com.jfeat.am.module.task.services.domain.model;

import com.jfeat.crud.plus.model.IdVersion;

import java.util.List;

/**
 * @author ygg
 * @date 15:39
 */
public class TaskDeletionModel {
    List<IdVersion> updateRecords;
    List<IdVersion> references;
    List<IdVersion> followers;

    public List<IdVersion> getUpdateRecords() {
        return updateRecords;
    }

    public void setUpdateRecords(List<IdVersion> updateRecords) {
        this.updateRecords = updateRecords;
    }

    public List<IdVersion> getReferences() {
        return references;
    }

    public void setReferences(List<IdVersion> references) {
        this.references = references;
    }

    public List<IdVersion> getFollowers() {
        return followers;
    }

    public void setFollowers(List<IdVersion> followers) {
        this.followers = followers;
    }
}
