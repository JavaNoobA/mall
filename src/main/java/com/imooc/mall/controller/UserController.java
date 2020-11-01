package com.imooc.mall.controller;

import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.UserForm;
import com.imooc.mall.service.impl.UserService;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private UserService userService;

    @PostMapping("/register")
    public ResponseVo register(@Valid @RequestBody UserForm userForm,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("表单检验有误, {} - {}",
                    bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR,
                    bindingResult.getFieldError().getField() + " " + bindingResult.getFieldError().getDefaultMessage());

        }
        return userService.register(userForm);
    }
}
