package com.jfeat.am.power.base;

import com.jfeat.am.common.exception.BusinessException;

/**
 * Created by vincent on 2017/11/29.
 */
public class PowerBusinessException extends BusinessException {
    public static final int PowerBusinessCodeBase = 4000;

    public PowerBusinessException(PowerBusinessCode bizCode, String message) {
        super(bizCode.getCode(), (message==null||message.length()==0) ? bizCode.getMessage() : message);
    }

    public PowerBusinessException(PowerBusinessCode bizCode) {
        super(bizCode.getCode(), bizCode.getMessage());
    }

}
