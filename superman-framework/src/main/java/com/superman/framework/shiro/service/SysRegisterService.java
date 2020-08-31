package com.superman.framework.shiro.service;

import com.superman.common.constant.CommonConstant;
import com.superman.common.constant.ShiroConstants;
import com.superman.common.constant.UserConstant;
import com.superman.common.utils.MessageUtils;
import com.superman.common.utils.ServletUtils;
import com.superman.framework.manager.AsyncManager;
import com.superman.framework.manager.factory.AsyncFactory;
import com.superman.framework.util.ShiroUtils;
import com.superman.system.domain.SysUser;
import com.superman.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * 注册校验方法
 * 
 * @author ruoyi
 */
@Component
public class SysRegisterService
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPasswordService passwordService;

    /**
     * 注册
     */
    public String register(SysUser user)
    {
        String msg = "", username = user.getLoginName(), password = user.getPassword();

        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA)))
        {
            msg = "验证码错误";
        }
        else if (StringUtils.isEmpty(username))
        {
            msg = "用户名不能为空";
        }
        else if (StringUtils.isEmpty(password))
        {
            msg = "用户密码不能为空";
        }
        else if (password.length() < UserConstant.PASSWORD_MIN_LENGTH
                || password.length() > UserConstant.PASSWORD_MAX_LENGTH)
        {
            msg = "密码长度必须在5到20个字符之间";
        }
        else if (username.length() < UserConstant.USERNAME_MIN_LENGTH
                || username.length() > UserConstant.USERNAME_MAX_LENGTH)
        {
            msg = "账户长度必须在2到20个字符之间";
        }
        else if (UserConstant.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(username)))
        {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        }
        else
        {
            user.setSalt(ShiroUtils.randomSalt());
            user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
            boolean regFlag = userService.registerUser(user);
            if (!regFlag)
            {
                msg = "注册失败,请联系系统管理人员";
            }
            else
            {
                AsyncManager.me().execute(
                    AsyncFactory.recordLogininfor(username, CommonConstant.REGISTER, MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }
}
