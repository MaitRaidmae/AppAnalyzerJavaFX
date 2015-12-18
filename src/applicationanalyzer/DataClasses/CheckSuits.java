package applicationanalyzer.DataClasses;

import applicationanalyzer.FXControllers.EditCheckSuitsController;
import applicationanalyzer.misc.Alerts;
import applicationanalyzer.misc.CallableStatementResults;
import applicationanalyzer.misc.ConnectionManager;
import applicationanalyzer.misc.SQLExecutor;
import java.io.IOException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import javafx.beans.property.SimpleObjectProperty;
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

public class CheckSuits {

    private final SimpleIntegerProperty chs_code;
    private final SimpleStringProperty chs_mnemo;
    private final SimpleStringProperty chs_comment;
    private final SimpleObjectProperty<Date> chs_datetime;

    public CheckSuits(Integer chs_code,
            String chs_mnemo,
            String chs_comment,
            Date chs_datetime) {
        this.chs_code = new SimpleIntegerProperty(chs_code);
        this.chs_mnemo = new SimpleStringProperty(chs_mnemo);
        this.chs_comment = new SimpleStringProperty(chs_comment);
        this.chs_datetime = new SimpleObjectProperty(chs_datetime);
    }

    public Integer getChsCode() {
        return chs_code.get();
    }

    public void setChsCode(Integer code) {
        chs_code.set(code);
    }

    public String getChsMnemo() {
        return chs_mnemo.get();
    }

    public void setChsMnemo(String mnemo) {
        chs_mnemo.set(mnemo);
    }

    public String getChsComment() {
        return chs_comment.get();
    }

    public void setChsComment(String comment) {
        chs_comment.set(comment);
    }

    public Date getChsDatetime() {
        return chs_datetime.get();
    }

    public void setChsDatetime(Date datetime) {
        chs_datetime.set(datetime);
    }

    public GridPane getGrid(Boolean editable) {
        GridPane grid = new GridPane();
        int k = 0;
        String fieldType = "";
        CallableStatementResults callResults = SQLExecutor.getTableRow("P_CHECK_SUITS", "CHS_CODE", this.getChsCode());
        ResultSet dataValues = callResults.getResultSet();
        ResultSetMetaData metaData;
        TextField textField = new TextField();
        ComboBox comboBox = new ComboBox();
        try {
            metaData = dataValues.getMetaData();
            dataValues.next();
            for (int i = 2; i <= metaData.getColumnCount(); i++) {
                Label fieldNameLbl = new Label(SQLExecutor.getPrettyName("B_CHECK_SUITS", metaData.getColumnName(i)));
                grid.add(fieldNameLbl, 0, k);
                if (!editable) {
                    Label fieldValueLbl = new Label(dataValues.getString(i));
                    grid.add(fieldValueLbl, 1, k);
                } else {
                    switch (metaData.getColumnName(i)) {

                        case "CHS_CODE":
                            textField = new TextField(dataValues.getString(i));
                            fieldType = "textField";
                            break;
                        case "CHS_MNEMO":
                            textField = new TextField(dataValues.getString(i));
                            fieldType = "textField";
                            break;
                        case "CHS_COMMENT":
                            textField = new TextField(dataValues.getString(i));
                            fieldType = "textField";
                            break;
                        case "CHS_DATETIME":
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
        try (CallableStatement callStmt = db_connection.prepareCall("{ call P_CHECK_SUITS.UPDATE_ROW(?,?,?,?) }")) {
            callStmt.setInt(1, this.getChsCode());
            callStmt.setString(2, this.getChsMnemo());
            callStmt.setString(3, this.getChsComment());
            callStmt.setObject(4, this.getChsDatetime());
            callStmt.execute();
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }

    public boolean showEditDialog(Window owner) {
        try {
//Load Edit FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/applicationanalyzer/FXML/EditCheckSuits.fxml"));
            AnchorPane page = (AnchorPane) fxmlLoader.load();
//Load edit Stage
            Stage editStage = new Stage();
            editStage.setTitle("<<<PUT TITLE HERE>>>");
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(owner);
            Scene scene = new Scene(page);
            editStage.setScene(scene);
// Set data object into the contoller
            EditCheckSuitsController controller = fxmlLoader.getController();
            controller.initObject(this);
            controller.setStage(editStage);
            editStage.showAndWait();
            return controller.isCommited();
        } catch (IOException e) {
            Alerts.AlertIO(e);
            return false;
        }
    }

    public void setFromGrid(GridPane grid) {
        for (Node node : grid.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                switch (textField.getId()) {

                    case "CHS_CODE":
                        this.setChsCode(Integer.parseInt(textField.getText()));
                        break;
                    case "CHS_MNEMO":
                        this.setChsMnemo(textField.getText());
                        break;
                    case "CHS_COMMENT":
                        this.setChsComment(textField.getText());
                        break;
                    case "CHS_DATETIME":
                        this.setChsDatetime(new Date(textField.getText()));
                        break;
                }
            } else if (node instanceof ComboBox) {
                ComboBox comboBox = (ComboBox) node;
                switch (comboBox.getId()) {

                }
            }
        }
    }
}
