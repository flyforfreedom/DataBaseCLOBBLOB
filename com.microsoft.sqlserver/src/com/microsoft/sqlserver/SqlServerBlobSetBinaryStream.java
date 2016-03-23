package com.microsoft.sqlserver;

/* SqlServerBlobSetBinaryStream.java
- Copyright (c) 2015, HerongYang.com, All Rights Reserved.
*/
import java.io.*;
import java.sql.*;
public class SqlServerBlobSetBinaryStream {
 public static void main(String [] args) {
   Connection con = null;
   try {
     com.microsoft.sqlserver.jdbc.SQLServerDataSource ds 
       = new com.microsoft.sqlserver.jdbc.SQLServerDataSource();
     ds.setServerName("192.168.4.12");
//     ds.setPortNumber(60782);
   //  ds.setInstanceName("SQLEXPRESS");
     ds.setDatabaseName("trinity");
     ds.setUser("trinity");
     ds.setPassword("trinity");
     con = ds.getConnection();

//Deleting the record for re-testing
     String subject = "Test of setBinaryStream() methods";
     Statement sta = con.createStatement(); 
     sta.executeUpdate("DELETE FROM Image WHERE Subject = '"
       +subject+"'");

//Inserting CLOB value with a PreparedStatement
     PreparedStatement ps = con.prepareStatement(
       "INSERT INTO Image (Subject, Body) VALUES (?,?)");
     ps.setString(1, subject);
     InputStream bodyIn = 
       new FileInputStream("SqlServerBlobSetBinaryStream.class");

//Test 1 - This will not work with JDBC 3.0 drivers
//   ps.setBinaryStream(2, bodyIn);

//Test 2 - This will not work with JDBC 3.0 drivers
//   File fileIn = new File("SqlServerBlobSetBinaryStream.class");
//   ps.setBinaryStream(2, bodyIn, fileIn.length());

//Test 3 - This works with JDBC 3.0 drivers
     File fileIn = new File("SqlServerBlobSetBinaryStream.class");
     ps.setBinaryStream(2, bodyIn, (int) fileIn.length());

     int count = ps.executeUpdate();
     bodyIn.close();
     ps.close();

//Retrieving BLOB value with getBytes()
     sta = con.createStatement(); 
     ResultSet res = sta.executeQuery("SELECT * FROM Image" 
       +" WHERE Subject = '"+subject+"'");
     res.next();
     System.out.println("The inserted record: "); 
     System.out.println("   Subject = "+res.getString("Subject"));
     System.out.println("   Body = "
       +new String(res.getBytes("Body"),0,32)); 
     res.close();

     sta.close();
     con.close();
   } catch (Exception e) {
     e.printStackTrace();
   }
 }
}