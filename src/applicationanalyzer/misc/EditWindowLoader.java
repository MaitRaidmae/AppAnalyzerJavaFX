/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.misc;

import applicationanalyzer.DataClasses.Checks;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author Hundisilm
 */
public class EditWindowLoader {

    private final String editableData;

    public EditWindowLoader(String editable) {
        editableData = editable;
    }
    
    private final DataStore dataStore = DataStore.getDataStore();
    
    public void commit(HashMap<String, String> dataMap) {
        switch (editableData) {
            case "Checks":
                Checks check = new Checks(Integer.parseInt(dataMap.get("CHK_CODE")),
                dataMap.get("CHK_MNEMO"),
                dataMap.get("CHK_TYPE"),
                dataMap.get("CHK_COMMENT"),
                Integer.parseInt(dataMap.get("CHK_ACTIVE")),
                Integer.parseInt(dataMap.get("CHK_CHS_CODE")));
                check.commit();
                break;
        }
    }
    
    //Returns select statement without the specific code.
    public String getQuerySQL(){
        String sql = "";
        switch(editableData){
            case "Checks":
                sql = "select * from b_checks where chk_code = " + dataStore.getCurrentCheck().getChkCode();
        }
        return sql;
    }
    
    //Checks if current Parameter is lov and returns LOV if true;
    public ObservableList<String> getLOV(String fieldName) {
        String lovSQL = "select * from table(MISC_UTILS.CONSTRAINT_LOV('HUNDISILM','B_CHECKS','"+fieldName+"'))";
        ResultSet lovValues = SQLExecutor.executeQuery(lovSQL);
        ObservableList<String> lovList = FXCollections.observableArrayList();
        try {
            while (lovValues.next()) {
                lovList.add(lovValues.getString(1));
            }
        } catch (SQLException slqe) {
            Alerts.AlertSQL(slqe);
        }
        return lovList;
    }  
}
