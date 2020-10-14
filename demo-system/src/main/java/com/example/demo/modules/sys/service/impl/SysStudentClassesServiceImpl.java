package com.example.demo.modules.sys.service.impl;

import com.example.demo.modules.sys.domain.SysClass;
import com.example.demo.modules.sys.domain.SysStudent;
import com.example.demo.modules.sys.service.SysClassService;
import com.example.demo.modules.sys.service.SysStudentClassesService;
import com.example.demo.modules.sys.service.SysStudentService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.beans.Transient;

@Service
public class SysStudentClassesServiceImpl implements SysStudentClassesService {

    @Resource
    private SysClassService sysClassService;

    @Resource
    private SysStudentService sysStudentService;

    /**
     * 学生订阅
     * @param sysStudent
     * @return
     */
    @Transactional
    @CachePut(cacheNames = "subscribe")
    @Override
    public boolean subscribe(SysStudent sysStudent) {
        sysStudentService.save(sysStudent);

        SysClass sysClass = sysClassService.getOne(sysStudent.getClassesId());
        sysClass.setCount(sysClass.getCount() - 1);

        return ObjectUtils.isEmpty(sysClassService.saveAndFlush(sysClass));
    }


    /**
     * 学生取消
     * @param sysStudent
     * @return
     */
    @CacheEvict(cacheNames = "cancel")
    @Transient
    @Override
    public boolean cancel(SysStudent sysStudent) {
        sysStudentService.delete(sysStudent);

        SysClass sysClass = sysClassService.getOne(sysStudent.getClassesId());
        sysClass.setCount(sysClass.getCount() + 1);

        return ObjectUtils.isEmpty(sysClassService.saveAndFlush(sysClass));
    }
}
