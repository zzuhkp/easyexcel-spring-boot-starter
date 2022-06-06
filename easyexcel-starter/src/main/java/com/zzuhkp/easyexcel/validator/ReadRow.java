package com.zzuhkp.easyexcel.validator;

/**
 * @author hkp
 * @date 2022/6/5 10:24 PM
 * @since 1.0
 */
public class ReadRow<T> {

    /**
     * Returns row index of a row in the sheet that contains this cell.Start form 0.
     */
    private final Integer rowIndex;

    private final T data;


    public ReadRow(Integer rowIndex, T data) {
        this.rowIndex = rowIndex;
        this.data = data;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ReadRow{" +
                "rowIndex=" + rowIndex +
                ", data=" + data +
                '}';
    }
}
