package com.yihaokezhan.hotel.controller.route;

import java.util.Map;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.Route;
import com.yihaokezhan.hotel.module.service.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 路由表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/route")
public class RouteController {

    @Autowired
    private IRouteService routeService;


    @GetMapping("/page")
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(routeService.mPage(params));
    }

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(routeService.mList(params));
    }

    @GetMapping("/one")
    public R one(@RequestParam Map<String, Object> params) {
        return R.ok().data(routeService.mOne(params));
    }

    @GetMapping("/{uuid}")
    public R get(@PathVariable String uuid) {
        return R.ok().data(routeService.mGet(uuid));
    }

    @PostMapping("")
    public R create(@Validated(AddGroup.class) @RequestBody Route entity) {
        return R.ok().data(routeService.mCreate(entity));
    }

    @PutMapping("")
    public R update(@Validated(UpdateGroup.class) @RequestBody Route entity) {
        return R.ok().data(routeService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    public R delete(@PathVariable String uuid) {
        return R.ok().data(routeService.mDelete(uuid));
    }
}
