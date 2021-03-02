package com.yihaokezhan.hotel.module.service;

import java.util.Map;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yihaokezhan.hotel.model.Pager;
import com.yihaokezhan.hotel.module.entity.SystemHttpLog;


/**
 * <p>
 * 网络日志 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
public interface ISystemHttpLogService extends IService<SystemHttpLog> {

    Pager<SystemHttpLog> mPage(Map<String, Object> params);

    boolean mCreate(SystemHttpLog entity);

}

