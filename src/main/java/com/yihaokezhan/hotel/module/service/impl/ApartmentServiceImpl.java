package com.yihaokezhan.hotel.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.remark.RemarkRecord;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Apartment;
import com.yihaokezhan.hotel.module.mapper.ApartmentMapper;
import com.yihaokezhan.hotel.module.service.IApartmentService;
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
public class ApartmentServiceImpl extends ServiceImpl<ApartmentMapper, Apartment>
        implements IApartmentService {

    @Override
    public Apartment getByMap(M params) {
        return this.getOne(getWrapper(params));
    }

    @Override
    public List<RemarkRecord> getRemark(String uuid) {
        Apartment entity = getByMap(M.m().put("uuid", uuid).put(WrapperUtils.SQL_SELECT, "remark"));
        if (entity == null) {
            return new ArrayList<>();
        }
        return entity.getRemark();
    }

    private QueryWrapper<Apartment> getWrapper(M params) {
        QueryWrapper<Apartment> wrapper = new QueryWrapper<Apartment>();

        String[] eqFields = new String[] {"uuid"};

        WrapperUtils.fillEqs(wrapper, params, eqFields);
        // WrapperUtils.fillLikes(wrapper, params, likeFields);
        WrapperUtils.fillSelects(wrapper, params);

        return wrapper;
    }
}
