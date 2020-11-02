package com.imooc.mall.service.impl;

import com.imooc.mall.dao.UserMapper;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.enums.RoleEnum;
import com.imooc.mall.form.UserRegisterForm;
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
    public ResponseVo<User> register(UserRegisterForm userRegisterForm) {
        int countByUsername = userMapper.countByUsername(userRegisterForm.getUsername());
        if (countByUsername > 0) {
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }

        int countByEmail = userMapper.countByEmail(userRegisterForm.getEmail());
        if (countByEmail > 0) {
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }
        String md5Pwd = DigestUtils.md5DigestAsHex(userRegisterForm.getPassword().getBytes());
        userRegisterForm.setPassword(md5Pwd);
        User user = new User();
        BeanUtils.copyProperties(userRegisterForm, user);
        user.setRole(RoleEnum.CUSTOMER.getCode());

        int result = userMapper.insertSelective(user);
        if (result == 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        return ResponseVo.success();
    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        final String md5Pwd = DigestUtils.md5DigestAsHex(password.getBytes());
        User user = userMapper.selectByUsernamePwd(username, md5Pwd);
        if (user == null) {
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        if (!md5Pwd.equals(user.getPassword())) {
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        return ResponseVo.success(user);
    }
}
