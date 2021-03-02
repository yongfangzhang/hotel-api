package com.yihaokezhan.hotel.module.service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yihaokezhan.hotel.module.entity.CommProvince;


/**
 * <p>
 * 省表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
public interface ICommProvinceService extends IService<CommProvince> {

    List<CommProvince> mList(Map<String, Object> params);

    CommProvince mGet(String code);
}

