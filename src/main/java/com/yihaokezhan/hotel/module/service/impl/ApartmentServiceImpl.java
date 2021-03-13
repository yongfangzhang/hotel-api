package com.yihaokezhan.hotel.module.service.impl;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.enums.ApartmentState;
import com.yihaokezhan.hotel.common.enums.RoomState;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Apartment;
import com.yihaokezhan.hotel.module.entity.Order;
import com.yihaokezhan.hotel.module.entity.Room;
import com.yihaokezhan.hotel.module.mapper.ApartmentMapper;
import com.yihaokezhan.hotel.module.service.IApartmentService;
import com.yihaokezhan.hotel.module.service.IRoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公寓表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
@CacheConfig(cacheNames = Apartment.TABLE_NAME)
public class ApartmentServiceImpl extends BaseServiceImpl<ApartmentMapper, Apartment> implements IApartmentService {

    @Autowired
    private IRoomService roomService;

    @Override
    public void onOrderCreated(Order order) {
        baseMapper.updateIncome(order.getApartmentUuid(), order.getPaidPrice(), order.getItems().size());
        roomService.onOrderCreated(order);
    }

    @Override
    public void onOrderCanceled(Order order) {
        baseMapper.updateIncome(order.getApartmentUuid(), order.getPaidPrice().negate(), -1 * order.getItems().size());
        roomService.onOrderCanceled(order);
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(key = "query", allEntries = true),
        @CacheEvict(key = "#apartment.getUuid()", allEntries = true)
    })
    // @formatter:on
    public Apartment mUpdate(Apartment apartment) {
        super.mUpdate(apartment);
        if (ApartmentState.FORBIDDEN.getValue().equals(apartment.getState())) {
            updateRoomState(apartment.getUuid(), RoomState.APARTMENT_FORBIDDEN);
        }
        return apartment;
    }

    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(key = "query", allEntries = true),
        @CacheEvict(key = "#uuid", allEntries = true)
    })
    // @formatter:on
    public boolean mDelete(String uuid) {
        super.mDelete(uuid);
        updateRoomState(uuid, RoomState.APARTMENT_DELETED);
        return true;
    }

    @Override
    public QueryWrapper<Apartment> getWrapper(Map<String, Object> params) {
        QueryWrapper<Apartment> wrapper = new QueryWrapper<Apartment>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "tenantUuid");
        WrapperUtils.fillEq(wrapper, params, "areaCode");
        WrapperUtils.fillEq(wrapper, params, "longitude");
        WrapperUtils.fillEq(wrapper, params, "latitude");
        WrapperUtils.fillEq(wrapper, params, "geohash4");
        WrapperUtils.fillEq(wrapper, params, "contactorMobile");
        WrapperUtils.fillEq(wrapper, params, "state");

        WrapperUtils.fillLike(wrapper, params, "name");
        WrapperUtils.fillLike(wrapper, params, "shortName");
        WrapperUtils.fillLike(wrapper, params, "address");
        WrapperUtils.fillLike(wrapper, params, "contactor");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "tenantUuids", "tenant_uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }

    private void updateRoomState(String apartmentUuid, RoomState state) {
        // 禁用/删除 公寓同时 禁用/删除 房间
        List<Room> rooms = roomService.mList(M.m().put("apartmentUuid", apartmentUuid));
        rooms.forEach(room -> {
            room.setState(state.getValue());
        });
        roomService.mBatchUpdate(rooms);
    }
}
