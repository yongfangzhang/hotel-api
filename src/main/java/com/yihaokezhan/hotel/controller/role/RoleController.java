package com.yihaokezhan.hotel.controller.role;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.Role;
import com.yihaokezhan.hotel.module.service.IRoleService;
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
 * 角色表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/hotel/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;


    @GetMapping("/page")
    @JsonView(V.S.class)
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(roleService.mPage(params));
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(roleService.mList(params));
    }

    @GetMapping("/one")
    @JsonView(V.S.class)
    public R one(@RequestParam Map<String, Object> params) {
        return R.ok().data(roleService.mOne(params));
    }

    @GetMapping("/{uuid}")
    @JsonView(V.S.class)
    public R get(@PathVariable String uuid) {
        return R.ok().data(roleService.mGet(uuid));
    }

    @PostMapping("")
    @JsonView(V.S.class)
    public R create(@Validated(AddGroup.class) @RequestBody Role entity) {
        return R.ok().data(roleService.mCreate(entity));
    }

    @PutMapping("")
    @JsonView(V.S.class)
    public R update(@Validated(UpdateGroup.class) @RequestBody Role entity) {
        return R.ok().data(roleService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    @JsonView(V.S.class)
    public R delete(@PathVariable String uuid) {
        return R.ok().data(roleService.mDelete(uuid));
    }
}
