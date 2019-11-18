package com.elcho.springboot.controller;

import com.elcho.springboot.dao.IUserDao;
import com.elcho.springboot.entity.User;

import com.elcho.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Logger;


@Controller
public class UserController {

    private static final Logger log = Logger.getLogger(String.valueOf(UserController.class));

    private static String str_user = "user";
    private static String SESSION_USER = "sessionUser";

    @Autowired
    private IUserService userDao;

    @RequestMapping(value = "/user")
    public String Home(Model model, HttpServletRequest request) throws Exception {
        List<User> userList = userDao.findAll();
        log.info("Index");
        log.info(userList.get(0).getUserName());

        for (User uu : userList) {
            log.info("--------  Home FindALl: -------------");
            log.info("--------  Default ID: " + uu.getId());
            log.info("--------  Default name: " + uu.getName());
            log.info("--------  Default email: " + uu.getEmail());
            log.info("--------  Default user name: " + uu.getUserName());
            log.info("--------  Default password: " + uu.getPassword());
            log.info("--------  Default role: " + uu.getRole());
        }
        request.getSession().removeAttribute(SESSION_USER);
        return "user/home";
    }


    @RequestMapping(value = "/user/admin")
    public String Admin(Model model, HttpServletRequest request) throws Exception {
        log.info("Admin");
        return "user/admin";
    }

    @RequestMapping(value = "/user/tutorials")
    public String tutorials(Model model, HttpServletRequest request) throws Exception {
        log.info("Tutorials");
        return "user/tutorials";
    }

    @RequestMapping(value = "/user/logout")
    public String logout(Model model, HttpServletRequest request) throws Exception {
        log.info("Tutorials");
        request.getSession().removeAttribute(SESSION_USER);
        return "user/home";
    }

    @RequestMapping(value = "/user/update")
    public String update(User u, Model model, HttpServletRequest request) throws Exception {
        log.info("update");
        if(u!=null && u.getUserName()!=null) {
            log.info("--------  update Name: " + u.getName());

            log.info("--------  update Email: " + u.getEmail());
            log.info("--------  update user name: " + u.getUserName());
            log.info("--------  update password: " + u.getPassword());
            log.info("--------  update role: " + u.getRole());
            User sessionUser = (User) request.getSession().getAttribute(SESSION_USER);

            userDao.update(u, sessionUser.getId());
            log.info("--------  update Done ");
            log.info("--------  Find user -------- ");
             sessionUser = userDao.findById(sessionUser.getId());
             request.getSession().setAttribute("sessionUser",sessionUser);

             model.addAttribute("info", "Update successful");
            /* return "forward:/user/logon";*/
        }
        return "user/update";
    }

    @RequestMapping(value = "/user/login" , method = {RequestMethod.POST, RequestMethod.GET})
    public String Login(User u, Model model, HttpServletRequest request, HttpSession session) throws Exception {

        log.info("--- Logon user: " + u.getUserName());
        log.info("--- request user: " + request.getParameter("userName"));
        User dbUser = null;

        if(u.getUserName()!=null) {
            dbUser = userDao.findByUserName(u.getUserName());
            if(dbUser == null) {
                log.info("user not exist");
                model.addAttribute("error", "User Not exist");
            } else {
                if (!dbUser.getPassword().equals(u.getPassword())) {
                    System.out.println("Password is not correct");
                    model.addAttribute("error", "password not correct");
                } else {
                    log.info("-----login successful------");
                    log.info("login user : " + dbUser.getUserName());
                    request.getSession().setAttribute("sessionUser", dbUser);

                    if(dbUser.getRole().equalsIgnoreCase("admin")){
                        return "user/admin";
                    }
                    model.addAttribute("loginUser", dbUser.getUserName());
                }
            }
        }


        // model.addAttribute(str_user, dbUser);
        return "user/login";
    }

    @RequestMapping(value = "/user/regist")
    public String Regist(User u, Model model, HttpServletRequest request) throws Exception {

        if(u!=null && u.getUserName()!=null) {
            log.info("Regist UserName: " + u.getUserName());
            User dbUser = userDao.findByUserName(u.getUserName());

            if(dbUser!=null) {
                model.addAttribute("error", "User Already exist");
                return "user/regist";
            }
            log.info("--------  Regist name: " + u.getName());
            log.info("--------  Regist Email: " + u.getEmail());
            log.info("--------  Regist user name: " + u.getUserName());
            log.info("--------  Regist password: " + u.getPassword());
            log.info("--------  Regist role: " + u.getRole());
            userDao.save(u);

            return "user/login";
           /* return "forward:/user/logon";*/
        }


        return "user/regist";
    }


}  