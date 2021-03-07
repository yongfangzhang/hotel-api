package com.yihaokezhan.hotel.controller.user;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.module.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/page/xs")
    @JsonView(V.XS.class)
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(userService.mPage(params));
    }

    @GetMapping("/list/xs")
    @JsonView(V.XS.class)
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(userService.mList(params));
    }

    // @GetMapping("/one")
    // @JsonView(V.S.class)
    // @SysLog(operation = Operation.RETRIEVE, description = "查看用户 %s", params =
    // "#params")
    // public R one(@RequestParam Map<String, Object> params) {
    // return R.ok().data(userService.mOne(params));
    // }

    // @GetMapping("/info")
    // @JsonView(V.S.class)
    // public R getInfo(@LoginUser User user) {
    // return R.ok().data(user);
    // }

    // @GetMapping("/{uuid}")
    // @JsonView(V.S.class)
    // @SysLog(operation = Operation.RETRIEVE, description = "查看用户详情 %s", params =
    // "#uuid")
    // public R get(@PathVariable String uuid) {
    // return R.ok().data(userService.mGet(uuid));
    // }

    // @PostMapping("")
    // @JsonView(V.S.class)
    // @SysLog(operation = Operation.CREATE, description = "创建用户 %s", params =
    // "#entity")
    // public R create(@Validated(AddGroup.class) @RequestBody User entity) {
    // return R.ok().data(userService.mCreate(entity));
    // }

    // @PutMapping("")
    // @JsonView(V.S.class)
    // @SysLog(operation = Operation.UPDATE, description = "更新用户 %s", params =
    // "#entity")
    // public R update(@Validated(UpdateGroup.class) @RequestBody User entity) {
    // return R.ok().data(userService.mUpdate(entity));
    // }

    // @DeleteMapping("/{uuid}")
    // @JsonView(V.S.class)
    // @SysLog(operation = Operation.DELETE, description = "删除用户 %s", params =
    // "#uuid")
    // public R delete(@PathVariable String uuid) {
    // return R.ok().data(userService.mDelete(uuid));
    // }
}
