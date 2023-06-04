package com.example.demo;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.example.demo.MYSQL.Util;
import com.example.demo.impact_factor;
import com.example.demo.Probability_of_action;
import java.io.File;
import java.io.FileNotFoundException;
import com.thoughtworks.xstream.*;
import java.io.FileWriter;
import java.io.IOException;

public class ThesisApplication extends Util  {

	public static void main(String[] args) throws FileNotFoundException {
	//	get_MySQL_data testobj = new get_MySQL_data();
	//	testobj.RunThis();

// Retrieving and instansiating data to object from impact_factor class to get impact_factor data
	 //	impact_factor obj = new impact_factor();
 	//	obj.impact();

// The methods used below are used to retrieve TEF data.

// Retrieving and instansiating data to object from TEF class
		//Probability_of_action obj2 = new Probability_of_action();
		//obj2.GetTEFData();

// Retrieving the Value count, which gives us the number of matching threat values from DB.

		contact_frequency obj3 = new contact_frequency();
		obj3.GetTEFValueData();



// Using XStream to serialize the objects to XML //

XStream xstream = new XStream();

// Export Impact_Factor to XML //
//xstream.alias("Category", impact_factor.class);
//xstream.alias("Type", impact_factor.class);
///xstream.alias("Impact_Rating", impact_factor.class);


//String xml = xstream.toXML(obj2.TEF_list);
//String xml = xstream.toXML(obj.getImpact_list());
String xml = xstream.toXML(obj3.getTEF_Value_list());

 
// Export to file
try {
FileWriter xmlfile = new FileWriter("Value.xml");
xmlfile.write(xml);
xmlfile.close();

}
catch (IOException e) { 
System.out.println("Error occured, please review.");
e.printStackTrace();
}

// To test output
//System.out.println(xml);
//

		


}
}





