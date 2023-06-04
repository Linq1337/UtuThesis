INSERT INTO `preprocess1_fairdata`
SELECT distinct value, max(threat_level_id)
FROM misp WHERE (value is not null) and (threat_level_id is not null) group by value

