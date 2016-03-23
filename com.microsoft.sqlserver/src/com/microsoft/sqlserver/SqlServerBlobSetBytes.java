package com.microsoft.sqlserver;

/* SqlServerBlobSetBytes.java
- Copyright (c) 2015, HerongYang.com, All Rights Reserved.
*/
import java.sql.*;
public class SqlServerBlobSetBytes {
 public static void main(String [] args) {
   Connection con = null;
   try {
     com.microsoft.sqlserver.jdbc.SQLServerDataSource ds 
       = new com.microsoft.sqlserver.jdbc.SQLServerDataSource();
     ds.setServerName("192.168.4.12");
//   ds.setPortNumber(60782);
 //  ds.setInstanceName("SQLEXPRESS");
   ds.setDatabaseName("trinity");
   ds.setUser("trinity");
   ds.setPassword("trinity");
     con = ds.getConnection();

//Deleting the record for re-testing
     String subject = "Test of the setBytes() method";
     Statement sta = con.createStatement(); 
     sta.executeUpdate("DELETE FROM Image WHERE Subject = '"
       +subject+"'");

//Inserting CLOB value with PreparedStatement.setBytes()
     PreparedStatement ps = con.prepareStatement(
       "INSERT INTO Image (Subject, Body) VALUES (?,?)");
     ps.setString(1, subject);
     byte[] bodyIn = {(byte)0xC9, (byte)0xCB, (byte)0xBB,
       (byte)0xCC, (byte)0xCE, (byte)0xB9,
       (byte)0xC8, (byte)0xCA, (byte)0xBC,
       (byte)0xCC, (byte)0xCE, (byte)0xB9,
       (byte)0xC9, (byte)0xCB, (byte)0xBB};
     ps.setBytes(2, bodyIn);
     int count = ps.executeUpdate();
     ps.close();

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