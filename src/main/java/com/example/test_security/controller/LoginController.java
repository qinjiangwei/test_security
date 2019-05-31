package com.example.test_security.controller;

import com.example.test_security.pojo.R;
import com.example.test_security.pojo.User;
import com.example.test_security.util.ConstantVal;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    DefaultKaptcha defaultKaptcha;

    @RequestMapping("userLogin")
    public String userLoging(HttpServletRequest request){
        User userinfo = new User();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String kaptcha = request.getParameter("kaptcha");

        userinfo.setUsername(username);
        userinfo.setPassword(password);
        String s = request.getSession().getAttribute(ConstantVal.CHECK_CODE).toString();

        if(StringUtils.isEmpty(kaptcha) && !s.equals(kaptcha)){
            return "redirect:login-error?error=1";
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        try {
            //使用SpringSecurity拦截登录请求，进行认证和授权
            Authentication authenticate = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authenticate);

            //使用 redis session共享
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());//不放入，验证过后无法登录
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "redirect:login-error?error=2";
        }

        return "redirect:index";
    }

    @ResponseBody
    @RequestMapping("captcha.jpg")
    public R applyCheckCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        R r = new R();
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = defaultKaptcha.createText();
        //生成图片验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        request.getSession().setAttribute(ConstantVal.CHECK_CODE, text);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        return r;

    }









}
