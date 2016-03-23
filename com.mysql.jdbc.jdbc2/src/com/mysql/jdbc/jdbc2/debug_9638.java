package com.mysql.jdbc.jdbc2;

/* MySqlDatabaseInfo.java
- Copyright (c) 2015, HerongYang.com, All Rights Reserved.
*/
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

import javax.sql.*;
public class debug_9638 {
	 
 public static void main(String [] args) {
   Connection con = null;
   int count = 0;
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
     sta.executeUpdate(
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
     

   //insert a single row using default values
        count += sta.executeUpdate(
          "INSERT INTO Profile2"
          + " (FirstName)"
          + " VALUES ('Herong')");

   //insert a single row using provided values
        count += sta.executeUpdate(
          "INSERT INTO Profile2"
          + " (FirstName, LastName, Point, BirthDate)"
          + " VALUES ('Janet', 'Gates', 999.99, '1984-10-13')");

   //insert rows with loop with random values
        Random r = new Random();
        for (int i=0; i<10; i++) {
        	Float points = 1000*r.nextFloat();
        	String firstName = Integer.toHexString(r.nextInt(9999));
        	String lastName = Integer.toHexString(r.nextInt(999999));
          count += sta.executeUpdate(
            "INSERT INTO Profile2"
            + " (FirstName, LastName, Point,c_time,y2)"
            //+ " VALUES ('"+firstName+"', '"+lastName+"', "+points+",'23:59:59.999','2016')");
            + " VALUES ('"+firstName+"', '"+lastName+"', "+points+",'24:00:00.000','2016')");
            //+ " (FirstName, LastName, Point)"
            //+ " VALUES ('"+firstName+"', '"+lastName+"', "+points+")");
        }

   //How many rows were inserted
        System.out.println("Number of rows inserted: "+count);

   //Checking inserted rows
        ResultSet res = sta.executeQuery(
          "SELECT * FROM Profile");
        System.out.println("List of Profiles: "); 
        while (res.next()) {
           System.out.println(
             "  "+res.getInt("ID")
             + ", "+res.getString("FirstName")
             + ", "+res.getString("LastName")
             + ", "+res.getDouble("Point")
             + ", "+res.getDate("BirthDate")
             + ", "+res.getTimestamp("ModTime")
             + ", "+res.getTimestamp("c_time")
             + ", "+res.getTimestamp("y2")
             + ", "+res.getTimestamp("y4"));
        }
        res.close();

        sta.close();
        con.close();
      } catch (Exception e) {
        System.err.println("Exception: "+e.getMessage());
      }
    }
   }
