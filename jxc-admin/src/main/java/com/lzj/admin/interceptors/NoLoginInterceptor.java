package com.lzj.admin.interceptors;

import com.lzj.admin.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 乐字节  踏实教育 用心服务
 *
 * 拦截器
 * @author 乐字节--老李
 * @version 1.0
 */
//SpringWebMVC的处理器拦截器     类似于Servlet开发中的过滤器Filter，用于处理器进行预处理和后处理。
public class NoLoginInterceptor  implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user= (User) request.getSession().getAttribute("user");

        if(null == user){
            /**
             * 用户未登录 或者 session 过期
             */
            response.sendRedirect("index");
            return false;
        }
        return true;
    }
}
