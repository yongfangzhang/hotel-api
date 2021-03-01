package com.yihaokezhan.hotel.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yihaokezhan.hotel.common.exception.RRException;
import com.yihaokezhan.hotel.common.handler.DynamicTenantHandler;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.validator.ValidatorUtils;
import com.yihaokezhan.hotel.module.entity.Tenant;
import com.yihaokezhan.hotel.module.service.ITenantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Order(0)
public class TenantInterceptor implements HandlerInterceptor {

    @Autowired
    private ITenantService tenantService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (this.skip(httpServletRequest)) {
            log.debug("skip datasource method {} set {}", httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI());
            return true;
        }
        String tenantUuid = httpServletRequest.getHeader(Constant.TENANT_SPLIT_HEADER);
        log.info("tenant from header {}", tenantUuid);
        if (StringUtils.isBlank(tenantUuid)) {
            log.error("request uri {}", httpServletRequest.getRequestURI());
            throw new RRException("需要Header " + Constant.TENANT_SPLIT_HEADER);
        }
        Tenant tenant = tenantService.mGet(tenantUuid);
        ValidatorUtils.validateTenant(tenant);
        DynamicTenantHandler.setTenant(tenantUuid);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView)
            throws Exception {
        DynamicTenantHandler.clearTenant();
    }

    private boolean skip(HttpServletRequest httpServletRequest) {
        return HttpMethod.OPTIONS.matches(httpServletRequest.getMethod());
    }
}
