drop table if exists `cr_user`;

create table `cr_user`
(
    `id`     bigint not null comment 'id',
    `mobile` varchar(11) comment '手机号',
    primary key (`id`),
    unique key `mobile_unique` (`mobile`)
) default charset = utf8mb4 comment ='会员';
