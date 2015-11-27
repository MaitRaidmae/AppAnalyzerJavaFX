/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationanalyzer.FXControllers;

import applicationanalyzer.misc.AlertSQL;
import applicationanalyzer.misc.SQLExecutor;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;

/**
 *
 * @author Hundisilm
 */
public class ImageViewController implements Initializable {

    @FXML
    private ImageView imageView;

    private Image retImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ResultSet images = SQLExecutor.executeQuery("select * from table(rqEval(NULL,'PNG','PNG_Example'))");
        try {
            while (images.next()) {
                Blob img = images.getBlob(3);
                byte[] imgData = img.getBytes(1, (int) img.length());
                retImage = new Image(new ByteArrayInputStream(imgData));
            }
        } catch (SQLException sqle) {
            AlertSQL.AlertSQL(sqle);
        }
        imageView.setImage(retImage);
    }
}
