package fxVakuutukset;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;


/**
 * 
 * @author olliterava,laidmale
 * @version 3.2.2023
 * Pääikkunan controller-java
 */
public class VakuutuksetGUIController implements Initializable {


    @FXML private Button lisaaVakuutus;
    @FXML private Button tallenna;
    
    @FXML private Button uusiAsiakas;
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;

    
    @FXML private void handleTallenna() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Huomautus");
        alert.setHeaderText(null);
        alert.setContentText("Tämä ei toimi vielä!");
        alert.showAndWait();
    }


@Override
public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub
    
}        
}