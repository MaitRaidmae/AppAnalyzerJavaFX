/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.FXControllers;

import applicationanalyzer.DataClasses.CheckSuits;
import applicationanalyzer.DataClasses.Checks;
import applicationanalyzer.misc.Alerts;
import applicationanalyzer.misc.CallableStatementResults;
import applicationanalyzer.misc.CssLoader;
import applicationanalyzer.misc.Helpers;
import applicationanalyzer.misc.SQLExecutor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ChecksController implements Initializable {

    @FXML
    private AnchorPane Checks;
    @FXML
    private AnchorPane checksPane;
    Checks checks;
    CheckSuits checkSuits;
    Stage stage;

    
    @FXML
    private void searchData(ActionEvent event) {
        showResults();
    }

    public void showResults() {
        showResults(99999999);
    }

    public void showResults(Integer results) {

        ObservableList<Checks> obsArrayList = FXCollections.observableArrayList();
        CallableStatementResults callResults = SQLExecutor.getTablePage("P_CHECKS", 1, results);
        ResultSet query_results = callResults.getResultSet();
        TableView<Checks> checksTable = new TableView();
        checksTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showCheckParsData(newValue));
        AnchorPane.setBottomAnchor(checksTable, 0.0);
        AnchorPane.setTopAnchor(checksTable, 0.0);
        AnchorPane.setRightAnchor(checksTable, 0.0);
        AnchorPane.setLeftAnchor(checksTable, 0.0);
        TableColumn chk_code = Helpers.initTableColumn("B_CHECKS", "CHK_CODE");
        TableColumn chk_mnemo = Helpers.initTableColumn("B_CHECKS", "CHK_MNEMO");
        TableColumn chk_type = Helpers.initTableColumn("B_CHECKS", "CHK_TYPE");
        TableColumn chk_comment = Helpers.initTableColumn("B_CHECKS", "CHK_COMMENT");
        TableColumn chk_active = Helpers.initTableColumn("B_CHECKS", "CHK_ACTIVE");
        TableColumn chk_chs_code = Helpers.initTableColumn("B_CHECKS", "CHK_CHS_CODE");
        checksPane.getChildren().add(checksTable);
        checksTable.getColumns().setAll(chk_code, chk_mnemo, chk_type, chk_comment, chk_active, chk_chs_code);
        try {
            while (query_results.next()) {
                Checks dataInstance = new Checks(query_results.getInt(1),
                        query_results.getString(2),
                        query_results.getString(3),
                        query_results.getString(4),
                        query_results.getInt(5),
                        query_results.getInt(6));
                obsArrayList.add(dataInstance);
            }
            chk_code.setCellValueFactory(new PropertyValueFactory<>("ChkCode"));
            chk_mnemo.setCellValueFactory(new PropertyValueFactory<>("ChkMnemo"));
            chk_type.setCellValueFactory(new PropertyValueFactory<>("ChkType"));
            chk_comment.setCellValueFactory(new PropertyValueFactory<>("ChkComment"));
            chk_active.setCellValueFactory(new PropertyValueFactory<>("ChkActive"));
            chk_active.setCellFactory(new ChecksCheckBoxFactory());
            chk_chs_code.setCellValueFactory(new PropertyValueFactory<>("ChkChsCode"));
            checksTable.setRowFactory(rowfactory -> {
                TableRow<Checks> row = new TableRow<>();
                row.setOnMouseClicked(MouseEventHandler(row));
                return row;
            });
            checksTable.setItems(obsArrayList);
            checksTable.getColumns().setAll(chk_code, chk_mnemo, chk_type, chk_comment, chk_active, chk_chs_code);
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
        callResults.close();

    }

    private EventHandler<MouseEvent> MouseEventHandler(TableRow<Checks> row) {
        EventHandler<MouseEvent> mouseEvent
                = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent event) {
                        if (event.getClickCount() >= 2) {
//Add DoubleClick Event here                        
                        } else if (event.getButton() == MouseButton.SECONDARY) {
// Add rightClick Event here (open popupMenu for example                            
                            ContextMenu contextMenu = new ContextMenu();
                            MenuItem editItem = new MenuItem("Edit");
                            Checks check = row.getItem();
                            editItem.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    check.showEditDialog(checksPane.getScene().getWindow());
                                    initialize(null, null);
                                }
                            });
                            contextMenu.getItems().add(editItem);
                            contextMenu.show(row, event.getScreenX(), event.getScreenY());
                        }
                    }
                };
        return mouseEvent;
    }

    public class ChecksCheckBoxFactory<T, Boolean> implements Callback {

        @Override
        public TableCell call(Object param) {
            CheckBox checkBox = new CheckBox();
            TableCell<Checks, Boolean> checkBoxCell = new TableCell() {
                @Override
                public void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        checkBox.setAlignment(Pos.CENTER);
                        setGraphic(checkBox);
                        Checks data = (Checks) getTableRow().getItem();
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
                        Checks data = (Checks) c.getTableRow().getItem();
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

    public void setStage(Stage newStage) {
        stage = newStage;
    }

    public void initObject(Checks newInstance) {

        checks = newInstance;
        GridPane checksGrid = checks.getGrid(true);
        CssLoader css = new CssLoader("/applicationanalyzer/FXML/CSS/Checks.css", "checksGrid");
        checksGrid.getColumnConstraints().add(new ColumnConstraints(css.GRID_COL1_WIDTH));
        checksGrid.getColumnConstraints().add(new ColumnConstraints(css.GRID_COL2_WIDTH));
        checksGrid.getStyleClass().add("checksGrid");
        checksPane.getChildren().add(checksGrid);
    }

    public void setCheckSuits(CheckSuits newCheckSuits) {
        checkSuits = newCheckSuits;
    }
//Custom part of the controller
    @FXML
    MenuBar menuBar;
    @FXML
    AnchorPane checkParsPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showResults();
    }

// Menu Handlers    
    @FXML
    private void handleQuit(ActionEvent event) {
        Stage application_stage = (Stage) menuBar.getScene().getWindow();
        application_stage.close();
    }

    @FXML
    private void handleViewCheckSuits(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/applicationanalyzer/FXML/CheckSuits.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root1));
            newStage.show();
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
    private void handleRunSuiteChecks(ActionEvent event) {
        boolean execution = SQLExecutor.executeProcedure("CHECKS.RUN_CHECKS(" + checkSuits.getChsCode() + ", 'Placeholder')");
    }
    
    private void showCheckParsData(Checks check) {
        GridPane grid = check.getParsGrid(false);
        checkParsPane.getChildren().clear();
        CssLoader css = new CssLoader("/applicationanalyzer/FXML/CSS/Checks.css", "checkParsGrid");
        if (grid != null) {
            grid.getColumnConstraints().add(new ColumnConstraints(css.GRID_COL1_WIDTH));
            grid.getColumnConstraints().add(new ColumnConstraints(css.GRID_COL2_WIDTH));
            checkParsPane.getChildren().add(grid);
        }
    }
}

