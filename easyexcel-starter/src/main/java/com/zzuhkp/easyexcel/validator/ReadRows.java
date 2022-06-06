package com.zzuhkp.easyexcel.validator;

import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hkp
 * @date 2022/6/5 10:29 PM
 * @since 1.0
 */
public class ReadRows<T> {

    private ExcelReadHeadProperty excelReadHeadProperty;

    private List<ReadRow<T>> rows;

    public ReadRows() {
        this.rows = new ArrayList<>();
    }

    public void setExcelReadHeadProperty(ExcelReadHeadProperty excelReadHeadProperty) {
        this.excelReadHeadProperty = excelReadHeadProperty;
    }

    public void setRows(List<ReadRow<T>> rows) {
        this.rows = rows;
    }

    public ExcelReadHeadProperty getExcelReadHeadProperty() {
        return excelReadHeadProperty;
    }

    public List<ReadRow<T>> getRows() {
        return rows;
    }

    public boolean isEmpty() {
        return rows == null || rows.isEmpty();
    }

    @Override
    public String toString() {
        return "ReadRows{" +
                "excelReadHeadProperty=" + excelReadHeadProperty +
                ", rows=" + rows +
                '}';
    }

}
