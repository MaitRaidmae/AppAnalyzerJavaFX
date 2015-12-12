/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.misc;

import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author Hundisilm
 */
public class Alerts {

    public static void AlertSQL(SQLException sqlError) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("The following error occured");
        alert.setContentText(sqlError.getMessage());
        alert.showAndWait();
    }

    public static void AlertWindow(Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Window opening error");
        alert.setHeaderText("Error loading new window");
        System.out.println("Error loading new window " + exception.getMessage());
        alert.setContentText(exception.getMessage());
        alert.showAndWait();
    }

    public static void AlertIO(IOException exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("IO Error");
        alert.setHeaderText("IO Error occurred");
        System.out.println("IO Error: " + exception.getMessage());
        alert.setContentText(exception.getMessage());
        alert.showAndWait();
    }
}
