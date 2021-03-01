package com.yihaokezhan.hotel.module.service.impl;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.CommProvince;
import com.yihaokezhan.hotel.module.mapper.CommProvinceMapper;
import com.yihaokezhan.hotel.module.service.ICommProvinceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 省表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
public class CommProvinceServiceImpl extends ServiceImpl<CommProvinceMapper, CommProvince>
        implements ICommProvinceService {

    @Override
    public List<CommProvince> mList(Map<String, Object> params) {
        return list(getWrapper(params));
    }

    @Override
    public CommProvince mGet(String code) {
        return getById(code);
    }

    private QueryWrapper<CommProvince> getWrapper(Map<String, Object> params) {
        QueryWrapper<CommProvince> wrapper = new QueryWrapper<CommProvince>();


        WrapperUtils.fillEq(wrapper, params, "code");
        WrapperUtils.fillLike(wrapper, params, "name");

        WrapperUtils.fillCreatedAtBetween(wrapper, params);
        WrapperUtils.fillSelect(wrapper, params);
        WrapperUtils.fillOrderBy(wrapper, params);
        WrapperUtils.fillGroupBy(wrapper, params);
        return wrapper;
    }
}
