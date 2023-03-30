package fxVakuutukset;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import vakuutus.SailoException;
import vakuutus.Vakuutus;
import javafx.scene.control.Button;

/**
 * 
 * @author olliterava,laidmale
 * @version 3.2.2023
 * Käynnistysikkunan controller luokka
 */
public class EtuIkkunaGUIController {

    @FXML
    private Button PeruutaNappi;

    @FXML
    private Button okNappi;
    
    @FXML private void handleTallenna() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Huomautus");
        alert.setHeaderText(null);
        alert.setContentText("Tämä ei toimi vielä!");
        alert.showAndWait();
    }
    
    
   

    

    
}
