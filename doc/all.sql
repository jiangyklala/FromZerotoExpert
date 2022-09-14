drop table if exists `user`;
create table `user` (
                        `id` bigint not null comment 'id',
                        `username` varchar(50) comment '名称',
                        `password` char(32) comment '密码',
                        `gender` varchar(50) default '未知' comment '性别',
                        `age` int default -1 comment '年龄',
                        `hobby` varchar(50) default NULL comment '爱好',
                        `tel` varchar(50) default NULL comment '电话',
                        primary key (`id`),
                        unique key `username_unique` (`username`)
) engine=innodb default charset=utf8mb4 comment='用户';

