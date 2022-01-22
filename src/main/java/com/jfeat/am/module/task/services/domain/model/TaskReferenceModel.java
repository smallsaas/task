package com.jfeat.am.module.task.services.domain.model;
import com.jfeat.am.module.task.services.persistence.model.TaskReference;

/**
 * Created by Code Generator on 2017-11-21
 */
public class TaskReferenceModel extends TaskReference {
    TaskReference taskReference;

    public TaskReference getTaskReference() {
        return taskReference;
    }

    public void setTaskReference(TaskReference taskReference) {
        this.taskReference = taskReference;
    }
}
