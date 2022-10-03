package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    @Bean
    public PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*
        * hasAuthority拥有某一个权限
        * hasAnyAuthority拥有其中的一个权限
        * hasRole拥有某一个角色
        * hasAnyRole拥有某任一个角色*/

        //配置403页面
        http.exceptionHandling().accessDeniedPage("/unauth");

        //用户注销
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/index").permitAll();

        // 配置认证
        http.formLogin()
                .loginPage("/login")// 配置哪个 url 为登录页面
                .loginProcessingUrl("/loginJudge") // 登录访问路径，也就是提交的路径
                .successForwardUrl("/success") // 登录成功之后跳转到哪个 url
                .failureForwardUrl("/fail");// 登录失败之后跳转到哪个 url

        http.authorizeRequests()
                .antMatchers("/login/**","/static/**").permitAll()//哪些路径可以直接访问，不需要鉴权
                .antMatchers("/test01").hasAuthority("admin")//哪些路径需要权限，在MyUserDetailsServiceImpl中登录时配置权限。
                .antMatchers("/test02").hasAnyAuthority("admin","role")//哪些路径需要权限中的一种
                .antMatchers("/test03").hasAnyRole("master","employee")  //拥有角色的一种就可以访问
                .anyRequest() // 其他请求
                .authenticated(); //需要认证
        // 关闭 csrf
        http.csrf().disable();

    }


}
