package com.example.demo.modules.security.config;

import com.example.demo.modules.security.UserDetailsServiceImpl;
import com.example.demo.modules.security.filter.DemoJwtAuthenticationTokenFilter;
import com.example.demo.modules.security.handle.DemoAccessDeineHandler;
import com.example.demo.modules.security.handle.DemoAuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DemoJwtAuthenticationTokenFilter demoJwtAuthenticationTokenFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                .antMatchers("/login/**", "/favicon.ico", "/js/**", "/css/**","/","/403","/404","/500",
                        "/h2-console/**","/layui/**","/treetable-lay/**","/images/**","/home","/mainSubscribe","/mainCancel").anonymous()
                .antMatchers("/**/*.css", "/**/*.js")
                .permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");


        // 添加自定义异常入口
        http
                .exceptionHandling()
                .authenticationEntryPoint(new DemoAuthenticationEntryPointImpl())
                .accessDeniedHandler(new DemoAccessDeineHandler());

        // 添加JWT filter 用户名登录
        http.addFilterBefore(demoJwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 解决 无法直接注入 AuthenticationManager
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}