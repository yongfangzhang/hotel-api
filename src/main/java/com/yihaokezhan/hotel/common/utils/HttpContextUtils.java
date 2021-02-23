package com.yihaokezhan.hotel.common.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Slf4j
public class HttpContextUtils {

    public static HttpServletRequest getHttpServletRequest() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return null;
        }
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return null;
        }
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
    }

    public static boolean isAjax(HttpServletRequest request) {
        return StringUtils.equals("XMLHttpRequest", request.getHeader("X-Requested-With"));
    }

    public static boolean isAjax() {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return false;
        }
        return StringUtils.equalsIgnoreCase("XMLHttpRequest", request.getHeader("X-Requested-With"))
                || StringUtils.equalsIgnoreCase("application/json", request.getHeader("accept"));
    }

    public static String getHeader(String name) {
        try {
            HttpServletRequest request = getHttpServletRequest();
            if (request == null) {
                return null;
            }
            return request.getHeader(name);
        } catch (Exception e) {
            log.error("获取request header失败", e);
            return null;
        }
    }

    public static Map<String, String> getHeaders(HttpServletRequest request) {
        request = request == null ? getHttpServletRequest() : request;
        Map<String, String> result = new HashMap<>(32);
        if (request == null) {
            return result;
        }
        Enumeration<String> headNames = request.getHeaderNames();
        while (headNames.hasMoreElements()) {
            String headName = headNames.nextElement();
            result.put(headName, request.getHeader(headName));
        }
        return result;
    }

    public static MediaType getMediaType(HttpServletRequest request, Resource r, String fileName) {
        MediaType mediaType = null;
        try {
            String mediaTypeStr = request.getServletContext().getMimeType(fileName);
            if (StringUtils.isBlank(mediaTypeStr)) {
                mediaTypeStr =
                        request.getServletContext().getMimeType(r.getFile().getAbsolutePath());
            }
            if (StringUtils.isNotBlank(mediaTypeStr)) {
                mediaType = MediaType.parseMediaType(mediaTypeStr);
            }
        } catch (IOException ex) {
            log.error("Could not determine file type.");
        }

        if (mediaType == null) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }
        return mediaType;
    }
}
