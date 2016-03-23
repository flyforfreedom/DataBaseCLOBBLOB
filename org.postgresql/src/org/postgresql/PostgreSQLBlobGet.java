package org.postgresql;

import java.io.IOException;
 
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 

public class PostgreSQLBlobGet {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException { 
	String jdbcURL="jdbc:postgresql://localhost/trinity";
	String user="trinity";
	String pwd="trinity";
	Connection conn = null;
	 
	try{
		Class.forName("org.postgresql.Driver");
		conn=DriverManager.getConnection(jdbcURL, user, pwd);
		conn.setAutoCommit(false);
		System.out.println("connected");
	
	PreparedStatement ps = conn.prepareStatement("SELECT Subject FROM imageOUT where Subject = ?");
	ps.setString(1, "Test on INSERT statement");
	ResultSet rs = ps.executeQuery();
	while (rs.next()) {
		Blob bodyOut = rs.getBlob("body");
	       int length = (int) bodyOut.length();
	       System.out.println("   Body Size = "+length);
	       byte[] body = bodyOut.getBytes(1, length);
	       System.out.println("   GetBlob  Body  = "+body+"..."); //add by aggie
	       String bodyHex = bytesToHex(body, 32);
	       System.out.println("   Body in HEX = "+bodyHex+"...");
	    // use the data in some way here
	}
	rs.close();
	ps.close();
	
	}finally{
		if(conn != null)
		{
			// clean(conn);
			System.out.println("[job]connection closed!");
			conn.close();
		}
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
