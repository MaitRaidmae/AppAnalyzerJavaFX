/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.misc;

import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author Hundisilm
 */
public class AlertSQL {
    public static void AlertSQL(SQLException sqlError){
      Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The following error occured");
            alert.setContentText(sqlError.getMessage());
            alert.showAndWait();
    }
}
