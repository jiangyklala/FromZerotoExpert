drop table if exists `user`;
create table `user` (
                        `id` bigint auto_increment not null,
                        `username` varchar(50) not null default(NULL),
                        `password` char(32) not null default(NULL),
                        `email` varchar(50) default(NULL),
                        `comment` varchar(50) default(NULL),
                        primary key (`id`),
                        unique key `username_unique` (`username`)
) engine=innodb default charset=utf8mb4 comment='用户';

insert into `user` (id, username, password) values(1, 'jiang', 'jiang111');

drop table if exists `demo`;
create table `demo` (
                        `id` bigint not null comment 'id',
                        `name` varchar(50) comment '名称',
                        primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='测试';

insert into `demo` (id, name) values (1, '测试');

