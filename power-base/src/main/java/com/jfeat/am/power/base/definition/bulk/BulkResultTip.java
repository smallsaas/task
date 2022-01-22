package com.jfeat.am.power.base.definition.bulk;

/**
 * Created by Administrator on 2017/11/29.
 */
public class BulkResultTip {
    private Integer code;
    private String message;
    private Integer count;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BulkResultTip(Integer code, String message){
        setCode(code);
        setMessage(message);
    }
    public BulkResultTip(Integer code){
        setCode(code);
    }

    public BulkResultTip(){}
}
