package com.yihaokezhan.hotel.controller.product;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.module.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-06-11
 */
@RestController
@RequestMapping("/hotel/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/page")
    @JsonView(V.S.class)
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(productService.mPage(params));
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(productService.mList(params));
    }

}
