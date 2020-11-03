package com.example.demo.modules.sys.service;

import com.example.demo.modules.sys.domain.SysClass;
import com.example.demo.modules.sys.domain.SysStudent;

public interface SysStudentClassesService {

    boolean subscribe(SysStudent sysStudent);

    boolean cancel(SysClass sysClass,Integer userId);
}
