package com.example.demo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.demo.MYSQL.Util;


public class contact_frequency {

  // Allow users to define risk levels // 


  // Encapsulates List and Content, Use getters to retrieve //
    private ArrayList< String> TEF_Value_list = new ArrayList<String>();

    public void GetTEFValueData () {

        String sql = " SELECT distinct value, COUNT(value) AS CNT " +
        "FROM misp GROUP BY value, value";
    
    try (Connection conn = Util.getConnection();
    
    // Change to PREPARED STATEMENT because of security reasons //
    Statement stmt  = conn.createStatement();
    ResultSet rs    = stmt.executeQuery(sql)) {
    
    // loop RS
    while (rs.next()) {
      //  String Type = rs.getString("type");		  
      //  String Category = rs.getString("category");
        String value = rs.getString("value");
        int CNT = rs.getInt("cnt");

    if(CNT > 100) {
        String CNT_String = String.valueOf("High");
     //   TEF_Value_list.add(Type);  
     //   TEF_Value_list.add(Category);  
        TEF_Value_list.add(CNT_String);
        TEF_Value_list.add(value);
    }
      if(CNT > 50 &&  CNT < 100) {
        String CNT_String = String.valueOf("Medium");
      //  TEF_Value_list.add(Type);  
      //  TEF_Value_list.add(Category);  
        TEF_Value_list.add(CNT_String);
        TEF_Value_list.add(value);
      }
    System.out.println(TEF_Value_list);
  }     
    // 
    } catch (SQLException ex) {
    System.out.println(ex.getMessage());
    }
    
    }

    public ArrayList<String> getTEF_Value_list() {
      return TEF_Value_list;
    }

    public void setTEF_Value_list(ArrayList<String> tEF_Value_list) {
      TEF_Value_list = tEF_Value_list;
    }
    
    }
         // Use for Debugging //
            //  System.out.println("test:" +Type);
            //  System.out.println("Category:" + Category);
            //  System.out.println("cnt:" + CNT);


        // Counting Category & Type occurance compared to Total_Category_Count
      //  if(CNT > 1) { 
      //      CNT = CNT / 10;
      //      System.out.println("counter" + CNT);
    
                     
 


