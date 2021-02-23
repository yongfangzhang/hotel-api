package com.yihaokezhan.hotel.module.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yihaokezhan.hotel.common.remark.RemarkRecord;
import com.yihaokezhan.hotel.common.utils.M;
import com.yihaokezhan.hotel.module.entity.RoleRoute;

/**
 * <p>
 * 角色路由表 服务类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
public interface IRoleRouteService extends IService<RoleRoute> {

    RoleRoute mGet(String uuid);

    List<RoleRoute> mList(M params);

    RoleRoute mOne(M params);

    List<RemarkRecord> getRemark(String uuid);
}
