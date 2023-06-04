       INSERT INTO category_type_frequency2 (category,type, Category_Type_Counter)
       SELECT category, type, count(category) as Category_Type_Counter
       FROM (SELECT category, type FROM mastersthesis.misp)  as category_type_frequency group by category, type;



