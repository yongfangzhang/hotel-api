package com.yihaokezhan.hotel.captcha.service;

import java.io.IOException;

/**
 * @author zhangyongfang
 * @since Wed Mar 03 2021
 */
public interface ICaptchaService {

    String generateBase64Captcha(String sid) throws IOException;

    void validateCaptcha(String sid, String code);

}
