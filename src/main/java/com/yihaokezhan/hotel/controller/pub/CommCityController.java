package com.yihaokezhan.hotel.controller.pub;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.annotation.Annc;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.module.service.ICommCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 市表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/pub/city")
public class CommCityController {

    @Autowired
    private ICommCityService commCityService;

    @Annc
    @GetMapping("/list")
    @JsonView(V.S.class)
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(commCityService.mList(params));
    }

    @Annc
    @GetMapping("/{code}")
    @JsonView(V.S.class)
    public R get(@PathVariable String code) {
        return R.ok().data(commCityService.mGet(code));
    }
}
