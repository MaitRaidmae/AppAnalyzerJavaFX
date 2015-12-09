package applicationanalyzer.FXControllers;

import applicationanalyzer.DataClasses.Applications;
import applicationanalyzer.misc.Alerts;
import applicationanalyzer.misc.CallableStatementResults;
import applicationanalyzer.misc.DataStore;
import applicationanalyzer.misc.SQLExecutor;
import applicationanalyzer.misc.StringToDecimal;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ApplicationsController implements Initializable {

    @FXML
    private TableView tblv_Applications;
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

    public void handleGetData(ActionEvent event) {
        if (resultsField.getText().equals("")) {
            resultsField.setText("0");
        }
        showResults(Integer.parseInt(resultsField.getText()));
    }

    @FXML
    public void handleAplCodeEditCommit(CellEditEvent<Applications, Integer> t) {
        Applications data = (Applications) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplCode(t.getNewValue());
    }

    @FXML
    public void handleAplNameEditCommit(CellEditEvent<Applications, String> t) {
        Applications data = (Applications) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplName(t.getNewValue());
    }

    @FXML
    public void handleAplIncomeEditCommit(CellEditEvent<Applications, Double> t) {
        Applications data = (Applications) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplIncome(t.getNewValue());
    }

    @FXML
    public void handleAplObligationsEditCommit(CellEditEvent<Applications, Double> t) {
        Applications data = (Applications) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplObligations(t.getNewValue());
    }

    @FXML
    public void handleAplReserveEditCommit(CellEditEvent<Applications, Double> t) {
        Applications data = (Applications) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplReserve(t.getNewValue());
    }

    @FXML
    public void handleAplDebtToIncomeEditCommit(CellEditEvent<Applications, Double> t) {
        Applications data = (Applications) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplDebtToIncome(t.getNewValue());
    }

    @FXML
    public void handleAplAgeEditCommit(CellEditEvent<Applications, Double> t) {
        Applications data = (Applications) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplAge(t.getNewValue());
    }

    @FXML
    public void handleAplEducationEditCommit(CellEditEvent<Applications, String> t) {
        Applications data = (Applications) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplEducation(t.getNewValue());
    }

    @FXML
    public void handleAplRejectedEditCommit(CellEditEvent<Applications, Integer> t) {
        Applications data = (Applications) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplRejected(t.getNewValue());
    }

    @FXML
    public void handleAplInDefaultEditCommit(CellEditEvent<Applications, Integer> t) {
        Applications data = (Applications) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplInDefault(t.getNewValue());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultsField.setText("200");
        showResults(Integer.parseInt(resultsField.getText()));
    }

    @FXML
    public void updateResults(ActionEvent event) {
        if (resultsField.getText().equals("")) {
            resultsField.setText("0");
        }
        Integer pageNr = Integer.parseInt(resultsField.getText());
        showResults(pageNr);
    }

    public void showResults(Integer results) {
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
            apl_code.setCellFactory(TextFieldTableCell.forTableColumn(new StringToDecimal("Integer")));
            apl_name.setCellValueFactory(new PropertyValueFactory<>("AplName"));
            apl_name.setCellFactory(TextFieldTableCell.forTableColumn());
            apl_income.setCellValueFactory(new PropertyValueFactory<>("AplIncome"));
            apl_income.setCellFactory(TextFieldTableCell.forTableColumn(new StringToDecimal("Double")));
            apl_obligations.setCellValueFactory(new PropertyValueFactory<>("AplObligations"));
            apl_obligations.setCellFactory(TextFieldTableCell.forTableColumn(new StringToDecimal("Double")));
            apl_reserve.setCellValueFactory(new PropertyValueFactory<>("AplReserve"));
            apl_reserve.setCellFactory(TextFieldTableCell.forTableColumn(new StringToDecimal("Double")));
            apl_debt_to_income.setCellValueFactory(new PropertyValueFactory<>("AplDebtToIncome"));
            apl_debt_to_income.setCellFactory(TextFieldTableCell.forTableColumn(new StringToDecimal("Double")));
            apl_age.setCellValueFactory(new PropertyValueFactory<>("AplAge"));
            apl_age.setCellFactory(TextFieldTableCell.forTableColumn(new StringToDecimal("Double")));
            apl_education.setCellValueFactory(new PropertyValueFactory<>("AplEducation"));
            apl_education.setCellFactory(TextFieldTableCell.forTableColumn());
            apl_rejected.setCellValueFactory(new PropertyValueFactory<>("AplRejected"));
            apl_rejected.setCellFactory(TextFieldTableCell.forTableColumn(new StringToDecimal("Integer")));
            apl_in_default.setCellValueFactory(new PropertyValueFactory<>("AplInDefault"));
            apl_in_default.setCellFactory(TextFieldTableCell.forTableColumn(new StringToDecimal("Integer")));
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
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errror!!! AAAaaaaerrgggh!!");
            alert.setHeaderText("Error loading new window");
            System.out.println("Error " + e.getMessage());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
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
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errror!!! AAAaaaaerrgggh!!");
            alert.setHeaderText("Error loading new window");
            System.out.println("Error " + e.getMessage());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private EventHandler<MouseEvent> MouseEventHandler(TableRow<Applications> row) {
        EventHandler<MouseEvent> mouseEvent
                = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent event) {
                        if (event.getClickCount() >= 2) {
                            DataStore dataStore = DataStore.getDataStore();
                            dataStore.setCurrentApp(row.getItem());
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/applicationanalyzer/FXML/ApplicationChecks.fxml"));
                                Parent root1 = (Parent) fxmlLoader.load();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root1));
                                stage.setTitle("Application Checks");
                                stage.getIcons().add(new Image("/applicationanalyzer/icon.png"));
                                stage.show();
                            } catch (Exception e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Errror!!! AAAaaaaerrgggh!!");
                                alert.setHeaderText("Error loading new window");
                                System.out.println("Error loading new window " + e.getMessage());
                                alert.setContentText(e.getMessage());
                                alert.showAndWait();
                            }
                        } else if (event.getButton() == MouseButton.SECONDARY) {
                            ContextMenu contextMenu = new ContextMenu();
                            MenuItem item = new MenuItem("Edit");
                            contextMenu.getItems().add(item);
                            contextMenu.show(row,event.getScreenX(),event.getScreenY());
                        }
                    }
                };
        return mouseEvent;
    }
}