/*
 import applicationanalyzer.DataClasses.CheckSuits;
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
 import javafx.scene.control.Alert;
 import applicationanalyzer.DataClasses.Checks;
 import applicationanalyzer.misc.Alerts;
 import applicationanalyzer.misc.DataStore;
 import applicationanalyzer.misc.SQLExecutor;
 import java.sql.ResultSetMetaData;
 import javafx.event.EventHandler;
 import javafx.fxml.FXMLLoader;
 import javafx.geometry.Pos;
 import javafx.scene.Parent;
 import javafx.scene.Scene;
 import javafx.scene.control.Alert.AlertType;
 import javafx.scene.control.CheckBox;
 import javafx.scene.control.ContextMenu;
 import javafx.scene.control.Label;
 import javafx.scene.control.MenuBar;
 import javafx.scene.control.MenuItem;
 import javafx.scene.control.TableCell;
 import javafx.scene.control.TableColumn.CellEditEvent;
 import javafx.scene.control.TableRow;
 import javafx.scene.input.MouseButton;
 import javafx.scene.input.MouseEvent;
 import javafx.scene.layout.AnchorPane;
 import javafx.scene.layout.GridPane;
 import javafx.stage.Stage;
 import javafx.util.Callback;

 public class ChecksController implements Initializable {
    
 @FXML
 private AnchorPane checks;
 @FXML
 private TableView<Checks> tblv_Checks;
 @FXML
 private TableColumn chk_code;
 @FXML
 private TableColumn chk_mnemo;
 @FXML
 private TableColumn chk_type;
 @FXML
 private TableColumn chk_comment;
 @FXML
 private TableColumn chk_active;
 @FXML
 private TableColumn chk_chs_code;
 @FXML
 private GridPane grd_check_pars;
 @FXML
 private MenuBar menuBar;

 DataStore dataStore = DataStore.getDataStore();
 CheckSuits checkSuits;

 public void handleGetData(ActionEvent event) {
 //Define new connection
 initialize(null, null);
 }


 // Menu Handlers
 @FXML
 public void handleQuit(ActionEvent event) {
 Stage application_stage = (Stage) menuBar.getScene().getWindow();
 application_stage.close();
 }

 @Override
 public void initialize(URL url, ResourceBundle rb) {
 DataStore data = DataStore.getDataStore();
 Integer currSuite = data.getCurrentSuite();
 if (currSuite == null) {
 currSuite = 1;
 }
 String sql = "select * from B_CHECKS where chk_chs_code = " + checkSuits.getChsCode();
 ResultSet query_results = SQLExecutor.executeQuery(sql);
 ObservableList<Checks> obsArrayList = FXCollections.observableArrayList();

 //Initialize the checks view
 try {
 while (query_results.next()) {
 Checks queryData = new Checks(query_results.getInt(1),
 query_results.getString(2),
 query_results.getString(3),
 query_results.getString(4),
 query_results.getInt(5),
 query_results.getInt(6));
 obsArrayList.add(queryData);
 }
 //new StringToDecimal() is a simple implementation of StringConverter<Number>
 chk_code.setCellValueFactory(new PropertyValueFactory<>("ChkCode"));
 chk_mnemo.setCellValueFactory(new PropertyValueFactory<>("ChkMnemo"));
 chk_type.setCellValueFactory(new PropertyValueFactory<>("ChkType"));
 chk_comment.setCellValueFactory(new PropertyValueFactory<>("ChkComment"));
 chk_active.setCellValueFactory(new PropertyValueFactory<>("ChkActive"));
 chk_active.setCellFactory(new CheckBoxCellFactory());
 tblv_Checks.setRowFactory(rowfactory -> {
 TableRow<Checks> row = new TableRow<>();
 row.setOnMouseClicked(MouseEventHandler(row));
 return row;
 });
 tblv_Checks.setItems(obsArrayList);
 tblv_Checks.getColumns().setAll(chk_code, chk_mnemo, chk_type, chk_comment, chk_active);
 } catch (SQLException sqle) {
 Alerts.AlertSQL(sqle);
 }

 tblv_Checks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showCheckParsData(newValue));
 //Generate Grid View for parameters

 }

 private void showCheckParsData(Checks check) {
 if (check != null) {
 int k = 0;
 String queryDataSql = "";

 switch (check.getChkType()) {
 case "MIN":
 case "MAX":
 queryDataSql = "select * from v_minmax_check_pars where chk_code = " + check.getChkCode();
 break;
 case "RPREDICT":
 queryDataSql = "select * from b_rpredict_check_pars where rcp_chk_code = " + check.getChkCode();
 break;
 }

 ResultSet dataValues = SQLExecutor.executeQuery(queryDataSql);
 ResultSetMetaData metaData;
 grd_check_pars.getChildren().clear();
 try {
 metaData = dataValues.getMetaData();
 dataValues.next();
 for (int i = 2; i <= metaData.getColumnCount(); i++) {
 Label fieldNameLbl = new Label(metaData.getColumnName(i));
 Label fieldValueLbl = new Label(dataValues.getString(i));
 grd_check_pars.add(fieldNameLbl, 0, k);
 grd_check_pars.add(fieldValueLbl, 1, k);
 k++;
 }
 } catch (SQLException sqle) {
 Alerts.AlertSQL(sqle);
 }
 }
 }

 public class CheckBoxCellFactory<T, Boolean> implements Callback {

 @Override
 public TableCell call(Object param) {
 CheckBox checkBox = new CheckBox();
 TableCell<Checks, Boolean> checkBoxCell = new TableCell() {
 @Override
 public void updateItem(Object item, boolean empty) {
 super.updateItem(item, empty);

 if (empty || item == null) {
 setText(null);
 setGraphic(null);
 } else {
 checkBox.setAlignment(Pos.CENTER);
 setGraphic(checkBox);
 Checks data = (Checks) getTableRow().getItem();
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
 Checks data = (Checks) c.getTableRow().getItem();

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

 private EventHandler<MouseEvent> MouseEventHandler(TableRow<Checks> row) {
 EventHandler<MouseEvent> mouseEvent
 = new EventHandler<MouseEvent>() {
 @Override
 public void handle(final MouseEvent event) {
 if (event.getClickCount() >= 2) {
 //Add DoubleClick mess here.
                           
 } else if (event.getButton() == MouseButton.SECONDARY) {
 ContextMenu contextMenu = new ContextMenu();
 MenuItem editItem = new MenuItem("Edit");
 Checks check = row.getItem();
 editItem.setOnAction(new EventHandler<ActionEvent>() {
 @Override
 public void handle(ActionEvent e) {
 check.showEditDialog(checks.getScene().getWindow());
 initialize(null,null);
 }
 });
 contextMenu.getItems().add(editItem);
 contextMenu.show(row, event.getScreenX(), event.getScreenY());
 }
 }
 };
 return mouseEvent;
 }
    
 //TODO Fix the placeholder stuff (this could use a popup window anyways).
 @FXML
 public void handleRunSuiteChecks(ActionEvent event) {
 boolean execution = SQLExecutor.executeProcedure("CHECKS.RUN_CHECKS(" + dataStore.getCurrentSuite() + ", 'Placeholder')");
 }
    
 public void initObject(CheckSuits newCheckSuits) {
 checkSuits = newCheckSuits;
 }
    
 public void setStage(Stage stage) {}

 }
 */
