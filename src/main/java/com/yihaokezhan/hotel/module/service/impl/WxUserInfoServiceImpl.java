package com.yihaokezhan.hotel.module.service.impl;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yihaokezhan.hotel.common.redis.CachingConfiguration;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.WxUserInfo;
import com.yihaokezhan.hotel.module.mapper.WxUserInfoMapper;
import com.yihaokezhan.hotel.module.service.IWxUserInfoService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
@CacheConfig(cacheResolver = CachingConfiguration.CACHE_RESOLVER_NAME, cacheNames = WxUserInfo.TABLE_NAME)
public class WxUserInfoServiceImpl extends BaseServiceImpl<WxUserInfoMapper, WxUserInfo>
        implements IWxUserInfoService {


    @Override
    public QueryWrapper<WxUserInfo> getWrapper(Map<String, Object> params) {
        QueryWrapper<WxUserInfo> wrapper = new QueryWrapper<WxUserInfo>();

        WrapperUtils.fillEq(wrapper, params, "uuid");
        WrapperUtils.fillEq(wrapper, params, "open_id");
        WrapperUtils.fillEq(wrapper, params, "union_id");
        WrapperUtils.fillEq(wrapper, params, "gender");
        WrapperUtils.fillEq(wrapper, params, "mobile");

        WrapperUtils.fillLike(wrapper, params, "nickname");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
