package com.yihaokezhan.hotel.module.service;

import java.util.Map;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yihaokezhan.hotel.model.Pager;
import com.yihaokezhan.hotel.module.entity.SystemLog;
import org.springframework.cache.annotation.CacheConfig;


/**
 * <p>
 * 系统日志 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@CacheConfig(cacheNames = SystemLog.TABLE_NAME)
public interface ISystemLogService extends IService<SystemLog> {

    Pager<SystemLog> mPage(Map<String, Object> params);

    boolean mCreate(SystemLog entity);

}

