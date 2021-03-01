package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Room;
import com.yihaokezhan.hotel.module.mapper.RoomMapper;
import com.yihaokezhan.hotel.module.service.IRoomService;
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
public class RoomServiceImpl extends BaseServiceImpl<RoomMapper, Room> implements IRoomService {

    @Override
    public QueryWrapper<Room> getWrapper(Map<String, Object> params) {
        QueryWrapper<Room> wrapper = new QueryWrapper<Room>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "apartmentUuid");
        WrapperUtils.fillEq(wrapper, params, "type");
        WrapperUtils.fillEq(wrapper, params, "state");
        WrapperUtils.fillEq(wrapper, params, "saleTimes");

        WrapperUtils.fillLike(wrapper, params, "floorNumber");
        WrapperUtils.fillLike(wrapper, params, "unitNumber");
        WrapperUtils.fillLike(wrapper, params, "number");

        WrapperUtils.fillInList(wrapper, params, "uuids", "uuid");
        WrapperUtils.fillInList(wrapper, params, "apartmentUuids", "apartment_uuid");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
