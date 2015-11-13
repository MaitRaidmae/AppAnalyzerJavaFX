/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.misc;

import applicationanalyzer.misc.AlertSQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Hundisilm
 */
public class SQLExecutor {

    public static boolean executeProcedure(String procedureName) {

        Connection db_connection;
        db_connection = ConnectionManager.cl_conn;
        Statement db_stmt;
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("begin " + procedureName + "; end;");
            db_stmt.close();
            return true;
        } catch (SQLException sqle) {
            AlertSQL.AlertSQL(sqle);
            return false;
        }
    }
    
    public static ResultSet executeQuery(String querySQL) {
        ResultSet queryResults;
        Connection db_connection;
        db_connection = ConnectionManager.cl_conn;
        Statement db_stmt;
        try {
            db_stmt = db_connection.createStatement();
            queryResults = db_stmt.executeQuery(querySQL);
            return queryResults;
        } catch (SQLException sqle) {
            AlertSQL.AlertSQL(sqle);
        }
    return null;
    }
}
