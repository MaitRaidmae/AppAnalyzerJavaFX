/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.DataClasses;

import applicationanalyzer.FXControllers.EditCheckController;
import applicationanalyzer.misc.Alerts;
import applicationanalyzer.misc.CallableStatementResults;
import applicationanalyzer.misc.ConnectionManager;
import applicationanalyzer.misc.SQLExecutor;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Checks {

    private final SimpleIntegerProperty chk_code;
    private final SimpleStringProperty chk_mnemo;
    private final SimpleStringProperty chk_type;
    private final SimpleStringProperty chk_comment;
    private final SimpleIntegerProperty chk_active;
    private final SimpleIntegerProperty chk_chs_code;
    private MinmaxCheckPars mmPars = null;
    private RpredictCheckPars rpPars = null;

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

        switch (chk_type) {
            case "MIN":
            case "MAX":
                this.mmPars = new MinmaxCheckPars(chk_code);
                break;
            case "RPREDICT":
                this.rpPars = new RpredictCheckPars(chk_code);
                break;
        }
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
        SQLExecutor.updateRowNvalue("P_CHECKS",getChkCode(),"CHK_ACTIVE",getChkActive());
    }

    public Integer getChkChsCode() {
        return chk_chs_code.get();
    }

    public void setChkChsCode(Integer chs_code) {
        chk_chs_code.set(chs_code);
    }

    public void commit() {
        Connection db_connection = ConnectionManager.cl_conn;
        try (CallableStatement callStmt = db_connection.prepareCall("{ call P_CHECKS.UPDATE_ROW(?,?,?,?,?,?) }")) {
            callStmt.setInt(1, this.getChkCode());
            callStmt.setString(2, this.getChkMnemo());
            callStmt.setString(3, this.getChkType());
            callStmt.setString(4, this.getChkComment());
            callStmt.setInt(5, this.getChkActive());
            callStmt.setInt(6, this.getChkChsCode());
            callStmt.execute();
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

    public MinmaxCheckPars getMinmaxPars() {
        MinmaxCheckPars minmaxPars = new MinmaxCheckPars(this.getChkCode());
        return minmaxPars;
    }

    public RpredictCheckPars getRpredictPars() {
        RpredictCheckPars rPredictPars = new RpredictCheckPars(this.getChkCode());
        return rPredictPars;
    }

    public CallableStatementResults getParsResults() {
        switch (this.getChkType()) {
            case "MIN":
            case "MAX":
                return MinmaxCheckPars.getResultSet(this.getChkCode());
            case "RPREDICT":
                return RpredictCheckPars.getResultSet(this.getChkCode());
        }
        return null;
    }

    public GridPane getGrid(Boolean editable) {
        GridPane grid = new GridPane();
        int k = 0;
        String fieldType = "";
        CallableStatementResults callResults = SQLExecutor.getTableRow("P_CHECKS", "CHK_CODE", this.getChkCode());
        ResultSet dataValues = callResults.getResultSet();
        ResultSetMetaData metaData;
        TextField textField = new TextField();
        ComboBox comboBox = new ComboBox();


        try {
            metaData = dataValues.getMetaData();
            dataValues.next();
            for (int i = 2; i <= metaData.getColumnCount(); i++) {
                Label fieldNameLbl = new Label(SQLExecutor.getPrettyName("B_CHECKS", metaData.getColumnName(i)));
                grid.add(fieldNameLbl, 0, k);
                if (!editable) {
                    Label fieldValueLbl = new Label(dataValues.getString(i));
                    grid.add(fieldValueLbl, 1, k);
                } else {
                    switch (metaData.getColumnName(i)) {

                        case "CHK_CODE":
                            textField = new TextField(dataValues.getString(i));
                            fieldType = "textField";
                            break;
                        case "CHK_MNEMO":
                            textField = new TextField(dataValues.getString(i));
                            fieldType = "textField";
                            break;
                        case "CHK_TYPE":
                            comboBox = new ComboBox(SQLExecutor.getLov("B_CHECKS", metaData.getColumnName(i)));
                            comboBox.setValue(dataValues.getString(i));
                            fieldType = "comboBox";
                            break;
                        case "CHK_COMMENT":
                            textField = new TextField(dataValues.getString(i));
                            fieldType = "textField";
                            break;
                        case "CHK_ACTIVE":
                            textField = new TextField(dataValues.getString(i));
                            fieldType = "textField";
                            break;
                        case "CHK_CHS_CODE":
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

    public void setFromGrid(GridPane grid) {
        for (Node node : grid.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                switch (textField.getId()) {

                    case "CHK_CODE":
                        this.setChkCode(Integer.parseInt(textField.getText()));
                        break;
                    case "CHK_MNEMO":
                        this.setChkMnemo(textField.getText());
                        break;
                    case "CHK_COMMENT":
                        this.setChkComment(textField.getText());
                        break;
                    case "CHK_ACTIVE":
                        this.setChkActive(Integer.parseInt(textField.getText()));
                        break;
                    case "CHK_CHS_CODE":
                        this.setChkChsCode(Integer.parseInt(textField.getText()));
                        break;
                }
            } else if (node instanceof ComboBox) {
                ComboBox comboBox = (ComboBox) node;
                switch (comboBox.getId()) {

                    case "CHK_TYPE":
                        this.setChkType((String) comboBox.getValue());
                        break;
                }
            }
        }
    }

    public GridPane getParsGrid(Boolean editable) {
        switch (this.getChkType()) {
            case "MIN":
            case "MAX":
                return mmPars.getGrid(editable);
            case "RPREDICT":
                return rpPars.getGrid(editable);
        }
        return null;
    }

    public void setParsFromGrid(GridPane grid) {
        switch (this.getChkType()) {
            case "MIN":
            case "MAX":
                mmPars.setFromGrid(grid);
                break;
            case "RPREDICT":
                rpPars.setFromGrid(grid);
                break;
        }
    }

    public void commitPars() {
        switch (this.getChkType()) {
            case "MIN":
            case "MAX":
                mmPars.commit();
                break;
            case "RPREDICT":
                rpPars.commit();
                break;
        }
    }

    public boolean showEditDialog(Window owner) {
        try {
            //Load Edit FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/applicationanalyzer/FXML/EditCheck.fxml"));
            AnchorPane page = (AnchorPane) fxmlLoader.load();

            //Load edit Stage
            Stage editStage = new Stage();
            editStage.setTitle("Edit Check: " + this.getChkMnemo());
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(owner);
            Scene scene = new Scene(page);
            editStage.setScene(scene);
            scene.getStylesheets().add("/applicationanalyzer/FXML/CSS/EditChecks.css");
            // Set the check into the contoller
            EditCheckController controller = fxmlLoader.getController();
            controller.initObject(this);
            controller.setStage(editStage);
            editStage.showAndWait();

            return controller.isCommited();
        } catch (IOException e) {
            Alerts.AlertIO(e);
            return false;
        }
    }

}
