package com.yihaokezhan.hotel.controller;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.TreeUtils;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.SystemDict;
import com.yihaokezhan.hotel.module.service.ISystemDictService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
 * 系统字典表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/hotel/SYSTEM_DICT")
public class SystemDictController {

    @Autowired
    private ISystemDictService systemDictService;

    @GetMapping("/page")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_SYSTEM_DICT_GET)
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(systemDictService.mPage(params));
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_SYSTEM_DICT_GET)
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(systemDictService.mList(params));
    }

    @GetMapping("/tree")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_SYSTEM_DICT_GET)
    public R tree(@RequestParam Map<String, Object> params) {
        List<SystemDict> records = systemDictService.mList(params);
        return R.ok().data(TreeUtils.bulid(records));
    }

    @GetMapping("/one")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_SYSTEM_DICT_GET)
    public R one(@RequestParam Map<String, Object> params) {
        return R.ok().data(systemDictService.mOne(params));
    }

    @GetMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_SYSTEM_DICT_GET)
    public R get(@PathVariable String uuid) {
        return R.ok().data(systemDictService.mGet(uuid));
    }

    @PostMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_SYSTEM_DICT_CREATE)
    @Transactional(rollbackFor = Exception.class)
    public R create(@Validated(AddGroup.class) @RequestBody SystemDict entity) {
        return R.ok().data(systemDictService.mCreate(entity));
    }

    @PutMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_SYSTEM_DICT_UPDATE)
    @Transactional(rollbackFor = Exception.class)
    public R update(@Validated(UpdateGroup.class) @RequestBody SystemDict entity) {
        return R.ok().data(systemDictService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_SYSTEM_DICT_DELETE)
    @Transactional(rollbackFor = Exception.class)
    public R delete(@PathVariable String uuid) {
        return R.ok().data(systemDictService.mDelete(uuid));
    }
}
