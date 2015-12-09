/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.FXControllers;

import applicationanalyzer.DataClasses.Checks;
import applicationanalyzer.DataClasses.MinmaxCheckPars;
import applicationanalyzer.misc.Alerts;
import applicationanalyzer.misc.DataStore;
import applicationanalyzer.misc.SQLExecutor;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Hundisilm
 */
public class EditCheckController implements Initializable {

    @FXML
    GridPane grid;
    
    @FXML
    GridPane grid_pars;            
    
    DataStore dataStore = DataStore.getDataStore();
    Checks check = dataStore.getCurrentCheck();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (true) {
            int k = 0;
            
            String queryDataSql = "select * from b_checks where chk_code = " + dataStore.getCurrentCheck().getChkCode();
            ResultSet dataValues = SQLExecutor.executeQuery(queryDataSql);
            ResultSetMetaData metaData;
            grid.getChildren().clear();
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setVgap(5);
            grid.setHgap(5);
            try {
                metaData = dataValues.getMetaData();
                dataValues.next();
                //Create a map
                for (int i = 2; i <= metaData.getColumnCount(); i++) {
                    Label fieldNameLbl = new Label(metaData.getColumnName(i));
                    grid.add(fieldNameLbl, 0, k);
                        
                    //Check for LOV 
                    TextField textField = new TextField(dataValues.getString(i));
                    textField.setId(metaData.getColumnName(i));
                    grid.add(textField, 1, k);
                        k++;
                }
            } catch (SQLException sqle) {
                Alerts.AlertSQL(sqle);
            }
            queryDataSql = "select * from b_minmax_check_pars where mcp_chk_code = " + dataStore.getCurrentCheck().getChkCode();
            dataValues = SQLExecutor.executeQuery(queryDataSql);
            grid_pars.getChildren().clear();
            grid_pars.setPadding(new Insets(10, 10, 10, 10));
            grid_pars.setVgap(5);
            grid_pars.setHgap(5);
            try {
                metaData = dataValues.getMetaData();
                dataValues.next();
                //Create a map
                for (int i = 2; i <= metaData.getColumnCount(); i++) {
                    Label fieldNameLbl = new Label(metaData.getColumnName(i));
                    grid_pars.add(fieldNameLbl, 0, k);
                        
                    //Check for LOV 
                    TextField textField = new TextField(dataValues.getString(i));
                    textField.setId(metaData.getColumnName(i));
                    grid_pars.add(textField, 1, k);
                        k++;
                }
            } catch (SQLException sqle) {
                Alerts.AlertSQL(sqle);
            }
            
        }
    }

    public void handleCommit(ActionEvent event) {

        for (Node node : grid.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                switch (textField.getId()){
                    case "CHK_CODE":
                        check.setChkCode(Integer.parseInt(textField.getText()));
                        break;
                    case "CHK_MNEMO":
                        check.setChkMnemo(textField.getText());
                        break;
                    case "CHK_TYPE":
                        check.setChkType(textField.getText());
                        break;
                    case "CHK_COMMENT":
                        check.setChkComment(textField.getText());
                        break;
                    case "CHK_ACTIVE":
                        check.setChkActive(Integer.parseInt(textField.getText()));
                        break;
                    case "CHK_CHS_CODE":
                        check.setChkChsCode(Integer.parseInt(textField.getText()));
                        break;
                }
            }
        }
        check.commit();
        switch (check.getChkType()) {
            case "MIN":
            case "MAX":
                MinMaxCommit();
                break;
            case "RPREDICT":
                RPredictCommit();
                
        }
        
    }
    
    private void MinMaxCommit() {
        MinmaxCheckPars mmPars = check.getMinmaxPars();
        for (Node node : grid_pars.getChildren()) {
            System.out.println(node);
            if (node instanceof TextField) {
                System.out.println(node);
                TextField textField = (TextField) node;
                switch (textField.getId()) {
                    case "MCP_THRESHOLD":
                        mmPars.setMcpThreshold(Double.parseDouble(textField.getText()));
                        break;
                    case "MCP_CHECK_FIELD":
                        mmPars.setMcpCheckField(textField.getText());
                        break;
                }
            }
        }
        mmPars.commit();
    }
    
    private void RPredictCommit(){
        
    }
}
