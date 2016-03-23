package oracle.jdbc.OracleDataSource;

/* OracleBlobSetBytes.java
- Copyright (c) 2015, HerongYang.com, All Rights Reserved.
*/
import java.sql.*;
public class OracleBlobSetBytes {
 public static void main(String [] args) {
   Connection con = null;
   try {
     oracle.jdbc.pool.OracleDataSource ds 
       = new oracle.jdbc.pool.OracleDataSource();
     ds.setDriverType("thin");
     ds.setServerName("192.168.4.10");
     ds.setPortNumber(1521);
     ds.setDatabaseName("orcl");
     ds.setUser("scott");
     ds.setPassword("Oracle11g");
     con = ds.getConnection();

//Deleting the record for re-testing
     String subject = "Test of the setBytes() method";
     Statement sta = con.createStatement(); 
     sta.executeUpdate("DELETE FROM Image WHERE Subject = '"
       +subject+"'");

//Inserting CLOB value with PreparedStatement.setBytes()
     PreparedStatement ps = con.prepareStatement(
       "INSERT INTO Image (ID, Subject, Body) VALUES (2,?,?)");
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