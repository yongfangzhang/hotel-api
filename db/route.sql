INSERT INTO yhkz_hotel.route (uuid,tenant_uuid,`path`,account_type,`type`,permissions,caption,description,remark,created_at,updated_at) VALUES
	 ('11eb763ed35bca81942677f8b1e79557',NULL,'/',2,1,'["*"]','根','【所有权限】超级管理员具有','[]','2021-02-24 09:23:57','2021-04-02 11:01:34'),
	 ('11eb7ca635e3f7c4890f8d10c588fccf',NULL,'/dashboard/',2,1,'["hotel:pub:*"]','首页','【首页】','[]','2021-03-03 22:58:31','2021-04-02 11:01:34'),
	 ('11eb7cb772e7b843b54159292e1d28d2',NULL,'/system/account/index',2,2,'["account:get", "account:role:get"]','账号-查看',NULL,'[]','2021-03-04 15:01:31','2021-04-02 11:01:34'),
	 ('11eb7cb772eb3ab4b54171f32c760538',NULL,'/system/account/edit',2,3,'["account:create", "account:role:create"]','账号-创建',NULL,'[]','2021-03-04 15:01:31','2021-04-02 11:01:34'),
	 ('11eb7cb772eb88d5b5411704e488d7b5',NULL,'/system/account/edit',2,4,'["account:update", "account:role:update"]','账号-修改',NULL,'[]','2021-03-04 15:01:31','2021-04-02 11:01:34'),
	 ('11eb7cb772ebd6f6b541f3eb313897f6',NULL,'/system/account/edit',2,5,'["account:delete", "account:role:delete"]','账号-删除',NULL,'[]','2021-03-04 15:01:31','2021-04-02 11:01:34'),
	 ('11eb7cb772edf9d7b541cbb36e4c9ff1',NULL,'/system/role/index',2,2,'["role:get", "role:route:get"]','角色-查看',NULL,'[]','2021-03-04 15:01:31','2021-04-02 11:01:34'),
	 ('11eb7cb772ee6f08b54173ad84fdfcee',NULL,'/system/role/edit',2,3,'["role:create", "role:route:create"]','角色-创建',NULL,'[]','2021-03-04 15:01:31','2021-04-02 11:01:34'),
	 ('11eb7cb772eebd29b541cb829ca03252',NULL,'/system/role/edit',2,4,'["role:update", "role:route:update"]','角色-修改',NULL,'[]','2021-03-04 15:01:31','2021-04-02 11:01:34'),
	 ('11eb7cb772ef325ab541b1f4f9a6d750',NULL,'/system/role/edit',2,5,'["role:delete", "role:route:delete"]','角色-删除',NULL,'[]','2021-03-04 15:01:31','2021-04-02 11:01:34');
