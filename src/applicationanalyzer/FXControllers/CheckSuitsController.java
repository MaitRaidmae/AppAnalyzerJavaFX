/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.FXControllers;

/**
 *
 * @author Hundisilm
 */
import applicationanalyzer.DataClasses.CheckSuits;
import applicationanalyzer.misc.Alerts;
import applicationanalyzer.misc.ConnectionManager;
import applicationanalyzer.misc.DataStore;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CheckSuitsController implements Initializable {

    @FXML
    private TableView tblv_CheckSuits;
    @FXML
    private TableColumn chs_code;
    @FXML
    private TableColumn chs_mnemo;
    @FXML
    private TableColumn chs_comment;
    @FXML
    private TableColumn chs_datetime;
    
    DataStore dataStore = DataStore.getDataStore();
    
    public void handleGetData(ActionEvent event) {
//Define new connection
        ConnectionManager connection = new ConnectionManager();
        Connection db_connection;
        db_connection = ConnectionManager.cl_conn;
        Statement db_stmt;
        ResultSet query_results;
        ObservableList<CheckSuits> obsArrayList = FXCollections.observableArrayList();
        String sql = "select * from B_CHECK_SUITS where rownum<200";

        try {
            db_stmt = db_connection.createStatement();
            query_results = db_stmt.executeQuery(sql);
            while (query_results.next()) {
                CheckSuits queryData = new CheckSuits(query_results.getInt(1),
                        query_results.getString(2),
                        query_results.getString(3),
                        query_results.getDate(4));
                obsArrayList.add(queryData);
            }
            chs_code.setCellValueFactory(new PropertyValueFactory<>("ChsCode"));
            chs_mnemo.setCellValueFactory(new PropertyValueFactory<>("ChsMnemo"));
            chs_comment.setCellValueFactory(new PropertyValueFactory<>("ChsComment"));
            chs_datetime.setCellValueFactory(new PropertyValueFactory<>("ChsDatetime"));
            tblv_CheckSuits.setRowFactory(rowfactory -> {
              TableRow<CheckSuits> row = new TableRow<>();
              row.setOnMouseClicked(MouseEventHandler(row));
              return row;
            });
            tblv_CheckSuits.setItems(obsArrayList);
            tblv_CheckSuits.getColumns().setAll(chs_code, chs_mnemo, chs_comment, chs_datetime);
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }

    }

    @FXML
    public void handleChsCodeEditCommit(CellEditEvent<CheckSuits, Integer> t
    ) {
        CheckSuits data = (CheckSuits) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setChsCode(t.getNewValue());
    }

    @FXML
    public void handleChsMnemoEditCommit(CellEditEvent<CheckSuits, String> t
    ) {
        CheckSuits data = (CheckSuits) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setChsMnemo(t.getNewValue());
    }

    @FXML
    public void handleChsCommentEditCommit(CellEditEvent<CheckSuits, String> t
    ) {
        CheckSuits data = (CheckSuits) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setChsComment(t.getNewValue());
    }

    @FXML
    public void handleChsDatetimeEditCommit(CellEditEvent<CheckSuits, Date> t
    ) {
        CheckSuits data = (CheckSuits) t.getTableView().getItems().get(
                t.getTablePosition().getRow());
        data.setChsDatetime(t.getNewValue());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
    }

    private EventHandler<MouseEvent> MouseEventHandler(TableRow<CheckSuits> row) {
        EventHandler<MouseEvent> doubleClick
                = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent doubleClick) {
                        if (doubleClick.getClickCount() >= 2) {
                            dataStore.setCurrentSuite(row.getItem().getChsCode());
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/applicationanalyzer/FXML/Checks.fxml"));
                                Parent root1 = (Parent) fxmlLoader.load();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root1));
                                stage.setTitle("Suite: " + row.getItem().getChsMnemo());
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
                        }
                    }
                };
        return doubleClick;
    }
}
