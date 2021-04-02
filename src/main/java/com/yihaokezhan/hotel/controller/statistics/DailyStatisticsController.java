package com.yihaokezhan.hotel.controller.statistics;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.module.service.IDailyStatisticsService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 每日统计表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/hotel/daily/statistics")
public class DailyStatisticsController {

    @Autowired
    private IDailyStatisticsService dailyStatisticsService;

    @GetMapping("/list")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_GET)
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(dailyStatisticsService.mList(params));
    }

}
