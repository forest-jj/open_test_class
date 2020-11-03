package com.example.demo.modules.sys.vo;


import com.example.demo.common.annotation.EndTime;
import com.example.demo.common.annotation.StartTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class SysClassVo {

    private String classesName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @StartTime
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @EndTime
    private LocalDateTime endTime;
}
