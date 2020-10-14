package com.example.demo.modules.sys.service;

import com.example.demo.modules.sys.domain.SysStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysStudentService extends JpaRepository<SysStudent, Integer> {

    SysStudent findByClassesId(Integer classesId);

    List<SysStudent> findByUserId(Integer userId);
}
