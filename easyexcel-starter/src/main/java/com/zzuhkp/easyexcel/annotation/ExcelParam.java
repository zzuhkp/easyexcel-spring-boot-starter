package com.zzuhkp.easyexcel.annotation;

import java.lang.annotation.*;

/**
 * @author hkp
 * @date 2022/6/2 11:12 AM
 * @since 1.0
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelParam {

    /**
     * 字段名称
     *
     * @return
     */
    String value() default "file";

    /**
     * 是否必须
     *
     * @return
     */
    boolean required() default true;

}
