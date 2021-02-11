SELECT *
FROM (
         SELECT *,
                difficulty_hard AS difficulty,
                'HARD'          AS type
         FROM `cytus2-level`
         UNION
         SELECT *,
                difficulty_chaos AS difficulty,
                'CHAOS'          AS type
         FROM `cytus2-level`
         UNION
         SELECT *,
                difficulty_glitch AS difficulty,
                'GLITCH'          AS type
         FROM `cytus2-level`
         WHERE difficulty_glitch IS NOT NULL
     ) AS t1
WHERE TRUE
  AND deleted = 0
  AND free = 1
  AND actor_id = 'PAFF'
  AND difficulty <= 1200
  AND difficulty >= 900
  AND type = 'CHAOS'
ORDER BY RAND()
LIMIT 1;
