package com.imooc.mall.service.impl;

import com.imooc.mall.dao.UserMapper;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.enums.RoleEnum;
import com.imooc.mall.form.UserForm;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IUserService;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

/**
 * @author pengfei.zhao
 * @date 2020/11/1 17:01
 */
@Service
public class UserService implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public ResponseVo register(UserForm userForm) {
        int countByUsername = userMapper.countByUsername(userForm.getUsername());
        if (countByUsername > 0) {
            return ResponseVo.error(ResponseEnum.USER_EXIST);
        }

        int countByEmail = userMapper.countByEmail(userForm.getEmail());
        if (countByEmail > 0) {
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }
        String md5Pwd = DigestUtils.md5DigestAsHex(userForm.getPassword().getBytes());
        userForm.setPassword(md5Pwd);
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        user.setRole(RoleEnum.CUSTOMER.getCode());

        int result = userMapper.insertSelective(user);
        if (result == 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        return ResponseVo.success();
    }
}
