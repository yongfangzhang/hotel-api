package com.yihaokezhan.hotel.common.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.HttpContextUtils;
import com.yihaokezhan.hotel.common.utils.IPUtils;
import com.yihaokezhan.hotel.common.utils.JSONUtils;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.xss.XssHttpServletRequestWrapper;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@RestControllerAdvice("com.yihaokezhan")
public class RRExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(RRException.class)
    public R<String> handleRRException(RRException e) {
        R<String> r = new R<String>();
        r.setCode(e.getCode());
        r.setMsg(e.getMsg());
        this.printRequestInfo(e);
        return r;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R<String> handleDuplicateKeyException(DuplicateKeyException e) {
        this.printRequestInfo(e);
        return R.error(ErrorCode.RECORD_EXSITED);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R<String> handleParamException(Exception e) {
        this.printRequestInfo(e);
        return R.error(ErrorCode.REQ_INVALID_PARAMS);
    }

    @ExceptionHandler(Exception.class)
    public R<String> handleException(Exception e) {
        this.printRequestInfo(e);
        return R.error("捕获到异常, 请联系管理员");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<String> handleNotSupportedException(Exception e) {
        this.printRequestInfo(e);
        return R.error(ErrorCode.REQ_NOT_SUPPORTED);
    }

    @ExceptionHandler(AuthorizationException.class)
    public R<String> handleAuthorizationException(AuthorizationException e) {
        this.printRequestInfo(e);
        return R.error(ErrorCode.ACCESS_DENIED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public R<String> handleUnauthorizedException(UnauthorizedException e) {
        this.printRequestInfo(e);
        return R.error(ErrorCode.AUTH_FORBIDDEN);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R<String> handleMaxFileSizeException(MaxUploadSizeExceededException e) {
        this.printRequestInfo(e);
        return R.error("文件大小超过上限");
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> constraintViolationException(SQLIntegrityConstraintViolationException e) {
        this.printRequestInfo(e);
        return R.error("部分绑定记录未找到/记录已被使用/存在字段没有默认值, 操作被禁止");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public R<String> dataIntegrityViolationException(DataIntegrityViolationException e) {
        this.printRequestInfo(e);
        return R.error("部分绑定记录未找到/记录已被使用/存在字段没有默认值, 操作被禁止");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<String> handleNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        this.printRequestInfo(e);
        return R.errors(ErrorCode.REQ_INVALID_PARAMS,
                errors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public R<String> handleNotValidException(ConstraintViolationException e) {
        List<String> errors = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());
        this.printRequestInfo(e);
        return R.errors(ErrorCode.REQ_INVALID_PARAMS, errors);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public R<String> handleNotValidException(MissingRequestHeaderException e) {
        this.printRequestInfo(e);
        return R.error("缺少Header: " + e.getHeaderName());
    }

    private void printRequestInfo(Throwable e) {
        logger.error(e.getMessage(), e);

        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        if (request == null) {
            return;
        }
        logger.error("Access-Token : {}", request.getHeader(Constant.ACCESS_TOKEN_HEADER));
        logger.error("IP : {}", IPUtils.getIpAddr(request));
        logger.error("method : {}", request.getMethod());
        logger.error("url : {}", request.getRequestURL().toString());
        logger.error("params map : {}", JSONUtils.toJSONString(request.getParameterMap()));
        logger.error("query params : {}", request.getQueryString());
        if (request instanceof ShiroHttpServletRequest) {
            ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
            if (shiroRequest.getRequest() instanceof XssHttpServletRequestWrapper) {
                String data =
                        ((XssHttpServletRequestWrapper) shiroRequest.getRequest()).getBodyStr();
                logger.error("body : {}", data);
            }
        }
    }
}
