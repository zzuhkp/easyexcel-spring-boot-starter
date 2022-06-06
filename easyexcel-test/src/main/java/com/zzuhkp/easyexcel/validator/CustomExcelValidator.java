package com.zzuhkp.easyexcel.validator;

import com.zzuhkp.easyexcel.model.DemoData;
import com.zzuhkp.easyexcel.validator.errors.DefaultExcelObjectError;
import com.zzuhkp.easyexcel.validator.errors.ExcelValidErrors;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hkp
 * @date 2022/6/6 11:38 AM
 * @since
 */
@Component
public class CustomExcelValidator implements ExcelValidator<DemoData> {
    @Override
    public ExcelValidErrors validate(ReadRows<DemoData> readRows) {
        ExcelValidErrors errors = new ExcelValidErrors();

        Map<Integer, List<ReadRow<DemoData>>> group = readRows.getRows().stream()
                .collect(Collectors.groupingBy(item -> item.getData().getInteger()));

        for (Map.Entry<Integer, List<ReadRow<DemoData>>> entry : group.entrySet()) {
            if (entry.getValue().size() > 1) {
                for (ReadRow<DemoData> readRow : entry.getValue()) {
                    errors.addError(new DefaultExcelObjectError(readRow.getRowIndex() + 1, "参数重复"));
                }
            }
        }
        return errors;
    }
}
