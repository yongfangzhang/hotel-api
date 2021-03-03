package com.yihaokezhan.hotel.controller.pub;

import java.util.Map;
import com.yihaokezhan.hotel.common.annotation.Annc;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.module.service.ICommProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 省表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/pub/province")
public class CommProvinceController {

    @Autowired
    private ICommProvinceService commProvinceService;

    @Annc
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(commProvinceService.mList(params));
    }

    @Annc
    @GetMapping("/{code}")
    public R get(@PathVariable String code) {
        return R.ok().data(commProvinceService.mGet(code));
    }
}
