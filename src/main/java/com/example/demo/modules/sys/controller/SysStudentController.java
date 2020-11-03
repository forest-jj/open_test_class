package com.example.demo.modules.sys.controller;

import com.example.demo.common.utils.DemoPageUtils;
import com.example.demo.common.utils.PageResult;
import com.example.demo.common.utils.R;
import com.example.demo.modules.security.util.JwtUtil;
import com.example.demo.modules.sys.domain.SysClass;
import com.example.demo.modules.sys.domain.SysStudent;
import com.example.demo.modules.sys.domain.SysUser;
import com.example.demo.modules.sys.service.SysClassService;
import com.example.demo.modules.sys.service.SysStudentClassesService;
import com.example.demo.modules.sys.service.SysStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/student")
@RestController
public class SysStudentController {


    @Autowired
    private SysStudentService sysStudentService;

    @Autowired
    private SysClassService sysClassService;

    @Autowired
    private SysStudentClassesService sysStudentClassesService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 查询课程
     *
     * @param currentPage 当前页
     * @param totalPage   总页数
     * @return
     */
    @GetMapping("/list")
    public PageResult<SysClass> list(@RequestParam(value = "page", defaultValue = "1") int currentPage, @RequestParam(value = "limit", defaultValue = "10") int totalPage, String classesName, HttpServletRequest request) {
        Integer userId = jwtUtil.getUserFromToken(request).getUserId();//获取当前id
        SysUser sysUser = new SysUser();
        SysClass sysClass = new SysClass();
        sysUser.setUserId(userId);
        sysClass.setCount(0);
        List<SysStudent> sysStudents = sysStudentService.findBySysUserOrderBySysClass(sysUser);
        List<SysClass> sysClasses = null;
        if (sysStudents.size() > 0) {
            List<SysClass> classes = sysStudents.stream().map(SysStudent::getSysClass).collect(Collectors.toList());
            List<Integer> classesId = classes.stream().map(SysClass::getClassesId).collect(Collectors.toList());
            sysClasses = sysClassService.findByCountGreaterThanAndClassesIdIsNotInOrderByCreateTime(0, classesId);
        }else {
            sysClasses = sysClassService.findByCountGreaterThan(0);
        }

        return new PageResult<SysClass>(DemoPageUtils.getPageList(sysClasses, currentPage, totalPage), sysClasses.size());
    }

    /**
     * 查询已订阅课程
     *
     * @param currentPage 当前页
     * @param totalPage   总页数
     * @return
     */
    @GetMapping("/lists")
    public PageResult<SysClass> lists(@RequestParam(value = "page", defaultValue = "1") int currentPage, @RequestParam(value = "limit", defaultValue = "10") int totalPage,  HttpServletRequest request) {
        List<SysClass> sysClasses =   sysClassService.findAll((r,q,c)->{
            Join join = r.join("sysStudent", JoinType.LEFT);
            return c.equal(join.get("sysUser").get("userId").as(Integer.class),getUserId(request));
        });


        return new PageResult<SysClass>(sysClasses);
    }

    /**
     * 订阅课程
     *
     * @param classesId
     * @return
     */
    @PostMapping("/subscribe")
    public R subscribe(@RequestBody Integer classesId, HttpServletRequest request) {
        SysStudent sysStudent = new SysStudent();
        SysUser sysUser = new SysUser();
        SysClass sysClass = new SysClass();
        sysUser.setUserId(getUserId(request));
        sysStudent.setSysUser(sysUser);
        sysClass.setClassesId(classesId);
        sysStudent.setSysClass(sysClass);
        sysStudentClassesService.subscribe(sysStudent);

        return R.ok("订阅成功");
    }

    /**
     * 订阅课程
     *
     * @param sysClass
     * @return
     */
    @PostMapping("/cancel")
    public R cancel(@RequestBody SysClass sysClass,HttpServletRequest request) {
        return R.ok(sysStudentClassesService.cancel(sysClass,getUserId(request)));
    }

    private Integer getUserId(HttpServletRequest request) {
        return jwtUtil.getUserFromToken(request).getUserId();
    }

}
