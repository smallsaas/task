package com.jfeat.am.power.base;

import com.alibaba.fastjson.util.TypeUtils;

/**
 * Created by vincenthuang on 06/01/2018.
 */
public class PowerRuntime {
    static {
        TypeUtils.compatibleWithJavaBean = true;
    }

    public static PowerRuntime run(){
        return new PowerRuntime();
    }
}
