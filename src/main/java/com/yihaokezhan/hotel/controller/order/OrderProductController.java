package com.yihaokezhan.hotel.controller.order;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonView;
import com.yihaokezhan.hotel.common.annotation.LoginUser;
import com.yihaokezhan.hotel.common.annotation.SysLog;
import com.yihaokezhan.hotel.common.enums.Operation;
import com.yihaokezhan.hotel.common.utils.Constant;
import com.yihaokezhan.hotel.common.utils.R;
import com.yihaokezhan.hotel.common.utils.V;
import com.yihaokezhan.hotel.common.validator.group.AddGroup;
import com.yihaokezhan.hotel.common.validator.group.UpdateGroup;
import com.yihaokezhan.hotel.model.TokenUser;
import com.yihaokezhan.hotel.module.entity.OrderProduct;
import com.yihaokezhan.hotel.module.entity.Product;
import com.yihaokezhan.hotel.module.service.IOrderProductService;
import com.yihaokezhan.hotel.module.service.IProductService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单商品表 前端控制器
 * </p>
 *
 * @author zhangyongfang
 * @since 2021-06-11
 */
@RestController
@RequestMapping("/hotel/order/product")
public class OrderProductController {

    @Autowired
    private IOrderProductService orderProductService;

    @Autowired
    private IProductService productService;


    @GetMapping("/page")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_GET)
    public R page(@RequestParam Map<String, Object> params) {
        return R.ok().data(orderProductService.mPage(params));
    }

    @GetMapping("/list")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_GET)
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().data(orderProductService.mList(params));
    }

    @GetMapping("/one")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_GET)
    public R one(@RequestParam Map<String, Object> params) {
        return R.ok().data(orderProductService.mOne(params));
    }

    @GetMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_GET)
    public R get(@PathVariable String uuid) {
        return R.ok().data(orderProductService.mGet(uuid));
    }

    @PostMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.CREATE, description = "创建订单商品 %s", params = "#entity")
    public R create(@Validated(AddGroup.class) @RequestBody OrderProduct entity,
            @LoginUser TokenUser tokenUser) {
        this.createProduct(entity, tokenUser);
        entity.updateTotalPrice();
        return R.ok().data(orderProductService.mCreate(entity));
    }

    @PutMapping("")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.UPDATE, description = "更新订单商品 %s", params = "#entity")
    public R update(@Validated(UpdateGroup.class) @RequestBody OrderProduct entity,
            @LoginUser TokenUser tokenUser) {
        this.createProduct(entity, tokenUser);
        entity.updateTotalPrice();
        return R.ok().data(orderProductService.mUpdate(entity));
    }

    @DeleteMapping("/{uuid}")
    @JsonView(V.S.class)
    @RequiresPermissions(Constant.PERM_ORDER_UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @SysLog(operation = Operation.DELETE, description = "删除订单商品 %s", params = "#uuid")
    public R delete(@PathVariable String uuid) {
        return R.ok().data(orderProductService.mDelete(uuid));
    }

    private void createProduct(OrderProduct entity, TokenUser tokenUser) {
        if (entity.isExsited()) {
            return;
        }
        // 新建商品
        Product product = new Product();
        product.setTenantUuid(tokenUser.getTenantUuid());
        product.setName(entity.getProductName());
        product.setPrice(entity.getProductPrice());
        productService.mCreate(product);
        entity.setProductUuid(product.getUuid());
    }
}
