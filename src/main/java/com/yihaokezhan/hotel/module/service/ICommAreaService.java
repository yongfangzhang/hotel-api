package com.yihaokezhan.hotel.module.service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yihaokezhan.hotel.module.entity.CommArea;


/**
 * <p>
 * 区表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
public interface ICommAreaService extends IService<CommArea> {


    List<CommArea> mList(Map<String, Object> params);

    CommArea mGet(String code);

}

