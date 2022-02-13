create table `cytus2-actor`
(
    id        char(20) primary key  not null,
    nicknames longtext default '[]' not null,
    constraint `cytus2-actor_id_uindex` unique (id)
) comment 'Cytus2 角色表';

INSERT INTO `cytus2-actor` (id, nicknames)
VALUES
