package com.yihaokezhan.hotel.common.shiro;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Configuration
@ConditionalOnProperty(value = "shiro.enabled", matchIfMissing = true)
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        // shiroFilter.setUnauthorizedUrl("/");
        shiroFilter.getFilters().put("statelessAuthc", statelessAuthcFilter());
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/**", "statelessAuthc");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    // subject工厂管理器
    @Bean
    public DefaultWebSubjectFactory subjectFactory() {
        return new StatelessDefaultSubjectFactory();
    }

    // 会话管理器
    @Bean
    public DefaultSessionManager sessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

    // 访问控制器
    @Bean
    public StatelessAuthcFilter statelessAuthcFilter() {
        return new StatelessAuthcFilter();
    }

    // 开启shiro aop注解支持.
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    // 自动代理所有的advisor: 由Advisor决定对哪些类的方法进行AOP代理
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    // 安全管理器
    @Bean
    public DefaultWebSecurityManager securityManager(DefaultSubjectFactory subjectFactory,
            DefaultSessionManager sessionManager, StatelessRealm statelessRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(statelessRealm);
        ((DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) securityManager.getSubjectDAO())
                .getSessionStorageEvaluator()).setSessionStorageEnabled(false);
        return securityManager;
    }

    // 自己定义的realm
    @Bean
    public StatelessRealm statelessRealm() {
        StatelessRealm userRealm = new StatelessRealm();
        userRealm.setCachingEnabled(false);
        return userRealm;
    }
}
