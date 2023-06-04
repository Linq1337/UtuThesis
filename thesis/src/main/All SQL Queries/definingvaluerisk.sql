INSERT INTO value_risk_definition (verylow_risk, low_risk, medium_risk, high_risk)
SELECT max(cnt) * 0.1 as verylow_risk, max(cnt) * 0.25 as low_risk, max(cnt) * 0.5 as medium_risk, max(cnt) * 1 as high_risk FROM mastersthesis.value_tef;



73.5 utvändigt inne i fälgen