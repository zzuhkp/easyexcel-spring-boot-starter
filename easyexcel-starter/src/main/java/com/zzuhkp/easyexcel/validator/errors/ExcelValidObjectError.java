package com.zzuhkp.easyexcel.validator.errors;

/**
 * @author hkp
 * @date 2022/6/2 3:48 PM
 * @since 1.0
 */
public interface ExcelValidObjectError {

    /**
     * 获取行号，从 1 开始
     *
     * @return
     */
    Integer getRow();

    /**
     * 获取错误消息
     *
     * @return
     */
    String getMessage();
}
