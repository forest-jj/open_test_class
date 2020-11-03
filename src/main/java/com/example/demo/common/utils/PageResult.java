package com.example.demo.common.utils;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private int code; // 状态码, 0表示成功

    private String msg;  // 提示信息

    private long count; // 总数量, bootstrapTable是total

    private int currentPage;

    private List<T> data; // 当前数据, bootstrapTable是rows

    public PageResult() {
    }

    public PageResult(List<T> rows) {
        this(rows, rows.size());
    }

    public PageResult(List<T> rows, long total) {
        this.count = total;
        this.data = rows;
        this.code = 0;
        this.msg = "查询成功";
    }


}
