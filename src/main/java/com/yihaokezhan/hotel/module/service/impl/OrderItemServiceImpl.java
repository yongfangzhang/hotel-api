package com.yihaokezhan.hotel.module.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.enums.Gender;
import com.yihaokezhan.hotel.common.enums.RoomState;
import com.yihaokezhan.hotel.common.enums.UserState;
import com.yihaokezhan.hotel.common.exception.RRException;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.OrderItem;
import com.yihaokezhan.hotel.module.entity.Room;
import com.yihaokezhan.hotel.module.entity.User;
import com.yihaokezhan.hotel.module.mapper.OrderItemMapper;
import com.yihaokezhan.hotel.module.service.IOrderItemService;
import com.yihaokezhan.hotel.module.service.IRoomService;
import com.yihaokezhan.hotel.module.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单详细表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
@CacheConfig(cacheNames = OrderItem.TABLE_NAME)
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IUserService userService;

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(key = "query", allEntries = true)
    })
    // @formatter:on
    public OrderItem mCreate(OrderItem entity) {
        super.mCreate(entity);
        Room room = new Room();
        room.setUuid(entity.getRoomUuid());
        room.setState(RoomState.STAY_CLEAN.getValue());
        room.setOrderItemUuid(entity.getUuid());
        roomService.mCreate(room);
        return entity;
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(key = "query", allEntries = true)
    })
    // @formatter:on
    public List<OrderItem> mBatchCreate(List<OrderItem> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            throw new RRException("入住人不能为空");
        }
        try {
            super.mBatchCreate(entities);
        } catch (RRException e) {
            throw e;
        } catch (Exception e) {
            log.error("创建订单子项目失败", e);
            throw new RRException("请检查是否同一房间分配了多个入住人");
        }
        // 更新房间
        List<Room> rooms = entities.stream().map(entity -> {
            Room room = new Room();
            room.setUuid(entity.getRoomUuid());
            room.setState(RoomState.STAY_CLEAN.getValue());
            room.setOrderItemUuid(entity.getUuid());
            return room;
        }).collect(Collectors.toList());

        roomService.mBatchUpdate(rooms);

        // 更新会员信息
        List<User> users = entities.stream().filter(entity -> entity.isSaveUser()).map(entity -> {
            User user = new User();
            user.setChannel(entity.getChannel());
            user.setGender(Gender.UNKNOWN.getValue());
            user.setMobile(entity.getMobile());
            user.setName(entity.getName());
            user.setState(UserState.NORMAL.getValue());
            return user;
        }).collect(Collectors.toList());

        userService.mBatchCreateMember(users);

        return entities;
    }

    @Override
    public OrderItem join(OrderItem item) {
        if (item == null) {
            return item;
        }
        roomService.attachOne(item, item.getRoomUuid(), (record, room) -> {
            item.setRoom(room);
        });
        return item;
    }

    @Override
    public List<OrderItem> join(List<OrderItem> items) {
        if (CollectionUtils.isEmpty(items)) {
            return items;
        }
        roomService.attachList(items, OrderItem::getRoomUuid, (record, roomMap) -> {
            record.setRoom(roomMap.get(record.getRoomUuid()));
        });
        return items;
    }

    @Override
    public QueryWrapper<OrderItem> getWrapper(Map<String, Object> params) {
        QueryWrapper<OrderItem> wrapper = new QueryWrapper<OrderItem>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "orderUuid");
        WrapperUtils.fillEq(wrapper, params, "tenantUuid");
        WrapperUtils.fillEq(wrapper, params, "apartmentUuid");
        WrapperUtils.fillEq(wrapper, params, "roomUuid");
        WrapperUtils.fillEq(wrapper, params, "mobile");
        WrapperUtils.fillEq(wrapper, params, "state");
        WrapperUtils.fillEq(wrapper, params, "lodgingType");

        WrapperUtils.fillLike(wrapper, params, "name");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "tenantUuids", "tenant_uuid");
        WrapperUtils.fillInList(wrapper, params, "orderUuids", "order_uuid");
        WrapperUtils.fillInList(wrapper, params, "apartmentUuids", "apartment_uuid");
        WrapperUtils.fillInList(wrapper, params, "roomUuids", "room_uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
