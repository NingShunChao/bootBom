package com.superman.framework.shiro.realm;

import com.superman.exception.user.CaptchaException;
import com.superman.exception.user.RoleBlockedException;
import com.superman.exception.user.UserBlockedException;
import com.superman.exception.user.UserNotExistsException;
import com.superman.exception.user.UserPasswordNotMatchException;
import com.superman.exception.user.UserPasswordRetryLimitExceedException;
import com.superman.framework.shiro.service.SysLoginService;
import com.superman.framework.util.ShiroUtils;
import com.superman.system.domain.SysUser;
import com.superman.system.service.ISysMenuService;
import com.superman.system.service.ISysRoleService;
import java.util.HashSet;
import java.util.Set;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: superman宁
 * @Description: 自定义Realm 处理登录 权限
 */
public class UserRealm extends AuthorizingRealm {

  private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

  @Autowired
  private ISysMenuService menuService;

  @Autowired
  private ISysRoleService roleService;

  @Autowired
  private SysLoginService loginService;

  /**
   * 授权
   * @param principalCollection
   * @return
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    SysUser user = ShiroUtils.getSysUser();
    // 角色列表
    Set<String> roles = new HashSet<String>();
    // 功能列表
    Set<String> menus = new HashSet<String>();
    // 管理员拥有所有权限
    if(user.isAdmin()){
      info.addRole("admin");
      info.addStringPermission("*:*:*");
    }else {
      roles = roleService.selectRoleKeys(user.getUserId());
      menus = menuService.selectPermsByUserId(user.getUserId());
      // 角色加入AuthorizationInfo认证对象
      info.setRoles(roles);
      // 权限加入AuthorizationInfo认证对象
      info.setStringPermissions(menus);
    }


    return info;
  }

  /**
   * 登录认证
   * @param authenticationToken
   * @return
   * @throws AuthenticationException
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    UsernamePasswordToken upToken = (UsernamePasswordToken)authenticationToken;
    String username = upToken.getUsername();
    String password = "";
    if(upToken.getPassword() != null){
      password = new String(upToken.getPassword());
    }
    SysUser user = null;
    try
    {
      user = loginService.login(username, password);
    }
    catch (CaptchaException e)
    {
      throw new AuthenticationException(e.getMessage(), e);
    }
    catch (UserNotExistsException e)
    {
      throw new UnknownAccountException(e.getMessage(), e);
    }
    catch (UserPasswordNotMatchException e)
    {
      throw new IncorrectCredentialsException(e.getMessage(), e);
    }
    catch (UserPasswordRetryLimitExceedException e)
    {
      throw new ExcessiveAttemptsException(e.getMessage(), e);
    }
    catch (UserBlockedException e)
    {
      throw new LockedAccountException(e.getMessage(), e);
    }
    catch (RoleBlockedException e)
    {
      throw new LockedAccountException(e.getMessage(), e);
    }
    catch (Exception e)
    {
      log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
      throw new AuthenticationException(e.getMessage(), e);
    }
    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
    return info;
  }

  /**
   * 清理缓存权限
   */
  public void clearCachedAuthorizationInfo()
  {
    this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
  }

}
