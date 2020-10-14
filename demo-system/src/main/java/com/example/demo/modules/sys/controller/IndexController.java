package com.example.demo.modules.sys.controller;


import com.example.common.utils.DemoPageUtils;
import com.example.demo.modules.security.util.JwtUtil;
import com.example.demo.modules.sys.service.SysClassService;
import com.example.demo.modules.sys.service.SysUserService;
import com.example.security.DemoSecurityUser;
import com.example.security.LoginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sun.dc.pr.PRError;


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

    @GetMapping("/login")
    public String index() {
        return "index";
    }


    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        DemoSecurityUser demoSecurityUser = getDemoSecurityUser(username, password);
        String token = JwtUtil.generateToken(demoSecurityUser);
        model.addAttribute("token", token);

        if (demoSecurityUser.getLoginType().equals(LoginType.ADMIN)) {
            model.addAttribute("users",sysUserService.findAll(DemoPageUtils.getPage()));

                return "admin";
        }else if (demoSecurityUser.getLoginType().equals(LoginType.TEACHER)){
            model.addAttribute("sysClasses",sysClassService.findAll(DemoPageUtils.getPage()));

                return "teacher";
        }else if (demoSecurityUser.getLoginType().equals(LoginType.STUDENT)){
            model.addAttribute("sysClasses",sysClassService.findAll(DemoPageUtils.getPage()));
            model.addAttribute("userId",  demoSecurityUser.getUserId());

            return "student";
        }

        return "index";
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

}
