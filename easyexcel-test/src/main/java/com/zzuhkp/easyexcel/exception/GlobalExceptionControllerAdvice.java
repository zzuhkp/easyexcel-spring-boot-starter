package com.zzuhkp.easyexcel.exception;

import com.alibaba.fastjson.JSON;
import com.zzuhkp.easyexcel.validator.ExcelValidException;
import com.zzuhkp.easyexcel.validator.errors.ExcelValidErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author hkp
 * @date 2022/6/6 7:19 PM
 * @since
 */
@RestControllerAdvice
public class GlobalExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String handleException(ExcelValidException e) {
        ExcelValidErrors errors = e.getErrors();
        return JSON.toJSONString(errors);
    }
}
