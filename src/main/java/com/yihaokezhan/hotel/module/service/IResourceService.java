package com.yihaokezhan.hotel.module.service;

import com.yihaokezhan.hotel.module.entity.Resource;
import org.springframework.cache.annotation.CacheConfig;


/**
 * <p>
 * 资源表 IService
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-03-01
 */
@CacheConfig(cacheNames = Resource.TABLE_NAME)
public interface IResourceService extends IBaseService<Resource> {

}

