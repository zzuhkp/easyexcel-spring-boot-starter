package com.zzuhkp.easyexcel.controller;

import com.zzuhkp.easyexcel.annotation.ExcelParam;
import com.zzuhkp.easyexcel.annotation.ExcelResponse;
import com.zzuhkp.easyexcel.model.DemoData;
import com.zzuhkp.easyexcel.validator.ReadRow;
import com.zzuhkp.easyexcel.validator.ReadRows;
import com.zzuhkp.easyexcel.validator.errors.ExcelValidErrors;
import com.zzuhkp.easyexcel.validator.errors.ExcelValidObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hkp
 * @date 2022/6/5 9:28 PM
 * @since 1.0
 */
@RestController
@RequestMapping("/easy/excel")
public class EasyExcelController {

@PostMapping("/list/obj")
public List<DemoData> listObj(@ExcelParam @Validated List<DemoData> list, ExcelValidErrors errors) {
    if (errors.hasErrors()) {
        String messages = errors.getAllErrors().stream().map(ExcelValidObjectError::getMessage).collect(Collectors.joining(" | "));
        throw new RuntimeException("发现异常:" + messages);
    }
    return list;
}

    @PostMapping("/list/rows")
    public ReadRows<DemoData> readRows(@ExcelParam(value = "file") @Validated ReadRows<DemoData> readRows) {
        return readRows;
    }

    @ExcelResponse
    @GetMapping("/list/download")
    public List<DemoData> downloadList() {
        return Arrays.asList(new DemoData(1, "hello", new Date()), new DemoData(2, "excel", new Date()));
    }

}
