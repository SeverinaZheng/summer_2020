package servlets;

import java.sql.*;
import com.mchange.v2.c3p0.*;
import java.io.IOException;
import java.beans.PropertyVetoException;

public class DataSource {
	private static DataSource datasource;
    private ComboPooledDataSource cpds;
    public static String dataIP;

    private DataSource() throws IOException, SQLException, PropertyVetoException {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver"); //loads the jdbc driver
        cpds.setJdbcUrl("jdbc:mysql://" + dataIP + ":3306/YCSB");
        cpds.setUser("root");
        cpds.setPassword("root");
        
        cpds.setMinPoolSize(5); 
        cpds.setAcquireIncrement(5); 
        cpds.setMaxPoolSize(10);
        //cpds.setTestConnectionOnCheckout(true);
    }
    
    public static DataSource getInstance() throws IOException, SQLException, PropertyVetoException {
        if (datasource == null) {
            datasource = new DataSource();
            return datasource;
        } else {
            return datasource;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.cpds.getConnection();
    }
}
