create table `cytus2-actor`
(
    id        char(20) primary key  not null,
    nicknames longtext default '[]' not null,
    constraint `cytus2-actor_id_uindex` unique (id)
) comment 'Cytus2 角色表';

INSERT INTO `cytus2-actor` (id, nicknames)
VALUES
    ('Alice', '[]'),
    ('Amiya', '["阿米娅", "兔兔","方舟","明日方舟"]'),
    ('Aroma', '[]'),
    ('Bobo', '[]'),
    ('Cherry', '["大姐头"]'),
    ('ConneR', '["老师"]'),
    ('Crystal PuNK', '["CP"]'),
    ('Graff.J', '["长颈鹿","肠紧鹿"]'),
    ('Hans', '[]'),
    ('Ilka', '["il"]'),
    ('Ivy', '[]'),
    ('JOE', '["细菌王"]'),
    ('Kaf', '["花谱"]'),
    ('Kizuna AI', '["爱酱", "绊爱", "AI"]'),
    ('Miku', '["初音", "初音未来"]'),
    ('Neko', '["小Neko"]'),
    ('NEKO#ΦωΦ', '["Neko", "野猫"]'),
    ('Nora', '[]'),
    ('PAFF', '["泡芙"]'),
    ('Rin', '[]'),
    ('ROBO_Head', '["Robo","萝卜","萝卜头"]'),
    ('Sagar', '["斑比","小鹿斑比"]'),
    ('Vanessa', '["v姐"]'),
    ('Xenon', '["X","Simon"]');
