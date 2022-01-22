package com.jfeat.am.module.task.services.domain.model;

import com.jfeat.am.module.task.services.persistence.model.Task;


import java.util.List;
/**
 * Created by Administrator on 2018/1/18.
 */
public class TaskListModel extends Task{

    String createByStaffName;
    List<String> containStaffName;

    public String getCreateByStaffName() {
        return createByStaffName;
    }

    public void setCreateByStaffName(String createByStaffName) {
        this.createByStaffName = createByStaffName;
    }

    public List<String> getContainStaffName() {
        return containStaffName;
    }

    public void setContainStaffName(List<String> containStaffName) {
        this.containStaffName = containStaffName;
    }
}
