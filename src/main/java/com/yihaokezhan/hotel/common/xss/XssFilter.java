package com.yihaokezhan.hotel.common.xss;

import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;


/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class XssFilter implements Filter {

    private List<String> xssExcludes;

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        if (this.xssExcludes != null) {
            String uri = httpReq.getRequestURI();
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            for (String exclude : this.xssExcludes) {
                if (antPathMatcher.match(exclude, uri)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(httpReq);
        chain.doFilter(xssRequest, response);
    }

    @Override
    public void destroy() {
    }

    public XssFilter(List<String> excludes) {
        this.xssExcludes = excludes;
    }

    public XssFilter() {
    }

}
