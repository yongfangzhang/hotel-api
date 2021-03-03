package com.yihaokezhan.hotel.common.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yihaokezhan.hotel.common.exception.ErrorCode;
import com.yihaokezhan.hotel.common.exception.RRException;
import com.yihaokezhan.hotel.module.entity.Tenant;
import org.apache.commons.lang3.BooleanUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyongfang
 * @since 2021-02-22
 */
@Slf4j
public class ValidatorUtils {
    private static final Validator VALIDATOR;

    static {
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * 
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws RRException 校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups) throws RRException {
        Set<ConstraintViolation<Object>> constraintViolations = VALIDATOR.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            log.error(constraint.getPropertyPath().toString() + " : "
                    + (constraint.getInvalidValue() != null ? constraint.getInvalidValue() : "null")
                    + " : " + constraint.getMessage());
            throw new RRException(constraint.getMessage());
        }
    }

    /**
     * 校验对象列表
     *
     * @param objects 待校验对象列表
     * @param groups  待校验的组
     * @throws RRException 校验不通过，则报RRException异常
     */
    public static void validateEntities(Collection<?> objects, Class<?>... groups)
            throws RRException {
        if (CollectionUtils.isEmpty(objects)) {
            return;
        }
        for (Object object : objects) {
            validateEntity(object, groups);
        }
    }

    /**
     * 获取校验对象错误列表
     * 
     * @param object 待校验对象
     * @param groups 待校验的组
     */
    public static List<String> getValidateMessages(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = VALIDATOR.validate(object, groups);
        if (constraintViolations.isEmpty()) {
            return new ArrayList<>();
        }
        return constraintViolations.stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
    }

    public static void validateTenant(Tenant tenant) {
        Assert.notNull(tenant, "租户不存在");
        Assert.state(BooleanUtils.isNotTrue(tenant.getDeleted()), "租户已删除");
    }

    public static void validateTenant(Tenant tenant, ErrorCode errCode) {
        Assert.notNull(tenant, errCode);
        Assert.state(BooleanUtils.isNotTrue(tenant.getDeleted()), errCode);
    }
}
