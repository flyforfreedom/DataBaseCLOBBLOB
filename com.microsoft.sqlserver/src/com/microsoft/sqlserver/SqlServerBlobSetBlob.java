package com.microsoft.sqlserver;

/* SqlServerBlobSetBlob.java
- Copyright (c) 2015, HerongYang.com, All Rights Reserved.
*/
import java.io.*;
import java.sql.*;
public class SqlServerBlobSetBlob {
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

//Deleting records for re-testing
     Statement sta = con.createStatement(); 
     sta.executeUpdate(
       "DELETE FROM Image WHERE Subject LIKE 'Copy of %'");

//Creating a PreparedStatement for inserting new records
     PreparedStatement ps = con.prepareStatement(
       "INSERT INTO Image (Subject, Body) VALUES (?,?)");

//Looping though the first 3 records
     ResultSet res = sta.executeQuery(
       "SELECT * FROM Image ORDER BY ID"); 
     int i = 0;
     while (res.next() && i<3) {
       i++;
       System.out.println("Copying record ID: "+res.getInt("ID"));
       String subject = res.getString("Subject");
       Blob body = res.getBlob("Body");

//Modifying the Blob object
       byte[] chuck = {(byte)0x00, (byte)0x00, (byte)0x00,
         (byte)0x00, (byte)0x00, (byte)0x00};
       body.setBytes(1,chuck);

//Inserting a new record with setBlob()
       ps.setString(1, "Copy of "+subject);
       ps.setBlob(2,body);
       ps.executeUpdate();
     }
     ps.close();
     res.close();
       
//Checking the new records
     res = sta.executeQuery(
       "SELECT * FROM Image WHERE Subject LIKE 'Copy of %'"); 
     while (res.next()) {
       System.out.println("Record ID: "+res.getInt("ID"));
       System.out.println("   Subject = "+res.getString("Subject"));
       byte[] body = res.getBytes("Body");
       String bodyHex = bytesToHex(body, 32);
       System.out.println("   Body in HEX = "+bodyHex+"...");
     }
     res.close();

     sta.close();
     con.close();
   } catch (Exception e) {
     e.printStackTrace();
   }
 }
 public static String bytesToHex(byte[] bytes, int max) {
   StringBuffer buffer = new StringBuffer();
   for (int i=0; i<bytes.length && i<max; i++) {
     buffer.append(Integer.toHexString(bytes[i] & 0xFF)); 
   }
   return buffer.toString().toUpperCase();
 }
}