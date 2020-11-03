package com.example.demo.common.utils;

import lombok.experimental.UtilityClass;

import java.util.List;


@UtilityClass
public class DemoPageUtils {

    public List getPageList(List<?> list,int currentPage,  int totalPage){
        int count = list.size();

        if (totalPage > count){
            return list;
        }

        if (((currentPage -1)*totalPage + totalPage) < count){
            count = (currentPage -1)*totalPage + totalPage;
        }

        List newList = list.subList((currentPage -1)*totalPage,count);

        return newList;
    }

}
