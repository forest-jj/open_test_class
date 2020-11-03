package com.example.demo.modules.sys.controller;

import com.example.demo.common.utils.DemoPageUtils;
import com.example.demo.common.utils.DemoUtils;
import com.example.demo.common.utils.PageResult;
import com.example.demo.common.utils.R;
import com.example.demo.modules.sys.domain.SysUser;
import com.example.demo.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询用户
     *
     * @param currentPage  当前页
     * @param totalPage 总页数
     * @return
     */
    @GetMapping("/list")
    public PageResult<SysUser> list(@RequestParam(value = "page", defaultValue = "1") int currentPage, @RequestParam(value = "limit", defaultValue = "10") int totalPage) {
        List<SysUser> all = sysUserService.findAll(Sort.by(Sort.Direction.DESC, "createTime"));
        List<SysUser> admin = all.stream().filter(p -> !p.getUsername().equals("Admin")).collect(Collectors.toList());
        int count = admin.size();

        return new PageResult<>(DemoPageUtils.getPageList(admin,currentPage,totalPage), count);
    }


    /**
     * 新增或修改用户
     *
     * @param user
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public R update(@RequestBody SysUser user) {
        user.setPassword(DemoUtils.encode(user.getPassword()));
        sysUserService.saveAndFlush(user);

        return R.ok("保存成功");
    }

    /**
     * 删除用户
     *
     * @return
     */
    @DeleteMapping("/delete/{userId}")
    public R delete(@PathVariable(value = "userId") Integer userId) {
        sysUserService.deleteById(userId);

        return R.ok("删除成功");
    }


}
