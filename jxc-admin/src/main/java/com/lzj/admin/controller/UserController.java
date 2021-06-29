package com.lzj.admin.controller;


import com.lzj.admin.exceptions.ParamsException;
import com.lzj.admin.model.RespBean;
import com.lzj.admin.pojo.User;
import com.lzj.admin.service.IUserService;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.springframework.security.access.method.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 老李
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @Resource
    private IUserService userService;






    /**
     * 用户信息设置页面转到基本信息页面
     * springmvc 中的model 负责在控制器和展现数据的视图之间传数据
     * @return
     */
    @RequestMapping("setting")
    public String setting(Principal principal, Model model){
        User user = userService.findUserByUserName(principal.getName());
        model.addAttribute("user",user);
        return "user/setting";
    }


    /**
     * 用户信息更新
     * @param user
     * @return
     */
    @RequestMapping("updateUserInfo")
    @ResponseBody
    public RespBean updateUserInfo(User user){
        try {
            userService.updateUserInfo(user);
            return RespBean.success("用户信息更新成功");
        } catch (ParamsException e) {
            e.printStackTrace();
            return RespBean.error(e.getMsg());
        }catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("用户信息更新失败!");
        }
    }


    /**
     * 用户密码更新页
     * @return
     */
    @RequestMapping("password")
    public String password(){
        return "user/password";
    }


    /**
     * 用户密码更新  session获取通过security中的principal获取用户名称
     * @param principal
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @return
     */
    @RequestMapping("updateUserPassword")
    @ResponseBody
    public RespBean updateUserPassword(Principal principal, String oldPassword, String newPassword, String confirmPassword){

            userService.updateUserPassword(principal.getName(),oldPassword,newPassword,confirmPassword);
            return RespBean.success("用户密码更新成功");

    }

    /**
     * 用户的管理主页面

     * @return
     */
    @RequestMapping("index")
    public String index(){

        return "user/user";
    }

}
