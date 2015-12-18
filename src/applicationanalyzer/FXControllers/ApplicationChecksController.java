/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.FXControllers;

import applicationanalyzer.DataClasses.ApplicationChecks;
import applicationanalyzer.DataClasses.Applications;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ApplicationChecksController implements Initializable {

    @FXML
    private AnchorPane ApplicationChecks;
    @FXML
    private AnchorPane applicationsPane;
    @FXML
    private AnchorPane applicationChecksPane;
    Applications applications;
    
    ApplicationChecks applicationChecks;
    
    Stage stage;

    @FXML
    public void searchData(ActionEvent event) {
        showResults(45);
    }

    public void showResults(Integer results) {

        GridPane apps_grid = applications.getGrid(false);
        applicationsPane.getChildren().add(apps_grid);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setStage(Stage newStage) {
        stage = newStage;
    }

    public void initObject(Applications newInstance) {

        applications = newInstance;
        GridPane applicationsGrid = applications.getGrid(false);
        CssLoader css = new CssLoader("/applicationanalyzer/FXML/CSS/ApplicationChecks.css", "applicationsGrid");
        applicationsGrid.getColumnConstraints().add(new ColumnConstraints(css.GRID_COL1_WIDTH));
        applicationsGrid.getColumnConstraints().add(new ColumnConstraints(css.GRID_COL2_WIDTH));
        applicationsGrid.getStyleClass().add("applicationsGrid");
        applicationsPane.getChildren().add(applicationsGrid);
        
        ObservableList<ApplicationChecks> obsArrayList = FXCollections.observableArrayList();
        CallableStatementResults callResults = SQLExecutor.getTableRow("P_APPLICATION_CHECKS", "APL_CODE", newInstance.getAplCode());
        ResultSet query_results = callResults.getResultSet();
        TableView<ApplicationChecks> applicationChecksTable = new TableView();
        AnchorPane.setBottomAnchor(applicationChecksTable, 0.0);
        AnchorPane.setTopAnchor(applicationChecksTable, 0.0);
        AnchorPane.setRightAnchor(applicationChecksTable, 0.0);
        AnchorPane.setLeftAnchor(applicationChecksTable, 0.0);
        
        TableColumn apl_code    = Helpers.initTableColumn("V_APPLICATION_CHECKS","APL_CODE");
        TableColumn chk_mnemo   = Helpers.initTableColumn("V_APPLICATION_CHECKS","CHK_MNEMO");
        TableColumn chk_value   = Helpers.initTableColumn("V_APPLICATION_CHECKS","CHK_VALUE");
        TableColumn run_date    = Helpers.initTableColumn("V_APPLICATION_CHECKS","RUN_DATE");
        TableColumn run_comment = Helpers.initTableColumn("V_APPLICATION_CHECKS","RUN_COMMENT");
        
        applicationChecksTable.getStyleClass().add("applicationChecksTable"); //TODO Add this to code synth
        try {
            while (query_results.next()) {
                ApplicationChecks dataInstance = new ApplicationChecks(query_results.getInt(1),
                        query_results.getString(2),
                        query_results.getInt(3),
                        query_results.getDate(4),
                        query_results.getString(5));
                obsArrayList.add(dataInstance);
            }
            apl_code.setCellValueFactory(new PropertyValueFactory<>("AplCode"));
            chk_mnemo.setCellValueFactory(new PropertyValueFactory<>("ChkMnemo"));
            chk_value.setCellValueFactory(new PropertyValueFactory<>("ChkValue"));
            run_date.setCellValueFactory(new PropertyValueFactory<>("RunDate"));
            run_comment.setCellValueFactory(new PropertyValueFactory<>("RunComment"));
            applicationChecksTable.setItems(obsArrayList);
            applicationChecksTable.getColumns().setAll(apl_code, chk_mnemo, chk_value, run_date, run_comment);
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
        callResults.close();
        applicationChecksPane.getChildren().add(applicationChecksTable);
    }
}
