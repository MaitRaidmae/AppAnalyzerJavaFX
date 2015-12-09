/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.DataClasses;

import applicationanalyzer.misc.Alerts;
import applicationanalyzer.misc.CallableStatementResults;
import applicationanalyzer.misc.ConnectionManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Checks {

    private final SimpleIntegerProperty chk_code;
    private final SimpleStringProperty chk_mnemo;
    private final SimpleStringProperty chk_type;
    private final SimpleStringProperty chk_comment;
    private final SimpleIntegerProperty chk_active;
    private final SimpleIntegerProperty chk_chs_code;

    public Checks(Integer chk_code,
            String chk_mnemo,
            String chk_type,
            String chk_comment,
            Integer chk_active,
            Integer chk_chs_code) {
        this.chk_code = new SimpleIntegerProperty(chk_code);
        this.chk_mnemo = new SimpleStringProperty(chk_mnemo);
        this.chk_type = new SimpleStringProperty(chk_type);
        this.chk_comment = new SimpleStringProperty(chk_comment);
        this.chk_active = new SimpleIntegerProperty(chk_active);
        this.chk_chs_code = new SimpleIntegerProperty(chk_chs_code);
    }

    public Integer getChkCode() {
        return chk_code.get();
    }

    public void setChkCode(Integer code) {
        chk_code.set(code);
    }

    public String getChkMnemo() {
        return chk_mnemo.get();
    }

    public void setChkMnemo(String mnemo) {
        chk_mnemo.set(mnemo);
    }

    public String getChkType() {
        return chk_type.get();
    }

    public void setChkType(String type) {
        chk_type.set(type);
    }

    public String getChkComment() {
        return chk_comment.get();
    }

    public void setChkComment(String comment) {
        chk_comment.set(comment);
    }

    public Integer getChkActive() {
        return chk_active.get();
    }

    public void setChkActive(Integer active) {
        chk_active.set(active);
    }

    public Integer getChkChsCode() {
        return chk_chs_code.get();
    }

    public void setChkChsCode(Integer chs_code) {
        chk_chs_code.set(chs_code);
    }
    
    public void commit() {
        Connection db_connection = ConnectionManager.cl_conn;
        try(CallableStatement callStmt = db_connection.prepareCall("{ call P_CHECKS.UPDATE_ROW(?,?,?,?,?,?) }")) {
            callStmt.setInt(1,this.getChkCode());
            callStmt.setString(2,this.getChkMnemo());
            callStmt.setString(3,this.getChkType());
            callStmt.setString(4,this.getChkComment());
            callStmt.setInt(5,this.getChkActive());
            callStmt.setInt(6,this.getChkChsCode());
            callStmt.execute();
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }
    
    public MinmaxCheckPars getMinmaxPars(){
        MinmaxCheckPars minmaxPars = new MinmaxCheckPars(chk_code.intValue());
        return minmaxPars;
    }   
}
