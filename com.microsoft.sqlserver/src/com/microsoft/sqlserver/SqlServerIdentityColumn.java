package com.microsoft.sqlserver;

/* SqlServerIdentityColumn.java
- Copyright (c) 2015, HerongYang.com, All Rights Reserved.
*/
import java.sql.*;
import javax.sql.*;
public class SqlServerIdentityColumn {
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

//Creating a database table
     Statement sta = con.createStatement(); 
     int count = sta.executeUpdate(
       "CREATE TABLE Profile ("
       + " ID INTEGER PRIMARY KEY IDENTITY,"
       + " FirstName VARCHAR(20) NOT NULL,"
       + " LastName VARCHAR(20),"
       + " Point REAL DEFAULT 0.0,"
       + " BirthDate DATETIME DEFAULT '1999-12-31',"
       + " ModTime DATETIME DEFAULT '2014-12-31 23:59:59.999',"
       + " c_time TIME DEFAULT '24:00:00.000',"
       + " y2 smallint)");
     System.out.println("Table created.");
     sta.close();

     con.close();        
   } catch (Exception e) {
     e.printStackTrace();
   }
 }
}
