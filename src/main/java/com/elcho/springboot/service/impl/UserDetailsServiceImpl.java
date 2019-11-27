package com.elcho.springboot.service.impl;


import com.elcho.springboot.controller.UserController;
import com.elcho.springboot.dao.IUserDao;
import com.elcho.springboot.entity.CusUser;
import com.elcho.springboot.entity.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserDao userDao;

    private static final Logger log = Logger.getLogger(String.valueOf(UserDetailsServiceImpl.class));



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("-----loadUserByUsername:" + username);
        String role = "ROLE_USER";

        // 模拟一个用户，替代数据库获取逻辑
        MyUser user = new MyUser();
        user.setUserName(username);
        user.setPassword(this.passwordEncoder.encode("123456"));
        CusUser cusUser = userDao.queryByUserName(username);
        if(cusUser !=null) {
            log.info("cusUser getUserName: " + cusUser.getUserName());
            log.info("cusUser getPassword: " + cusUser.getPassword());
            log.info("cusUser:getRole: " + cusUser.getRole());
            log.info("cusUser:getEmail: " + cusUser.getEmail());
            log.info("cusUser:getName: " + cusUser.getName());

            user.setPassword(this.passwordEncoder.encode(cusUser.getPassword()));
            role = cusUser.getRole();
        }

        // 输出加密后的密码
        System.out.println(user.getPassword());

        return new User(username, user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                user.isAccountNonLocked(), AuthorityUtils.commaSeparatedStringToAuthorityList(role));

    }
}
