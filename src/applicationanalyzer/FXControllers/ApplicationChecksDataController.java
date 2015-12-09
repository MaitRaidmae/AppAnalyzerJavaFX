/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.FXControllers;

import applicationanalyzer.misc.Alerts;
import applicationanalyzer.DataClasses.AppChecks;
import applicationanalyzer.DataClasses.ApplicationChecksData;
import applicationanalyzer.DataClasses.Applications;
import applicationanalyzer.misc.DataStore;
import applicationanalyzer.misc.SQLExecutor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class ApplicationChecksDataController implements Initializable {

    @FXML
    private TableView tblv_ApplicationChecksData;
    @FXML
    private Label apl_code;
    @FXML
    private Label apl_name;
    @FXML
    private Label apl_income;
    @FXML
    private Label apl_obligations;
    @FXML
    private Label apl_reserve;
    @FXML
    private Label apl_debt_to_income;
    @FXML
    private Label apl_age;
    @FXML
    private Label apl_education;
    @FXML
    private Label apl_rejected;
    @FXML
    private Label apl_in_default;
    @FXML
    private Label check_mnemo;
    @FXML
    private Label check_value;
    @FXML
    private GridPane application_info;
    @FXML
    private GridPane checks_info;

    private Integer application_code;

    public void ApplicationChecksDataController() {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        DataStore dataStore = DataStore.getDataStore();
        Applications currApp = dataStore.getCurrentApp();
        LinkedHashMap<String, String> fields = currApp.getFieldsWithValues();
        application_info.getColumnConstraints().add(new ColumnConstraints(150));
        application_info.getColumnConstraints().add(new ColumnConstraints(150));
        int k = 0;
        for (String name : fields.keySet()) {
            Label fieldNameLbl = new Label(name);
            Label fieldValueLbl = new Label(fields.get(name));
            application_info.add(fieldNameLbl, 0, k);
            application_info.add(fieldValueLbl, 1, k);
            k++;
        }
        
        ResultSet checks_results;
        ObservableList<ApplicationChecksData> obsArrayList = FXCollections.observableArrayList();
        String checksSql = "select * from v_app_checks where ack_apl_code=" + currApp.getAplCode();
        checks_results = SQLExecutor.executeQuery(checksSql);
        // Set column widths for the view
        checks_info.getColumnConstraints().add(new ColumnConstraints(200));
        checks_info.getColumnConstraints().add(new ColumnConstraints(200));
        try {
            int i = 0;
            while (checks_results.next()) {
                AppChecks queryData = new AppChecks(checks_results.getInt(1),
                        checks_results.getString(2),
                        checks_results.getString(3));
                Label titleLabel = new Label(queryData.getChkMnemo());
                Label valueLabel = new Label(queryData.getChkResult());
                checks_info.add(titleLabel, 0, i);
                checks_info.add(valueLabel, 1, i);
                i++;
            }
        } catch (SQLException sqle) {
            Alerts.AlertSQL(sqle);
        }
    }
}
