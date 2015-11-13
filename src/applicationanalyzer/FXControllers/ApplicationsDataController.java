package applicationanalyzer.FXControllers;

import applicationanalyzer.DataClasses.ApplicationsData;
import applicationanalyzer.misc.ConnectionManager;
import applicationanalyzer.misc.DataStore;
import applicationanalyzer.misc.StringToDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ListResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ApplicationsDataController implements Initializable {

    @FXML
    private TableView tblv_ApplicationsData;
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
    private MenuBar menuBar;

    public void handleGetData(ActionEvent event) {
//Define new connection
        ConnectionManager connection = new ConnectionManager();
        Connection db_connection;
        db_connection = ConnectionManager.cl_conn;
        Statement db_stmt;
        ResultSet query_results;
        ObservableList<ApplicationsData> obsArrayList = FXCollections.observableArrayList();
        String sql = "select * from B_APPLICATIONS where rownum<200";

        try {
            db_stmt = db_connection.createStatement();
            query_results = db_stmt.executeQuery(sql);
            while (query_results.next()) {
                ApplicationsData table_row = new ApplicationsData(query_results.getInt(1),
                        query_results.getString(2),
                        query_results.getDouble(3),
                        query_results.getDouble(4),
                        query_results.getDouble(5),
                        query_results.getDouble(6),
                        query_results.getDouble(7),
                        query_results.getString(8),
                        query_results.getInt(9),
                        query_results.getInt(10));
                obsArrayList.add(table_row);
            }
            //TODO Add imports to the datagenerator for StringToDecimal and TextFieldTableCell
//new StringToDecimal() is a simple implementation of StringConverter<Number>
            apl_code.setCellValueFactory(new PropertyValueFactory<>("AplCode"));
            apl_code.setCellFactory(TextFieldTableCell.forTableColumn(new StringToDecimal("Integer")));
            apl_name.setCellValueFactory(new PropertyValueFactory<>("AplName"));
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
            tblv_ApplicationsData.setItems(obsArrayList);
            tblv_ApplicationsData.getColumns().setAll(apl_code, apl_name, apl_income, apl_obligations, apl_reserve, apl_debt_to_income, apl_age, apl_education, apl_rejected, apl_in_default);
        } catch (SQLException sqle) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The following error occured when trying to execute:" + sql);
            alert.setContentText(sqle.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    public void handleAplCodeEditCommit(CellEditEvent<ApplicationsData, Integer> t) {
        ApplicationsData data = (ApplicationsData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplCode(t.getNewValue());
    }

    @FXML
    public void handleAplNameEditCommit(CellEditEvent<ApplicationsData, String> t) {
        ApplicationsData data = (ApplicationsData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplName(t.getNewValue());
    }

    @FXML
    public void handleAplIncomeEditCommit(CellEditEvent<ApplicationsData, Double> t) {
        ApplicationsData data = (ApplicationsData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplIncome(t.getNewValue());
    }

    @FXML
    public void handleAplObligationsEditCommit(CellEditEvent<ApplicationsData, Double> t) {
        ApplicationsData data = (ApplicationsData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplObligations(t.getNewValue());
    }

    @FXML
    public void handleAplReserveEditCommit(CellEditEvent<ApplicationsData, Double> t) {
        ApplicationsData data = (ApplicationsData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplReserve(t.getNewValue());
    }

    @FXML
    public void handleAplDebtToIncomeEditCommit(CellEditEvent<ApplicationsData, Double> t) {
        ApplicationsData data = (ApplicationsData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplDebtToIncome(t.getNewValue());
    }

    @FXML
    public void handleAplAgeEditCommit(CellEditEvent<ApplicationsData, Double> t) {
        ApplicationsData data = (ApplicationsData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplAge(t.getNewValue());
    }

    @FXML
    public void handleAplEducationEditCommit(CellEditEvent<ApplicationsData, String> t) {
        ApplicationsData data = (ApplicationsData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplEducation(t.getNewValue());
    }

    @FXML
    public void handleAplRejectedEditCommit(CellEditEvent<ApplicationsData, Integer> t) {
        ApplicationsData data = (ApplicationsData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplRejected(t.getNewValue());
    }

    @FXML
    public void handleAplInDefaultEditCommit(CellEditEvent<ApplicationsData, Integer> t) {
        ApplicationsData data = (ApplicationsData) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setAplInDefault(t.getNewValue());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    // Custom handlers not generated by the code synth.
    @FXML
    public void handleQuit(ActionEvent event) {
        Stage application_stage = (Stage) menuBar.getScene().getWindow();
        application_stage.close();
    }
    
    // TODO - Remove this mess and add a cellFactory
    @FXML
    public void applicantNameClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Node node = ((Node) event.getTarget()).getParent();
            TableRow<ApplicationsData> row;
            if (node instanceof TableRow) {
                row = (TableRow) node;
            } else {
                // clicking on text part
                row = (TableRow) node.getParent();
            }
            DataStore dataStore = DataStore.getDataStore();
            dataStore.setCurrentApp(row.getItem());
            System.out.println(dataStore.toString());
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/applicationanalyzer/FXML/ApplicationChecks.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.setTitle(row.getItem().getAplName());
                stage.getIcons().add(new Image("/applicationanalyzer/icon.png"));
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Errror!!! AAAaaaaerrgggh!!");
                alert.setHeaderText("Error loading new window");
                System.out.println("Error loading new window " + e.getMessage());
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void handleViewChecks(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/applicationanalyzer/FXML/Checks.fxml"));
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
}
/*    
    public class ApplicationInfoRb extends ListResourceBundle {

        @Override
        protected Object[][] getContents() {
            return new Object[][]{
                // Hacked ResourceBundle to do my bidding There's probably a more reasonable way to do this.
                {"apl_code", application_code.toString()},};
        }
    }
}
    */
