package com.yihaokezhan.hotel.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yihaokezhan.hotel.common.remark.RemarkRecord;
import com.yihaokezhan.hotel.common.validator.ValidatorUtils;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.model.BaseEntity;
import com.yihaokezhan.hotel.model.Pager;
import com.yihaokezhan.hotel.model.Query;
import com.yihaokezhan.hotel.module.service.IBaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 基础服务实现类
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Service
public abstract class BaseServiceImpl<C extends BaseMapper<T>, T extends BaseEntity>
        extends ServiceImpl<C, T> implements IBaseService<T> {

    @Override
    public List<T> mList(Map<String, Object> params) {
        return join(list(getWrapper(params)));
    }

    @Override
    public Pager<T> mPage(Map<String, Object> params) {
        Page<T> page = new Query<T>(params).getPage();
        page(page, getWrapper(params));
        join(page.getRecords());
        return new Pager<>(page);
    }

    @Override
    public T mGet(String uuid) {
        return join(getById(uuid));
    }

    @Override
    public T mOne(Map<String, Object> params) {
        return join(getOne(getWrapper(params)));
    }

    @Override
    public boolean mCreate(T entity) {
        ValidatorUtils.validateEntity(entity, AddGroup.class);
        return save(entity);
    }

    @Override
    public boolean mUpdate(T entity) {
        ValidatorUtils.validateEntity(entity, UpdateGroup.class);
        return updateById(entity);
    }

    @Override
    public boolean mDelete(String uuid) {
        return removeById(uuid);
    }

    @Override
    public List<RemarkRecord> getRemark(String uuid) {
        T entity = getById(uuid);
        if (entity == null) {
            return new ArrayList<>();
        }
        return entity.getRemark();
    }
}
