package com.yihaokezhan.hotel.module.service.impl;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.CommArea;
import com.yihaokezhan.hotel.module.mapper.CommAreaMapper;
import com.yihaokezhan.hotel.module.service.ICommAreaService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 区表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
@CacheConfig(cacheNames = CommArea.TABLE_NAME)
public class CommAreaServiceImpl extends ServiceImpl<CommAreaMapper, CommArea>
        implements ICommAreaService {


    @Override
    // @formatter:off
    @Caching(evict = {
        @CacheEvict(key = Constant.CACHE_PREFIX_QUERY, allEntries = true)
    })
    // @formatter:on
    public List<CommArea> mList(Map<String, Object> params) {
        return list(getWrapper(params));
    }

    @Override
    @Cacheable(key = "#code")
    public CommArea mGet(String code) {
        return getById(code);
    }

    private QueryWrapper<CommArea> getWrapper(Map<String, Object> params) {
        QueryWrapper<CommArea> wrapper = new QueryWrapper<CommArea>();

        WrapperUtils.fillEq(wrapper, params, "code");
        WrapperUtils.fillEq(wrapper, params, "city_code");

        WrapperUtils.fillLike(wrapper, params, "name");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
