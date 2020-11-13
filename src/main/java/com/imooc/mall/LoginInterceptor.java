package com.imooc.mall;

import com.imooc.mall.consts.MallConst;
import com.imooc.mall.exception.UserLoginException;
import com.imooc.mall.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器-统一判断登录状态
 *
 * @author pengfei.zhao
 * @date 2020/11/2 20:57
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final HttpSession session = request.getSession();
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        if (user == null) {
            //response.getWriter().print("可以直接写返回的json数据");
            //return false;
            throw new UserLoginException();
        }
        return true;
    }
}
