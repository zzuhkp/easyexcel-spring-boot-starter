package com.zzuhkp.easyexcel.validator.errors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hkp
 * @date 2022/6/5 10:31 PM
 * @since 1.0
 */
public class ExcelValidErrors {

    private final List<ExcelValidObjectError> errors;

    public ExcelValidErrors() {
        this.errors = new ArrayList<>();
    }

    public ExcelValidErrors(List<ExcelValidObjectError> errors) {
        this.errors = new ArrayList<>(errors);
    }

    public List<ExcelValidObjectError> getAllErrors() {
        return Collections.unmodifiableList(errors);
    }


    public List<ExcelValidObjectError> getObjectErrors() {
        return this.errors.stream().filter(item -> item.getClass() == ExcelValidObjectError.class).collect(Collectors.toList());
    }

    public List<ExcelValidFieldError> getFieldErrors() {
        return this.errors.stream().filter(item -> item.getClass() == ExcelValidFieldError.class)
                .map(item -> (ExcelValidFieldError) item).collect(Collectors.toList());
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public void merge(ExcelValidErrors errors) {
        this.errors.addAll(errors.getAllErrors());
    }

    public boolean addError(ExcelValidObjectError error) {
        return this.errors.add(error);
    }

    @Override
    public String toString() {
        return "ExcelValidErrors{" +
                "errors=" + errors +
                '}';
    }
}
