package com.tanxin.framework.exception;

import com.tanxin.framework.model.response.CommonCode;
import com.tanxin.framework.model.response.ResultCode;

public class ExceptionCast {
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
