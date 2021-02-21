create table `cytus2-actor`
(
    id        char(20)                                  not null,
    nicknames longtext collate utf8mb4_bin default '[]' not null,
    constraint `cytus2-actor_id_uindex`
        unique (id)
)
    comment 'Cytus2 角色表';

alter table `cytus2-actor`
    add primary key (id);

INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Alice', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Aroma', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Bobo', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Cherry', '["大姐头"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('ConneR', '["老师"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Crystal PuNK', '["CP"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Graff.J', '["长颈鹿","肠紧鹿"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Hans', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Ivy', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('JOE', '["细菌王"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Kizuna AI', '["爱酱", "绊爱", "AI"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Miku', '["初音", "初音未来"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Neko', '["小Neko"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('NEKO#ΦωΦ', '["Neko", "野猫"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Nora', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('PAFF', '["泡芙"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Rin', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('ROBO_Head', '["Robo","萝卜","萝卜头"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Sagar', '["斑比","小鹿斑比"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Vanessa', '["v姐"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Xenon', '["X","Simon"]');
