package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.redis.CachingConfiguration;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.RoomPrice;
import com.yihaokezhan.hotel.module.mapper.RoomPriceMapper;
import com.yihaokezhan.hotel.module.service.IRoomPriceService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 房间价格表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
@CacheConfig(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, cacheNames = RoomPrice.TABLE_NAME)
public class RoomPriceServiceImpl extends BaseServiceImpl<RoomPriceMapper, RoomPrice>
        implements IRoomPriceService {


    @Override
    public QueryWrapper<RoomPrice> getWrapper(Map<String, Object> params) {
        QueryWrapper<RoomPrice> wrapper = new QueryWrapper<RoomPrice>();


        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "room_uuid");
        WrapperUtils.fillEq(wrapper, params, "type");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
