package com.yihaokezhan.hotel.module.service.impl;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.enums.RoomState;
import com.yihaokezhan.hotel.common.utils.MapUtils;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.common.validator.ValidatorUtils;
import com.yihaokezhan.hotel.module.entity.Order;
import com.yihaokezhan.hotel.module.entity.Room;
import com.yihaokezhan.hotel.module.mapper.RoomMapper;
import com.yihaokezhan.hotel.module.service.IRoomService;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
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
            return;
        }
        ValidatorUtils.validateEntities(room.getPrices(), group);
    }

    @Override
    public void beforeAction(List<Room> rooms, Class<?> group) {
        if (CollectionUtils.isEmpty(rooms)) {
            super.beforeAction(rooms, group);
            return;
        }
        rooms.forEach(room -> {
            ValidatorUtils.validateEntities(room.getPrices(), group);
        });
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(allEntries = true)
    })
    // @formatter:on
    public void onOrderCreated(Order order) {
        order.getItems().forEach(item -> {
            baseMapper.updateIncome(item.getRoomUuid(), item.getPaidPrice());
        });
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(allEntries = true)
    })
    // @formatter:on
    public void onOrderCanceled(Order order) {
        order.getItems().forEach(item -> {
            baseMapper.updateIncome(item.getRoomUuid(), item.getPaidPrice().negate());
        });
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(allEntries = true)
    })
    // @formatter:on
    public void clearOrderItems(List<String> orderItemUuids) {
        if (CollectionUtils.isEmpty(orderItemUuids)) {
            return;
        }
        Room entity = new Room();
        entity.setOrderItemUuid("");
        entity.setState(RoomState.EMPTY_DARTY.getValue());
        baseMapper.update(entity, new QueryWrapper<Room>().in("order_item_uuid", orderItemUuids));
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

        WrapperUtils.fillStates(wrapper, params);

        if (!MapUtils.getBoolean(params, "nstate")) {
            wrapper.ne("state", RoomState.APARTMENT_FORBIDDEN.getValue());
            wrapper.ne("state", RoomState.APARTMENT_DELETED.getValue());
        }

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
