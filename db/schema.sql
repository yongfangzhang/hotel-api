-- yhkz_hotel.account definition

CREATE TABLE `account` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `tenant_uuid` char(32) NOT NULL COMMENT '所属租户',
  `user_uuid` char(32) NOT NULL COMMENT '所属用户',
  `account` varchar(200) NOT NULL COMMENT '登录账号',
  `type` tinyint(3) unsigned NOT NULL COMMENT '平台类型',
  `salt` varchar(100) NOT NULL COMMENT '盐值',
  `password` varchar(200) NOT NULL COMMENT '密码',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '账号状态',
  `device` varchar(200) DEFAULT NULL COMMENT '设备',
  `user_agent` text COMMENT 'UA',
  `remark` json DEFAULT NULL COMMENT '备注',
  `last_login_at` datetime DEFAULT NULL COMMENT '最后登录时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `account_tenant_typea_ccount_UN` (`tenant_uuid`,`type`,`account`) USING BTREE,
  UNIQUE KEY `account_uset_typeUN` (`user_uuid`,`type`) USING BTREE,
  KEY `account_account_IDX` (`account`) USING BTREE,
  KEY `account_state_IDX` (`state`) USING BTREE,
  KEY `account_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE,
  KEY `account_type_IDX` (`type`) USING BTREE,
  KEY `account_user_uuid_IDX` (`user_uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录账号表';


-- yhkz_hotel.apartment definition

CREATE TABLE `apartment` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `tenant_uuid` char(32) NOT NULL COMMENT '租户UUID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `short_name` varchar(20) NOT NULL COMMENT '简称',
  `area_code` char(6) NOT NULL COMMENT '县区',
  `address` varchar(100) NOT NULL COMMENT '详细地址',
  `longitude` double(10,6) NOT NULL COMMENT '经度',
  `latitude` double(10,6) NOT NULL COMMENT '维度',
  `geohash4` char(5) DEFAULT NULL COMMENT 'geohash4',
  `contactor` varchar(50) NOT NULL COMMENT '联系人',
  `contactor_mobile` varchar(20) NOT NULL COMMENT '联系人手机号',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '公寓状态',
  `sale_times` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '销售次数',
  `income` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '总收益',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已删除',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `apartment_tenant_uuid_name_IDX` (`tenant_uuid`,`name`) USING BTREE,
  KEY `apartment_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE,
  KEY `apartment_name_IDX` (`name`) USING BTREE,
  KEY `apartment_short_name_IDX` (`short_name`) USING BTREE,
  KEY `apartment_area_code_IDX` (`area_code`) USING BTREE,
  KEY `apartment_address_IDX` (`address`) USING BTREE,
  KEY `apartment_geohash4_IDX` (`geohash4`) USING BTREE,
  KEY `apartment_contactor_IDX` (`contactor`) USING BTREE,
  KEY `apartment_contactor_mobile_IDX` (`contactor_mobile`) USING BTREE,
  KEY `apartment_created_at_IDX` (`created_at`) USING BTREE,
  KEY `apartment_state_IDX` (`state`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公寓表';


-- yhkz_hotel.comm_area definition

CREATE TABLE `comm_area` (
  `code` char(6) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `city_code` char(6) NOT NULL,
  PRIMARY KEY (`code`),
  KEY `city_code` (`city_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='区表';


-- yhkz_hotel.comm_city definition

