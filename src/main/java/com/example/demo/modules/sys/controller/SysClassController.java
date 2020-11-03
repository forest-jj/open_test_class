package com.example.demo.modules.sys.controller;

import com.example.demo.common.utils.DemoPageUtils;
import com.example.demo.common.utils.PageResult;
import com.example.demo.common.utils.R;
import com.example.demo.modules.sys.domain.SysClass;
import com.example.demo.modules.sys.service.IService;
import com.example.demo.modules.sys.service.SysClassService;
import com.example.demo.modules.sys.vo.SysClassVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sysClass")
@RestController
public class SysClassController {

    @Autowired
    private SysClassService sysClassService;

    @Autowired
    private IService iService;

    /**
     * 查询课程
     *
     * @param currentPage 当前页
     * @param totalPage   总页数
     * @return
     */
    @GetMapping("/list")
    public PageResult<SysClass> list(@RequestParam(value = "page", defaultValue = "1") int currentPage, @RequestParam(value = "limit", defaultValue = "10") int totalPage, SysClassVo sysClassVo) {
        List<SysClass> sysClasses = (List<SysClass>) iService.findByCondition(sysClassService, sysClassVo);

        return new PageResult<SysClass>(DemoPageUtils.getPageList(sysClasses, currentPage, totalPage), sysClasses.size());
    }


    @PostMapping("/saveOrUpdate")
    public R update(@RequestBody SysClass sysClass) {
        sysClassService.saveAndFlush(sysClass);

        return R.ok("保存成功");
    }

    /**
     * 删除用户
     *
     * @return
     */
    @DeleteMapping("/delete/{classesId}")
    public R delete(@PathVariable("classesId") Integer classesId) {
        sysClassService.deleteById(classesId);

        return R.ok("删除成功");
    }


}
