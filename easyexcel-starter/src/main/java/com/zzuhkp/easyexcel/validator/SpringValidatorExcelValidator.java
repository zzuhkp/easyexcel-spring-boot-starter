package com.zzuhkp.easyexcel.validator;

import com.alibaba.excel.metadata.Head;
import com.zzuhkp.easyexcel.validator.errors.DefaultExcelObjectError;
import com.zzuhkp.easyexcel.validator.errors.DefaultExcelValidFieldError;
import com.zzuhkp.easyexcel.validator.errors.ExcelValidErrors;
import com.zzuhkp.easyexcel.validator.errors.ExcelValidObjectError;
import org.springframework.validation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 默认校验
 *
 * @author hkp
 * @date 2022/6/2 3:53 PM
 * @since
 */
public class SpringValidatorExcelValidator implements ExcelValidator<Object> {

    private final Validator validator;

    public SpringValidatorExcelValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public ExcelValidErrors validate(ReadRows<Object> readRows) {
        if (readRows.isEmpty()) {
            return new ExcelValidErrors(Collections.emptyList());
        }
        Map<String, Integer> fieldColumnIndexMap = readRows.getExcelReadHeadProperty().getHeadMap().values()
                .stream().collect(Collectors.toMap(Head::getFieldName, Head::getColumnIndex));
        List<ReadRow<Object>> rows = readRows.getRows();

        List<ExcelValidObjectError> validObjectErrors = new ArrayList<>();
        for (ReadRow<Object> row : rows) {
            Integer rowIndex = row.getRowIndex();
            Object data = row.getData();
            Errors errors = new BeanPropertyBindingResult(data, "data");
            this.validator.validate(data, errors);
            for (ObjectError error : errors.getAllErrors()) {
                Integer column = null;
                String message = error.getDefaultMessage();
                ExcelValidObjectError validObjectError = null;
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    column = fieldColumnIndexMap.get(fieldError.getField());
                    validObjectError = new DefaultExcelValidFieldError(rowIndex + 1, column + 1, message);
                } else {
                    validObjectError = new DefaultExcelObjectError(rowIndex + 1, message);
                }
                validObjectErrors.add(validObjectError);
            }
        }

        return new ExcelValidErrors(validObjectErrors);
    }
}
