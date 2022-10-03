package com.example.security.controller;


import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/success")
    public String success() {
        return "index";
    }

    @RequestMapping("/loginJudge")
    public void loginJudge() {
        System.out.println("66666");
    }

    @ResponseBody
    @RequestMapping("/test01")
    public String testLogin01() {
        return "test01";
    }

    @ResponseBody
    @RequestMapping("/test02")
    public String testLogin02() {

        return "test02";
    }
    @ResponseBody
    @RequestMapping("/test03")
    public String testLogin03() {

        return "test03";
    }

    @RequestMapping("/fail")
    public String fail(){
        return "fail";
    }

    @RequestMapping("/unauth")
    public String unauth(){
        return "unauth";
    }

    @Secured({"ROLE_master"})
    @RequestMapping("/update")
    @ResponseBody
    public String update(){
        return "update";
    }

    @Secured({"ROLE_visitor"})
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(){
        return "delete";
    }
}
