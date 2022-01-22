package com.jfeat.am.power.base.definition.bulk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */
public class BulkResult {
    private BulkResultTip success;
    private List<BulkResultTip> failure;

    public BulkResult(BulkResultTip success){
        setSuccess(success);
    }

    public BulkResult(BulkResultTip success, List<BulkResultTip> failure){
        setSuccess(success);
        if(failure!=null && failure.size()>0) {
            setFailure(failure);
        }
    }

    public BulkResult(BulkResultTip success, BulkResultTip failure) {
        setSuccess(success);
        if(failure!=null) {
            List<BulkResultTip> failureList = new ArrayList<BulkResultTip>();
            failureList.add(failure);
            setFailure(failureList);
        }
    }

    public BulkResultTip getSuccess() {
        return success;
    }

    public void setSuccess(BulkResultTip success) {
        this.success = success;
    }

    public List<BulkResultTip> getFailure() {
        return failure;
    }

    public void setFailure(List<BulkResultTip> failure) {
        this.failure = failure;
    }
}
