create table `cytus2-level`
(
    id                bigint auto_increment primary key,
    name              varchar(200)                              not null,
    actor_id          char(20)                                  null,
    difficulty_hard   int                                       not null comment 'HARD 难度定数等级 * 100',
    difficulty_chaos  int                                       not null comment 'CHAOS 难度定数等级 * 100',
    difficulty_glitch int                                       null comment 'GLITCH 难度等级 * 100; 为空则无绿谱',
    deleted           tinyint(1)                   default 0    not null comment '是否被新版删除',
    free              tinyint(1)                   default 0    not null comment '是否可以免费获得(除绿谱)',
    nicks             longtext collate utf8mb4_bin default '[]' not null,
    constraint `cytus2-level_cytus2-actor_id_fk` foreign key (actor_id) references `cytus2-actor` (id),
    constraint nicks check (json_valid(`nicks`))
) comment 'Cytus2 歌曲库';

INSERT INTO `cytus2-level` (name, actor_id, difficulty_hard, difficulty_chaos, difficulty_glitch, deleted, free, nicks)
VALUES
