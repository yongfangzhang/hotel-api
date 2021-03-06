package com.yihaokezhan.hotel.controller.pub;

import java.util.LinkedHashMap;
import java.util.Map;

import com.yihaokezhan.hotel.common.annotation.Annc;
import com.yihaokezhan.hotel.common.enums.BaseEnum;
import com.yihaokezhan.hotel.common.exception.ErrorCode;
import com.yihaokezhan.hotel.common.utils.EnumUtils;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.PackageUtils;
import com.yihaokezhan.hotel.common.utils.R;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyongfang
 * @since Thu Mar 04 2021
 */
@RestController
@RequestMapping("/hotel/pub/enum")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EnumController {

    @Annc
    @GetMapping("")
    public R get() {
        M em = M.m();
        M emMap = M.m();
        // 基础枚举
        String packageName = "com.yihaokezhan.hotel.common.enums";
        PackageUtils.getClasses(packageName).forEach(clz -> {
            try {
                if (clz == null) {
                    return;
                }
                Class<BaseEnum> enumClz = (Class<BaseEnum>) clz;
                String k = clz.getSimpleName();
                if (StringUtils.isBlank(k)) {
                    return;
                }
                em.put(clz.getSimpleName(), EnumUtils.getEnum(enumClz));
                emMap.put(clz.getSimpleName(), EnumUtils.getEnumMap(enumClz));
            } catch (Exception ignore) {
            }
        });

        // 错误码
        Map<String, Object> map = new LinkedHashMap<>();
        Map<Object, String> nameMap = new LinkedHashMap<>();
        for (final ErrorCode err : ErrorCode.class.getEnumConstants()) {
            Integer v = err.getCode();
            map.put(err.toString(), v);
            nameMap.put(v, err.getMessage());
        }
        em.put("ErrorCode", map);
        emMap.put("ErrorCode", nameMap);
        M m = M.m().put("enum", em).put("enumMap", emMap);

        return R.ok().data(m);
    }
}
