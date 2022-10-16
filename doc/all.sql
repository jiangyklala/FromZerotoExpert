drop table if exists `demo`;
create table `demo` (
                        `id` bigint not null comment 'id',
                        `name` varchar(50) comment '名称',
                        primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='测试';

insert into `demo` (id, name) values (1, '测试');



drop table if exists `user`;
create table `user` (
                        `id` bigint auto_increment not null,
                        `username` varchar(50) default(NULL),
                        `useraccount` varchar(50) not null default(NULL),
                        `password` char(32) not null default(NULL),
                        `salt` varchar(50) not null default(NULL),
                        `email` varchar(50) default(NULL),
                        `comment` varchar(50) default(NULL),
                        primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='用户';

insert into `user` (id, username, password) values(1, 'jiang', 'jiang111');


drop table if exists `disallow_word`;
create table `disallow_word` (
                                 `id` bigint auto_increment not null,
                                 `value` varchar(50) not null comment '敏感字',
                                 `type` int default(0) comment '类型',
                                 primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='敏感词库';

insert into disallow_word(value) values ('尼玛');
insert into disallow_word(value) values ('站长');
insert into disallow_word(value) values ('国家领导人');
insert into disallow_word(value) values ('操');
insert into disallow_word(value) values ('lala');


alter table user add salt varchar(225) default(NULL);
alter table user add useraccount varchar(50) default(NULL);

drop table if exists `ipuvpv_data`;
create table `ipuvpv_data` (
                        `id` bigint auto_increment not null,
                        `date` varchar(50) default(NULL),
                        `ip` varchar(50) not null default(NULL),
                        `pv` varchar(50) not null default(NULL),
                        `uv` varchar(50) not null default(NULL),
                        `comment` varchar(50) default(NULL),
                        primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='IPUVPV数据';


drop table if exists `record_log`;
create table `record_log` (
                        `op_time` bigint not null,
                        `op_ac` varchar(50) default(NULL),
                        `status` varchar(50) not null default(NULL),
                        `op_type` varchar(50) not null default(NULL),
                        `req_url` varchar(50) not null default(NULL),
                        `req_mtd` varchar(255) not null default(NULL),
                        `op_desc` varchar(50) default(NULL),
                        `err_msg` varchar(50) default(NULL),
                        `time_csm` varchar(50) not null default(NULL),
                        `op_ip` varchar(50) not null default(NULL),
                        primary key (`op_time`)
) engine=innodb default charset=utf8mb4 comment='系统操作日志';

ALTER TABLE record_log ADD INDEX idx_ac (op_ac(12), status, op_type);


