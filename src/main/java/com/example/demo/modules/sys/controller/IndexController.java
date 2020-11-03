package com.example.demo.modules.sys.controller;


import cn.hutool.core.util.ObjectUtil;
import com.example.demo.common.utils.R;
import com.example.demo.modules.security.util.JwtUtil;
import com.example.demo.modules.sys.domain.SysUser;
import com.example.demo.modules.sys.service.SysClassService;
import com.example.demo.modules.sys.service.SysUserService;
import com.example.demo.modules.sys.vo.LoginVO;
import com.example.demo.security.DemoSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 首页
 */

@Controller
public class IndexController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysClassService sysClassService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/")
    public String index() {
        return "index";
    }



    @PostMapping("/login")
    @ResponseBody
    public R login(@RequestBody LoginVO loginVO) {
        DemoSecurityUser demoSecurityUser = getDemoSecurityUser(loginVO.getUsername(),loginVO.getPassword());
        return R.ok(jwtUtil.generateToken(demoSecurityUser));
    }


    /**
     * 获取用户
     *
     * @param username
     * @param password
     * @return
     */
    private DemoSecurityUser getDemoSecurityUser(String username, String password) {
        //用户验证
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        DemoSecurityUser userDetail = (DemoSecurityUser) authentication.getPrincipal();

        return userDetail;
    }


    @GetMapping("/403")
    public String error403(){
        return "error/403";
    }

    @GetMapping("/404")
    public String error404(){
        return "error/404";
    }

    @GetMapping("/500")
    public String error405(){
        return "error/500";
    }

    /**
     * 根据id判断用户跳转页面
     * @param username
     * @return
     */
    @GetMapping("/home")
    public String home(String username){
        if (StringUtils.isEmpty(username)) {
            return "index";
        }

        SysUser sysUser = sysUserService.findSecurityUserByUser(username);
        if (ObjectUtil.isNotNull(sysUser)) {
            switch (sysUser.getRole()){
                case "0":
                    return "admin/list";
                case "1":
                    return "teacher/list";
                case "2":
                    return "student/main";
            }
        }

        return "index";
    }


    @GetMapping("/mainSubscribe")
    public String mainSubscribe(){
        return "student/list";
    }

    @GetMapping("/mainCancel")
    public String mainCancel(){
        return "student/list2";
    }


}
