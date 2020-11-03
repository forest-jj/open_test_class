package com.example.demo.modules.sys.service;

import com.example.demo.modules.sys.domain.SysStudent;
import com.example.demo.modules.sys.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysStudentService extends JpaRepository<SysStudent, Integer> {
    List<SysStudent> findBySysUserOrderBySysClass(SysUser sysUser);

    void deleteBySysUser_UserIdAndSysClass_ClassesId(Integer userId, Integer classesId);
}
