INSERT INTO Value_TEF (category, type, value, cnt)
SELECT category, type, value, COUNT(value) AS cnt
FROM misp
GROUP BY category, type, value
HAVING (cnt > 0)



