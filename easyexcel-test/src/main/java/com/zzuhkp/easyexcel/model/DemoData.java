package com.zzuhkp.easyexcel.model;

import com.zzuhkp.easyexcel.validator.DemoDataValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author hkp
 * @date 2022/6/5 9:29 PM
 * @since
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@DemoDataValid
public class DemoData {

    private Integer integer;

    private String string;

    private Date date;
}
