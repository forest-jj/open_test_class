package com.example.demo.modules.sys.controller;

import com.example.common.utils.R;
import com.example.demo.modules.sys.domain.SysClass;
import com.example.demo.modules.sys.domain.SysStudent;
import com.example.demo.modules.sys.service.SysClassService;
import com.example.demo.modules.sys.service.SysStudentClassesService;
import com.example.demo.modules.sys.service.SysStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/student")
@Controller
public class SysStudentController {

    @Autowired
    private SysClassService sysClassService;

    @Autowired
    private SysStudentService sysStudentService;


    @Autowired
    private SysStudentClassesService sysStudentClassesService;

    /**
     * TODO: 条件查询
     * 查询课程
     * @param pageNum 当前页
     * @param pageSize 总页数
     * @param model
     * @param token
     * @return
     */
    @GetMapping("/list")
    public String list(Model model, String token,Integer userId){
        List<SysClass> sysClasses = sysClassService.findByCountGreaterThan(0);
        List<SysStudent> students = sysStudentService.findByUserId(userId);
        //遍历取交集 查询学生订阅的课程
        List<SysClass> sysStudents = sysClasses.stream().filter(c -> students.stream().map(SysStudent::getClassesId)
                         .anyMatch(id -> id.equals(c.getClassesId()))).collect(Collectors.toList());
        //只查看有效课程
        List<SysClass> sysClass = sysClasses.stream().filter(c -> !sysStudents.contains(c)).collect(Collectors.toList());

        model.addAttribute("sysClasses",sysClass);
        model.addAttribute("sysStudents",sysStudents);
        model.addAttribute("token",token);

        return "student";
    }

    /**
     * 订阅课程
     * @param sysStudent
     * @return
     */
    @ResponseBody
    @PostMapping("/subscribe")
    public R subscribe(@RequestBody SysStudent sysStudent){
        sysStudentClassesService.subscribe(sysStudent);

        return R.ok("订阅成功");
    }

   /**
     * 订阅课程
     * @param sysStudent
     * @return
     */
    @ResponseBody
    @PostMapping("/cancel")
    public R cancel(@RequestBody SysStudent sysStudent){
        sysStudentClassesService.cancel(sysStudent);

        return R.ok("取消成功");
    }



}
