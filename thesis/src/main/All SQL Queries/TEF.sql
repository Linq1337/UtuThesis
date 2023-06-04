SELECT 
  category, type, 100 * Category_Type_Counter / Total_Category_Type as data_out
FROM category_type_frequency2 as b cross join total_category_type as t;