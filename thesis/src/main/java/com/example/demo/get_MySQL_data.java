package com.example.demo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.example.demo.MYSQL.Util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.sql.PreparedStatement;

public class get_MySQL_data {
   
    public void RunThis () { 
    // Change Limit for real world case, but remember to deal with new creation of a large volume of files, DB has around half a millions rows.
    String sql = " SELECT id, category, type, threat_level_id, category_type_counter, Percentage  " +
    "FROM fair_data group by id, category, type, threat_level_id, category_type_counter, Percentage LIMIT 1";

    // Use this for added security if needed //
    //    String sql = " SELECT category, type, threat_level_id, category_type_counter, Percentage  " +
   // "FROM fair_data WHERE Category = ? group by category, type, threat_level_id, category_type_counter, Percentage LIMIT 1";

try (Connection conn = Util.getConnection();
// Use PREPARED STATEMENT to prevent SQL injection //
Statement stmt  = conn.createStatement();
//PreparedStatement stmt = conn.prepareStatement(sql);
ResultSet rs    = stmt.executeQuery(sql)) {
//stmt.setString(1,"Antivirus Detection");

// loop RS
while (rs.next()) {
    String category = rs.getString("category");	
    String type = rs.getString("type");		  
    double impactfactor = rs.getDouble("Threat_level_id");
    double Value_Counter = rs.getInt("category_type_counter");
    double Category_Type_Percentage = rs.getInt("Percentage");
// Total_Value_fixed can be changed to Parameter like in other classes //

// Updated, for use with more data add a loop to +1 on datasource //    
      String oldFileName = "C:/Users/alexa/Downloads/unbbayes-4.22.18-dist/unbbayes-4.22.18/examples/bn/net/BBNModelThesis.net";
      String tmpFileName = "C:/Users/alexa/Downloads/unbbayes-4.22.18-dist/unbbayes-4.22.18/examples/bn/net/BBNModelThesisTemp.net";

      BufferedReader Reader = null;
      BufferedWriter Writer = null;
      try {
         Reader = new BufferedReader(new FileReader(oldFileName));
         Writer = new BufferedWriter(new FileWriter(tmpFileName));
         String row;
         while ((row = Reader.readLine()) != null) {

// Impact Factor Processing BEGIN Here //
// If risk is above 5 then risk will be defined as high in BBN model .NET file.
// Line.replace assumes that the original file follows the format 1.0 0.0 0.0 0.0 //
if(impactfactor >= 4)
{
    if (row.contains("potential (Threat_Capability){ data = ( 1.0 0.0 0.0 );"))
        row = row.replace("( 1.0 0.0 0.0 )", "( 1.0 0.0 0.0 )");
      //  bw.write(line+"\n");
}

// Medium Impact Factor
if(impactfactor > 1 & impactfactor <= 3)
{
    if (row.contains("potential (Threat_Capability){ data = ( 1.0 0.0 0.0 );"))
        row = row.replace("( 1.0 0.0 0.0 )", "( 0.0 1.0 0.0 )");
      //  bw.write(line+"\n");
}

// Low Impact Factor
if(impactfactor == 1)
{
    if (row.contains("potential (Threat_Capability){ data = ( 1.0 0.0 0.0 );"))
        row = row.replace("( 1.0 0.0 0.0 )", "( 0.0 0.0 1.0 )");
      //  bw.write(line+"\n");
}
// Impact Factor Processing END Here //


// Resistance_Capability Processing BEGIN Here //

// Change line.contains to change everything between (0.0 0.0 0.0) always, currently manual version
if (row.contains("potential (Resistance_Capability){ data = ( 1.0 0.0 0.0 );"))
    row = row.replace("( 1.0 0.0 0.0 )", "( 1.0 0.0 0.0 )"); 
// Resistance_Capability Processing END Here //


// Probability_of_action Processing BEGIN Here //
// Calculates the Number of Types an incident has occured before //

if(Value_Counter < 3) {
    if (row.contains("potential (Probability_of_action){ data = ( 1.0 0.0 0.0 );"))
        row = row.replace("( 1.0 0.0 0.0 )", "( 0.0 0.0 1.0 )");  
}

if(Value_Counter > 3 & Value_Counter < 10) {
    if (row.contains("potential (Probability_of_action){ data = ( 1.0 0.0 0.0 );"))
        row = row.replace("( 1.0 0.0 0.0 )", "( 0.0 1.0 0.0 )");  
    }

if(Value_Counter > 10) {
    if (row.contains("potential (Probability_of_action){ data = ( 1.0 0.0 0.0 );"))
        row = row.replace("( 1.0 0.0 0.0 )", "( 1.0 0.0 0.0 )");  
        }

// Probability_of_action Processing END Here //


// Threat_Event_Frequency through matching types and categories Processing BEGIN Here //
// Not completed in this version but can be adjusted in the same manner as alternative 1
//if (row.contains("potential (Contact_Frequency){ data = ( 1.0 0.0 0.0 );"))
//    row = row.replace("( 1.0 0.0 0.0 )", "( 1.0 0.0 0.0 )"); 

if(Category_Type_Percentage < 5) {
    if (row.contains("potential (Contact_Frequency){ data = ( 1.0 0.0 0.0 );"))
        row = row.replace("( 1.0 0.0 0.0 )", "( 1.0 0.0 0.0 )");  
}

if(Category_Type_Percentage < 0.5 & Category_Type_Percentage < 5) {
    if (row.contains("potential (Contact_Frequency){ data = ( 1.0 0.0 0.0 );"))
        row = row.replace("( 1.0 0.0 0.0 )", "( 0.0 1.0 0.0 )");  
}

if(Category_Type_Percentage < 0 & Category_Type_Percentage < 0.5 ) {
    if (row.contains("potential (Contact_Frequency){ data = ( 1.0 0.0 0.0 );"))
        row = row.replace("( 1.0 0.0 0.0 )", "( 0.0 0.0 0.1 )");  
}

    Writer.write(row+"\n");

         } 
        }     
        catch (Exception e) {
         return;
      } finally {
         try {
            if(Reader != null)
               Reader.close();
         } catch (IOException e) {
            //
         }
         try {
            if(Writer != null)
               Writer.close();
         } catch (IOException e) {
            //
         }
      }
      // Delete old file
      File oldFile = new File(oldFileName);
      oldFile.delete();

      // And reane the temporary file to the previous files name
      File newFile = new File(tmpFileName);
      newFile.renameTo(oldFile);
}
} catch (SQLException ex) {
System.out.println(ex.getMessage());
}


}


    
}
