package com.imooc.mall.controller;

import com.imooc.mall.consts.MallConst;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.UserLoginForm;
import com.imooc.mall.form.UserRegisterForm;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.impl.UserServiceImpl;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author pengfei.zhao
 * @date 2020/11/1 21:01
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public ResponseVo<User> user(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        user.setPassword("");
        return ResponseVo.success(user);
    }

    @PostMapping("/register")
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userRegisterForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("表单检验有误, {} - {}",
                    bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR,
                    bindingResult.getFieldError().getField() + " " + bindingResult.getFieldError().getDefaultMessage());

        }
        return userService.register(userRegisterForm);
    }

    @PostMapping("/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm loginForm,
                                  BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return ResponseVo.error(ResponseEnum.PARAM_ERROR);

        }
        final ResponseVo<User> userResponse = userService.login(loginForm.getUsername(), loginForm.getPassword());
        session.setAttribute(MallConst.CURRENT_USER, userResponse.getData());

        return userResponse;
    }

    @GetMapping("/logout")
    public ResponseVo logout(HttpSession session) {
        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success("退出成功");
    }
}
