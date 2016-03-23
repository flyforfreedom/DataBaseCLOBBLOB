package com.mysql.jdbc.jdbc2;

/* MySqlDatabaseInfo.java
- Copyright (c) 2015, HerongYang.com, All Rights Reserved.
*/
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import javax.sql.*;
public class MySqlDatabaseInfo {
	 
 public static void main(String [] args) {
   Connection con = null;
   
   try {

//Setting up the DataSource object
     com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds 
       = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
     ds.setServerName("trinity.cx3kp8guwhbp.ap-southeast-1.rds.amazonaws.com");
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
     
  

   
//Closing the connection
     con.close();
   } catch (Exception e) {
     System.err.println("Exception: "+e.getMessage());
   }
 }
}
