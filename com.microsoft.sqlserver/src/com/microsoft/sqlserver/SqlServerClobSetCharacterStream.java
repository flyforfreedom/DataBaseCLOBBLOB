package com.microsoft.sqlserver;

/* SqlServerClobSetCharacterStream.java
- Copyright (c) 2015, HerongYang.com, All Rights Reserved.
*/
import java.io.*;
import java.sql.*;
public class SqlServerClobSetCharacterStream {
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
     String subject = "Test of setCharacterStream() methods";
     Statement sta = con.createStatement(); 
     sta.executeUpdate("DELETE FROM Article WHERE Subject = '"
       +subject+"'");

//Inserting CLOB value with a PreparedStatement
     PreparedStatement ps = con.prepareStatement(
       "INSERT INTO Article (Subject, Body) VALUES (?,?)");
     ps.setString(1, subject);
     Reader bodyIn = 
       new FileReader("D://Trinity_work//JCS Task Force//9618//SqlServerClobSetCharacterStream.java");

//Test 1 - This will not work with JDBC 3.0 drivers
//   ps.setCharacterStream(2, bodyIn);

//Test 2 - This will not work with JDBC 3.0 drivers
//  File fileIn = new File("SqlServerClobSetCharacterStream.java");
//   ps.setCharacterStream(2, bodyIn, fileIn.length());

//Test 3 - This works with JDBC 3.0 drivers
     File fileIn = new File("SqlServerClobSetCharacterStream.java");
     ps.setCharacterStream(2, bodyIn, (int) fileIn.length());

     int count = ps.executeUpdate();
     bodyIn.close();
     ps.close();

//Retrieving CLOB value with getString()
     sta = con.createStatement(); 
     ResultSet res = sta.executeQuery("SELECT * FROM Article" 
       +" WHERE Subject = '"+subject+"'");
     res.next();
     System.out.println("The inserted record: "); 
     System.out.println("   Subject = "+res.getString("Subject"));
     System.out.println("   Body = "
       +res.getString("Body").substring(0,256)); 
     res.close();

     sta.close();
     con.close();
   } catch (Exception e) {
     e.printStackTrace();
   }
 }
}
