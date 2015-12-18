package applicationanalyzer.FXControllers;

import applicationanalyzer.DataClasses.Applications;
import applicationanalyzer.misc.Alerts;
import applicationanalyzer.misc.CallableStatementResults;
import applicationanalyzer.misc.CssLoader;
import applicationanalyzer.misc.Helpers;
import applicationanalyzer.misc.SQLExecutor;
import java.io.IOException;
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
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ApplicationsController implements Initializable {

    @FXML
    private AnchorPane Applications;
    @FXML
    private AnchorPane applicationsPane;

    Applications applications;
    Stage stage;


    public void showResults(Integer results) {

        ObservableList<Applications> obsArrayList = FXCollections.observableArrayList();
        CallableStatementResults callResults = SQLExecutor.getTablePage("P_APPLICATIONS", 1, results);
        ResultSet query_results = callResults.getResultSet();
        TableView<Applications> applicationsTable = new TableView();
        AnchorPane.setBottomAnchor(applicationsTable, 0.0);
        AnchorPane.setTopAnchor(applicationsTable, 0.0);
        AnchorPane.setRightAnchor(applicationsTable, 0.0);
        AnchorPane.setLeftAnchor(applicationsTable, 0.0);
        applicationsPane.getChildren().add(applicationsTable);
        applicationsTable.getStyleClass().add("applicationsTable");
        
        TableColumn apl_code            = Helpers.initTableColumn("B_APPLICATIONS", "APL_CODE");
        TableColumn apl_name            = Helpers.initTableColumn("B_APPLICATIONS", "APL_NAME");
        TableColumn apl_income          = Helpers.initTableColumn("B_APPLICATIONS", "APL_INCOME");
        TableColumn apl_obligations     = Helpers.initTableColumn("B_APPLICATIONS", "APL_OBLIGATIONS");
        TableColumn apl_reserve         = Helpers.initTableColumn("B_APPLICATIONS", "APL_RESERVE");
        TableColumn apl_debt_to_income  = Helpers.initTableColumn("B_APPLICATIONS", "APL_DEBT_TO_INCOME");
        TableColumn apl_age             = Helpers.initTableColumn("B_APPLICATIONS", "APL_AGE");
        TableColumn apl_education       = Helpers.initTableColumn("B_APPLICATIONS", "APL_EDUCATION");
        TableColumn apl_rejected        = Helpers.initTableColumn("B_APPLICATIONS", "APL_REJECTED");
        TableColumn apl_in_default      = Helpers.initTableColumn("B_APPLICATIONS", "APL_IN_DEFAULT");
        
        try {
            while (query_results.next()) {
                Applications dataInstance = new Applications(query_results.getInt(1),
                        query_results.getString(2),
                        query_results.getDouble(3),
                        query_results.getDouble(4),
                        query_results.getDouble(5),
                        query_results.getDouble(6),
                        query_results.getDouble(7),
                        query_results.getString(8),
                        query_results.getInt(9),
                        query_results.getInt(10));
                obsArrayList.add(dataInstance);
            }
            apl_code.setCellValueFactory(new PropertyValueFactory<>("AplCode"));
            apl_name.setCellValueFactory(new PropertyValueFactory<>("AplName"));
            apl_income.setCellValueFactory(new PropertyValueFactory<>("AplIncome"));
            apl_obligations.setCellValueFactory(new PropertyValueFactory<>("AplObligations"));
            apl_reserve.setCellValueFactory(new PropertyValueFactory<>("AplReserve"));
            apl_debt_to_income.setCellValueFactory(new PropertyValueFactory<>("AplDebtToIncome"));
            apl_age.setCellValueFactory(new PropertyValueFactory<>("AplAge"));
            apl_education.setCellValueFactory(new PropertyValueFactory<>("AplEducation"));
            apl_rejected.setCellValueFactory(new PropertyValueFactory<>("AplRejected"));
            apl_in_default.setCellValueFactory(new PropertyValueFactory<>("AplInDefault"));
            applicationsTable.setRowFactory(rowfactory -> {
                TableRow<Applications> row = new TableRow<>();
                row.setOnMouseClicked(MouseEventHandler(row));
                return row;
            });
            System.out.println(obsArrayList.get(1).getAplName());
            applicationsTable.getColumns().setAll(apl_code, apl_name, apl_income, apl_obligations, apl_reserve, apl_debt_to_income, apl_age, apl_education, apl_rejected, apl_in_default);
            applicationsTable.setItems(obsArrayList);
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
        callResults.close();

    }

    private EventHandler<MouseEvent> MouseEventHandler(TableRow<Applications> row) {
        EventHandler<MouseEvent> mouseEvent
                = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent event) {
                        Applications currApp = row.getItem();
                        if (event.getClickCount() >= 2) {
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/applicationanalyzer/FXML/ApplicationChecks.fxml"));
                                AnchorPane page = (AnchorPane) fxmlLoader.load();
                                Stage editStage = new Stage();
                                editStage.setTitle("Application Checks for: " + currApp.getAplName());
                                editStage.initModality(Modality.WINDOW_MODAL);
                                editStage.initOwner(Applications.getScene().getWindow());
                                Scene scene = new Scene(page);
                                editStage.setScene(scene);
                                scene.getStylesheets().add("/applicationanalyzer/FXML/CSS/ApplicationChecks.css");
                                ApplicationChecksController controller = fxmlLoader.getController();
                                controller.initObject(currApp);
                                controller.setStage(editStage);
                                editStage.showAndWait();
                            } catch (IOException e) {
                                Alerts.AlertIO(e);
                            }
                        } else if (event.getButton() == MouseButton.SECONDARY) {
// Add rightClick Event here (open popupMenu for example
                        }
                    }
                };
        return mouseEvent;
    }

    public void setStage(Stage newStage) {
        stage = newStage;
    }

    public void initObject(Applications newInstance) {

        applications = newInstance;
        GridPane applicationsGrid = applications.getGrid(true);
        CssLoader css = new CssLoader("/applicationanalyzer/FXML/CSS/Applications.css", "applicationsGrid");
        applicationsGrid.getColumnConstraints().add(new ColumnConstraints(css.GRID_COL1_WIDTH));
        applicationsGrid.getColumnConstraints().add(new ColumnConstraints(css.GRID_COL2_WIDTH));
        applicationsGrid.getStyleClass().add("applicationsGrid");
        applicationsPane.getChildren().add(applicationsGrid);
    }

    // Custom Stuff below here:
    
    @FXML
    private MenuBar menuBar;
    @FXML
    private TextField resultsField;
    
    @FXML
    public void handleQuit(ActionEvent event) {
        Stage application_stage = (Stage) menuBar.getScene().getWindow();
        application_stage.close();
    }

    @FXML
    public void handleViewSuits(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/applicationanalyzer/FXML/CheckSuits.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage newStage = new Stage();
            newStage.setTitle("Check suits");
            newStage.setScene(new Scene(root1));
            newStage.show();
        } catch (Exception e) {
            Alerts.AlertWindow(e);
        }
    }

    @FXML
    public void handleViewImage(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/applicationanalyzer/FXML/ImageView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage imageStage = new Stage();
            imageStage.setTitle("An Image!!!!");
            imageStage.setScene(new Scene(root1));
            imageStage.show();
        } catch (Exception e) {
            Alerts.AlertWindow(e);
        }
    }
    
    @FXML
    private void updateResults(ActionEvent event) {
        Integer results = Integer.parseInt(resultsField.getText());
        showResults(results);
    }
    
    @FXML
    public void searchData(ActionEvent event) {
        Integer results = Integer.parseInt(resultsField.getText());
        showResults(results);
    }
    
     @Override
     public void initialize(URL url, ResourceBundle rb) {
        resultsField.setText("200");
        showResults(200);
     }
}

