SELECT distinct value, category, type, count(category) as Category_Type_Counter, value_cnt 
FROM misp WHERE(value and category is not null) group by value, category, type, value_cnt 


