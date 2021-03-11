package com.yihaokezhan.hotel.module.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.enums.RoomState;
import com.yihaokezhan.hotel.common.utils.MapUtils;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.common.validator.ValidatorUtils;
import com.yihaokezhan.hotel.module.entity.Room;
import com.yihaokezhan.hotel.module.mapper.RoomMapper;
import com.yihaokezhan.hotel.module.service.IRoomService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 房间表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
@CacheConfig(cacheNames = Room.TABLE_NAME)
public class RoomServiceImpl extends BaseServiceImpl<RoomMapper, Room> implements IRoomService {

    @Override
    public void beforeAction(Room room, Class<?> group) {
        if (room == null) {
            super.beforeAction(room, group);
        }
        ValidatorUtils.validateEntities(room.getPrices(), group);
    }

    @Override
    public void beforeAction(List<Room> rooms, Class<?> group) {
        if (CollectionUtils.isEmpty(rooms)) {
            super.beforeAction(rooms, group);
        }
        rooms.forEach(room -> {
            ValidatorUtils.validateEntities(room.getPrices(), group);
        });
    }

    @Override
    public QueryWrapper<Room> getWrapper(Map<String, Object> params) {
        QueryWrapper<Room> wrapper = new QueryWrapper<Room>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "apartmentUuid");
        WrapperUtils.fillEq(wrapper, params, "typeUuid");
        WrapperUtils.fillEq(wrapper, params, "type");
        WrapperUtils.fillEq(wrapper, params, "state");
        WrapperUtils.fillEq(wrapper, params, "saleTimes");

        WrapperUtils.fillLike(wrapper, params, "floorNumber");
        WrapperUtils.fillLike(wrapper, params, "unitNumber");
        WrapperUtils.fillLike(wrapper, params, "number");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "apartmentUuids", "apartment_uuid");
        WrapperUtils.fillInList(wrapper, params, "typeUuids", "type_uuid");

        String states = MapUtils.getString(params, "states");
        if (StringUtils.isNotBlank(states)) {
            wrapper.in("state", Arrays.asList(StringUtils.split(states, ",")));
        }

        wrapper.ne("state", RoomState.APARTMENT_FORBIDDEN.getValue());
        wrapper.ne("state", RoomState.APARTMENT_DELETED.getValue());

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
