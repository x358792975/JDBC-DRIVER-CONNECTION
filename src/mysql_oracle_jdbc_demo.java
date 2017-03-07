import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class mysql_oracle_jdbc_demo {
	
	public mysql_oracle_jdbc_demo (){
		
	}

public String createTable(){
	String sql = "Create table if not exists myTable(email varchar(20), password varchar(10))";
	System.out.println("Table 'myTable' created");
	return sql;

}
public String insertRow(){
	String sql= "insert into user "
			  + "(name, gender)"
			+ " Values ('Sean','M' ) ";
	System.out.println("Data 'Sean M' inserted");
	return sql;
}
public String deleteRow(){
	String sql = "DELETE FROM user WHERE name = 'Sean'";
	System.out.println("Data deleted");
	return sql;
}
public String select(){
	String sql = "select name, gender from user";
	return sql;
}
public void testconnection_mysql (int hr_offset) {        
    Connection connect = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs;
    
    String userUrl="jdbc:mysql://localhost:3306/user_info";
    String userName="root";
    String userPass="cuixiang";
	
	try {
	      // This will load the MySQL driver, each DB has its own driver
	      Class.forName("com.mysql.jdbc.Driver");
	      // Setup the connection with the DB
	      connect = DriverManager
    		          .getConnection(userUrl,userName ,userPass);
	      
	      String qry1a = "SELECT CURDATE() + " + hr_offset;
	      
	      //System.out.println(currentTime(qry1a));



    //String sql="createTable()";
    //insertRow();
    //select();
    //deleteRow();

    	  
    	  preparedStatement = connect.prepareStatement(qry1a);
    	  
    	  java.sql.Statement myStymt = connect.createStatement();
    	  //myStymt.executeUpdate(insertRow());
    	  myStymt.executeUpdate(deleteRow());
    	  myStymt.executeUpdate(createTable());
    	 //myStymt.executeQuery(select());

    	 java.sql.Statement stmt = connect.createStatement();
    	 ResultSet rs2 = stmt.executeQuery("SELECT name FROM user");
    	 
    	 while (rs2.next()){
    		 String message=rs2.getString("name");
    		 System.out.println(message);
    		 
    	 }

    	  
    	  
    	  //myStymt.executeUpdate(select());

    	  
    	  // "id, uid, create_time, token for id_management.id_logtime";
    	  // Parameters start with 1

    	  
    	  ResultSet r1=preparedStatement.executeQuery();
            if (r1.next())
            {
              String nt = r1.getString(1); 
              System.out.println(hr_offset + " hour(s) ahead of the system clock of mysql at 72.229.179.55 is:" + nt);
            }
            r1.close();
            preparedStatement.close();
    	  
    	} catch (Exception e) {
    		try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	  	} finally {
		      if (preparedStatement != null) {
			        try {
						preparedStatement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			  }	      

		      if (connect != null) {
		        try {
					connect.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      }
	    }
}

public int testConnection (int hr_offset) {
    String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
    String dbURL1 = "jdbc:oracle:thin:@localhost:1521:xe";
    
    String userName1 = "system";
    String userPassword1 = "cuixiang";

    //String oracle_driver = "oracle.jdbc.driver.OracleDriver";
    
    Connection conn;
    PreparedStatement pstmt;
    ResultSet rs;

	int flag = 0;
	String newTime;
	

    try
    {    
    	Class.forName("oracle.jdbc.driver.OracleDriver");
    }
    catch (Exception e)
    {
        System.out.println(e.getMessage());
    }

    try
    {
    	
        conn = DriverManager.getConnection(dbURL1, userName1, userPassword1);
        String stmtQuery = "select sysdate + " + hr_offset + " from dual";
        pstmt = conn.prepareStatement(stmtQuery);
        //pstmt.setString(1,userName1);
        rs = pstmt.executeQuery();
        if (rs.next())
        {
          newTime = rs.getString(1); 
          System.out.println(hr_offset + " hour(s) ahead of the system clock of Oracle at sean's localhost  is:" + newTime);
        }
        rs.close();
        pstmt.close();
        
        try
        {
          conn.close();
        } 
        catch (SQLException e) {};
        
    }
    catch (SQLException e)
    {
      System.out.println(e.getMessage());
      flag = -1;
    }
            
	return flag;
}

public static void main(String[] args)
{
	try
	{
		if (args.length != 1) {
			System.out.println("Usage: java -jar Ora_DBTest.jar <number_of_hr_offset>");
			System.out.println("Success returns error level 0. Error return greater than zero.");
			System.exit(1);
		}

        /* Print a copyright. */
        System.out.println("Example for Oracle DB connection via Java");
        System.out.println("Copyright: Bon Sy");
        System.out.println("Free to use this at your own risk!");
        
    	mysql_oracle_jdbc_demo DBConnect_instance = new mysql_oracle_jdbc_demo();

       
    	if (DBConnect_instance.testConnection(Integer.parseInt(args[0])) == 0) {
            System.out.println("Successful Completion");
        } else {
            System.out.println("Oracle DB connection fail");
        }
        
    	
       DBConnect_instance.testconnection_mysql(Integer.parseInt(args[0]));
        
	} 
	catch (Exception e){
		 //probably error in input
		System.out.println("Hmmm... Looks like input error ....");
	}		
}
}
