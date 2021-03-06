package com.yihaokezhan.hotel.controller.pub;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.captcha.service.ICaptchaService;
import com.yihaokezhan.hotel.common.annotation.Annc;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.RandomUtils;
import com.yihaokezhan.hotel.common.utils.V;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel/pub/captcha")
public class CaptchaController {

    @Autowired
    private ICaptchaService captchaService;

    @Annc
    @GetMapping("")
    @JsonView(V.S.class)
    public R getCaptcha(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        String sid = RandomUtils.randomString32();
        String base64Img = captchaService.generateBase64Captcha(sid);
        M m = M.m().put("image", base64Img).put("sid", sid);
        return R.ok().data(m);
    }
}
