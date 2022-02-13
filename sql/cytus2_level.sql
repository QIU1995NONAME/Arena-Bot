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
    ('100sec Cat Dreams', 'NEKO#ΦωΦ', 700, 1500, null, 0, 0, '[]'),
    ('3:00 a.m.', 'Vanessa', 800, 1180, null, 0, 1, '[]'),
    ('3G0', 'Ilka', 770, 1200, null, 0, 0, '[]'),
    ('99 Glooms', 'Ivy', 670, 1360, null, 0, 1, '[]'),
    ('不可解（Cytus II Edit.）', 'Kaf', 700, 1220, null, 0, 0, '[]'),
    ('彩', 'Rin', 920, 1520, null, 0, 0, '[]'),
    ('超常マイマイン', 'Graff.J', 840, 1300, null, 0, 0, '[]'),
    ('雛鳥', 'Kaf', 600, 1020, null, 0, 0, '[]'),
    ('東京Funk', 'Graff.J', 800, 1300, null, 0, 0, '[]'),
    ('都市の呼吸', 'Alice', 820, 1260, null, 0, 0, '[]'),
    ('粉骨砕身カジノゥ', 'Graff.J', 1040, 1380, null, 0, 0, '[]'),
    ('風の声', 'Aroma', 470, 1000, null, 0, 0, '[]'),
    ('光線チューニング', 'Graff.J', 730, 1280, null, 0, 0, '[]'),
    ('帰り路', 'Kaf', 770, 1100, null, 0, 0, '[]'),
    ('過去を喰らう', 'Kaf', 800, 1080, null, 0, 0, '[]'),
    ('眷戀', 'Crystal PuNK', 600, 1120, null, 0, 1, '[]'),
    ('決戦', 'Rin', 730, 1220, null, 0, 0, '[]'),
    ('黎明-REIMEI-', 'Bobo', 600, 1200, null, 0, 1, '[]'),
    ('糸', 'Kaf', 770, 1200, null, 0, 0, '[]'),
    ('魔法みたいなミュージック！', 'Miku', 840, 1280, null, 0, 0, '[]'),
    ('漂流', 'Aroma', 800, 1420, null, 0, 0, '[]'),
    ('気楽なCloudy', 'NEKO#ΦωΦ', 800, 1280, null, 0, 1, '[]'),
    ('三燈火', 'Rin', 820, 1260, null, 0, 0, '[]'),
    ('神様と羊飼い', 'Bobo', 840, 1240, null, 0, 0, '[]'),
    ('時計の部屋と精神世界', 'Graff.J', 940, 1300, null, 0, 0, '[]'),
    ('双龍飛閃-Dual Dragoon-', 'Graff.J', 920, 1500, null, 0, 1, '[]'),
    ('未確認少女進行形', 'Kaf', 730, 1100, null, 0, 0, '[]'),
    ('下水鳴動して鼠一匹', 'NEKO#ΦωΦ', 730, 1320, null, 0, 0, '[]'),
    ('響け!', 'NEKO#ΦωΦ', 770, 1300, null, 0, 1, '[]'),
    ('小悪魔×3の大脫走！？', 'NEKO#ΦωΦ', 960, 1380, null, 1, 0, '[]'),
    ('心臟と絡繰', 'Kaf', 730, 1160, null, 0, 0, '[]'),
    ('「妖怪録、我し来にけり。 」', 'Rin', 860, 1420, null, 0, 0, '[]'),
    ('夜行バスにて', 'Kaf', 700, 1140, null, 0, 0, '[]'),
    ('一啖兩啖', 'Neko', 770, 1180, null, 0, 0, '[]'),
    ('月西江', 'Miku', 500, 800, null, 0, 0, '[]'),
    ('最愛テトラグラマトン', 'Graff.J', 800, 1240, null, 0, 0, '[]'),
    ('Abduction', 'ConneR', 860, 1120, null, 0, 0, '[]'),
    ('Absolutely', 'JOE', 570, 1180, null, 0, 0, '[]'),
    ('Accelerator', 'Nora', 730, 1360, null, 0, 0, '[]'),
    ('Accelerator', 'ROBO_Head', 730, 1380, null, 0, 0, '[]'),
    ('ACID BASILISK', 'Graff.J', 960, 1520, null, 0, 0, '[]'),
    ('AIAIAI (feat. 中田ヤスタカ)', 'Kizuna AI', 900, 1160, null, 0, 0, '[]'),
    ('Alb', 'Ilka', 900, 1400, null, 0, 0, '[]'),
    ('Alexandrite', 'Ivy', 820, 1400, null, 0, 1, '[]'),
    ('ALIVE', 'Amiya', 940, 1480, null, 0, 0, '[]'),
    ('Alterna Pt.1 -Cosmogony-', 'NEKO#ΦωΦ', 470, 1180, null, 0, 1, '[]'),
    ('Amenemhat', 'Sagar', 840, 1360, null, 0, 0, '[]'),
    ('Anchor', 'Vanessa', 500, 1020, null, 0, 1, '[]'),
    ('ANiMA', 'Alice', 1180, 1580, null, 0, 0, '[]'),
    ('Anzen Na Kusuri', 'Aroma', 630, 1060, null, 0, 0, '[]'),
    ('Aphasia', 'ConneR', 730, 1280, null, 0, 0, '[]'),
    ('A Portent of the Silver Wheel', 'Sagar', 840, 1360, null, 0, 0, '[]'),
    ('Aragami', 'Hans', 1080, 1500, null, 0, 0, '[]'),
    ('Area184', 'Ivy', 880, 1180, null, 0, 1, '[]'),
    ('Arklight', 'ROBO_Head', 770, 1420, null, 0, 0, '[]'),
    ('Armaros', 'ROBO_Head', 820, 1240, null, 0, 0, '[]'),
    ('Ascension to Heaven', 'Graff.J', 840, 1420, null, 0, 1, '[]'),
    ('Ask to Wind Live Mix', 'Graff.J', 880, 1040, null, 0, 0, '[]'),
    ('AssaultMare', 'Ivy', 1060, 1420, null, 0, 1, '[]'),
    ('Asylum', 'Xenon', 730, 1240, null, 0, 0, '[]'),
    ('ATONEMENT', 'Nora', 570, 1120, null, 0, 0, '[]'),
    ('Äventyr', 'Bobo', 770, 1260, null, 0, 1, '[]'),
    ('Awakening', 'ROBO_Head', 500, 1100, null, 0, 0, '[]'),
    ('Baptism of Fire (CliqTrack remix)', 'PAFF', 700, 1200, null, 0, 1, '[]'),
    ('Bass Music', 'JOE', 880, 1340, null, 0, 0, '[]'),
    ('Bastard of Hardcore', 'Nora', 730, 1320, 1400, 0, 0, '[]'),
    ('Beautiful Lie', 'Aroma', 630, 1120, null, 0, 0, '[]'),
    ('Better than your error system', 'NEKO#ΦωΦ', 820, 1320, null, 0, 1, '[]'),
    ('Biotonic', 'Ivy', 700, 1300, null, 0, 1, '[]'),
    ('Blackest Luxury Car', 'Graff.J', 730, 1220, null, 0, 0, '[]'),
    ('Black Hole', 'Xenon', 470, 1000, null, 0, 0, '[]'),
    ('Blah!!', 'Neko', 820, 1400, null, 0, 0, '[]'),
    ('Blessing Reunion', 'Vanessa', 600, 1140, null, 0, 1, '[]'),
    ('BloodyMare', 'Ivy', 840, 1320, null, 0, 1, '[]'),
    ('Bloody Purity', 'Ivy', 880, 1440, null, 0, 0, '[]'),
    ('Blossoms for Life', 'PAFF', 700, 1220, null, 0, 1, '[]'),
    ('Blow my mind', 'NEKO#ΦωΦ', 800, 1420, null, 0, 1, '[]'),
    ('Blow My Mind (tpz Overheat Remix)', 'NEKO#ΦωΦ', 770, 1500, null, 0, 0, '[]'),
    ('Blue Star', 'Miku', 600, 1080, null, 0, 0, '[]'),
    ('BlythE', 'Graff.J', 770, 1220, null, 0, 0, '[]'),
    ('Body Talk', 'PAFF', 800, 1180, null, 0, 1, '[]'),
    ('Boiling Blood', 'Amiya', 880, 1400, null, 0, 0, '[]'),
    ('Brain Power', 'NEKO#ΦωΦ', 730, 1200, null, 0, 1, '[]'),
    ('Brave My Heart', 'Graff.J', 730, 1100, null, 0, 0, '[]'),
    ('Breaching BIOS', 'ROBO_Head', 800, 1400, null, 0, 0, '[]'),
    ('BREAK FREE', 'Graff.J', 960, 1320, null, 0, 0, '[]'),
    ('BREAK IT', 'Miku', 820, 1320, null, 0, 0, '[]'),
    ('Break The Core', 'ROBO_Head', 1000, 1360, null, 0, 0, '[]'),
    ('Break Through The Barrier', 'ROBO_Head', 840, 1360, null, 0, 0, '[]'),
    ('Bring the light', 'Aroma', 470, 900, null, 0, 0, '[]'),
    ('Bullet Waiting for Me (James Landino remix)', 'PAFF', 500, 1180, null, 0, 1, '[]'),
    ('Caliburne ～Story of the Legendary sword～', 'Graff.J', 980, 1520, null, 0, 0, '[]'),
    ('Can''t Make a Song!!', 'Miku', 730, 1160, null, 0, 0, '[]'),
    ('Capture me', 'Cherry', 700, 1160, null, 0, 0, '[]'),
    ('Capybara Kids'' Paradise', 'NEKO#ΦωΦ', 980, 1480, null, 0, 0, '[]'),
    ('Celestial Sounds (KIVΛ Remix)', 'ROBO_Head', 940, 1500, null, 0, 1, '[]'),
    ('Centimeter Johnny', 'Graff.J', 840, 1260, null, 0, 0, '[]'),
    ('Chandelier XIII', 'Crystal PuNK', 600, 1280, null, 0, 1, '[]'),
    ('change', 'Aroma', 500, 920, null, 0, 0, '[]'),
    ('Chaos and Abyss -3rd Movement-', 'Vanessa', 940, 1460, null, 0, 1, '[]'),
    ('CHAOS', 'ROBO_Head', 1120, 1480, null, 0, 1, '[]'),
    ('CHAOS //System Offline//', 'Vanessa', 840, 1300, null, 0, 1, '[]'),
    ('Childish', 'JOE', 770, 1260, null, 1, 0, '[]'),
    ('Chocolate Missile', 'Neko', 630, 1360, 1480, 0, 0, '[]'),
    ('Chosen', 'Xenon', 700, 1280, null, 0, 0, '[]'),
    ('Chrome VOX', 'NEKO#ΦωΦ', 920, 1460, 1560, 0, 1, '[]'),
    ('Circus Time', 'Graff.J', 770, 1380, null, 0, 0, '[]'),
    ('Cityscape', 'PAFF', 770, 1240, null, 0, 0, '[]'),
    ('Claim the Game', 'ROBO_Head', 700, 1200, null, 0, 1, '[]'),
    ('Climax', 'Graff.J', 980, 1460, null, 0, 0, '[]'),
    ('Code Interceptor', 'Graff.J', 800, 1300, null, 0, 0, '[]'),
    ('CODE NAME: GAMMA', 'NEKO#ΦωΦ', 880, 1480, null, 0, 1, '[]'),
    ('CODE NAME: SIGMA', 'Ivy', 920, 1460, null, 0, 0, '[]'),
    ('CODE:RED', 'Ilka', 960, 1420, null, 0, 0, '[]'),
    ('cold', 'ROBO_Head', 700, 1140, null, 0, 0, '[]'),
    ('Collide', 'Crystal PuNK', 730, 1200, null, 0, 1, '[]'),
    ('concentric circles', 'Xenon', 770, 1220, null, 0, 0, '[]'),
    ('conflict', 'Ivy', 1140, 1520, 1540, 0, 1, '[]'),
    ('Conundrum', 'Graff.J', 770, 1280, null, 0, 1, '[]'),
    ('CREDENCE', 'Cherry', 800, 1120, 1300, 0, 0, '[]'),
    ('Crimson Fate', 'Crystal PuNK', 840, 1340, null, 0, 0, '[]'),
    ('Cristalisia', 'Ivy', 630, 1280, null, 0, 0, '[]'),
    ('Curiosity killed the cat', 'Graff.J', 570, 1120, null, 0, 0, '[]'),
    ('Cybernetic', 'Miku', 800, 1360, null, 0, 0, '[]'),
    ('Dance till Dawn', 'Nora', 630, 1100, null, 0, 0, '[]'),
    ('Dark Madness', 'Crystal PuNK', 770, 1360, null, 0, 1, '[]'),
    ('Darling Staring...', 'Crystal PuNK', 770, 1360, null, 0, 0, '[]'),
    ('Dasein', 'ROBO_Head', 730, 1320, 1400, 0, 1, '[]'),
    ('Deadly Slot Game', 'ROBO_Head', 800, 1340, 1440, 0, 1, '[]'),
    ('Dead Master', 'ROBO_Head', 980, 1300, null, 0, 0, '[]'),
    ('Dead Point', 'ROBO_Head', 800, 1320, null, 0, 1, '[]'),
    ('Dead V-Code (Special Edit)', 'Graff.J', 820, 1380, null, 0, 0, '[]'),
    ('Decade', 'Miku', 630, 920, null, 0, 0, '[]'),
    ('Deep Dive', 'Crystal PuNK', 630, 1360, null, 0, 1, '[]'),
    ('Demetrius', 'ConneR', 630, 1280, null, 0, 0, '[]'),
    ('Deus Ex Machina', 'ConneR', 840, 1240, null, 0, 0, '[]'),
    ('Devilic Sphere', 'ROBO_Head', 980, 1320, null, 0, 1, '[]'),
    ('DigiGroove', 'Ivy', 800, 1340, null, 0, 0, '[]'),
    ('dimensionalize nervous breakdown (rev.flat)', 'ROBO_Head', 880, 1380, null, 0, 0, '[]'),
    ('DJ Mashiro is dead or alive', 'Neko', 940, 1420, null, 1, 0, '[]'),
    ('Doldrums', 'Sagar', 770, 1420, null, 0, 0, '[]'),
    ('DON''T LISTEN TO THIS WHILE DRIVING', 'Graff.J', 860, 1360, null, 0, 0, '[]'),
    ('DON''T STOP ROCKIN''', 'Graff.J', 840, 1280, null, 0, 0, '[]'),
    ('Dream', 'Hans', 630, 1140, null, 0, 0, '[]'),
    ('D R G', 'Ivy', 570, 1120, null, 0, 1, '[]'),
    ('Drifted Fragments', 'Ivy', 840, 1280, null, 0, 0, '[]'),
    ('Dropping Lightspeed', 'NEKO#ΦωΦ', 880, 1520, null, 0, 1, '[]'),
    ('Drop The World', 'Nora', 840, 1460, null, 0, 0, '[]'),
    ('Duality', 'Vanessa', 770, 1140, null, 0, 1, '[]'),
    ('dynamo', 'Graff.J', 800, 1500, null, 0, 1, '[]'),
    ('Effervesce', 'Crystal PuNK', 770, 1320, null, 0, 1, '[]'),
    ('El Brillo Solatario', 'Amiya', 800, 1280, null, 0, 0, '[]'),
    ('Elysian Volitation', 'Ilka', 980, 1480, null, 0, 0, '[]'),
    ('Elysium', 'Sagar', 600, 1220, null, 0, 0, '[]'),
    ('EMber', 'ROBO_Head', 730, 1220, null, 0, 1, '[]'),
    ('End of fireworks', 'Bobo', 570, 1100, null, 0, 0, '[]'),
    ('End of the Moonlight', 'Graff.J', 670, 1280, null, 0, 0, '[]'),
    ('Ephemeral', 'Hans', 700, 1120, null, 0, 0, '[]'),
    ('Eternity', 'Nora', 770, 1120, null, 0, 0, '[]'),
    ('Eutopia', 'PAFF', 670, 1120, null, 0, 0, '[]'),
    ('Evolutionary Mechanization', 'Amiya', 770, 1300, null, 0, 0, '[]'),
    ('Exoseven', 'ROBO_Head', 770, 1300, null, 0, 0, '[]'),
    ('extinguisher', 'NEKO#ΦωΦ', 800, 1100, 1240, 0, 1, '[]'),
    ('Fade Into The Darkness', 'ROBO_Head', 960, 1380, null, 0, 0, '[]'),
    ('Fading Star', 'Graff.J', 700, 1160, null, 0, 1, '[]'),
    ('Familiar Craze', 'Crystal PuNK', 880, 1320, null, 0, 0, '[]'),
    ('Favorites', 'PAFF', 470, 1100, null, 0, 0, '[]'),
    ('Fight Another Day (Andy Tunstall remix)', 'PAFF', 630, 1020, null, 0, 1, '[]'),
    ('Fighting', 'Xenon', 800, 1140, null, 0, 0, '[]'),
    ('Final Step!', 'Graff.J', 730, 1240, null, 0, 0, '[]'),
    ('Fireflies (Funk Fiction remix)', 'PAFF', 600, 960, null, 0, 1, '[]'),
    ('Firstborns', 'Bobo', 840, 1380, null, 0, 1, '[]'),
    ('Floor of Lava', 'ConneR', 880, 1600, null, 1, 0, '[]'),
    ('For You the Bellz Toll', 'NEKO#ΦωΦ', 770, 1440, null, 0, 1, '[]'),
    ('FREEDOM DiVE↓', 'Ivy', 1080, 1560, null, 0, 0, '[]'),
    ('Friction', 'Alice', 800, 1180, null, 0, 0, '[]'),
    ('FUJIN Rumble', 'Graff.J', 940, 1500, null, 0, 1, '[]'),
    ('Fur War, Pur War', 'ConneR', 820, 1520, null, 0, 0, '[]'),
    ('FUSE', 'Graff.J', 820, 1380, null, 0, 1, '[]'),
    ('future base (Prod. Yunomi)', 'Kizuna AI', 600, 1120, null, 0, 0, '[]'),
    ('Gekkouka', 'ConneR', 800, 1160, null, 0, 0, '[]'),
    ('Gigantic Saga', 'Graff.J', 670, 1220, null, 0, 1, '[]'),
    ('Glass Wall', 'Miku', 600, 1060, null, 0, 0, '[]'),
    ('Glorious Crown', 'Graff.J', 1100, 1580, null, 0, 0, '[]'),
    ('glory day', 'Graff.J', 770, 1160, null, 0, 0, '[]'),
    ('Grand Emotion', 'Nora', 770, 1240, null, 0, 0, '[]'),
    ('Gravity', 'PAFF', 700, 1280, 1380, 0, 1, '[]'),
    ('Green Hope', 'PAFF', 800, 1200, null, 0, 1, '[]'),
    ('Grimoire of Crimson', 'ROBO_Head', 820, 1100, null, 0, 1, '[]'),
    ('Hagiasmos', 'ROBO_Head', 770, 1300, null, 0, 0, '[]'),
    ('Halcyon', 'Ivy', 920, 1340, null, 0, 0, '[]'),
    ('Halloween Party', 'Ivy', 940, 1520, null, 0, 1, '[]'),
    ('Happiness Breeze', 'NEKO#ΦωΦ', 770, 1160, 1440, 0, 1, '[]'),
    ('Hard Landing', 'NEKO#ΦωΦ', 800, 1080, null, 0, 1, '[]'),
    ('Headrush', 'NEKO#ΦωΦ', 630, 1260, null, 0, 0, '[]'),
    ('Heat Ring', 'Ivy', 770, 1140, null, 0, 0, '[]'),
    ('Heliopolis Project', 'Bobo', 770, 1160, null, 0, 1, '[]'),
    ('hello, alone (Prod. MATZ)', 'Kizuna AI', 700, 940, null, 0, 0, '[]'),
    ('Hello Days', 'Graff.J', 700, 1200, null, 0, 1, '[]'),
    ('Hello, Morning (Prod. Nor)', 'Kizuna AI', 800, 1240, null, 0, 0, '[]'),
    ('Hello Pinky', 'Graff.J', 670, 1140, null, 0, 0, '[]'),
    ('Hesitant Blade', 'Graff.J', 570, 1220, null, 0, 1, '[]'),
    ('Higher and Higher', 'JOE', 770, 1360, null, 0, 0, '[]'),
    ('History DstrØyeR', 'Ilka', 1060, 1520, null, 0, 0, '[]'),
    ('Homebound Train & Moving Thoughts', 'Ivy', 500, 900, null, 1, 0, '[]'),
    ('honeykill', 'ROBO_Head', 1000, 1520, null, 0, 0, '[]'),
    ('Hop Step Adventure☆', 'Graff.J', 730, 1340, null, 0, 1, '[]'),
    ('hunted', 'Cherry', 670, 1300, null, 0, 0, '[]'),
    ('Hydra', 'NEKO#ΦωΦ', 820, 1220, 1640, 0, 0, '[]'),
    ('Hydrangea', 'JOE', 600, 1280, 1420, 0, 0, '[]'),
    ('Hyperbola', 'ROBO_Head', 800, 1260, null, 0, 1, '[]'),
    ('IBUKI', 'Bobo', 730, 1240, null, 0, 1, '[]'),
    ('I can avoid it.#φωφ', 'Neko', 800, 1320, null, 0, 0, '[]'),
    ('I hate to tell you', 'Alice', 860, 1240, null, 0, 0, '[]'),
    ('͟͝͞Ⅱ́̕', 'Vanessa', 1000, 1500, null, 0, 1, '[]'),
    ('II-V', 'Vanessa', 770, 1240, null, 0, 1, '[]'),
    ('iL', 'Ivy', 1100, 1600, null, 1, 0, '[]'),
    ('illMenate', 'PAFF', 860, 1320, null, 0, 1, '[]'),
    ('I luv U', 'ConneR', 820, 1040, null, 0, 0, '[]'),
    ('Immram Brain', 'Sagar', 730, 1320, null, 0, 0, '[]'),
    ('I''M NOT', 'Cherry', 700, 1120, null, 0, 0, '[]'),
    ('Imprint', 'Crystal PuNK', 800, 1340, null, 0, 0, '[]'),
    ('Imprinting', 'ConneR', 800, 1300, 1520, 0, 0, '[]'),
    ('Inari', 'Rin', 600, 1040, null, 0, 0, '[]'),
    ('Incyde', 'Vanessa', 840, 1540, null, 0, 1, '[]'),
    ('INSPION', 'Xenon', 980, 1320, null, 0, 0, '[]'),
    ('Inspiration', 'PAFF', 570, 1040, null, 0, 1, '[]'),
    ('Installation[表]', 'Vanessa', 430, 940, null, 0, 1, '[]'),
    ('Installation[里]', 'Vanessa', 630, 1260, null, 0, 1, '[]'),
    ('Instinct', 'ConneR', 800, 1160, null, 0, 0, '[]'),
    ('Interstellar Experience', 'Graff.J', 820, 1440, null, 0, 1, '[]'),
    ('IOLITE-SUNSTONE', 'Xenon', 730, 1180, null, 0, 0, '[]'),
    ('I Wish You Were Mine', 'PAFF', 770, 1220, null, 0, 0, '[]'),
    ('Jakarta PROGRESSION', 'Nora', 820, 1440, null, 0, 0, '[]'),
    ('Jazzy Glitch Machine', 'ROBO_Head', 820, 1380, null, 0, 0, '[]'),
    ('Juicy Gossip', 'JOE', 700, 1180, null, 0, 0, '[]'),
    ('Kaguya', 'Graff.J', 900, 1460, null, 0, 1, '[]'),
    ('KANATA', 'PAFF', 600, 900, 1180, 0, 1, '[]'),
    ('Karma', 'Xenon', 700, 1140, null, 0, 0, '[]'),
    ('Keep it up', 'NEKO#ΦωΦ', 820, 1320, null, 0, 1, '[]'),
    ('Keep the torch', 'Amiya', 670, 1160, null, 0, 0, '[]'),
    ('King of Desert', 'Bobo', 670, 1240, null, 0, 1, '[]'),
    ('La Prière', 'Graff.J', 840, 1480, null, 0, 0, '[]'),
    ('Leaving All Behind', 'Ivy', 700, 1340, null, 0, 0, '[]'),
    ('Legacy', 'Alice', 500, 920, null, 0, 0, '[]'),
    ('Legacy', 'Sagar', 770, 1340, null, 0, 0, '[]'),
    ('LEVEL4', 'Cherry', 700, 1300, null, 0, 0, '[]'),
    ('Leviathan', 'Hans', 920, 1320, null, 0, 0, '[]'),
    ('Levolution', 'Ilka', 700, 1180, null, 0, 0, '[]'),
    ('Libera Me', 'Ivy', 880, 1380, null, 0, 0, '[]'),
    ('Liberation', 'NEKO#ΦωΦ', 960, 1400, 1580, 0, 0, '[]'),
    ('LIFE is GAME', 'ROBO_Head', 800, 1220, null, 0, 0, '[]'),
    ('Lifill', 'Graff.J', 800, 1220, null, 0, 1, '[]'),
    ('Light of Buenos Aires', 'ConneR', 730, 1220, null, 0, 0, '[]'),
    ('Lights of Muse', 'Graff.J', 800, 1420, null, 0, 1, '[]'),
    ('Light up my love!!', 'PAFF', 900, 1200, null, 0, 0, '[]'),
    ('Like Asian Spirit', 'Graff.J', 730, 1440, null, 0, 1, '[]'),
    ('Lilac for Anabel', 'PAFF', 730, 1260, null, 0, 1, '[]'),
    ('Living for you (Andy Tunstall remix)', 'Cherry', 700, 1120, null, 0, 0, '[]'),
    ('Living In The One', 'Alice', 860, 1040, null, 0, 0, '[]'),
    ('Log In', 'Neko', 700, 1240, null, 0, 0, '[]'),
    ('Lost in the Nowhere', 'Hans', 860, 1540, null, 0, 0, '[]'),
    ('LOUDER MACHINE', 'NEKO#ΦωΦ', 820, 1240, null, 0, 0, '[]'),
    ('L''Ultima Cena', 'ConneR', 820, 1360, null, 0, 0, '[]'),
    ('Lunar Mare', 'Ivy', 820, 1460, null, 0, 1, '[]'),
    ('Luolimasi', 'ROBO_Head', 700, 1140, null, 0, 1, '[]'),
    ('Maboroshi', 'NEKO#ΦωΦ', 960, 1380, null, 0, 0, '[]'),
    ('Magical Toy Box', 'Graff.J', 940, 1540, null, 0, 1, '[]'),
    ('Make Me Alive', 'PAFF', 500, 960, null, 0, 0, '[]'),
    ('Make Me Burn', 'ROBO_Head', 630, 1320, null, 0, 1, '[]'),
    ('Make U Mine', 'Aroma', 570, 1140, null, 0, 0, '[]'),
    ('Malstream', 'Crystal PuNK', 700, 1280, null, 0, 0, '[]'),
    ('Mammal', 'Neko', 920, 1400, null, 0, 0, '[]'),
    ('ManiFesto', 'Amiya', 700, 1140, null, 0, 0, '[]'),
    ('Marigold', 'Alice', 940, 1540, null, 0, 0, '[]'),
    ('Mari-Temari', 'Rin', 630, 1240, null, 0, 0, '[]'),
    ('Masquerade', 'Ivy', 730, 1200, null, 0, 0, '[]'),
    ('Medusa', 'Graff.J', 820, 1340, null, 0, 0, '[]'),
    ('meet you (Prod. DÉ DÉ MOUSE)', 'Kizuna AI', 570, 1140, null, 0, 0, '[]'),
    ('melty world (Prod. TeddyLoid)', 'Kizuna AI', 920, 1340, null, 0, 0, '[]'),
    ('Midnight', 'ROBO_Head', 770, 1400, null, 0, 1, '[]'),
    ('Miku', 'Miku', 600, 1060, null, 0, 0, '[]'),
    ('Milky Way Galaxy (SIHanatsuka Remix)', 'ROBO_Head', 820, 1340, null, 0, 0, '[]'),
    ('miracle step (Prod. Nor)', 'Kizuna AI', 570, 1200, null, 0, 0, '[]'),
    ('mirai (Prod. ☆Taku Takahashi)', 'Kizuna AI', 570, 960, null, 0, 0, '[]'),
    ('More Than Diamond', 'PAFF', 570, 920, null, 0, 1, '[]'),
    ('Nautilus', 'JOE', 600, 1160, null, 0, 0, '[]'),
    ('Neon Escape', 'Aroma', 670, 1060, null, 1, 0, '[]'),
    ('New Challenger Approaching', 'Ivy', 880, 1500, null, 0, 0, '[]'),
    ('New Quest', 'Bobo', 530, 1300, null, 0, 1, '[]'),
    ('New World', 'PAFF', 570, 1000, null, 0, 1, '[]'),
    ('new world (Prod. Yunomi)', 'Kizuna AI', 700, 1040, null, 0, 0, '[]'),
    ('Nídhögg', 'Sagar', 940, 1420, null, 0, 0, '[]'),
    ('Nightmare', 'Graff.J', 960, 1560, null, 0, 0, '[]'),
    ('Nocturnal Type', 'ROBO_Head', 820, 1200, null, 0, 1, '[]'),
    ('No-Effected World', 'PAFF', 530, 960, null, 0, 1, '[]'),
    ('No One Can''t Stop Me', 'Aroma', 570, 1120, null, 0, 0, '[]'),
    ('NORDLYS', 'Bobo', 860, 1260, null, 0, 1, '[]'),
    ('Nostalgia Sonatina', 'ConneR', 820, 1200, null, 1, 0, '[]'),
    ('NRG_Tech', 'ROBO_Head', 800, 1300, null, 0, 0, '[]'),
    ('Obey', 'Graff.J', 770, 1260, null, 0, 0, '[]'),
    ('OBLIVION', 'Graff.J', 770, 1200, null, 0, 0, '[]'),
    ('Occidens', 'Ivy', 700, 1480, null, 0, 0, '[]'),
    ('Olympia', 'ConneR', 600, 1200, null, 0, 0, '[]'),
    ('One Way Love', 'NEKO#ΦωΦ', 770, 900, null, 0, 1, '[]'),
    ('Online', 'NEKO#ΦωΦ', 800, 1340, null, 0, 0, '[]'),
    ('Open the Game', 'JOE', 630, 1360, null, 0, 0, '[]'),
    ('Operation Blade', 'Amiya', 700, 1160, null, 0, 0, '[]'),
    ('Operation Pyrite', 'Amiya', 820, 1340, null, 0, 0, '[]'),
    ('Orison', 'PAFF', 880, 1340, null, 0, 0, '[]'),
    ('Oshama Scramble!', 'Graff.J', 770, 1420, null, 0, 0, '[]'),
    ('OUT OF THE MATRIX', 'Graff.J', 960, 1300, null, 0, 0, '[]'),
    ('over the reality (Prod. Avec Avec)', 'Kizuna AI', 820, 1260, null, 0, 0, '[]'),
    ('paradigm-paragramme-program', 'Ivy', 960, 1360, null, 0, 1, '[]'),
    ('Path and Period', 'Hans', 820, 1280, null, 0, 0, '[]'),
    ('PERSONA', 'Graff.J', 670, 1100, null, 0, 0, '[]'),
    ('Perspectives', 'Aroma', 630, 1040, 1300, 0, 0, '[]'),
    ('Phagy Mutation', 'Nora', 800, 1300, null, 1, 0, '[]'),
    ('Phantom', 'Graff.J', 700, 1280, null, 0, 0, '[]'),
    ('Phantom Razor', 'Xenon', 730, 1260, 1420, 0, 0, '[]'),
    ('Pink Graduation', 'Neko', 730, 1040, null, 1, 0, '[]'),
    ('PIXIE DUST', 'PAFF', 530, 1120, null, 0, 0, '[]'),
    ('Platinum', 'Hans', 470, 940, null, 0, 0, '[]'),
    ('Play The Future', 'Graff.J', 630, 1280, null, 0, 0, '[]'),
    ('popotnik ~ The Traveller of Ljubljana', 'Graff.J', 770, 1200, null, 0, 1, '[]'),
    ('Pounding Destination', 'Graff.J', 430, 1020, null, 0, 1, '[]'),
    ('PrayStation (HiTECH NINJA Remix)', 'NEKO#ΦωΦ', 820, 1380, null, 0, 0, '[]'),
    ('Prema Flowers', 'Crystal PuNK', 730, 1220, null, 0, 1, '[]'),
    ('Pressure', 'Ivy', 770, 1200, null, 0, 1, '[]'),
    ('Pure Powerstomper', 'ROBO_Head', 820, 1240, 1420, 0, 1, '[]'),
    ('Purge', 'Ivy', 800, 1260, null, 0, 1, '[]'),
    ('Qualia', 'Ivy', 1200, 1320, null, 0, 0, '[]'),
    ('Quantum Labyrinth', 'Ivy', 800, 1200, null, 0, 0, '[]'),
    ('Quinsialyn', 'Bobo', 770, 1380, null, 0, 1, '[]'),
    ('Ramen is God', 'NEKO#ΦωΦ', 980, 1460, 1640, 0, 0, '[]'),
    ('Ra', 'Vanessa', 880, 1280, null, 0, 1, '[]'),
    ('Ready to Take the Next Step', 'NEKO#ΦωΦ', 840, 1500, null, 0, 0, '[]'),
    ('Realize', 'Cherry', 840, 1220, null, 0, 0, '[]'),
    ('REBELLIA', 'Graff.J', 770, 1400, null, 0, 0, '[]'),
    ('Rebellion Trigger', 'Ilka', 1040, 1480, null, 0, 0, '[]'),
    ('REBELLIUM', 'ConneR', 920, 1460, null, 0, 0, '[]'),
    ('Rebirth', 'NEKO#ΦωΦ', 700, 1160, 1540, 0, 0, '[]'),
    ('Re:Boost', 'PAFF', 500, 1140, 1320, 0, 1, '[]'),
    ('Recall', 'PAFF', 630, 920, null, 0, 1, '[]'),
    ('Red Five', 'Ivy', 840, 1380, null, 0, 0, '[]'),
    ('Red Storm Sentiment', 'Graff.J', 500, 1100, null, 0, 1, '[]'),
    ('Re:incRnaTiØN ～夕焼ケ世界ノ決別ヲ～', 'Neko', 1000, 1600, null, 0, 0, '[]'),
    ('Rei', 'NEKO#ΦωΦ', 800, 1220, null, 0, 1, '[]'),
    ('REmorse', 'NEKO#ΦωΦ', 880, 1460, null, 0, 0, '[]'),
    ('Renegade', 'Amiya', 800, 1200, null, 0, 0, '[]'),
    ('Reset', 'Ivy', 800, 1200, null, 0, 1, '[]'),
    ('RESET MAN', 'Graff.J', 770, 1220, null, 0, 0, '[]'),
    ('Restriction', 'ROBO_Head', 670, 1080, null, 0, 1, '[]'),
    ('Resurrection', 'NEKO#ΦωΦ', 840, 1200, null, 0, 1, '[]'),
    ('Re:The END -再-', 'Ilka', 960, 1580, null, 0, 0, '[]'),
    ('RETRIEVE', 'Cherry', 800, 1160, null, 0, 0, '[]'),
    ('Return of the Lamp', 'Sagar', 840, 1360, null, 0, 0, '[]'),
    ('Return', 'Xenon', 600, 1100, null, 0, 0, '[]'),
    ('Re:VeLΔTiØN ～光道ト破壞ノ雙白翼～', 'NEKO#ΦωΦ', 940, 1440, 1620, 0, 0, '[]'),
    ('Rhuzerv', 'Hans', 900, 1480, null, 0, 0, '[]'),
    ('R.I.P.', 'Neko', 960, 1440, null, 0, 0, '[]'),
    ('Risoluto[表]', 'Vanessa', 770, 1300, null, 0, 1, '[]'),
    ('Risoluto[里]', 'Vanessa', 730, 1360, null, 0, 1, '[]'),
    ('Rosa Rubus', 'Vanessa', 600, 1140, null, 0, 1, '[]'),
    ('Ruins in the Mirage', 'Hans', 600, 1120, null, 0, 0, '[]'),
    ('Run Go Run', 'Hans', 700, 1200, null, 0, 0, '[]'),
    ('Sacrifice', 'Sagar', 700, 1380, null, 0, 0, '[]'),
    ('Saika', 'Ivy', 700, 1220, null, 0, 0, '[]'),
    ('Sairai', 'Xenon', 500, 960, null, 0, 0, '[]'),
    ('Samurai', 'Xenon', 940, 1400, null, 0, 0, '[]'),
    ('Save me Now', 'PAFF', 770, 1200, null, 0, 1, '[]'),
    ('Scenery in your eyes', 'Cherry', 630, 1000, null, 1, 0, '[]'),
    ('Sdorica The Story Unfolds', 'Graff.J', 530, 1100, null, 0, 1, '[]'),
    ('Secret Garden', 'Ivy', 700, 1100, null, 0, 1, '[]'),
    ('SECRET;WEAPON', 'PAFF', 820, 1380, null, 0, 1, '[]'),
    ('Sentimental Journey', 'Ivy', 800, 1420, null, 0, 1, '[]'),
    ('Sharing The World', 'Miku', 600, 960, null, 0, 0, '[]'),
    ('SHIRO', 'ROBO_Head', 630, 1300, null, 0, 0, '[]'),
    ('Sickest City', 'ROBO_Head', 730, 1520, null, 0, 0, '[]'),
    ('Silent Voice', 'Graff.J', 630, 1260, null, 0, 1, '[]'),
    ('Silver Lotus', 'Ilka', 670, 1140, null, 0, 0, '[]'),
    ('Sleeping Beast', 'PAFF', 670, 940, null, 0, 1, '[]'),
    ('SNIPE WHOLE', 'Graff.J', 800, 1260, null, 0, 0, '[]'),
    ('Snow Blossom', 'Bobo', 770, 1240, null, 0, 1, '[]'),
    ('So In Love', 'PAFF', 600, 1160, null, 0, 0, '[]'),
    ('So...', 'PAFF', 600, 920, null, 0, 0, '[]'),
    ('Sovereign', 'Ivy', 860, 1320, null, 0, 1, '[]'),
    ('Space Alien', 'Sagar', 920, 1380, null, 0, 0, '[]'),
    ('Speed of Light', 'Amiya', 600, 1100, null, 0, 0, '[]'),
    ('Spotlight On', 'Aroma', 730, 1200, null, 0, 0, '[]'),
    ('Spring', 'Graff.J', 900, 1340, null, 0, 1, '[]'),
    ('Standby for Action', 'JOE', 880, 1260, null, 0, 0, '[]'),
    ('Stargazer', 'Graff.J', 600, 1040, null, 0, 1, '[]'),
    ('Starlight (KIVΛ Remix)', 'Nora', 600, 1120, null, 0, 0, '[]'),
    ('Starry Summoner', 'Rin', 600, 1060, null, 0, 0, '[]'),
    ('Stewrica -Cross-', 'Graff.J', 800, 1180, null, 0, 1, '[]'),
    ('Still', 'Cherry', 600, 1120, null, 0, 0, '[]'),
    ('Still (Piano Version)', 'Crystal PuNK', 530, 940, null, 0, 1, '[]'),
    ('Stop at nothing (Andy Tunstall remix)', 'Cherry', 700, 1120, null, 0, 0, '[]'),
    ('Stranger', 'NEKO#ΦωΦ', 570, 1160, null, 0, 0, '[]'),
    ('Streetlights (ft. CassieGemini)', 'PAFF', 600, 1220, null, 0, 1, '[]'),
    ('Subconscious Mind', 'ROBO_Head', 800, 1220, null, 0, 1, '[]'),
    ('Summer Zephyr', 'Ivy', 630, 1040, null, 0, 1, '[]'),
    ('Sunday Night Blues', 'NEKO#ΦωΦ', 570, 980, null, 0, 0, '[]'),
    ('Sunset', 'Hans', 570, 960, null, 0, 0, '[]'),
    ('Sunshine Duration', 'Crystal PuNK', 730, 1220, null, 0, 1, '[]'),
    ('Super Attractor', 'PAFF', 900, 1380, null, 0, 0, '[]'),
    ('Survive', 'PAFF', 430, 1020, null, 0, 1, '[]'),
    ('sweet conflict', 'Graff.J', 730, 1200, null, 0, 0, '[]'),
    ('Symbol (PTB10 Remix)', 'ROBO_Head', 730, 1340, null, 0, 0, '[]'),
    ('Symmetry', 'Ivy', 920, 1520, null, 0, 0, '[]'),
    ('SYSTEMFEIT', 'Cherry', 730, 1260, null, 0, 0, '[]'),
    ('syūten', 'Vanessa', 530, 1120, null, 0, 1, '[]'),
    ('Take me to the Future', 'JOE', 800, 1360, null, 0, 0, '[]'),
    ('Ten Thousand Stars', 'Miku', 770, 1180, null, 0, 0, '[]'),
    ('The 89''s Momentum', 'Graff.J', 700, 1280, null, 0, 0, '[]'),
    ('The 90''s Decision', 'Graff.J', 800, 1380, null, 0, 0, '[]'),
    ('The Beautiful Moonlight', 'Alice', 700, 940, null, 0, 0, '[]'),
    ('THE BEGINNING', 'Vanessa', 330, 1200, null, 0, 1, '[]'),
    ('The breath of the soul', 'Bobo', 770, 1360, null, 0, 1, '[]'),
    ('The Cross (feat. Silvia Su)', 'Crystal PuNK', 670, 1240, null, 0, 1, '[]'),
    ('The Devil Will Pray', 'ROBO_Head', 900, 1520, null, 0, 0, '[]'),
    ('The End Years', 'Vanessa', 670, 1300, null, 0, 1, '[]'),
    ('The Grand Debate', 'Rin', 730, 1260, null, 0, 0, '[]'),
    ('The Siege', 'Rin', 840, 1260, null, 0, 0, '[]'),
    ('The Spark', 'NEKO#ΦωΦ', 800, 1200, null, 0, 1, '[]'),
    ('The Whole Rest', 'Vanessa', 730, 1180, 1340, 0, 1, '[]'),
    ('TIGER LILY', 'Graff.J', 940, 1460, null, 0, 0, '[]'),
    ('Time to Fight', 'Ivy', 920, 1420, null, 0, 0, '[]'),
    ('To Further Dream', 'Ivy', 800, 1280, null, 0, 0, '[]'),
    ('Tokiwatari', 'Ivy', 730, 1320, null, 0, 1, '[]'),
    ('TOKONOMA Spacewalk', 'NEKO#ΦωΦ', 800, 1300, null, 0, 0, '[]'),
    ('Tomb Robber', 'Bobo', 800, 1300, null, 0, 1, '[]'),
    ('tondari-hanetari', 'ConneR', 840, 1280, null, 0, 0, '[]'),
    ('To next page', 'Alice', 820, 1220, null, 0, 0, '[]'),
    ('To the Light', 'Xenon', 820, 1280, null, 0, 0, '[]'),
    ('tRinity saga', 'Bobo', 960, 1480, null, 0, 1, '[]'),
    ('Trrricksters!!', 'Graff.J', 1020, 1560, null, 0, 0, '[]'),
    ('TSUBAKI', 'Bobo', 700, 1280, null, 0, 1, '[]'),
    ('Tsukiyura', 'Graff.J', 860, 1360, null, 0, 1, '[]'),
    ('tundra', 'ROBO_Head', 860, 1260, null, 0, 0, '[]'),
    ('Tunnef''s Nightmare', 'ROBO_Head', 900, 1420, null, 0, 0, '[]'),
    ('Turnstile Jumper', 'JOE', 700, 1360, null, 0, 0, '[]'),
    ('U.A.D', 'Graff.J', 730, 1220, null, 0, 0, '[]'),
    ('Ultimate feat.放課後のあいつ', 'Xenon', 840, 1300, null, 0, 0, '[]'),
    ('Under the same sky', 'PAFF', 700, 880, null, 0, 1, '[]'),
    ('UnNOT!CED', 'NEKO#ΦωΦ', 770, 1340, null, 0, 0, '[]'),
    ('Until the Blue Moon Rises', 'Graff.J', 630, 1240, null, 0, 1, '[]'),
    ('Uranus', 'Nora', 800, 1200, null, 0, 0, '[]'),
    ('Used to be', 'Vanessa', 500, 980, 1240, 0, 1, '[]'),
    ('Utopiosphere', 'Alice', 700, 1020, null, 0, 0, '[]'),
    ('velkinta feat. Cikado & A-Tse', 'Crystal PuNK', 630, 1220, null, 0, 0, '[]'),
    ('Venus di Ujung Jari', 'Miku', 500, 760, null, 0, 0, '[]'),
    ('Victims of Will', 'Ilka', 700, 1300, null, 0, 0, '[]'),
    ('Violet', 'Xenon', 730, 1500, null, 0, 0, '[]'),
    ('ViRUS', 'ROBO_Head', 800, 1140, null, 0, 0, '[]'),
    ('VIS::CRACKED', 'Ivy', 1160, 1580, null, 0, 0, '[]'),
    ('V.', 'Ivy', 1120, 1540, null, 0, 1, '[]'),
    ('Vox Enchanted', 'Bobo', 500, 1040, null, 0, 1, '[]'),
    ('V.R.W (feat. shully)', 'Crystal PuNK', 600, 1200, null, 0, 0, '[]'),
    ('V. //System Offline//', 'Vanessa', 840, 1340, null, 0, 1, '[]'),
    ('Walnuts Walkers', 'NEKO#ΦωΦ', 840, 1360, null, 0, 0, '[]'),
    ('We''re All Gonna Die', 'Graff.J', 1160, 1560, null, 0, 0, '[]'),
    ('What''s Your PR.Ice?', 'Ivy', 770, 1240, null, 0, 0, '[]'),
    ('Whirlwind', 'Graff.J', 770, 1320, null, 0, 0, '[]'),
    ('Whisper in my Head', 'Xenon', 730, 1300, null, 0, 0, '[]'),
    ('Who Am I?', 'PAFF', 600, 1080, 1300, 0, 1, '[]'),
    ('Wicked Ceremony', 'Ivy', 860, 1400, null, 0, 0, '[]'),
    ('Winter Games', 'PAFF', 530, 1000, null, 0, 1, '[]'),
    ('XING', 'Graff.J', 800, 1220, null, 0, 0, '[]'),
    ('Xiorc', 'ConneR', 1060, 1320, null, 0, 0, '[]'),
    ('XODUS', 'Graff.J', 1020, 1560, null, 0, 0, '[]'),
    ('XYZ', 'Vanessa', 940, 1520, null, 0, 1, '[]'),
    ('YUBIKIRI-GENMAN', 'Alice', 600, 900, null, 0, 0, '[]'),
    ('Zealous Hearts (Rayark Edit)', 'NEKO#ΦωΦ', 800, 1120, null, 0, 1, '[]'),
    ('Zeus', 'ROBO_Head', 700, 1200, null, 0, 0, '[]'),
    ('βinαrΨ', 'Crystal PuNK', 730, 1240, null, 0, 1, '[]'),
    ('すゝめ☆クノイチの巻', 'Rin', 960, 1460, null, 0, 0, '[]'),
    ('そして花になる', 'Kaf', 500, 1040, null, 0, 0, '[]'),
    ('そんなに私を期待させないで', 'Graff.J', 700, 1040, null, 0, 1, '[]'),
    ('バステット (Cytus II Edit)', 'Bobo', 730, 1220, null, 0, 1, '[]'),
    ('メルの黃昏', 'Kaf', 880, 1240, null, 0, 0, '[]'),
    ('ラッキー☆オーブ (3R2 Remix)', 'Miku', 730, 1120, null, 0, 0, '[]'),
    ('ラッキー☆オーブ', 'Miku', 700, 1140, null, 0, 0, '[]'),
    ('リラ', 'Neko', 820, 1260, null, 0, 0, '[]');