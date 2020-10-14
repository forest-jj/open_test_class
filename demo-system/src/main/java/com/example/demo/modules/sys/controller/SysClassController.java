package com.example.demo.modules.sys.controller;

import com.example.common.utils.DemoPageUtils;
import com.example.common.utils.R;
import com.example.demo.modules.sys.domain.SysClass;
import com.example.demo.modules.sys.service.SysClassService;
import com.example.demo.modules.sys.service.SysStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/class")
@Controller
public class SysClassController {

    @Autowired
    private SysClassService sysClassService;

    @Autowired
    private SysStudentService studentService;

    /**
     * TODO：条件查询
     * 查询课程
     * @param pageNum 当前页
     * @param pageSize 总页数
     * @param model
     * @param token
     * @return
     */
    @GetMapping("/list")
    public String list(@RequestParam(value = "pageNum",defaultValue = "0") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, Model model, String token){
        Page<SysClass> sysClasses = sysClassService.findAll(DemoPageUtils.getPage(pageNum, pageSize));
        model.addAttribute("sysClasses",sysClasses);
        model.addAttribute("token",token);

        return "teacher";
    }


    /**
     * 新增用户
     * @param token
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String add(String token,Model model){
        model.addAttribute("token", token);

        return "class/add";
    }

    /**
     * 更新课程
     * @param classesId
     * @param token
     * @param model
     * @return
     */
    @GetMapping("/update")
    public String update(Integer classesId,String token,Model model){
        SysClass sysClass = sysClassService.findById(classesId).get();
        model.addAttribute("sysClass", sysClass);
        model.addAttribute("token", token);

        return "class/update";
    }

    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public R update(@RequestBody SysClass sysClass){
        sysClassService.saveAndFlush(sysClass);

        return R.ok("保存成功");
    }

    /**
     * 删除用户
     * @return
     */
    @DeleteMapping("/delete/{classesId}")
    @ResponseBody
    public R delete(Integer classesId){
        if (!ObjectUtils.isEmpty(studentService.findByClassesId(classesId))) {
            return R.error("请取消后删除！");
        }

        sysClassService.deleteById(classesId);

        return R.ok("删除成功");
    }



}
