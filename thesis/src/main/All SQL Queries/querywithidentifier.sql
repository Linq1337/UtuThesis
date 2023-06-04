       INSERT INTO testtable (uuid, category,type, Category_Type_Counter)
       SELECT uuid, category, type, count(category) as Category_Type_Counter
       FROM (SELECT category, type, uuid FROM mastersthesis.misp)  as category_type_frequency group by uuid, category, type;
