package com.tanxin.framework.exception;

import com.tanxin.framework.model.response.ResultCode;

public class CustomException extends RuntimeException {
    ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        this.resultCode=resultCode;
    }

    public ResultCode getResultCode(){
        return resultCode;
    }

}
