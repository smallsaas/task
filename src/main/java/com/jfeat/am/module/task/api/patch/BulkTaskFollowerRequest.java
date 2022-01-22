package com.jfeat.am.module.task.api.patch;

import com.jfeat.am.module.task.services.persistence.model.TaskFollower;

import java.util.List;

public class BulkTaskFollowerRequest {
    private List<TaskFollower> items;

    public List<TaskFollower> getItems() {
        return items;
    }

    public void setItems(List<TaskFollower> items) {
        this.items = items;
    }
}
