package com.microsoft.sqlserver;

/* SqlServerBlobInsert.java
- Copyright (c) 2015, HerongYang.com, All Rights Reserved.
*/
import java.sql.*;
public class SqlServerBlobInsert {
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
     String subject = "Test on INSERT statement";
     Statement sta = con.createStatement(); 
     sta.executeUpdate("DELETE FROM Image WHERE Subject = '"
       +subject+"'");

//Inserting CLOB value with a regular insert statement
     sta = con.createStatement(); 
     int count = sta.executeUpdate(
       "INSERT INTO Image"
       +" (Subject, Body)"
       +" VALUES ('"+subject+"'"
       +", 0xC9CBBBCCCEB9C8CABCCCCEB9C9CBBB)"); //SQL Server format
//     +", x'C9CBBBCCCEB9C8CABCCCCEB9C9CBBB')"); // MySQL format

//Retrieving BLOB value with getBytes()
     ResultSet res = sta.executeQuery(
       "SELECT * FROM Image WHERE Subject = '"+subject+"'");
     res.next();
     System.out.println("The inserted record: "); 
     System.out.println("   Subject = "+res.getString("Subject"));
     System.out.println("   Body = "
       +new String(res.getBytes("Body"))); 
      
     res.close();
     sta.close();

     con.close();
   } catch (Exception e) {
     e.printStackTrace();
   }
 }
}