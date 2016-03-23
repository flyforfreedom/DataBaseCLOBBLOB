package oracle.jdbc.OracleDataSource;

/* OracleClobInsert.java
- Copyright (c) 2015, HerongYang.com, All Rights Reserved.
*/
import java.sql.*;
public class OracleClobInsert {
 public static void main(String [] args) {
   Connection con = null;
   try {
     oracle.jdbc.pool.OracleDataSource ds 
       = new oracle.jdbc.pool.OracleDataSource();
     ds.setDriverType("thin");
     ds.setServerName("localhost");
     ds.setPortNumber(1521);
     ds.setDatabaseName("XE");
     ds.setUser("Herong");
     ds.setPassword("TopSecret");
     con = ds.getConnection();

//Deleting the record for re-testing
     String subject = "Test on INSERT statement";
     Statement sta = con.createStatement(); 
     sta.executeUpdate("DELETE FROM Article WHERE Subject = '"
       +subject+"'");

//Inserting CLOB value with a regular insert statement
     sta = con.createStatement(); 
     int count = sta.executeUpdate(
       "INSERT INTO Article"
       +" (ID, Subject, Body)"
       +" VALUES (1, '"+subject+"', 'A BLOB (Binary Large OBject) is"
       +" a large chunk of data which is stored in a database.')");

//Retrieving CLOB value with getString()
     ResultSet res = sta.executeQuery(
       "SELECT * FROM Article WHERE Subject = '"+subject+"'");
     res.next();
     System.out.println("The inserted record: "); 
     System.out.println("   Subject = "+res.getString("Subject"));
     System.out.println("   Body = "+res.getString("Body")); 
     res.close();
     sta.close();

     con.close();
   } catch (Exception e) {
     e.printStackTrace();
   }
 }
}