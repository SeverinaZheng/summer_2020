/**
 * 
 */
package servlets;

import java.io.PrintWriter;

import java.sql.ResultSet;



/**
 * @author Feb 5, 2018oasad
 *
 */
public class ServletPrinter {
	 void printUserTable(ResultSet rs, PrintWriter out)
	  {
	    try
	    {
	      out.println(
	    	"<h1>PRINTING INFO FOR USER=" + rs.getString(1)
	          + "</h1><br>field1="
	          + rs.getString(2)
	          + "<br>field2="
	          + rs.getString(3)
	          + "<br>field3="
	          + rs.getString(4)
	          + "<br>field4="
	          + rs.getString(5)
	          + "<br>field5="
	          + rs.getString(6)
	          + "<br>field6="
	          + rs.getString(7)
	          + "<br>field7="
	          + rs.getString(8)
	          + "<br>field8="
	          + rs.getString(9)
	          + "<br>field9="
	          + rs.getString(10)
	          + "<br>field10="    
	          + rs.getString(11)
	          + "<TD>");
	      /*
	          + user.getEmail()
	          + "<TD><a href=\"/rubis/servlet/PutBidAuth?itemId="
	          + user.getId()
	          + "\"><IMG SRC=\"/rubis/images/bid_now.jpg\" height=22 width=90></a>");
	          */
	    }
	    catch (Exception e)
	    {
	      out.println("Unable to print Item (exception: " + e + ")<br>");
	    }
	  }

}