CREATE TABLE `comm_city` (
  `code` char(6) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `province_code` char(6) NOT NULL,
  PRIMARY KEY (`code`),
  KEY `province_code` (`province_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='市表';


-- yhkz_hotel.comm_province definition

CREATE TABLE `comm_province` (
  `code` char(6) NOT NULL COMMENT 'code',
  `name` varchar(50) NOT NULL COMMENT '名称',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='省表';


-- yhkz_hotel.daily_statistics definition

CREATE TABLE `daily_statistics` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `tenant_uuid` char(32) NOT NULL COMMENT '租户UUID',
  `channel` tinyint(3) unsigned NOT NULL COMMENT '渠道',
  `original_price` decimal(10,2) NOT NULL COMMENT '原始价格',
  `paid_price` decimal(10,2) NOT NULL COMMENT '支付价格',
  `deposit` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '押金',
  `deposit_refunded` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已退押金',
  `deposit_deduction` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已扣除押金',
  `product_income` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品收益',
  `sale_times` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '销售次数',
  `statistics_date` date NOT NULL COMMENT '统计日期',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  KEY `daily_statistics_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE,
  KEY `daily_statistics_channel_IDX` (`channel`) USING BTREE,
  KEY `daily_statistics_statistics_date_IDX` (`statistics_date`) USING BTREE,
  KEY `daily_statistics_created_at_IDX` (`created_at`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='每日统计表';


-- yhkz_hotel.`order` definition

CREATE TABLE `order` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `tenant_uuid` char(32) NOT NULL COMMENT '租户UUID',
  `apartment_uuid` char(32) NOT NULL COMMENT '公寓UUID',
  `user_uuid` char(32) DEFAULT NULL COMMENT '用户UUID',
  `number` char(32) NOT NULL COMMENT '订单号',
  `biz_number` varchar(255) DEFAULT NULL COMMENT '第三方订单号',
  `channel` tinyint(3) unsigned NOT NULL COMMENT '订单渠道',
  `original_price` decimal(10,2) DEFAULT NULL COMMENT '原始价格',
  `paid_price` decimal(10,2) DEFAULT NULL COMMENT '支付价格',
  `deposit` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '押金',
  `deposit_state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '押金状态',
  `deposit_deduction` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已扣押金',
  `state` tinyint(3) unsigned NOT NULL COMMENT '订单状态',
  `type` tinyint(3) unsigned NOT NULL COMMENT '订单类型',
  `user_type` tinyint(3) unsigned NOT NULL COMMENT '用户类型',
  `account_type` tinyint(3) unsigned DEFAULT NULL COMMENT '账户类型',
  `main_name` varchar(20) DEFAULT NULL COMMENT '主入住人姓名',
  `main_mobile` varchar(20) DEFAULT NULL COMMENT '主入住人手机号',
  `paid_at` datetime DEFAULT NULL COMMENT '支付时间',
  `canceled_at` datetime DEFAULT NULL COMMENT '取消时间',
  `finished_at` datetime DEFAULT NULL COMMENT '完成时间',
  `commented_at` datetime DEFAULT NULL COMMENT '评价时间',
  `operator_uuid` char(32) NOT NULL COMMENT '操作人',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_time_at` time DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `order_number_IDX` (`number`) USING BTREE,
  KEY `order_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE,
  KEY `order_apartment_uuid_IDX` (`apartment_uuid`) USING BTREE,
  KEY `order_user_uuid_IDX` (`user_uuid`) USING BTREE,
  KEY `order_from_IDX` (`channel`) USING BTREE,
  KEY `order_state_IDX` (`state`) USING BTREE,
  KEY `order_paid_at_IDX` (`paid_at`) USING BTREE,
  KEY `order_created_at_IDX` (`created_at`) USING BTREE,
  KEY `order_type_IDX` (`type`) USING BTREE,
  KEY `order_biz_number_IDX` (`biz_number`) USING BTREE,
  KEY `order_operator_uuid_IDX` (`operator_uuid`) USING BTREE,
  KEY `order_created_time_at_IDX` (`created_time_at`) USING BTREE,
  KEY `order_deposit_state_IDX` (`deposit_state`) USING BTREE,
  KEY `order_main_name_IDX` (`main_name`) USING BTREE,
  KEY `order_main_mobile_IDX` (`main_mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';


-- yhkz_hotel.product definition

CREATE TABLE `product` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `tenant_uuid` char(32) NOT NULL COMMENT '租户UUID',
  `name` varchar(255) NOT NULL COMMENT '商品名称',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  KEY `product_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE,
  KEY `product_name_IDX` (`name`) USING BTREE,
  KEY `product_created_at_IDX` (`created_at`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';


-- yhkz_hotel.resource definition

CREATE TABLE `resource` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `tenant_uuid` char(32) NOT NULL COMMENT '所属租户',
  `type` tinyint(3) unsigned NOT NULL COMMENT '资源类型',
  `file_type` tinyint(3) unsigned NOT NULL COMMENT '文件类型',
  `file_size` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '文件大小',
  `file_name` varchar(200) NOT NULL COMMENT '文件名称',
  `owner_type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '归属类型',
  `owner_uuid` char(32) NOT NULL COMMENT '归属人',
  `visit_path` varchar(200) NOT NULL COMMENT '访问路径',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  KEY `resource_type_IDX` (`type`) USING BTREE,
  KEY `resource_file_type_IDX` (`file_type`) USING BTREE,
  KEY `resource_owner_type_IDX` (`owner_type`) USING BTREE,
  KEY `resource_owner_uuid_IDX` (`owner_uuid`) USING BTREE,
  KEY `resource_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源表';


-- yhkz_hotel.`role` definition

CREATE TABLE `role` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `tenant_uuid` char(32) DEFAULT NULL COMMENT '所属租户',
  `account_type` tinyint(3) unsigned NOT NULL COMMENT '账户类型',
  `code` varchar(20) NOT NULL COMMENT '角色编码，后台用',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `role_UN` (`tenant_uuid`,`account_type`,`code`),
  KEY `role_name_IDX` (`name`) USING BTREE,
  KEY `role_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE,
  KEY `role_account_type_IDX` (`account_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';


-- yhkz_hotel.room definition

CREATE TABLE `room` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `tenant_uuid` char(32) NOT NULL COMMENT '所属租户',
  `order_item_uuid` char(32) DEFAULT NULL COMMENT '当前入住订单项',
  `apartment_uuid` char(32) NOT NULL COMMENT '公寓UUID',
  `type_uuid` char(32) NOT NULL COMMENT '房间类型UUID',
  `floor_number` varchar(10) NOT NULL COMMENT '楼号',
  `unit_number` varchar(10) NOT NULL COMMENT '单元号',
  `number` varchar(10) NOT NULL COMMENT '房间号',
  `price` decimal(10,2) DEFAULT '100.00' COMMENT '基础价格',
  `prices` json NOT NULL COMMENT '价格数组',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '房间状态',
  `sale_times` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '销售次数',
  `income` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '总收益',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已删除',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `room_apartment_floor_unit_room_IDX` (`apartment_uuid`,`floor_number`,`unit_number`,`number`) USING BTREE,
  KEY `room_floor_number_IDX` (`floor_number`) USING BTREE,
  KEY `room_unit_number_IDX` (`unit_number`) USING BTREE,
  KEY `room_number_IDX` (`number`) USING BTREE,
  KEY `room_state_IDX` (`state`) USING BTREE,
  KEY `room_created_at_IDX` (`created_at`) USING BTREE,
  KEY `room_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE,
  KEY `room_type_uuid_IDX` (`type_uuid`) USING BTREE,
  KEY `room_order_item_uuid_IDX` (`order_item_uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间表';


-- yhkz_hotel.room_price definition

CREATE TABLE `room_price` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `tenant_uuid` char(32) NOT NULL COMMENT '所属租户',
  `room_uuid` char(32) NOT NULL COMMENT '房间UUID',
  `type` tinyint(3) unsigned NOT NULL COMMENT '价格类型',
  `price` decimal(12,2) NOT NULL COMMENT '价格',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  KEY `room_price_room_uuid_IDX` (`room_uuid`) USING BTREE,
  KEY `room_price_type_IDX` (`type`) USING BTREE,
  KEY `room_price_created_at_IDX` (`created_at`) USING BTREE,
  KEY `room_price_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间价格表(Deprecated)';


-- yhkz_hotel.route definition

CREATE TABLE `route` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `tenant_uuid` char(32) DEFAULT NULL COMMENT '所属租户',
  `path` varchar(100) NOT NULL COMMENT '路由',
  `account_type` tinyint(3) unsigned NOT NULL COMMENT '账户类型',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '路由类型(CRUD)',
  `permissions` json NOT NULL COMMENT '权限列表(逗号分割)',
  `caption` varchar(100) NOT NULL COMMENT '标题',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `route_UN` (`account_type`,`path`,`type`),
  KEY `route_path_IDX` (`path`) USING BTREE,
  KEY `route_type_IDX` (`type`) USING BTREE,
  KEY `route_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE,
  KEY `route_account_type_IDX` (`account_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='路由表';


-- yhkz_hotel.system_http_log definition

CREATE TABLE `system_http_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tenant_uuid` char(32) NOT NULL COMMENT '租户UUID',
  `module` tinyint(4) NOT NULL COMMENT '模块',
  `path` varchar(512) NOT NULL COMMENT '路径',
  `headers` text COMMENT '请求header',
  `parameter_map` text COMMENT '请求query参数',
  `method` varchar(32) NOT NULL COMMENT '方法',
  `duration` bigint(20) NOT NULL COMMENT '时长',
  `time` datetime NOT NULL COMMENT '时间',
  `status` int(11) NOT NULL COMMENT '状态',
  `request_body` mediumtext COMMENT '请求body',
  `response_body` mediumtext COMMENT '响应body',
  `ip` varchar(20) DEFAULT NULL COMMENT 'IP',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `system_http_log_id_index` (`id`) USING BTREE,
  KEY `system_http_log_method_index` (`method`) USING BTREE,
  KEY `system_http_log_module_index` (`module`) USING BTREE,
  KEY `system_http_log_status_index` (`status`) USING BTREE,
  KEY `system_http_log_tenant_uuid_index` (`tenant_uuid`) USING BTREE,
  KEY `system_http_log_time_idx` (`time`) USING BTREE,
  KEY `system_http_log_created_at_IDX` (`created_at`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网络日志';


-- yhkz_hotel.system_log definition

CREATE TABLE `system_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tenant_uuid` char(32) DEFAULT NULL COMMENT '租户UUID',
  `account_uuid` char(32) DEFAULT NULL COMMENT '账号UUID',
  `account_name` varchar(100) DEFAULT NULL COMMENT '账号名称',
  `account_type` tinyint(3) unsigned DEFAULT NULL COMMENT '账号类型',
  `level` tinyint(1) NOT NULL DEFAULT '3' COMMENT '级别',
  `target` tinyint(4) DEFAULT NULL COMMENT '操作目标',
  `type` tinyint(4) DEFAULT NULL COMMENT '操作类型',
  `linked` char(32) DEFAULT NULL COMMENT '关联UUID',
  `ip` varchar(20) NOT NULL COMMENT 'IP',
  `content` text NOT NULL COMMENT '内容',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `system_log_account_type_IDX` (`account_type`) USING BTREE,
  KEY `system_log_account_uuid_IDX` (`account_uuid`) USING BTREE,
  KEY `system_log_created_at_IDX` (`created_at`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3094 DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';


-- yhkz_hotel.tenant definition

CREATE TABLE `tenant` (
  `uuid` char(32) NOT NULL COMMENT '租户UUID',
  `tenant_uuid` char(32) NOT NULL COMMENT '租户UUID(后端好处理interceptor)',
  `name` varchar(50) NOT NULL COMMENT '租户名称',
  `legal_person` varchar(50) NOT NULL COMMENT '法人',
  `contactor` varchar(50) NOT NULL COMMENT '联系人',
  `contactor_mobile` varchar(20) NOT NULL COMMENT '联系人手机号',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已删除',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `tenant_extend_contactor_mobile_IDX` (`contactor_mobile`) USING BTREE,
  UNIQUE KEY `tenant_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE,
  KEY `tenant_name_IDX` (`name`) USING BTREE,
  KEY `tenant_contactor_IDX` (`contactor`) USING BTREE,
  KEY `tenant_legal_person_IDX` (`legal_person`) USING BTREE,
  KEY `tenant_created_at_IDX` (`created_at`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户表';


-- yhkz_hotel.`user` definition

CREATE TABLE `user` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `tenant_uuid` char(32) NOT NULL COMMENT '所属租户',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `gender` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '性别',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `wx_uuid` char(32) DEFAULT NULL COMMENT '微信UUID',
  `channel` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '获客渠道',
  `state` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '用户状态',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `user_mobile_IDX` (`mobile`) USING BTREE,
  UNIQUE KEY `user_wx_uuid_IDX` (`wx_uuid`) USING BTREE,
  KEY `user_name_IDX` (`name`) USING BTREE,
  KEY `user_gender_IDX` (`gender`) USING BTREE,
  KEY `user_state_IDX` (`state`) USING BTREE,
  KEY `user_channel_IDX` (`channel`) USING BTREE,
  KEY `user_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';


-- yhkz_hotel.wx_user_info definition

CREATE TABLE `wx_user_info` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `tenant_uuid` char(32) NOT NULL COMMENT '所属租户',
  `open_id` varchar(100) NOT NULL COMMENT '微信OpenId',
  `union_id` varchar(100) DEFAULT NULL COMMENT '微信UUID',
  `avatar_url` varchar(255) NOT NULL COMMENT '微信头像URL',
  `nickname` varchar(100) NOT NULL COMMENT '微信昵称',
  `gender` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '性别',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `country` varchar(20) DEFAULT NULL COMMENT '国家',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `language` varchar(20) DEFAULT NULL COMMENT '语言',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `wx_user_info_UN` (`open_id`),
  UNIQUE KEY `wx_user_info_union_id_UN` (`union_id`) USING BTREE,
  KEY `wx_user_info_created_at_IDX` (`created_at`) USING BTREE,
  KEY `wx_user_info_gender_IDX` (`gender`) USING BTREE,
  KEY `wx_user_info_mobile_IDX` (`mobile`) USING BTREE,
  KEY `wx_user_info_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信绑定的用户信息';


-- yhkz_hotel.account_role definition

CREATE TABLE `account_role` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `tenant_uuid` char(32) NOT NULL COMMENT '所属租户',
  `account_uuid` char(32) NOT NULL COMMENT '账户UUID',
  `role_uuid` char(32) NOT NULL COMMENT '角色UUID',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  KEY `account_role_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE,
  KEY `account_role_FK` (`role_uuid`),
  KEY `account_role_FK_1` (`account_uuid`),
  CONSTRAINT `account_role_FK` FOREIGN KEY (`role_uuid`) REFERENCES `role` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户角色表';


-- yhkz_hotel.order_item definition

CREATE TABLE `order_item` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `order_uuid` char(32) NOT NULL COMMENT '订单UUID',
  `tenant_uuid` char(32) NOT NULL COMMENT '租户UUID',
  `apartment_uuid` char(32) NOT NULL COMMENT '公寓UUID',
  `room_uuid` char(32) NOT NULL COMMENT '分配房间UUID',
  `name` varchar(20) NOT NULL COMMENT '入住人姓名',
  `mobile` varchar(20) NOT NULL COMMENT '入住人手机号',
  `price_type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '价格类型',
  `original_price` decimal(10,2) NOT NULL COMMENT '原始价格',
  `paid_price` decimal(10,2) NOT NULL COMMENT '支付价格',
  `state` tinyint(3) unsigned NOT NULL COMMENT '订单状态',
  `lodging_type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '入住类型',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_time_at` time DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `live_at` datetime NOT NULL COMMENT '入住时间',
  `leave_at` datetime NOT NULL COMMENT '离店时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `order_item_UN` (`order_uuid`,`apartment_uuid`,`room_uuid`),
  KEY `order_item_FK` (`order_uuid`),
  KEY `order_item_FK_1` (`room_uuid`),
  KEY `order_item_FK_2` (`apartment_uuid`),
  KEY `order_item_FK_3` (`tenant_uuid`),
  KEY `order_item_created_time_at_IDX` (`created_time_at`) USING BTREE,
  KEY `order_item_name_IDX` (`name`) USING BTREE,
  KEY `order_item_mobile_IDX` (`mobile`) USING BTREE,
  CONSTRAINT `order_item_FK` FOREIGN KEY (`order_uuid`) REFERENCES `order` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详细表';


-- yhkz_hotel.order_product definition

CREATE TABLE `order_product` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `order_uuid` char(32) NOT NULL COMMENT '订单UUID',
  `tenant_uuid` char(32) NOT NULL COMMENT '租户UUID',
  `product_uuid` char(32) DEFAULT NULL COMMENT '商品UUID',
  `product_name` varchar(255) NOT NULL COMMENT '商品名称',
  `product_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `product_count` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '商品数量',
  `total_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品总价',
  `paid_by_deposit` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否押金支付',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  KEY `order_product_FK` (`order_uuid`),
  KEY `order_product_FK_1` (`product_uuid`),
  KEY `order_product_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE,
  KEY `order_product_product_name_IDX` (`product_name`) USING BTREE,
  KEY `order_product_created_at_IDX` (`created_at`) USING BTREE,
  CONSTRAINT `order_product_FK` FOREIGN KEY (`order_uuid`) REFERENCES `order` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_product_FK_1` FOREIGN KEY (`product_uuid`) REFERENCES `product` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单商品表';


-- yhkz_hotel.role_route definition

CREATE TABLE `role_route` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `tenant_uuid` char(32) DEFAULT NULL COMMENT '所属租户',
  `role_uuid` char(32) NOT NULL COMMENT '角色ID',
  `route_uuid` char(32) NOT NULL COMMENT '路由ID',
  `remark` json DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `role_route_UN` (`role_uuid`,`route_uuid`),
  KEY `role_route_FK_1` (`route_uuid`),
  KEY `role_route_tenant_uuid_IDX` (`tenant_uuid`) USING BTREE,
  CONSTRAINT `role_route_FK` FOREIGN KEY (`role_uuid`) REFERENCES `role` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_route_FK_1` FOREIGN KEY (`route_uuid`) REFERENCES `route` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色路由表';


-- yhkz_hotel.system_dict definition

CREATE TABLE `system_dict` (
  `uuid` char(32) NOT NULL COMMENT 'UUID',
  `parent_uuid` char(32) DEFAULT NULL COMMENT '父节点',
  `tenant_uuid` char(32) DEFAULT NULL COMMENT '租户UUID',
  `code` varchar(100) NOT NULL COMMENT '编码',
  `type` tinyint(4) NOT NULL,
  `name` varchar(120) NOT NULL,
  `value` varchar(255) NOT NULL,
  `sequence` tinyint(3) unsigned NOT NULL DEFAULT '200' COMMENT '顺序',
  `extend_field` json DEFAULT NULL COMMENT '扩展字段',
  `description` varchar(250) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` json DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`uuid`),
  KEY `system_dict_FK` (`parent_uuid`),
  KEY `system_dict_code_IDX` (`code`) USING BTREE,
  KEY `system_dict_type_IDX` (`type`) USING BTREE,
  KEY `system_dict_name_IDX` (`name`) USING BTREE,
  KEY `system_dict_value_IDX` (`value`) USING BTREE,
  KEY `system_dict_sequence_IDX` (`sequence`) USING BTREE,
  CONSTRAINT `system_dict_FK` FOREIGN KEY (`parent_uuid`) REFERENCES `system_dict` (`uuid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统字典表';
