/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.FXControllers;

import applicationanalyzer.DataClasses.Checks;
import applicationanalyzer.misc.DataStore;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Hundisilm
 */
public class EditCheckController implements Initializable {

    @FXML
    AnchorPane editBox;

    @FXML
    AnchorPane editParsBox;

    @FXML
    GridPane grid_pars;

    private GridPane checkGrid;
    private GridPane parsGrid;
    private Boolean commited;
    private Checks check;
    private Stage editStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setCheck(Checks newCheck) {
        this.check = newCheck;
        checkGrid = newCheck.getEditGrid();
        editBox.getChildren().add(checkGrid);

        parsGrid = check.getEditParsGrid();
        editParsBox.getChildren().add(parsGrid);
    }

    public void setStage(Stage stage) {
        editStage = stage;
    }

    public boolean isCommited() {
        return commited;
    }

    public void handleCommit(ActionEvent event) {      
        check.setFromGrid(checkGrid);
        check.setParsFromGrid(parsGrid);
        check.commit();
        check.commitPars();
        commited = true;
        editStage.close();
    }

}
