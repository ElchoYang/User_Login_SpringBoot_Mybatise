package com.elcho.springboot.verificationcode;


import com.elcho.springboot.config.SecurityProperties;
import com.elcho.springboot.controller.ValidateCodeController;
import com.elcho.springboot.handler.MyAuthenctiationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    private MyAuthenctiationFailureHandler myAuthenticationFailHander;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals("/user/login", request.getRequestURI())
                &&StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
            try {
                System.out.println("验证码");
                validate(new ServletWebRequest(request));
            }
            catch (ValidateCodeException e) {
                System.out.println("验证码");
                myAuthenticationFailHander.onAuthenticationFailure(request, response, e);
                // 不继续执行
                return;
            }
        }
        // 继续执行下一步
        filterChain.doFilter(request, response);
    }
    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        System.out.println("验证码");
        // 从Session中获取imageCode对象
        VerifyCode verifyCode = (VerifyCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
        System.out.println("从session获取在imagecode对象"+verifyCode+"    "+codeInRequest);
        if (StringUtils.isEmpty(codeInRequest)) {
            System.out.println("纳尼？？？为空");
            throw new ValidateCodeException("验证码为空或者不存在");
        }
        if(verifyCode == null){
            System.out.println("纳尼？？？不存在");
            throw new ValidateCodeException("验证码不存在，请刷新验证码");
        }
        if (verifyCode.isExpired()) {
            //从session移除过期的验证码
            System.out.println("纳尼？？？过期");
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码过期");
        }
        if (!StringUtils.equalsIgnoreCase(verifyCode.getText(), codeInRequest)) {
            System.out.println("纳尼？？？不匹配");
            throw new ValidateCodeException("验证码不匹配");
        }
        // session 中移除key
        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
    }

    public MyAuthenctiationFailureHandler getMyAuthenticationFailHander() {
        return myAuthenticationFailHander;
    }

    public void setMyAuthenticationFailHander(MyAuthenctiationFailureHandler myAuthenticationFailHander) {
        this.myAuthenticationFailHander = myAuthenticationFailHander;
    }


}
