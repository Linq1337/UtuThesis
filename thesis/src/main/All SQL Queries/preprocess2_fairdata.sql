INSERT INTO `preprocess2_fairdata`
SELECT distinct value as id, category, type, count(category) as Category_Type_Counter
FROM (SELECT value, category, type FROM mastersthesis.misp WHERE(value is not null))  as category_type_frequency group by value, category, type;
       
       