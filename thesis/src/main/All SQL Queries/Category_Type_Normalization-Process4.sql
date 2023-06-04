 UPDATE fair_data
   inner join category_percentage ON (fair_data.category = category_percentage.category and fair_data.type = category_percentage.type)
   SET fair_data.Percentage = category_percentage.Percentage