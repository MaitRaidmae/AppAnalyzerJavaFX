/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.FXControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Alert;
import applicationanalyzer.DataClasses.ChecksData;
import applicationanalyzer.misc.AlertSQL;
import applicationanalyzer.misc.DataStore;
import applicationanalyzer.misc.SQLExecutor;
import applicationanalyzer.misc.StringToDecimal;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ChecksController implements Initializable {

    @FXML
    private TableView tblv_ChecksData;
    @FXML
    private TableColumn chk_code;
    @FXML
    private TableColumn chk_mnemo;
    @FXML
    private TableColumn chk_type;
    @FXML
    private TableColumn chk_field;
    @FXML
    private TableColumn chk_value_c;
    @FXML
    private TableColumn chk_value_n;
    @FXML
    private TableColumn chk_value_lov_code;
    @FXML
    private TableColumn chk_function;
    @FXML
    private TableColumn chk_comment;
    @FXML
    private TableColumn chk_active;
    @FXML
    private MenuBar menuBar;
    
    DataStore data = DataStore.getDataStore();

    public void handleGetData(ActionEvent event) {
//Define new connection
        initialize(null,null);
    }
   
    @FXML
    public void handleChkCodeEditCommit(CellEditEvent<ChecksData, Integer> t) {
        ChecksData data = (ChecksData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setChkCode(t.getNewValue());
    }

    @FXML
    public void handleChkMnemoEditCommit(CellEditEvent<ChecksData, String> t) {
        ChecksData data = (ChecksData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setChkMnemo(t.getNewValue());
    }

    @FXML
    public void handleChkTypeEditCommit(CellEditEvent<ChecksData, String> t) {
        ChecksData data = (ChecksData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setChkType(t.getNewValue());
    }

    @FXML
    public void handleChkFieldEditCommit(CellEditEvent<ChecksData, String> t) {
        ChecksData data = (ChecksData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setChkField(t.getNewValue());
        System.out.println(t.getNewValue());
    }

    @FXML
    public void handleChkValueCEditCommit(CellEditEvent<ChecksData, String> t) {
        ChecksData data = (ChecksData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setChkValueC(t.getNewValue());
    }

    @FXML
    public void handleChkValueNEditCommit(CellEditEvent<ChecksData, Double> t) {
        ChecksData data = (ChecksData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setChkValueN(t.getNewValue());
    }

    @FXML
    public void handleChkValueLovCodeEditCommit(CellEditEvent<ChecksData, Integer> t) {
        ChecksData data = (ChecksData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setChkValueLovCode(t.getNewValue());
    }

    @FXML
    public void handleChkFunctionEditCommit(CellEditEvent<ChecksData, String> t) {
        ChecksData data = (ChecksData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setChkFunction(t.getNewValue());
    }

    @FXML
    public void handleChkCommentEditCommit(CellEditEvent<ChecksData, String> t) {
        ChecksData data = (ChecksData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setChkComment(t.getNewValue());
    }

    @FXML
    public void handleChkActiveEditCommit(CellEditEvent<ChecksData, Integer> t) {
        ChecksData data = (ChecksData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setChkActive(t.getNewValue());
    }

    // Menu Handlers
    @FXML
    public void handleQuit(ActionEvent event) {
        Stage application_stage = (Stage) menuBar.getScene().getWindow();
        application_stage.close();
    }

    @FXML
    public void handleRunChecks(ActionEvent event) {
        boolean execution = SQLExecutor.executeProcedure("CHECKS.RUN_CHECKS");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DataStore data = DataStore.getDataStore();
        Integer currSuite = data.getCurrentSuite();
        if (currSuite == null) {
          currSuite = 1;
        }
        String sql = "select * from B_CHECKS where chk_chs_code = " + currSuite;
        ResultSet query_results = SQLExecutor.executeQuery(sql);
        ObservableList<ChecksData> obsArrayList = FXCollections.observableArrayList();
        try {
            while (query_results.next()) {
                ChecksData table_row = new ChecksData(query_results.getInt(1),
                        query_results.getString(2),
                        query_results.getString(3),
                        query_results.getString(4),
                        query_results.getString(5),
                        query_results.getDouble(6),
                        query_results.getInt(7),
                        query_results.getString(8),
                        query_results.getString(9),
                        query_results.getInt(10)
                );
                obsArrayList.add(table_row);
            }
            //new StringToDecimal() is a simple implementation of StringConverter<Number>
            chk_code.setCellValueFactory(new PropertyValueFactory<>("ChkCode"));
            chk_code.setCellFactory(TextFieldTableCell.forTableColumn(new StringToDecimal("Integer")));
            chk_mnemo.setCellValueFactory(new PropertyValueFactory<>("ChkMnemo"));
            chk_mnemo.setCellFactory(TextFieldTableCell.forTableColumn());
            chk_type.setCellValueFactory(new PropertyValueFactory<>("ChkType"));
            chk_type.setCellFactory(ComboBoxTableCell.forTableColumn(getLov("chk_type")));
            chk_field.setCellValueFactory(new PropertyValueFactory<>("ChkField"));
            chk_field.setCellFactory(TextFieldTableCell.forTableColumn());
            chk_value_c.setCellValueFactory(new PropertyValueFactory<>("ChkValueC"));
            chk_value_c.setCellFactory(TextFieldTableCell.forTableColumn());
            chk_value_n.setCellValueFactory(new PropertyValueFactory<>("ChkValueN"));
            chk_value_n.setCellFactory(TextFieldTableCell.forTableColumn(new StringToDecimal("Double")));
            chk_value_lov_code.setCellValueFactory(new PropertyValueFactory<>("ChkValueLovCode"));
            chk_value_lov_code.setCellFactory(TextFieldTableCell.forTableColumn(new StringToDecimal("Integer")));
            chk_function.setCellValueFactory(new PropertyValueFactory<>("ChkFunction"));
            chk_function.setCellFactory(TextFieldTableCell.forTableColumn());
            chk_comment.setCellValueFactory(new PropertyValueFactory<>("ChkComment"));
            chk_comment.setCellFactory(TextFieldTableCell.forTableColumn());
            chk_active.setCellValueFactory(new PropertyValueFactory<>("ChkActive"));
            chk_active.setCellFactory(new CheckBoxCellFactory());
            tblv_ChecksData.setItems(obsArrayList);
            tblv_ChecksData.getColumns().setAll(chk_code, chk_mnemo, chk_type, chk_field, chk_value_c, chk_value_n, chk_value_lov_code, chk_function, chk_comment, chk_active);
        } catch (SQLException sqle) {
            AlertSQL.AlertSQL(sqle);
        }
    }
    
    private ObservableList<String> getLov(String fieldName) {
        String lovSQL = "select * from table(MISC_UTILS.CONSTRAINT_LOV('HUNDISILM','B_CHECKS','CHK_TYPE'))";
        ResultSet lovValues = SQLExecutor.executeQuery(lovSQL);
        ObservableList<String> lovList =  FXCollections.observableArrayList();
        try {
        while (lovValues.next()) {
                lovList.add(lovValues.getString(1));
            }
        } catch (SQLException slqe){
            AlertSQL.AlertSQL(slqe);
        }
        return lovList;
    }

    public class CheckBoxCellFactory<T, Boolean> implements Callback {

        @Override
        public TableCell call(Object param) {
            CheckBox checkBox = new CheckBox();
            TableCell<ChecksData, Boolean> checkBoxCell = new TableCell() {
                @Override
                public void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        checkBox.setAlignment(Pos.CENTER);
                        setGraphic(checkBox);
                        ChecksData data = (ChecksData) getTableRow().getItem();
                        if (data != null && data.getChkActive() == 1) {
                            checkBox.setSelected(true);
                        } else {
                            checkBox.setSelected(false);
                        }
                    }
                }
            };

            checkBoxCell.addEventFilter(MouseEvent.MOUSE_CLICKED,
                    (MouseEvent event) -> {
                        TableCell c = (TableCell) event.getSource();
                        CheckBox chkBox = (CheckBox) checkBoxCell.getChildrenUnmodifiable().get(0);
                        ChecksData data = (ChecksData) c.getTableRow().getItem();

                        if (chkBox.isSelected()) {
                            data.setChkActive(1);
                        } else {
                            data.setChkActive(0);
                        }
                    }
            );

            return checkBoxCell;
        }
    }
    
    @FXML
    public void handleViewCheckSuits(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/applicationanalyzer/FXML/CheckSuits.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errror!!! AAAaaaaerrgggh!!");
            alert.setHeaderText("Error loading new window");
            System.out.println("Error " + e.getMessage());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    public void handleRunSuiteChecks(ActionEvent event) {
        boolean execution = SQLExecutor.executeProcedure("CHECKS.RUN_CHECKS("+data.getCurrentSuite()+")");
    }

}
