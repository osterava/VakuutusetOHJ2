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


public class VakuutuksetGUIController {


    @FXML private Button lisaaVakuutus;
    @FXML private Button tallenna;
    @FXML private Button uusiAsiakas;
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;

    
    
}