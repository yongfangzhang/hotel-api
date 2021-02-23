package com.yihaokezhan.hotel.module.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yihaokezhan.hotel.common.remark.RemarkRecord;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.module.entity.OrderItem;

/**
 * <p>
 * 订单详细表 服务类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
public interface IOrderItemService extends IService<OrderItem> {

    OrderItem mGet(String uuid);

    OrderItem mOne(M params);

    List<RemarkRecord> getRemark(String uuid);
}
