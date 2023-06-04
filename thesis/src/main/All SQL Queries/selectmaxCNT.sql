INSERT INTO value_tef (verylow_risk, low_risk, medium_risk, high_risk)
SELECT max(cnt) * 0.1 as verylow_risk, max(cnt) * 0.25 as low_risk, max(cnt) * 0.5 as medium_risk, max(cnt) * 1 as max_risk FROM mastersthesis.value_tef;

