/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.DataClasses;

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
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
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

    public RpredictCheckPars(Integer RCP_CHK_CODE) {
        CallableStatementResults callResults = SQLExecutor.getTableRow("P_RPREDICT_CHECK_PARS", "RCP_CHK_CODE", RCP_CHK_CODE);
        ResultSet pars = callResults.getResultSet();
        Integer code = 0;
        String type = "";
        Double threshold = 0.0;
        Integer chk_code = 0;
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

    public GridPane getGrid(Boolean editable) {
        GridPane grid = new GridPane();
        int k = 0;
        String fieldType = "";
        CallableStatementResults callResults = SQLExecutor.getTableRow("P_RPREDICT_CHECK_PARS", "RCP_CODE", this.getRcpCode());
        ResultSet dataValues = callResults.getResultSet();
        ResultSetMetaData metaData;
        TextField textField = new TextField();
        ComboBox comboBox = new ComboBox();
        try {
            metaData = dataValues.getMetaData();
            dataValues.next();
            for (int i = 2; i <= metaData.getColumnCount(); i++) {
                Label fieldNameLbl = new Label(SQLExecutor.getPrettyName("B_RPREDICT_CHECK_PARS", metaData.getColumnName(i)));
                grid.add(fieldNameLbl, 0, k);
                if (!editable) {
                    Label fieldValueLbl = new Label(dataValues.getString(i));
                    grid.add(fieldValueLbl, 1, k);
                } else {
                    switch (metaData.getColumnName(i)) {

                        case "RCP_CODE":
                            textField = new TextField(dataValues.getString(i));
                            fieldType = "textField";
                            break;
                        case "RCP_TYPE":
                            textField = new TextField(dataValues.getString(i));
                            fieldType = "textField";
                            break;
                        case "RCP_THRESHOLD":
                            textField = new TextField(dataValues.getString(i));
                            fieldType = "textField";
                            break;
                        case "RCP_CHK_CODE":
                            textField = new TextField(dataValues.getString(i));
                            fieldType = "textField";
                            break;
                        case "RCP_MODEL":
                            textField = new TextField(dataValues.getString(i));
                            fieldType = "textField";
                            break;
                    }
                    switch (fieldType) {
                        case "textField":
                            textField.setId(metaData.getColumnName(i));
                            grid.add(textField, 1, k);
                            break;
                        case "comboBox":
                            comboBox.setId(metaData.getColumnName(i));
                            grid.add(comboBox, 1, k);
                            break;
                    }
                }
                k++;
            }
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
        callResults.close();
        return grid;
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


    public void setFromGrid(GridPane grid) {
        for (Node node : grid.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                switch (textField.getId()) {

                    case "RCP_CODE":
                        this.setRcpCode(Integer.parseInt(textField.getText()));
                        break;
                    case "RCP_TYPE":
                        this.setRcpType(textField.getText());
                        break;
                    case "RCP_THRESHOLD":
                        this.setRcpThreshold(Double.parseDouble(textField.getText()));
                        break;
                    case "RCP_CHK_CODE":
                        this.setRcpChkCode(Integer.parseInt(textField.getText()));
                        break;
                    case "RCP_MODEL":
                        this.setRcpModel(textField.getText());
                        break;
                }
            } else if (node instanceof ComboBox) {
                ComboBox comboBox = (ComboBox) node;
                switch (comboBox.getId()) {

                }
            }
        }
    }

    public static CallableStatementResults getResultSet(Integer RCP_CHK_CODE) {
        CallableStatementResults callResults = SQLExecutor.getTableRow("P_RPREDICT_CHECK_PARS", "RCP_CHK_CODE", RCP_CHK_CODE);
        return callResults;
    }
}
