package org.backendtestframework.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBUtil {
	
	public String dbServerName;
    public String dbUserName;
    public String dbPassword;
    public String dbPortName;
    public String dbName;
    
    public Connection con ;
    public Statement st;
    public ResultSet rs;
    
    public Connection setConncetion() {
 	        try {
 	       	Util util = new Util();
 	   	
 	       	dbServerName = util.getValueFromAppProperties("dbServer");
 	        dbUserName = util.getValueFromAppProperties("dbUserName");
 	        dbPassword = util.getValueFromAppProperties("dbPassword");
 	        dbPortName = util.getValueFromAppProperties("dbPort");
 	        dbName = util.getValueFromAppProperties("dbName");
 	        
 	       String url = "jdbc:mysql://"+dbServerName+":"+dbPortName+"/"+dbName;
 	        con = DriverManager.getConnection(url, dbUserName, dbPassword);
 	        
			} catch (SQLException e) {
				e.printStackTrace();
			}
 	        return con;
    }
    
    public String getSingleDataFromDB(Connection con, String SQL) {
    	String output = null;
    	
    	try {
        st = con.createStatement();
	    rs = st.executeQuery(SQL);
	    
 	        if (rs.next()) { //get first result
 	        	output = rs.getString(1);//Column 1
 	        }
 	       
 	    } catch (Exception ex) {
 	       ex.printStackTrace();
	    }
    	return output;
    }
    
    public  List<String> getSingleRowDataFromDB(Connection con, String SQL) {
    	List<String> dataList = null ;
    	try {
        st = con.createStatement();
	    rs = st.executeQuery(SQL);
	     
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int column_count = rsmd.getColumnCount();
	    
	   dataList = new ArrayList<String>();
	    
 	        if (rs.next()) {//get first result
 	        	for(int i=1;i<=column_count;i++) {
 	        		dataList.add(rs.getString(i));
 	        	}
 	        }
 	       
 	    } catch (SQLException ex) {
 	       ex.printStackTrace();
	    }
    	return dataList;
    }
    
    public  List<List<String>> getAllRowDataFromDB(Connection con, String SQL) {
    	List<List<String>> rowList = null ;
    	List<String> dataList = null ;
    	try {
        st = con.createStatement();
	    rs = st.executeQuery(SQL);

	    ResultSetMetaData rsmd = rs.getMetaData();
	    int column_count = rsmd.getColumnCount();
	    dataList = new ArrayList<String>();
	    rowList = new ArrayList<>();
	     while(rs.next()) {
	    	 
 	        	for(int i=1;i<column_count;i++) {
 	        		
 	        		dataList.add(rs.getString(i));
 	        	}
 	        
 	        	rowList.add(new ArrayList<>(dataList));
	     }
 	    } catch (SQLException ex) {
 	       ex.printStackTrace();
	    }
    	return rowList;
    }
    
    public void insert_Update_Delete_Data(Connection con,PreparedStatement statement) {
    	 
		try {    	 
			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Query exeucted successfully!");
			}
    	}catch (SQLException e) {
			e.printStackTrace();
		}
    }

	
	public static void main(String[] args) {
		 try {
		 DBUtil dbutil = new DBUtil();
		 Connection con = dbutil.setConncetion();
		 System.out.println(dbutil.getSingleDataFromDB(con,"SELECT Emp_Name FROM employee WHERE Emp_ID=1;"));
		 System.out.println(dbutil.getSingleRowDataFromDB(con,"SELECT * FROM employee WHERE Emp_ID=1;"));
		 System.out.println(dbutil.getAllRowDataFromDB(con,"SELECT * FROM employee;"));
 
 	 PreparedStatement statement = null;
 
 	// Inserting values into DB
	 String insertSql = "INSERT INTO employee (Emp_Name, Job_Category, Salary) VALUES (?, ?, ?)";
	 statement = con.prepareStatement(insertSql);
	 statement.setString(1, "Will");
	 statement.setString(2, "Support");
	 statement.setInt(3, 1600);
	 dbutil.insert_Update_Delete_Data(con, statement);
 
 	// Update into database
 	String updateSql = "UPDATE employee SET Emp_Name=?, Job_Category=? , Salary=? WHERE Emp_ID=?";
	statement = con.prepareStatement(updateSql);
	statement.setString(1, "Raj");
	statement.setString(2, "Associate");
	statement.setInt(3, 2000);
	statement.setInt(4, 2);
	dbutil.insert_Update_Delete_Data(con, statement);
	/*
	// Delete into database
	String sql = "DELETE FROM employee WHERE Emp_ID=?";
	 
	statement = con.prepareStatement(sql);
	statement.setInt(1, 5);
	dbutil.insert_Update_Delete_Data(con, statement); */
		 
	con.close();	 
  }
  catch (Exception e) {
	e.printStackTrace();
  }
}
}

