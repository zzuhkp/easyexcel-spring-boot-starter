package com.zzuhkp.easyexcel.validator.errors;

/**
 * @author hkp
 * @date 2022/6/6 11:00 AM
 * @since
 */
public class DefaultExcelValidFieldError extends DefaultExcelObjectError implements ExcelValidFieldError {

    private Integer column;

    public DefaultExcelValidFieldError(Integer row, Integer column, String message) {
        super(row, message);
        this.column = column;
    }

    @Override
    public Integer getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "DefaultExcelValidFieldError{" +
                "column=" + column +
                '}';
    }
}
