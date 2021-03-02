package com.yihaokezhan.hotel.module.service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yihaokezhan.hotel.module.entity.CommCity;


/**
 * <p>
 * 市表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
public interface ICommCityService extends IService<CommCity> {

    List<CommCity> mList(Map<String, Object> params);

    CommCity mGet(String code);
}

