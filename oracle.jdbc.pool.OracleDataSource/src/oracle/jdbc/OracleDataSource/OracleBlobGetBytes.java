package oracle.jdbc.OracleDataSource;

 
//import java.io.*;
import java.sql.*;
public class OracleBlobGetBytes {
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

//Retrieving BLOB value with getBytes()
     Statement sta = con.createStatement(); 
     ResultSet res = sta.executeQuery("SELECT * FROM Imageout"); 
     int i = 0;
     while (res.next() && i<3) {
       i++;
       System.out.println("Record ID: "+res.getInt("ID"));
       System.out.println("   Subject = "+res.getString("Subject"));
       byte[] body = res.getBytes("Body");
       String bodyHex = bytesToHex(body, 32);
       System.out.println("   Body in HEX = "+bodyHex+"...");
     }
     res.close();

     sta.close();
     con.close();
   } catch (Exception e) {
     e.printStackTrace();
   }
 }
 public static String bytesToHex(byte[] bytes, int max) {
   StringBuffer buffer = new StringBuffer();
   for (int i=0; i<bytes.length && i<max; i++) {
     buffer.append(Integer.toHexString(bytes[i] & 0xFF)); 
   }
   return buffer.toString().toUpperCase();
 }
}
