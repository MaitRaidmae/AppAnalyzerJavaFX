/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.DataClasses;

import static applicationanalyzer.DataClasses.MinmaxCheckPars.getResultSet;
import applicationanalyzer.misc.Alerts;
import applicationanalyzer.misc.CallableStatementResults;
import applicationanalyzer.misc.ConnectionManager;
import applicationanalyzer.misc.SQLExecutor;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RpredictCheckPars {

    private final SimpleIntegerProperty rcp_code;
    private final SimpleStringProperty rcp_type;
    private final SimpleDoubleProperty rcp_threshold;
    private final SimpleIntegerProperty rcp_chk_code;
    private final SimpleStringProperty rcp_model;

    public RpredictCheckPars(Integer rcp_code,
            String rcp_type,
            Double rcp_threshold,
            Integer rcp_chk_code,
            String rcp_model) {
        this.rcp_code = new SimpleIntegerProperty(rcp_code);
        this.rcp_type = new SimpleStringProperty(rcp_type);
        this.rcp_threshold = new SimpleDoubleProperty(rcp_threshold);
        this.rcp_chk_code = new SimpleIntegerProperty(rcp_chk_code);
        this.rcp_model = new SimpleStringProperty(rcp_model);
    }

    public RpredictCheckPars(Integer chk_code) {
        CallableStatementResults callResults = SQLExecutor.getTableRow("P_RPREDICT_CHECK_PARS", "RCP_CHK_CODE", chk_code);
        ResultSet pars = callResults.getResultSet();
        Integer code = 0;
        String type = "";
        Double threshold = 0.0;
        String model = "";
        try {
            pars.next();
            code = pars.getInt(1);
            type = pars.getString(2);
            threshold = pars.getDouble(3);
            chk_code = pars.getInt(4);
            model = pars.getString(5);
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
        callResults.close();
        this.rcp_code = new SimpleIntegerProperty(code);
        this.rcp_type = new SimpleStringProperty(type);
        this.rcp_threshold = new SimpleDoubleProperty(threshold);
        this.rcp_chk_code = new SimpleIntegerProperty(chk_code);
        this.rcp_model = new SimpleStringProperty(model);
    }

    public Integer getRcpCode() {
        return rcp_code.get();
    }

    public void setRcpCode(Integer code) {
        rcp_code.set(code);
    }

    public String getRcpType() {
        return rcp_type.get();
    }

    public void setRcpType(String type) {
        rcp_type.set(type);
    }

    public Double getRcpThreshold() {
        return rcp_threshold.get();
    }

    public void setRcpThreshold(Double threshold) {
        rcp_threshold.set(threshold);
    }

    public Integer getRcpChkCode() {
        return rcp_chk_code.get();
    }

    public void setRcpChkCode(Integer chk_code) {
        rcp_chk_code.set(chk_code);
    }

    public String getRcpModel() {
        return rcp_model.get();
    }

    public void setRcpModel(String model) {
        rcp_model.set(model);
    }

    public void commit() {
        Connection db_connection = ConnectionManager.cl_conn;
        try (CallableStatement callStmt = db_connection.prepareCall("{ call P_RPREDICT_CHECK_PARS.UPDATE_ROW(?,?,?,?,?) }")) {
            callStmt.setInt(1, this.getRcpCode());
            callStmt.setString(2, this.getRcpType());
            callStmt.setDouble(3, this.getRcpThreshold());
            callStmt.setInt(4, this.getRcpChkCode());
            callStmt.setString(5, this.getRcpModel());
            callStmt.execute();
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

    public static CallableStatementResults getResultSet(Integer chk_code) {
        CallableStatementResults callResults = SQLExecutor.getTableRow("P_RPREDICT_CHECK_PARS", "RCP_CHK_CODE", chk_code);
        return callResults;
    }

    public GridPane getEditGrid() {
        GridPane grid = new GridPane();
        int k = 0;
        CallableStatementResults callResults = SQLExecutor.getTableRow("P_RPREDICT_CHECK_PARS", "RCP_CODE", this.getRcpCode());
        ResultSet dataValues = callResults.getResultSet();
        ResultSetMetaData metaData;
        grid.getChildren().clear();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        try {
            metaData = dataValues.getMetaData();
            dataValues.next();
            for (int i = 2; i <= metaData.getColumnCount(); i++) {
                Label fieldNameLbl = new Label(SQLExecutor.getPrettyName("B_RPREDICT_CHECK_PARS", metaData.getColumnName(i)));
                grid.add(fieldNameLbl, 0, k);
                TextField textField = new TextField(dataValues.getString(i));
                textField.setId(metaData.getColumnName(i));
                grid.add(textField, 1, k);
                k++;
            }
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
        return grid;
    }
    
    public void setFromGrid(GridPane grid) {
        for (Node node : grid.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                switch (textField.getId()) {
                    case "RCP_MODEL":
                        this.setRcpModel(textField.getText());
                        break;
                    case "RCP_THRESHOLD":
                        this.setRcpThreshold(Double.parseDouble(textField.getText()));
                        break;
                    case "RCP_TYPE":
                        this.setRcpType(textField.getText());
                        break;
                }
            }
        }
    }

}
