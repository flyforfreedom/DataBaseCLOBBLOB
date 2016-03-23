package com.microsoft.sqlserver;

/* SqlServerBlobGetBinaryStream.java
- Copyright (c) 2015, HerongYang.com, All Rights Reserved.
*/
import java.io.*;
import java.sql.*;
public class SqlServerBlobGetBinaryStream {
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

//Retrieving CLOB value with getBinaryStream()
     Statement sta = con.createStatement(); 
     ResultSet res = sta.executeQuery("SELECT * FROM Image"); 
     int i = 0;
     while (res.next() && i<1) {
       i++;
       System.out.println("Record ID: "+res.getInt("ID"));
       System.out.println("   Subject = "+res.getString("Subject"));
       InputStream bodyOut = res.getBinaryStream("Body");
       String fileOut = "BlobOut_"+res.getInt("ID")+".bin";
       saveOutputStream(fileOut,bodyOut);
       bodyOut.close();
       System.out.println("   Body = (Saved in "+fileOut+")");
     }
     res.close();

     sta.close();
     con.close();
   } catch (Exception e) {
     e.printStackTrace();
   }
 }
 public static void saveOutputStream(String name, InputStream body) {
   int c;
   try {
     OutputStream f = new FileOutputStream(name);
     while ((c=body.read())>-1) {
       f.write(c);
     }
     f.close();
   } catch (Exception e) {
     System.err.println("Exception: "+e.getMessage());
     e.printStackTrace();
   }
 }
}