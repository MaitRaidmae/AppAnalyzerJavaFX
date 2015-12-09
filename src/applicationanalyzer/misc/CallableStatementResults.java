/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.misc;

import applicationanalyzer.interfaces.StatementResults;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The purpose of this class is to attempt to abstract the calls to the
 * database.
 *
 */
public class CallableStatementResults implements StatementResults {

    private CallableStatement callStatement;
    private ResultSet resultSet;

    public CallableStatementResults(CallableStatement call, ResultSet result) {
        this.callStatement = call;
        this.resultSet = result;
    }

    public CallableStatementResults() {
    }

    
    public ResultSet getResultSet(){
        return this.resultSet;
    }

    @Override
    public void close() {
        try {
            resultSet.close();
            callStatement.close();
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

}
