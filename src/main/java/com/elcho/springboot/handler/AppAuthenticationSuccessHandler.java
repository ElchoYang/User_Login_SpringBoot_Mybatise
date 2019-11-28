package com.elcho.springboot.handler;

import com.elcho.springboot.config.SecurityProperties;
import com.elcho.springboot.entity.LoginType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/***
 * 验证成功跳转处理类.在验证成功后filter调用执行.
 */
@Component
public class AppAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties myProperties;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {
        System.out.println("开始根据权限判断将要跳转的页面................");

        System.out.println("request..... " + request.getParameter("userName"));

        String url=determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(request,response,url);
    }
    protected String determineTargetUrl(Authentication authentication){
        System.out.print("determineTargetUrl");
        String url="";
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        List<String> roles=new ArrayList<>();
        for(GrantedAuthority grantedAuthority:grantedAuthorities){
            roles.add(grantedAuthority.getAuthority());
        }

        System.out.println("-----" + roles + "--------");
        //这里返回路径重定向,同样返回的是controller请求路径,并不是指静态文件的路径
        if(roles.contains("ROLE_ADMIN")){
            return  "/user/admin";
        }else if(roles.contains("ROLE_DBA")){
            return  "/user/dba";
        }else if (roles.contains("ROLE_USER")){
            return "/user/login";
        }else {
            return  "/accessDenied";
        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //super.onAuthenticationSuccess(request, response, authentication);
        logger.info("MyAuthenticationSuccessHandler login success!");
        if (!LoginType.JSON.equals(myProperties.getLoginType())) {
            logger.info("response.getWriter()");
            response.setContentType("application/json;charset=UTF-8");
            // 把authentication对象转成 json 格式 字符串 通过 response 以application/json;charset=UTF-8 格式写到响应里面去
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            // 父类的方法 就是 跳转
            logger.info("go to super onAuthenticationSuccess");
            //super.onAuthenticationSuccess(request, response, authentication);
            this.handle(request, response, authentication);
        }

    }
}
