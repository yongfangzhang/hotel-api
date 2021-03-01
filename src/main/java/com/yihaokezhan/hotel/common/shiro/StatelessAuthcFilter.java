package com.yihaokezhan.hotel.common.shiro;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yihaokezhan.hotel.common.utils.TokenUtils;
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
    private TokenUtils tokenUtils;

    @Autowired
    private ShiroUtils shiroUtils;

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
            Object mappedValue) throws Exception {
        return false;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        // 客户端生成的消息摘要
        HttpServletRequest req = (HttpServletRequest) request;
        String token = tokenUtils.getTokenFromReq(req);
        // 客户端传入的用户身份
        if (StringUtils.isBlank(token)) {
            // 无需校验
            return true;
        }
        String uuid = tokenUtils.getUuidByToken(token);
        if (StringUtils.isBlank(uuid)) {
            onLoginFail(response, token);
            return false;
        }
        if (!shiroUtils.login(uuid, token)) {
            // 登录失败
            onLoginFail(response, token);
            return false;
        }
        return true;
    }

    // 登录失败时默认返回401状态码
    private void onLoginFail(ServletResponse response, String token) throws IOException {
        log.error("shiro login failed: {}", token);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("Access Denied");
    }
}
