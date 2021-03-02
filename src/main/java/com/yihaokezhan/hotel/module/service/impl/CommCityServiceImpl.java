package com.yihaokezhan.hotel.module.service.impl;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.CommCity;
import com.yihaokezhan.hotel.module.mapper.CommCityMapper;
import com.yihaokezhan.hotel.module.service.ICommCityService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 市表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
@CacheConfig(cacheNames = CommCity.TABLE_NAME)
public class CommCityServiceImpl extends ServiceImpl<CommCityMapper, CommCity>
        implements ICommCityService {

    @Override
    @Cacheable(key = "'query::list::' + #a0")
    public List<CommCity> mList(Map<String, Object> params) {
        return list(getWrapper(params));
    }

    @Override
    @Cacheable(key = "#code")
    public CommCity mGet(String code) {
        return getById(code);
    }

    private QueryWrapper<CommCity> getWrapper(Map<String, Object> params) {
        QueryWrapper<CommCity> wrapper = new QueryWrapper<CommCity>();

        WrapperUtils.fillEq(wrapper, params, "code");
        WrapperUtils.fillEq(wrapper, params, "province_code");

        WrapperUtils.fillLike(wrapper, params, "name");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
