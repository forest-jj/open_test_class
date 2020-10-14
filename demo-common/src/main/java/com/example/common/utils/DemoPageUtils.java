package com.example.common.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@UtilityClass
public class DemoPageUtils {

    public Pageable getPage() {
        return getPage(0, 10);
    }

    public Pageable getPage(int pageNum, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        return pageable;
    }

}