INSERT INTO yhkz_hotel.route (uuid,tenant_uuid,`path`,account_type,`type`,permissions,caption,description,remark,created_at,updated_at) VALUES
	 ('11eb7cb772efa78bb54103d46979ba3c',NULL,'/system/permission/index',2,2,'["route:get"]','路由-查看',NULL,'[]','2021-03-04 15:01:31','2021-04-02 11:01:34'),
	 ('11eb7cb772eff5acb541df28a8971999',NULL,'/system/permission/index',2,3,'["route:create"]','路由-创建',NULL,'[]','2021-03-04 15:01:31','2021-04-02 11:01:34'),
	 ('11eb7cb772f043cdb541834d01b7676f',NULL,'/system/permission/index',2,4,'["route:update"]','路由-修改',NULL,'[]','2021-03-04 15:01:31','2021-04-02 11:01:34'),
	 ('11eb7cb772f091eeb541d3f0c80e35b1',NULL,'/system/permission/index',2,5,'["route:delete"]','路由-删除',NULL,'[]','2021-03-04 15:01:31','2021-04-02 11:01:34'),
	 ('11eb7cb772f0e00fb541dfb239de3fa2',NULL,'/system/slog/index',2,2,'["system:log:get"]','日志-查看',NULL,'[]','2021-03-04 15:01:31','2021-04-02 11:01:34'),
	 ('11eb7d5c73aa848f87a4f90fa0e15eeb',NULL,'/apartment/management/index',2,2,'["apartment:get"]','公寓-查看',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34'),
	 ('11eb7d5c73b029e087a43930ad7db1d6',NULL,'/apartment/management/edit',2,3,'["apartment:create"]','公寓-创建',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34'),
	 ('11eb7d5c73b0c62187a49dd6369f3872',NULL,'/apartment/management/edit',2,4,'["apartment:update"]','公寓-修改',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34'),
	 ('11eb7d5c73b13b5287a4810151b4f00f',NULL,'/apartment/management/edit',2,5,'["apartment:delete"]','公寓-删除',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34'),
	 ('11eb7d5c73b1626387a48110d6aa7fca',NULL,'/room/management/index',2,2,'["room:get", "room:price:get"]','房间-查看',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34');
INSERT INTO yhkz_hotel.route (uuid,tenant_uuid,`path`,account_type,`type`,permissions,caption,description,remark,created_at,updated_at) VALUES
	 ('11eb7d5c73b1b08487a48544ec4f5ae7',NULL,'/room/management/edit',2,3,'["room:create"]','房间-创建',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34'),
	 ('11eb7d5c73b1d79587a4ef041fe19ab9',NULL,'/room/management/edit',2,4,'["room:update"]','房间-修改',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34'),
	 ('11eb7d5c73b1fea687a489dedd941d6c',NULL,'/room/management/edit',2,5,'["room:delete"]','房间-删除',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34'),
	 ('11eb7d5c73b3ac5787a47d9d8908f17d',NULL,'/room/price/index',2,2,'["room:price:get"]','房价-查看',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34'),
	 ('11eb7d5c73b4218887a455030dddb629',NULL,'/room/price/edit',2,3,'["room:price:create"]','房价-创建',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34'),
	 ('11eb7d5c73b46fa987a40d9753093aa3',NULL,'/room/price/edit',2,4,'["room:price:update"]','房价-修改',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34'),
	 ('11eb7d5c73b4e4da87a423ef068d013f',NULL,'/room/price/edit',2,5,'["room:price:delete"]','房价-删除',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34'),
	 ('11eb7d5c73b5a82b87a4ddca9ea026ee',NULL,'/order/management/index',2,2,'["order:get", "order:item:get"]','订单-查看',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34'),
	 ('11eb7d5c73b61d5c87a4e96b4dc9dea7',NULL,'/order/management/edit',2,3,'["order:create", "order:item:create"]','订单-创建',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34'),
	 ('11eb7d5c73b6446d87a48564664dd08f',NULL,'/order/management/edit',2,4,'["order:update", "order:item:update"]','订单-修改',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34');
INSERT INTO yhkz_hotel.route (uuid,tenant_uuid,`path`,account_type,`type`,permissions,caption,description,remark,created_at,updated_at) VALUES
	 ('11eb7d5c73b6928e87a41f4f3af42e5d',NULL,'/order/management/edit',2,5,'["order:delete", "order:item:delete"]','订单-删除',NULL,'[]','2021-03-05 10:42:40','2021-04-02 11:01:34'),
	 ('11eb7f17ca551122a07d0f5a71aa598d',NULL,'/system/dict/index',2,2,'["system:dict:get"]','字典-查看',NULL,'[]','2021-03-07 15:36:12','2021-04-02 11:01:34'),
	 ('11eb834974a53fe8ac075fba9b7a81e9',NULL,'/statistics/',2,1,'["order:get:*", "order:item:get:*"]','统计分析',NULL,'[]','2021-03-12 23:41:48','2021-04-02 11:01:34'),
	 ('11eb8bdd2f0126e0b477cf20c005cdf2',NULL,'/report/',2,1,'["order:get:*", "order:item:get:*"]','报表',NULL,'[]','2021-03-23 21:39:26','2021-04-02 11:01:34');