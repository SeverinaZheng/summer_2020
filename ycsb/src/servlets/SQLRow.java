package servlets;

import java.util.*;
import java.io.*;
import java.sql.ResultSet;

public class SQLRow implements Serializable{
	private int key;
	private String[] data = new String[10];
	
	public SQLRow(ResultSet rs) {
		try {
			//rs.next();
			key = rs.getInt("YCSB_KEY");
			for (int i = 0; i < 10 ; i++) {
				data[i] = rs.getString("FIELD"+(i+1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SQLRow(int pKey, String dataString) {
		key = pKey;
		for (int i = 0 ; i < 10; i ++) {
			data[i]=dataString;
		}
	}
	
	public String toString() {
		String result = "<h1>PRINTING INFO FOR USER="+key +"</h1><br>field1="+data[0];
		for (int i = 1; i <10; i++) {
			result += "<br>field" + (i+1) +"="+data[i];
		}
		return result;
	}

}
