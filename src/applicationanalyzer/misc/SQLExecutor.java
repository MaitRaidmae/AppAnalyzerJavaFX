/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.misc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Hundisilm
 */
public class SQLExecutor {

    public static boolean executeProcedure(String procedureName) {

        Connection dbConnection;
        dbConnection = ConnectionManager.cl_conn;
        Statement stmt;
        try {
            stmt = dbConnection.createStatement();
            stmt.execute("begin " + procedureName + "; end;");
            stmt.close();
            return true;
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
            return false;
        }
    }

    public static ResultSet executeQuery(String querySQL) {
        ResultSet queryResults;
        Connection dbConnection;
        dbConnection = ConnectionManager.cl_conn;
        Statement stmt;
        try {
            stmt = dbConnection.createStatement();
            queryResults = stmt.executeQuery(querySQL);
            return queryResults;
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
        return null;
    }
    
    /** 
    *   The function uses convention to call a results page procedure in OracleDatabase
    *   with the following expected parameters: 1st - page number, 2nd number of pages
    *   3rd cursor with results. 
     * @param packageName
     * @param pageNr
     * @param pageResultsNr
     * @return 
    */
    public static CallableStatementResults getTablePage(String packageName, Integer pageNr, Integer pageResultsNr) {
        Connection db_connection = ConnectionManager.cl_conn;
        ResultSet queryResults;
        CallableStatement callStmt;
        try {
            callStmt = db_connection.prepareCall("{ call " + packageName + ".GET_RESULTS_PAGE(?,?,?) }");
            callStmt.registerOutParameter(3,OracleTypes.CURSOR);
            callStmt.setInt(1,pageNr);
            callStmt.setInt(2,pageResultsNr);
            callStmt.execute();
            queryResults = (ResultSet)callStmt.getObject(3);
            CallableStatementResults callResults = new CallableStatementResults(callStmt,queryResults);
            return callResults;
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
        return null;
    }
    
    /** 
    *   The function is meant for getting a table row based on a integer key value
    *   which would typically be a primary key or a foreign key (it would have to be unique).
    * 
     * @param packageName
     * @param columnName
     * @param key
     * @return 
    */
        public static CallableStatementResults getTableRow(String packageName, String columnName, Integer key) {
        Connection db_connection = ConnectionManager.cl_conn;
        ResultSet queryResults;
        CallableStatement callStmt;
        try {
            callStmt = db_connection.prepareCall("{ call " + packageName + ".GET_ROW(?,?,?) }");
            callStmt.registerOutParameter(3,OracleTypes.CURSOR);
            callStmt.setString(1,columnName);
            callStmt.setInt(2,key);
            callStmt.execute();
            queryResults = (ResultSet)callStmt.getObject(3);
            CallableStatementResults callResults = new CallableStatementResults(callStmt,queryResults);
            return callResults;
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
        return null;
    }
        
        /** 
    *   The function is meant for getting a table row based on a integer key value
    *   which would typically be a primary key or a foreign key (it would have to be unique).
    * 
     * @param tableName
     * @param columnName
     * @return 
    */
     public static String getPrettyName(String tableName, String columnName) {
        Connection db_connection = ConnectionManager.cl_conn;
        String prettyName;
        try(CallableStatement callStmt = db_connection.prepareCall("{? = call MISC_UTILS.PRETTY_NAME(?,?) }")) {
            System.out.println(tableName);
            System.out.println(columnName);
            callStmt.registerOutParameter(1,Types.VARCHAR);
            callStmt.setString(2,tableName);
            callStmt.setString(3,columnName);
            callStmt.executeUpdate();
            prettyName = (String) callStmt.getObject(1);
            return prettyName;
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
        return null;
    }
     
    public static ObservableList<String> getLov(String tableName, String fieldName) {
        String lovSQL = "select * from table(MISC_UTILS.CONSTRAINT_LOV('HUNDISILM','" + tableName +"','" + fieldName + "'))";
        ResultSet lovValues = SQLExecutor.executeQuery(lovSQL);
        ObservableList<String> lovList = FXCollections.observableArrayList();
        try {
            while (lovValues.next()) {
                lovList.add(lovValues.getString(1));
            }
        } catch (SQLException slqe) {
            Alerts.AlertSQL(slqe);
        }
        return lovList;
    }

        
}
