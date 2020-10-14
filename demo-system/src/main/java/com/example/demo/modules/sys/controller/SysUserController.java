package com.example.demo.modules.sys.controller;

import com.example.common.utils.DemoPageUtils;
import com.example.common.utils.DemoUtils;
import com.example.common.utils.R;
import com.example.demo.modules.sys.domain.SysUser;
import com.example.demo.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * TODO：条件查询
     * 查询用户
     * @param pageNum 当前页
     * @param pageSize 总页数
     * @param model
     * @param token
     * @return
     */
    @GetMapping("/list")
    public String list(@RequestParam(value = "pageNum",defaultValue = "0") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, Model model,String token){
        Page<SysUser> sysUsers = sysUserService.findAll(DemoPageUtils.getPage(pageNum, pageSize));
        model.addAttribute("users",sysUsers);
        model.addAttribute("token",token);

        return "admin";
    }

    /**
     * 新增用户
     * @param userId
     * @param token
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String add(String token,Model model){
            model.addAttribute("token", token);

        return "admin/add";
    }

    /**
     * 更新用户
     * @param userId
     * @param token
     * @param model
     * @return
     */
    @GetMapping("/update")
    public String update(Integer userId,String token,Model model){
            SysUser sysUser = sysUserService.findById(userId).get();
            model.addAttribute("user", sysUser);
            model.addAttribute("token", token);

        return "admin/update";
    }

    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public R update(@RequestBody SysUser user){
        user.setPassword(DemoUtils.encode(user.getPassword()));
        sysUserService.saveAndFlush(user);

        return R.ok("保存成功");
    }

    /**
     * 删除用户
     * @return
     */
    @DeleteMapping("/delete/{userId}")
    @ResponseBody
    public R delete(Integer userId){
        sysUserService.deleteById(userId);

        return R.ok("删除成功");
    }



}
