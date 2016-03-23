package com.microsoft.sqlserver;


//STEP 1. Import required packages
//Compile c:\progra~1\java\JDK17~1.0_7\bin\javac SQL_read_decimal.java
import java.sql.*;
import java.math.BigDecimal;
import java.text.DecimalFormat; 


public class SQL_read_decimal {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  
   static final String DB_URL = "jdbc:sqlserver://192.168.4.12:1433;databaseName=trinity;selectMethod=cursor";

   //  Database credentials
   static final String USER = "trinity";
   static final String PASS = "trinity";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected database successfully...");
      
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
       String sql = "select col1, col3 from TGE_SQL";
      ResultSet rs = stmt.executeQuery(sql);
      //STEP 5: Extract data from result set
 

        DatabaseMetaData dbmd  = conn.getMetaData();
		System.out.println("JDBC driver version is " + dbmd .getDriverVersion());
		System.out.println("=====  Database info =====");  
		System.out.println("DatabaseProductName: " + dbmd.getDatabaseProductName() );  
		System.out.println("DatabaseProductVersion: " + dbmd.getDatabaseProductVersion() );  
		System.out.println("DatabaseMajorVersion: " + dbmd.getDatabaseMajorVersion() );  
		System.out.println("DatabaseMinorVersion: " + dbmd.getDatabaseMinorVersion() );  
		System.out.println("=====  Driver info =====");  
		System.out.println("DriverName: " + dbmd.getDriverName() );  
		System.out.println("DriverVersion: " + dbmd.getDriverVersion() );  
		System.out.println("DriverMajorVersion: " + dbmd.getDriverMajorVersion() );  
		System.out.println("DriverMinorVersion: " + dbmd.getDriverMinorVersion() );  
		System.out.println("=====  JDBC/DB attributes =====");  
		System.out.print("Supports getGeneratedKeys(): ");  
		if (dbmd.supportsGetGeneratedKeys() )  
			System.out.println("true");  
		else  
		System.out.println("false");  
         //Display values
    while(rs.next()){
         //Retrieve by column name
         //String newCol3;
         String col1 = rs.getString("col1");
		 //String col3 = rs.getString("col3");
		 
         System.out.println("[1]Col1: " + col1);
		 if(rs.getString("col3").contains("E")){
		 //System.out.println(", col3: " + new BigDecimal(rs.getObject(2).toString()).toPlainString());
		 System.out.println("[2] col3: " + new BigDecimal(rs.getString("col3")).toPlainString());
		 } else {
			System.out.println("[3] Col3: " + rs.getString("col3")); 
		 }
	  }
      rs.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }// do nothing
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Finish !");
}//end main
}//end JDBCExample