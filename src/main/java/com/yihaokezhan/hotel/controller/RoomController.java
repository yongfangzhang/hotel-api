package com.yihaokezhan.hotel.controller;


import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.module.entity.Room;
import com.yihaokezhan.hotel.module.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 房间表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/hotel/room")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @GetMapping("/{uuid}")
    public R get(@PathVariable String uuid) {
        return R.ok().data(roomService.getById(uuid));
    }

    @PostMapping("")
    public R create(@Validated(AddGroup.class) @RequestBody Room entity) {
        return R.ok().data(roomService.save(entity));
    }

    @PutMapping("")
    public R update(@Validated(UpdateGroup.class) @RequestBody Room entity) {
        return R.ok().data(roomService.updateById(entity));
    }

    @DeleteMapping("/{uuid}")
    public R delete(@PathVariable String uuid) {
        return R.ok().data(roomService.removeById(uuid));
    }
}

