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

INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Aroma', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Cherry', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('ConneR', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Crystal PuNK', '["CP"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Ivy', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('JOE', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Kizuna AI', '["爱酱", "绊爱", "AI"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Miku', '["初音", "初音未来"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Neko', '["小Neko"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('NEKO#ΦωΦ', '["Neko", "野猫"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Nora', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('PAFF', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Rin', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('ROBO_Head', '["Robo","萝卜","萝卜头"]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Sagar', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Vanessa', '[]');
INSERT INTO `cytus2-actor` (id, nicknames) VALUES ('Xenon', '[]');
