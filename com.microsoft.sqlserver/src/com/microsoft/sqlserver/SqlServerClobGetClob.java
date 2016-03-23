package com.microsoft.sqlserver;

/* SqlServerClobGetClob.java
- Copyright (c) 2007, HerongYang.com, All Rights Reserved.
*/
import java.io.*;
import java.sql.*;
public class SqlServerClobGetClob {
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

//Retrieving CLOB value with getClob()
     Statement sta = con.createStatement(); 
     ResultSet res = sta.executeQuery("SELECT * FROM Article"); 
     int i = 0;
     while (res.next() && i<3) {
       i++;
       //System.out.println("Record ID: "+res.getInt("ID"));
       System.out.println("   Subject = "+res.getString("Subject"));
       Clob bodyOut = res.getClob("Body");
       int length = (int) bodyOut.length();
       System.out.println("   Body Size = "+length);
       String body = bodyOut.getSubString(1, length);
       if (body.length() > 100) body = body.substring(0,100);
       System.out.println("   Body = "+body+"...");
       bodyOut.free(); // new in JDBC 4.0
     }
     res.close();

     sta.close();
     con.close();
   } catch (Exception e) {
     e.printStackTrace();
   }
 }
}