package com.elcho.springboot.config;

import com.elcho.springboot.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        //推荐使用该密码加密类
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        System.out.print("AuthenticationProvider");
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //是否隐藏没有找到用户的异常
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        //设置userService实例
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        //设置密码加密类
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return  daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        //注册daoAuthenticationProvider
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //super.configure(web);
        System.out.println("开始配置静态资源...............5");
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        System.out.println("开始配置权限...............4");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .sessionFixation().none();
        http.authorizeRequests()
                .antMatchers("/user/login","/user/regist","/user").permitAll()
                .antMatchers("/","/home").hasAnyRole("USER","ADMIN","DBA")
                .antMatchers("/admin/**").hasAnyRole("ADMIN","DBA")
                .antMatchers("/dba/**").hasAnyRole("DBA")
                .anyRequest().authenticated()
                .and().csrf().disable()
                .formLogin()
                .loginPage("/user/login")
                .successHandler(authenticationSuccessHandler)
                .failureForwardUrl("/myError")
                .usernameParameter("userName").passwordParameter("password")
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");
    }
}
