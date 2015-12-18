/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.FXControllers;

import applicationanalyzer.DataClasses.CheckSuits;
import applicationanalyzer.misc.CssLoader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Hundisilm
 */
public class EditCheckSuitsController implements Initializable {

    @FXML
    AnchorPane editCheckSuitsPane;

    @FXML
    GridPane grid_pars;

    private GridPane checkGrid;
    private GridPane parsGrid;
    private Boolean commited;
    private CheckSuits checkSuits;
    private Stage editStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initObject(CheckSuits newCheckSuits) {
                //Load CSS and set 
        
        this.checkSuits = newCheckSuits;
        checkGrid = checkSuits.getGrid(true);
        CssLoader css = new CssLoader("/applicationanalyzer/FXML/CSS/EditCheckSuits.css", "editCheckSuitsGrid");
        checkGrid.getColumnConstraints().add(new ColumnConstraints(css.GRID_COL1_WIDTH));
        checkGrid.getColumnConstraints().add(new ColumnConstraints(css.GRID_COL2_WIDTH));
        checkGrid.getStyleClass().add("checkGrid");
        editCheckSuitsPane.getChildren().add(checkGrid);

    }

    public void setStage(Stage stage) {
        editStage = stage;
    }

    public boolean isCommited() {
        return commited;
    }

    public void handleCommit(ActionEvent event) {      
        checkSuits.setFromGrid(checkGrid);
        checkSuits.commit();
        commited = true;
        editStage.close();
    }

}
