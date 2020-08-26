package servlets;

import java.beans.PropertyVetoException;
import net.spy.memcached.MemcachedClient; 

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import jdbc.mysqlConn;

/**
 * Servlet implementation class ycsb
 */
@WebServlet("/ycsb")
public class ycsb extends HttpServlet {
	private static final long serialVersionUID = 1L;
    boolean setIP = false;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ycsb() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @throws SQLException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    public void executeInsertSQL(PrintWriter out, int userId,String f1, String f2, String f3, String f4,String f5, String f6, String f7,String f8, String f9, String f10) throws SQLException,PropertyVetoException,IOException
    {
    	ServletPrinter sp=null;
    	 //ResultSet rs = null;
         Connection myConn=  null;
         //Statement st=null;
         try {
 			 myConn = DataSource.getInstance().getConnection();
 			 //st = myConn.createStatement();
 		} catch (Exception e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
          sp = new ServletPrinter();
         try {
        	 String values="("+userId+","+f1+","+f2+","+f3+","+f4+","+f5+","+f6+","+f7+","+f8+","+f9+","+f10+","+"1"+")";
        	 String sql="insert into usertable values(?,?,?,?,?,?,?,?,?,?,?,?)";
        	 PreparedStatement preparedStatement = myConn.prepareStatement(sql);
        	 
        	 preparedStatement.setInt(1, userId);
        	 preparedStatement.setString(2, f1);
        	 preparedStatement.setString(3, f2);
        	 preparedStatement.setString(4, f3);
        	 preparedStatement.setString(5, f4);
        	 preparedStatement.setString(6, f5);
        	 preparedStatement.setString(7, f6);
        	 preparedStatement.setString(8, f7);
        	 preparedStatement.setString(9, f8);
        	 preparedStatement.setString(10, f9);
        	 preparedStatement.setString(11, f9);
        	 preparedStatement.setString(12, "1");
        	 
        	 out.println("<h2>Request = Insert, UserKey="+preparedStatement.toString()+"</h2>");
        	 
        	 preparedStatement.executeUpdate();
 
        	 
        	 out.println(sql);
        	 System.out.println(sql);
 
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
         
         
 		/*try {
 			while(rs.next())  
 				sp.printUserTable(rs, out);
 				//System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
 				
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}  */
 		
 		//rs.close();
 		//st.close();
 		myConn.close();

    }
    
    public void executeScanSQL(PrintWriter out, int userId, int numberOfRecords) throws SQLException, PropertyVetoException, IOException
    {
    	
    	//*****This piece of code for retrieving records one by one from database/cache
    	
    	
    	MemcachedClient client= MemcachedSource.getInstance().getClient();
    	ResultSet rs = null;
		Connection myConn = null;
		Statement st = null;
    	
    	for (int i = 0; i < numberOfRecords; i++) {
    		int row = userId - i;
    		
    		String result= (String) client.get("" + row);
    		
    		if (result == null) {
    			//System.out.println("Key " + row + " not in the cache!");
    			result="";
    			if (myConn == null) {
    				myConn = DataSource.getInstance().getConnection();
    				st = myConn.createStatement();
    			}
    			String sql="select * from usertable where YCSB_KEY="+row;
            	// out.println("<h2>Request = Scan, UserKey="+sql+"</h2>");
     			rs=st.executeQuery(sql);
     			
     			rs.next();
     			try {
     				//rs.next();
     				result = "<h1>PRINTING INFO FOR USER="+ rs.getInt("YCSB_KEY") +"</h1>";
     				for (int j = 0; j < 10 ; j++) {
     					result = result + "<br>field" + (j+1) + "=" + rs.getString("FIELD" + (j+1));
     				}
     				//System.out.println(result);
     			} catch (Exception e) {
     				e.printStackTrace();
     			}
     			
     			client.add("" + row, 0, result);
     			
     			out.println(result);
     			rs.close();
     			
    		} else {
    			//System.out.println("Key " + row + " in memcached!");
    			out.println(result);
    		}
    		
    		
    		
    	}
    	
    	if (myConn != null) {
    		st.close();
    		myConn.close();
    	}
		
		rs = null;
		
		
		
		
    	
    	//*****This piece of code for retrieving numberOfRecords records from database/cache at once
    	
    	/*
    	MemcachedClient client= MemcachedSource.getInstance().getClient();
    	
    	SQLRow[] result = (SQLRow[]) client.get("" + userId);
    	if (result == null) {
    		ResultSet rs = null;
    		Connection myConn = DataSource.getInstance().getConnection();
    		Statement st = myConn.createStatement();
    		
    		String sql = "select * from usertable where YCSB_KEY<="+userId+ " order by YCSB_KEY desc limit "+numberOfRecords;
    		rs = st.executeQuery(sql);
    		result = new SQLRow[numberOfRecords];
    		for (int i = 0; i < numberOfRecords; i++) {
    			rs.next();
    			result[i] = new SQLRow(rs);
    			out.println(result[i].toString());
    		}
  
    		client.add(userId+"", 0, result);
    		
    		
    		st.close();
    		myConn.close();
    	} else {
    		for (int i = 0; i < numberOfRecords; i++) {
    			out.println(result[i].toString());
    		}
    	}
    	
    	result = null;
    	client = null;
    	*/
   
    	//*****This piece of code for retrieving records one by one from database
    	/*
    	Connection myConn = DataSource.getInstance().getConnection();
    	Statement st = myConn.createStatement();
    	ServletPrinter sp = new ServletPrinter();
    	for (int i = 0; i < numberOfRecords; i++) {
    		int row = userId-i;
    		String sql="select * from usertable where YCSB_KEY="+row;
    		ResultSet rs=st.executeQuery(sql);
    		
    		rs.next();
 			sp.printUserTable(rs,out);
    	}
    	
    	st.close();
    	myConn.close();
    	*/
    	
    	//*****This piece of code for retrieving numberOfRecords records from database at once
    	/*
    	ServletPrinter sp=null;
    	ResultSet rs = null;
        Connection myConn=  null;
        Statement st=null;
        SQLRow[] result = new SQLRow[numberOfRecords];
        try {
        	myConn = DataSource.getInstance().getConnection();
 			st=myConn.createStatement();
 		} catch (SQLException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
        	sp = new ServletPrinter();
        try {
        	String sql="select * from usertable where YCSB_KEY<="+userId+ " order by YCSB_KEY desc limit "+numberOfRecords;
        	// out.println("<h2>Request = Scan, UserKey="+sql+"</h2>");
 			rs=st.executeQuery(sql);
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
         
         
 		try {
 			
 			//while(rs.next())  
 				//sp.printUserTable(rs, out);
 				//System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
 			for (int i = 0; i < numberOfRecords; i++) {
 				rs.next();
 				result[i] = new SQLRow(rs);
 				out.println(result[i].toString());
 			}
 			
 				
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}  
 		
 		rs.close();
 		st.close();
 		myConn.close();
 		*/
    	
    	//****This code just returns a response
    	//out.println("Returned!");

    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		String keyStr = null, requestType = null, recordCountStr=null;
		
		requestType = request.getParameter("request");
		
		/*** Read memcached and Database IP addresses from file called IPConfig in conf folder ***/
		if (!setIP) {
			File configDir = new File(System.getProperty("catalina.base"), "conf");
			File configFile = new File(configDir, "IPConfig");
			
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			
			br.readLine();
			MemcachedSource.memcachedIP = br.readLine();
			br.readLine();
			DataSource.dataIP = br.readLine();
			
			br.close();
			
			setIP = true;
		}
		
		response.setContentType("text/html");
		 
		PrintWriter out = response.getWriter();

        out.println("<html>");

        out.println("<body>");

        out.println("<head>");

        out.println("<title>Web YCSB</title>");

        out.println("</head>");

        out.println("<body>");
        

        out.println("<h1>YCSB Query Result</h1>");
        
        if (requestType.equals("scan"))
	    {
	    	keyStr = request.getParameter("key");
	    	int key  = new Integer(keyStr);
	    	recordCountStr = request.getParameter("recordcount");
	    	
	    	int recordCount;
	    	 if (recordCountStr == null || recordCountStr.equals(""))
	    		 recordCount = new Integer(50);
	    	    else
	    	    	recordCount = new Integer(recordCountStr);
	    	 out.println("<h2>Request = Scan, UserKey="+key+", recordCount="+recordCount +"</h2>");

	    	 try {
	    		 executeScanSQL(out,key,recordCount);
			} catch (PropertyVetoException|SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
        
        
        else if (requestType.equals("insert"))
	    {
        	 
        	 
	    	keyStr = request.getParameter("key");
	    	int key  = new Integer(keyStr);
	    	
	    	out.println("<h2>Request = Insert, UserKey="+key+"</h2>");
	    	
	    	String f1=request.getParameter("field1");
	    	String f2=request.getParameter("field2");
	    	String f3=request.getParameter("field3");
	    	String f4=request.getParameter("field4");
	    	String f5=request.getParameter("field5");
	    	String f6=request.getParameter("field6");
	    	String f7=request.getParameter("field7");
	    	String f8=request.getParameter("field8");
	    	String f9=request.getParameter("field9");
	    	String f10=request.getParameter("field10");
	    	

	    	 try {
				executeInsertSQL(out,key,f1,f2,f3,f4,f5,f6,f7,f8,f9,f10);
			} catch (PropertyVetoException|SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
        
        out.println("<h1>DONE!</h1>");

        out.println("</body>");

        out.println("</html>");
        
        
        
       
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

}
