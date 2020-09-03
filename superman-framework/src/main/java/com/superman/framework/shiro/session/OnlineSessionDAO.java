package com.superman.framework.shiro.session;

import com.superman.enums.OnlineStatus;
import com.superman.framework.manager.AsyncManager;
import com.superman.framework.manager.factory.AsyncFactory;
import com.superman.framework.shiro.service.SysShiroService;
import java.io.Serializable;
import java.util.Date;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: superman宁
 * @Description: 针对自定义的ShiroSession的db操作
 */
public class OnlineSessionDAO extends EnterpriseCacheSessionDAO {

  /**
   * 同步session到数据库的周期 单位为毫秒（默认1分钟）
   */
  @Value("${shiro.session.dbSyncPeriod}")
  private int dbSyncPeriod;

  /**
   * 上次同步数据库的时间戳
   */
  private static final String LAST_SYNC_DB_TIMESTAMP = OnlineSessionDAO.class.getName() + "LAST_SYNC_DB_TIMESTAMP";

  @Autowired
  private SysShiroService sysShiroService;

  public OnlineSessionDAO()
  {
    super();
  }

//  public OnlineSessionDAO(long expireTime)
//  {
//    super();
//  }


  /**
   * 根据会话ID获取会话
   * @param sessionId
   * @return
   */
  @Override
  protected Session doReadSession(Serializable sessionId) {
    return sysShiroService.getSession(sessionId);
  }

  @Override
  public void update(Session session) throws UnknownSessionException {
    super.update(session);
  }

  /**
   * 当会话过期/停止属性会调用
   * @param session
   */
  @Override
  protected void doDelete(Session session) {
    OnlineSession onlineSession = (OnlineSession)session;
    if(onlineSession == null){
      return;
    }
    onlineSession.setStatus(OnlineStatus.off_line);
    sysShiroService.deleteSession(onlineSession);
  }

  /**
   * 更新会话：如更新会话最后访问时间/停止会话/设置超时时间/设置移除属性等会调用
   * @param onlineSession
   */
  public void syncToDb(OnlineSession onlineSession){
    Date lastSyncTimestamp = (Date) onlineSession.getAttribute(LAST_SYNC_DB_TIMESTAMP);
    if (lastSyncTimestamp != null)
    {
      boolean needSync = true;
      long deltaTime = onlineSession.getLastAccessTime().getTime();
      if(deltaTime < dbSyncPeriod* 60 * 1000){
        // 时间差不足，无需同步
        needSync = false;
      }
      // isGuest = true 访客
      boolean isGuest = onlineSession.getUserId() == null || onlineSession.getUserId() == "0";
      // session 数据变更了 同步
      if(isGuest && onlineSession.isAttributeChanged()){
        needSync = true;
      }

      if (!needSync)
      {
        return;
      }
    }

    // 更新上次同步数据库时间
    onlineSession.setAttribute(LAST_SYNC_DB_TIMESTAMP, onlineSession.getLastAccessTime());
    // 更新完后 重置标识
    if (onlineSession.isAttributeChanged())
    {
      onlineSession.resetAttributeChanged();
    }
    AsyncManager.me().execute(AsyncFactory.syncSessionToDb(onlineSession));
  }
}

