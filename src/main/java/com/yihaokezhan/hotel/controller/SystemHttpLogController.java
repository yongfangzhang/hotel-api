package com.yihaokezhan.hotel.controller;

import java.util.Map;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.module.service.ISystemHttpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 网络日志 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/system/http-log")
public class SystemHttpLogController {

    @Autowired
    private ISystemHttpLogService systemHttpLogService;

    @GetMapping("/page")
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(systemHttpLogService.mPage(params));
    }
}
