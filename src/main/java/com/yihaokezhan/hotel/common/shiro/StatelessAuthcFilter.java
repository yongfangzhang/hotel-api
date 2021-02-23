package com.yihaokezhan.hotel.common.shiro;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yihaokezhan.hotel.common.wx.WXService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Slf4j
public class StatelessAuthcFilter extends AccessControlFilter {

    @Autowired
    private ShiroUtils shiroUtils;

    @Autowired
    private WXService wxService;

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
            Object mappedValue) throws Exception {
        return false;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        // 客户端生成的消息摘要
        HttpServletRequest req = (HttpServletRequest) request;
        String openId = wxService.getOpenIdFromRequest(req);
        // 客户端传入的用户身份
        if (StringUtils.isBlank(openId)) {
            // 无需校验
            return true;
        }
        if (StringUtils.isBlank(openId)) {
            onLoginFail(response, openId);
            return false;
        }
        if (!shiroUtils.login(openId)) {
            // 登录失败
            onLoginFail(response, openId);
            return false;
        }
        return true;
    }

    // 登录失败时默认返回401状态码
    private void onLoginFail(ServletResponse response, String openId) throws IOException {
        log.error("shiro login failed: {}", openId);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("login error");
    }
}
