package com.yihaokezhan.hotel.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.remark.RemarkRecord;
import com.yihaokezhan.hotel.common.utils.M;
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
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {


    @Override
    public Room mGet(String uuid) {
        return this.getById(uuid);
    }

    @Override
    public Room mOne(M params) {
        return this.getOne(getWrapper(params));
    }

    @Override
    public List<RemarkRecord> getRemark(String uuid) {
        Room entity = this.getById(uuid);
        if (entity == null) {
            return new ArrayList<>();
        }
        return entity.getRemark();
    }

    private QueryWrapper<Room> getWrapper(M params) {
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

        WrapperUtils.fillSelect(wrapper, params);

        return wrapper;
    }
}
