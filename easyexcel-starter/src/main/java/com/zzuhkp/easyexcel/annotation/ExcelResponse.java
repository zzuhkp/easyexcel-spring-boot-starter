package com.zzuhkp.easyexcel.annotation;

import java.lang.annotation.*;

/**
 * @author hkp
 * @date 2022/6/6 3:18 PM
 * @since 1.0
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResponse {

    String fileName() default "default";

    String sheetName() default "Sheet1";

}
