package com.mysql.jdbc.jdbc2;

/* MySqlDatabaseInfo.java
- Copyright (c) 2015, HerongYang.com, All Rights Reserved.
*/
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import javax.sql.*;
public class MySqlYearTest {
	 
 public static void main(String [] args) {
   Connection con = null;
   
   try {

//Setting up the DataSource object
     com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds 
       = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
     ds.setServerName("trinity.cbcslkvnswmn.us-west-1.rds.amazonaws.com");
     //ds.setPortNumber(3306);
     ds.setDatabaseName("trinity");
     ds.setUser("trinity");
     ds.setPassword("trinity123");

//Getting a connection object
     con = ds.getConnection();
     
     
//Getting driver and database info
     DatabaseMetaData meta = con.getMetaData();
     System.out.println("Server name: " 
       + meta.getDatabaseProductName());
     System.out.println("Server version: "
       + meta.getDatabaseProductVersion());
     System.out.println("Driver name: "
       + meta.getDriverName());
     System.out.println("Driver version: "
       + meta.getDriverVersion());
     System.out.println("JDBC major version: "
       + meta.getJDBCMajorVersion());
     System.out.println("JDBC minor version: "
       + meta.getJDBCMinorVersion());
     
  // Creating a database table
     Statement sta = con.createStatement(); 
     int count = sta.executeUpdate(
       // "CREATE TABLE Profile ("
    		 "CREATE TABLE Profile2 ("
       + " ID INTEGER PRIMARY KEY AUTO_INCREMENT,"
       + " FirstName VARCHAR(20) NOT NULL,"
       + " LastName VARCHAR(20),"
       + " Point REAL DEFAULT 0.0,"
       + " BirthDate DATE DEFAULT '1988-12-31',"
       + " ModTime TIMESTAMP DEFAULT '2006-12-31 23:59:59.999',"
       //+ " c_time TIME DEFAULT '23:59:59.999',"
       //+ " ModTime TIMESTAMP DEFAULT '2006-12-31 24:00:00.000',"
       + " c_time TIME DEFAULT '24:00:00.000',"
       + " y2 YEAR(2) DEFAULT '16', y4 YEAR(4) DEFAULT '2016')");
     System.out.println("Table created.");
     sta.close();

   
//Closing the connection
     con.close();
   } catch (Exception e) {
     System.err.println("Exception: "+e.getMessage());
   }
 }
}