/*
 import applicationanalyzer.DataClasses.Applications;
 import applicationanalyzer.misc.Alerts;
 import applicationanalyzer.misc.CallableStatementResults;
 import applicationanalyzer.misc.SQLExecutor;
 import java.io.IOException;
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
 import javafx.scene.Parent;
 import javafx.scene.Scene;
 import javafx.scene.control.MenuBar;
 import javafx.scene.control.TableColumn;
 import javafx.scene.control.TableView;
 import javafx.scene.control.cell.PropertyValueFactory;
 import javafx.scene.control.TableRow;
 import javafx.scene.control.TextField;
 import javafx.scene.input.MouseButton;
 import javafx.scene.input.MouseEvent;
 import javafx.scene.layout.AnchorPane;
 import javafx.scene.layout.VBox;
 import javafx.stage.Modality;
 import javafx.stage.Stage;

 public class ApplicationsController implements Initializable {

 @FXML
 private TableView tblv_Applications;
 @FXML
 private AnchorPane Applications;
 @FXML
 private TableColumn apl_code;
 @FXML
 private TableColumn apl_name;
 @FXML
 private TableColumn apl_income;
 @FXML
 private TableColumn apl_obligations;
 @FXML
 private TableColumn apl_reserve;
 @FXML
 private TableColumn apl_debt_to_income;
 @FXML
 private TableColumn apl_age;
 @FXML
 private TableColumn apl_education;
 @FXML
 private TableColumn apl_rejected;
 @FXML
 private TableColumn apl_in_default;
 @FXML
 private TextField resultsField;

 @FXML
 private MenuBar menuBar;

 private Integer results = 200;

 @FXML
 public void searchData(ActionEvent event) {
 results = Integer.parseInt(resultsField.getText());
 showResults();
 }

 public void showResults() {

 ObservableList<Applications> obsArrayList = FXCollections.observableArrayList();
 CallableStatementResults callResults = SQLExecutor.getTablePage("P_APPLICATIONS", 1, results);
 ResultSet query_results = callResults.getResultSet();
 try {
 while (query_results.next()) {
 Applications dataInstance = new Applications(query_results.getInt(1),
 query_results.getString(2),
 query_results.getDouble(3),
 query_results.getDouble(4),
 query_results.getDouble(5),
 query_results.getDouble(6),
 query_results.getDouble(7),
 query_results.getString(8),
 query_results.getInt(9),
 query_results.getInt(10));
 obsArrayList.add(dataInstance);
 }
 apl_code.setCellValueFactory(new PropertyValueFactory<>("AplCode"));
 apl_name.setCellValueFactory(new PropertyValueFactory<>("AplName"));
 apl_income.setCellValueFactory(new PropertyValueFactory<>("AplIncome"));
 apl_obligations.setCellValueFactory(new PropertyValueFactory<>("AplObligations"));
 apl_reserve.setCellValueFactory(new PropertyValueFactory<>("AplReserve"));
 apl_debt_to_income.setCellValueFactory(new PropertyValueFactory<>("AplDebtToIncome"));
 apl_age.setCellValueFactory(new PropertyValueFactory<>("AplAge"));
 apl_education.setCellValueFactory(new PropertyValueFactory<>("AplEducation"));
 apl_rejected.setCellValueFactory(new PropertyValueFactory<>("AplRejected"));
 apl_in_default.setCellValueFactory(new PropertyValueFactory<>("AplInDefault"));
 tblv_Applications.setRowFactory(rowfactory -> {
 TableRow<Applications> row = new TableRow<>();
 row.setOnMouseClicked(MouseEventHandler(row));
 return row;
 });
 tblv_Applications.setItems(obsArrayList);
 tblv_Applications.getColumns().setAll(apl_code, apl_name, apl_income, apl_obligations, apl_reserve, apl_debt_to_income, apl_age, apl_education, apl_rejected, apl_in_default);
 } catch (SQLException sqle) {
 Alerts.AlertSQL(sqle);
 }
 callResults.close();

 }

 @Override
 public void initialize(URL url, ResourceBundle rb) {
 resultsField.setText(results.toString());
 showResults();
 }

 @FXML
 private void updateResults(ActionEvent event) {
 results = Integer.parseInt(resultsField.getText());
 showResults();
 }

 private EventHandler<MouseEvent> MouseEventHandler(TableRow<Applications> row) {
 EventHandler<MouseEvent> mouseEvent
 = new EventHandler<MouseEvent>() {
 @Override
 public void handle(final MouseEvent event) {
                        
 Applications currApp = row.getItem();
 if (event.getClickCount() >= 2) {
 try {
 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/applicationanalyzer/FXML/ApplicationChecks.fxml"));
 AnchorPane page = (AnchorPane) fxmlLoader.load();
 Stage editStage = new Stage();
 editStage.setTitle("Application Checks for: " + currApp.getAplName());
 editStage.initModality(Modality.WINDOW_MODAL);
 editStage.initOwner(Applications.getScene().getWindow());
 Scene scene = new Scene(page);
 editStage.setScene(scene);
 scene.getStylesheets().add("/applicationanalyzer/FXML/CSS/ApplicationChecks.css");
 ApplicationChecksController controller = fxmlLoader.getController();
 controller.initObject(currApp);
 controller.setStage(editStage);
 editStage.showAndWait();
 } catch (IOException e) {
                                
 Alerts.AlertIO(e);
 }
 } else if (event.getButton() == MouseButton.SECONDARY) {
 // Add rightClick Event here (open popupMenu for example.
 }
 }
 };
 return mouseEvent;
 }

 // Custom handlers not generated by the code synth.
 @FXML
 public void handleQuit(ActionEvent event) {
 Stage application_stage = (Stage) menuBar.getScene().getWindow();
 application_stage.close();
 }

 @FXML
 public void handleViewSuits(ActionEvent event) {
 try {
 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/applicationanalyzer/FXML/CheckSuits.fxml"));
 Parent root1 = (Parent) fxmlLoader.load();
 Stage stage = new Stage();
 stage.setTitle("Check suits");
 stage.setScene(new Scene(root1));
 stage.show();
 } catch (Exception e) {
 Alerts.AlertWindow(e);
 }
 }

 @FXML
 public void handleViewImage(ActionEvent event) {
 try {
 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/applicationanalyzer/FXML/ImageView.fxml"));
 Parent root1 = (Parent) fxmlLoader.load();
 Stage stage = new Stage();
 stage.setTitle("An Image!!!!");
 stage.setScene(new Scene(root1));
 stage.show();
 } catch (Exception e) {
 Alerts.AlertWindow(e);
 }
 }

 }
 */
