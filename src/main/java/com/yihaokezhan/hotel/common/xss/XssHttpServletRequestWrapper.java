package com.yihaokezhan.hotel.common.xss;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;


/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    /**
     * 没被包装过的HttpServletRequest（特殊场景，需要自己过滤）
     */
    HttpServletRequest orgRequest;
    /**
     * html过滤
     */
    private final static HTMLFilter HTML_FILTER = new HTMLFilter();
    /**
     * application/json的body
     */
    private byte[] body = null;
    private String bodyStr = null;

    public XssHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        orgRequest = request;
        // JSON 读取body
        if (StringUtils.startsWithIgnoreCase(super.getHeader(HttpHeaders.CONTENT_TYPE),
                MediaType.APPLICATION_JSON_VALUE)) {
            this.setBody(request);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 非json类型，直接返回
        if (StringUtils.startsWithIgnoreCase(super.getHeader(HttpHeaders.CONTENT_TYPE),
                MediaType.APPLICATION_JSON_VALUE)) {
            final ByteArrayInputStream bis = new ByteArrayInputStream(body);
            return this.createInputStream(bis);
        } else {
            return super.getInputStream();
        }
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(this.xssEncode(name, false));
        if (StringUtils.isNotBlank(value)) {
            value = this.xssEncode(value, true);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameters = super.getParameterValues(name);
        if (parameters == null || parameters.length == 0) {
            return null;
        }

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = this.xssEncode(parameters[i], true);
        }
        return parameters;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = this.xssEncode(values[i], true);
            }
            map.put(key, values);
        }
        return map;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(this.xssEncode(name, false));
        if (StringUtils.isNotBlank(value)) {
            value = this.xssEncode(value, false);
        }
        return value;
    }

    private String xssEncode(String input, boolean filterWd) {
        String value = HTML_FILTER.filter(input);
        return value;
    }

    /**
     * 获取最原始的request
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
        if (request instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) request).getOrgRequest();
        }

        return request;
    }

    private ServletInputStream createInputStream(ByteArrayInputStream stream) {
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() throws IOException {
                return stream.read();
            }
        };
    }

    private void setBody(HttpServletRequest request) throws IOException {

        byte[] bytes = StreamUtils.copyToByteArray(request.getInputStream());
        String content = new String(bytes);
        // xss
        content = xssEncode(content, true);
        this.bodyStr = content;
        this.body = content.getBytes();
    }

    public byte[] getBody() {
        return body;
    }

    public String getBodyStr() {
        return bodyStr;
    }
}
