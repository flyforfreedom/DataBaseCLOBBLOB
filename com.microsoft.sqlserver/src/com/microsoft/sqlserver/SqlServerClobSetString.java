package com.microsoft.sqlserver;

/* SqlServerClobSetString.java
- Copyright (c) 2015, HerongYang.com, All Rights Reserved.
*/
import java.sql.*;
public class SqlServerClobSetString {
 public static void main(String [] args) {
   Connection con = null;
   try {
     com.microsoft.sqlserver.jdbc.SQLServerDataSource ds 
       = new com.microsoft.sqlserver.jdbc.SQLServerDataSource();
     ds.setServerName("192.168.4.12");
//   ds.setPortNumber(60782);
//   ds.setInstanceName("SQLEXPRESS");
   ds.setDatabaseName("trinity");
   ds.setUser("trinity");
   ds.setPassword("trinity");
   con = ds.getConnection();

//Deleting the record for re-testing
     String subject = "Test of the setString() method";
     Statement sta = con.createStatement(); 
     sta.executeUpdate("DELETE FROM Article WHERE Subject = '"
       +subject+"'");

//Inserting CLOB value with a PreparedStatement
     PreparedStatement ps = con.prepareStatement(
       "INSERT INTO Article (Subject, Body) VALUES (?,?)");
     ps.setString(1, subject);
     ps.setString(2, "He is wonderful and strange and who knows"
       +" how old he is, he thought. Never have I had such"
       +" a strong fish nor one who acted so strangely..."
       +" He cannot know that it is only one man against him,"
       +" nor that it is an old man. But what a great fish"
       +" he is and what will he bring in the market"
       +" if the flesh is good.");
     int count = ps.executeUpdate();
     ps.close();

//Retrieving CLOB value with getString()
     ResultSet res = sta.executeQuery(
       "SELECT * FROM Article WHERE Subject = '"+subject+"'");
     res.next();
     System.out.println("The inserted record: "); 
     System.out.println("   Subject = "+res.getString("Subject"));
     System.out.println("   Body = "+res.getString("Body")); 
     res.close();
     sta.close();

     con.close();
   } catch (Exception e) {
     e.printStackTrace();
   }
 }
}
