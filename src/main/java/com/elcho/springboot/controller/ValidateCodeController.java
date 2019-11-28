package com.elcho.springboot.controller;

import com.elcho.springboot.verificationcode.VerifyCode;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ValidateCodeController {
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @GetMapping("/verifycode/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.根据随机数生成图片
        VerifyCode vc = new VerifyCode(60);//设置60秒过期
        /*ImageCode imageCode = createImageCode(request);*/
        // 2.将图片存入session中
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, vc);
        // 3.将生成的图片写入到接口响应中
        ImageIO.write(vc.getImage(), "JPEG", response.getOutputStream());
    }

}