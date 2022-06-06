package com.zzuhkp.easyexcel.validator;

import com.zzuhkp.easyexcel.validator.errors.ExcelValidErrors;

/**
 * 数据校验
 *
 * @author hkp
 * @date 2022/6/2 3:49 PM
 * @since
 */
public interface ExcelValidator<T> {

    /**
     * 校验
     *
     * @param readRows 读取的行信息
     * @return
     */
    ExcelValidErrors validate(ReadRows<T> readRows);

}
