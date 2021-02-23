package com.yihaokezhan.hotel.common.wx;

import javax.servlet.http.HttpServletRequest;
import com.yihaokezhan.hotel.common.utils.Constant;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
public class WXService {

    public String getOpenIdFromRequest(NativeWebRequest request) {
        String openId = request.getHeader(Constant.WX_OPENID);
        WXThreadLocal.setOpenId(openId);
        return openId;
    }


    public String getOpenIdFromRequest(HttpServletRequest request) {
        String openId = request.getHeader(Constant.WX_OPENID);
        WXThreadLocal.setOpenId(openId);
        return openId;
    }

}
