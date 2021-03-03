package com.yihaokezhan.hotel.captcha.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.google.code.kaptcha.Producer;
import com.yihaokezhan.hotel.captcha.service.ICaptchaService;
import com.yihaokezhan.hotel.common.exception.ErrorCode;
import com.yihaokezhan.hotel.common.redis.RedisService;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.validator.Assert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyongfang
 * @since Wed Mar 03 2021
 */
@Service
@Slf4j
public class CaptchaServiceImpl implements ICaptchaService {

    @Autowired
    private Producer producer;

    @Autowired
    private RedisService redisService;

    @Override
    public String generateBase64Captcha(String sid) throws IOException {
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        String base64Img = Base64Utils.encodeToString(outputStream.toByteArray());
        redisService.set(getCaptchaKey(sid), text, Constant.CACHE_DURATION_CAPTCHA);
        log.info("sid: {}, code: {}", sid, text);
        return "data:image/jpg;base64," + base64Img;
    }

    @Override
    public void validateCaptcha(String sid, String code) {
        Assert.state(!StringUtils.isAnyBlank(sid, code), ErrorCode.REQ_INVALID_PARAMS);
        String cachedCode = redisService.get(getCaptchaKey(sid));
        Assert.isTrue(code.equalsIgnoreCase(cachedCode), ErrorCode.CAPTCHA_FAILED);
    }

    private String getCaptchaKey(String sid) {
        return Constant.CACHE_PREFIX_CAPTCHA + sid;
    }
}
