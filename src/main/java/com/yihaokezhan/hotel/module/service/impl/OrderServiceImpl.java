package com.yihaokezhan.hotel.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.remark.RemarkRecord;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.common.utils.WrapperUtils;
import com.yihaokezhan.hotel.module.entity.Order;
import com.yihaokezhan.hotel.module.mapper.OrderMapper;
import com.yihaokezhan.hotel.module.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {


    @Override
    public Order getByMap(M params) {
        return this.getOne(getWrapper(params));
    }

    @Override
    public List<RemarkRecord> getRemark(String uuid) {
        Order entity = getByMap(M.m().put("uuid", uuid).put(WrapperUtils.SQL_SELECT, "remark"));
        if (entity == null) {
            return new ArrayList<>();
        }
        return entity.getRemark();
    }

    private QueryWrapper<Order> getWrapper(M params) {
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();

        String[] eqFields = new String[] {"uuid"};

        WrapperUtils.fillEqs(wrapper, params, eqFields);
        // WrapperUtils.fillLikes(wrapper, params, likeFields);
        WrapperUtils.fillSelects(wrapper, params);

        return wrapper;
    }
}
