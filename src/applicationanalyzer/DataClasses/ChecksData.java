/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.DataClasses;

import applicationanalyzer.misc.ConnectionManager;
import applicationanalyzer.misc.AlertSQL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ChecksData {

    private final SimpleIntegerProperty chk_code;
    private final SimpleStringProperty chk_mnemo;
    private final SimpleStringProperty chk_type;
    private final SimpleStringProperty chk_field;
    private final SimpleStringProperty chk_value_c;
    private final SimpleDoubleProperty chk_value_n;
    private final SimpleIntegerProperty chk_value_lov_code;
    private final SimpleStringProperty chk_function;
    private final SimpleStringProperty chk_comment;
    private final SimpleIntegerProperty chk_active;

    public ChecksData(Integer chk_code,
            String chk_mnemo,
            String chk_type,
            String chk_field,
            String chk_value_c,
            Double chk_value_n,
            Integer chk_value_lov_code,
            String chk_function,
            String chk_comment,
            Integer chk_active) {
        this.chk_code = new SimpleIntegerProperty(chk_code);
        this.chk_mnemo = new SimpleStringProperty(chk_mnemo);
        this.chk_type = new SimpleStringProperty(chk_type);
        this.chk_field = new SimpleStringProperty(chk_field);
        this.chk_value_c = new SimpleStringProperty(chk_value_c);
        this.chk_value_n = new SimpleDoubleProperty(chk_value_n);
        this.chk_value_lov_code = new SimpleIntegerProperty(chk_value_lov_code);
        this.chk_function = new SimpleStringProperty(chk_function);
        this.chk_comment = new SimpleStringProperty(chk_comment);
        this.chk_active = new SimpleIntegerProperty(chk_active);
         // Converts integer to Boolean
    }

    ConnectionManager connection = new ConnectionManager();
    Connection db_connection = ConnectionManager.cl_conn;
    Statement db_stmt;

    public Integer getChkCode() {
        return chk_code.get();
    }

    public void setChkCode(Integer code) {
        chk_code.set(code);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_CHECKS set CHK_CODE='" + code + "' where chk_code = " + this.getChkCode());
        } catch (SQLException sqle) {
            AlertSQL.AlertSQL(sqle);
        }
    }

    public String getChkMnemo() {
        return chk_mnemo.get();
    }

    public void setChkMnemo(String mnemo) {
        chk_mnemo.set(mnemo);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_CHECKS set CHK_MNEMO='" + mnemo + "' where chk_code = " + this.getChkCode());
        } catch (SQLException sqle) {
            AlertSQL.AlertSQL(sqle);
        }
    }

    public String getChkType() {
        return chk_type.get();
    }

    public void setChkType(String type) {
        chk_type.set(type);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_CHECKS set CHK_TYPE='" + type + "' where chk_code = " + this.getChkCode());
        } catch (SQLException sqle) {
            AlertSQL.AlertSQL(sqle);
        }
    }

    public String getChkField() {
        return chk_field.get();
    }

    public void setChkField(String field) {
        chk_field.set(field);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_CHECKS set CHK_FIELD='" + field + "' where chk_code = " + this.getChkCode());
        } catch (SQLException sqle) {
            AlertSQL.AlertSQL(sqle);
        }
    }

    public String getChkValueC() {
        return chk_value_c.get();
    }

    public void setChkValueC(String value_c) {
        chk_value_c.set(value_c);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_CHECKS set CHK_VALUE_C='" + value_c + "' where chk_code = " + this.getChkCode());
        } catch (SQLException sqle) {
            AlertSQL.AlertSQL(sqle);
        }
    }

    public Double getChkValueN() {
        return chk_value_n.get();
    }

    public void setChkValueN(Double value_n) {
        chk_value_n.set(value_n);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.executeUpdate("Update B_CHECKS set CHK_VALUE_N='" + value_n + "' where chk_code = " + this.getChkCode());
        } catch (SQLException sqle) {
            AlertSQL.AlertSQL(sqle);
        }
    }

    public Integer getChkValueLovCode() {
        return chk_value_lov_code.get();
    }

    public void setChkValueLovCode(Integer value_lov_code) {
        chk_value_lov_code.set(value_lov_code);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_CHECKS set CHK_VALUE_LOV_CODE='" + value_lov_code + "' where chk_code = " + this.getChkCode());
        } catch (SQLException sqle) {
            AlertSQL.AlertSQL(sqle);
        }
    }

    public String getChkFunction() {
        return chk_function.get();
    }

    public void setChkFunction(String function) {
        chk_function.set(function);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_CHECKS set CHK_FUNCTION='" + function + "' where chk_code = " + this.getChkCode());
        } catch (SQLException sqle) {
            AlertSQL.AlertSQL(sqle);
        }
    }

    public String getChkComment() {
        return chk_comment.get();
    }

    public void setChkComment(String comment) {
        chk_comment.set(comment);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_CHECKS set CHK_COMMENT='" + comment + "' where chk_code = " + this.getChkCode());
        } catch (SQLException sqle) {
            AlertSQL.AlertSQL(sqle);
        }
    }

    public Integer getChkActive() {
        return chk_active.get();
    }

    public void setChkActive(Integer active) {
        chk_active.set(active);
        try {
            db_stmt = db_connection.createStatement();
            db_stmt.execute("Update B_CHECKS set CHK_ACTIVE='" + active + "' where chk_code = " + this.getChkCode());
        } catch (SQLException sqle) {
            AlertSQL.AlertSQL(sqle);
        }
    }
}
