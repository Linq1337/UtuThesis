package com.example.demo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.demo.MYSQL.Util;

public class Probability_of_action {


    // This class retrieves the occurance of Category and Type matches which is used partly to calculate probability //
    
      // Encapsulates List and Content, Use getters to retrieve //
    private ArrayList<String> TEF_list = new ArrayList<String>();
    
    public void GetTEFData () {
        
    String sql = " SELECT distinct value, category, type, count(category) as Category_Type_Counter, value_cnt  " +
    "FROM misp WHERE(value and category IS NOT null) group by value, category, type, value_cnt ";

try (Connection conn = Util.getConnection();

// Change to PREPARED STATEMENT because of security reasons //
Statement stmt  = conn.createStatement();
ResultSet rs    = stmt.executeQuery(sql)) {

// loop RS
while (rs.next()) {
    String Type = rs.getString("type");		  
    String Category = rs.getString("category");
    double Category_Type_Counter = rs.getDouble("Category_Type_Counter");
    double Total_Value_Fixed = rs.getDouble("value_cnt");
//double Total_Value_Fixed = 485853;


// Use the getters values in TEF_Value_Data class for these or modify manually to define low, med, high //
// OR Allow users to modify the values // 
    double low = 70.8 / Total_Value_Fixed;
    double medium = 177 / Total_Value_Fixed;
    double high = 354 / Total_Value_Fixed;
    double veryhigh = 708 / Total_Value_Fixed;

// Calculating Percentage //
    double TEF_Percentage_Pre =  Category_Type_Counter / Total_Value_Fixed  ;
    double TEF_Percentage = TEF_Percentage_Pre * 100;


// Counting Category & Type and determining the frequency of threat. Include the list add to isolate in the context of the conditions //
if(TEF_Percentage <= low) { 
        String TEF_Percentage_output = String.valueOf("Low");
        TEF_list.add(Type);  
        TEF_list.add(Category);
        TEF_list.add(TEF_Percentage_output);
        
    }
if(TEF_Percentage > low && TEF_Percentage >= medium) 
    {
        String TEF_Percentage_output = String.valueOf("Medium");
        TEF_list.add(Type);  
        TEF_list.add(Category);
        TEF_list.add(TEF_Percentage_output);
    }
if(TEF_Percentage > medium) 
    {
        String TEF_Percentage_output = String.valueOf("High");
        TEF_list.add(Type);  
        TEF_list.add(Category);
        TEF_list.add(TEF_Percentage_output);
    }

//String TEF_Percentage_String = String.valueOf(TEF_Percentage);

// Add values to List //
//TEF_list.add(Type);  
//TEF_list.add(Category);
//TEF_list.add(TEF_Percentage_String);
//TEF_list.add(TEF_Percentage_output);
//TEF_list.add(Category_Type_Counter);



System.out.println(TEF_list);




// Use this code for debug purposes //
    /* System.out.println("Type:" +Type);
    System.out.println("Category:" + Category);
    System.out.println("Category_counter:" + Category_Type_Counter);
    System.out.println("TEF_Percentage:" + TEF_Percentage);  
    System.out.println("Value_Match_Counter:" + Value_CNT);


                 
 Declare variables as private and user getters and setters to achieve encapsulation  */
   
}
} catch (SQLException ex) {
System.out.println(ex.getMessage());
}







}

    public ArrayList<String> getTEF_list() {
        return TEF_list;
    }

    public void setTEF_list(ArrayList<String> tEF_list) {
        TEF_list = tEF_list;
    }



}

