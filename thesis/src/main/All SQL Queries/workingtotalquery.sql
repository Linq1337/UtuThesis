UPDATE misp
SET value_cnt = (SELECT SUM(mycount) as mycounter
FROM (SELECT DISTINCT category, type, COUNT(category) as mycount
FROM misp 
GROUP BY category, type) as t);