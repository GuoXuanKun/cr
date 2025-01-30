-- 用户/会员
drop table if exists `cr_user`;

create table `cr_user`
(
    `id`     bigint not null comment 'id',
    `mobile` varchar(11) comment '手机号',
    primary key (`id`),
    unique key `mobile_unique` (`mobile`)
) default charset = utf8mb4 comment ='会员';


-- 乘车人/乘客
-- 乘车人不一定是网站的会员。比如你给爷爷奶奶等买票，他们只是单纯的乘车人身份
drop table if exists `cr_passenger`;
create table `cr_passenger`
(
    `id`          bigint      not null comment 'id',
    `user_id`   bigint      not null comment '会员id',
    `name`        varchar(20) not null comment '姓名',
    `id_card`     varchar(18) not null comment '身份证号',
    `type`        char(1)     not null comment '旅客类型',
    `create_time` datetime(3) not null comment '创建时间',
    `update_time` datetime(3) not null comment '更新时间',
    primary key (`id`),
    key `index_user_id` (`user_id`)
) default charset = utf8mb4 comment ='乘车人';