package com.zzuhkp.easyexcel.validator;

import com.zzuhkp.easyexcel.validator.errors.ExcelValidErrors;

/**
 * 校验异常
 *
 * @author hkp
 * @date 2022/6/2 3:47 PM
 * @since
 */
public class ExcelValidException extends RuntimeException {

    private ExcelValidErrors errors;

    public ExcelValidException(String message, ExcelValidErrors errors) {
        super(message);
        this.errors = errors;
    }

    public ExcelValidErrors getErrors() {
        return errors;
    }

}
