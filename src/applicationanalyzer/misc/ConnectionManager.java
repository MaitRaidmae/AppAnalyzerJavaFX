/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.misc;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author Hundisilm
 */
public class ConnectionManager {
    
    public static Connection cl_conn;

    
    public ConnectionManager(){}
    public ConnectionManager(String userid, String password){}
            
    public Connection getDBConnection(String userid, String password) throws SQLException{
    
    //String jdbcUrl = "jdbc:oracle:thin:@localhost:1521/DatabaseHundi";
    String jdbcUrl = "jdbc:oracle:thin:@localhost:1522/hundiplug";
    
    OracleDataSource ds;
    ds = new OracleDataSource();
    ds.setURL(jdbcUrl);
    cl_conn=ds.getConnection(userid,password);
    return cl_conn;
    }   
}
