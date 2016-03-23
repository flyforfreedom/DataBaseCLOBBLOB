package com.microsoft.sqlserver;

/* SqlServerBlobSetBinaryStreamError.java
- Copyright (c) 2015, HerongYang.com, All Rights Reserved.
*/
import java.io.*;
import java.sql.*;
public class SqlServerBlobSetBinaryStreamError {
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
     String subject = "Test of setBinaryStream() method error";
     Statement sta = con.createStatement(); 
     sta.executeUpdate("DELETE FROM Image WHERE Subject = '"
       +subject+"'");

//Inserting CLOB value with PreparedStatement.setBinaryStream()
     PreparedStatement ps = con.prepareStatement(
       "INSERT INTO Image (Subject, Body) VALUES (?,?)");
     ps.setString(1, subject);
     InputStream bodyIn = 
       new FileInputStream("SqlServerBlobSetBinaryStream.class");
     File fileIn = new File("SqlServerBlobSetBinaryStream.class");
     ps.setBinaryStream(2, bodyIn, (int) fileIn.length());

//Error - Closing the InputStream too early.
     bodyIn.close();

     int count = ps.executeUpdate();
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