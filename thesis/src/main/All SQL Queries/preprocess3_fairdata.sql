INSERT INTO `preprocess3_fairdata`
SELECT distinct value, COUNT(value) AS CNT  FROM misp GROUP BY value, value;