INSERT INTO `fair_data`
SELECT v.threat_level, v.value, c.category, c.type, value_cnt as categorytypecount
FROM preprocess1_fairdata v
    INNER JOIN preprocess2_fairdata c ON v.value = c.value
	INNER JOIN preprocess3_fairdata cnt ON cnt.value  = c.value   
ORDER BY c.category 