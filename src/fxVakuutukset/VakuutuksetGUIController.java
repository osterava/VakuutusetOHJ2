package fxVakuutukset;

import java.awt.Desktop;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.control.Button;

import javafx.application.Application;
import javafx.stage.Stage;
import vakuutus.Asiakas;
import vakuutus.SailoException;
import vakuutus.Vakuutus;
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
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelJasen;
    @FXML private ListChooser<Asiakas> chooserAsiakkaat;
    
    @FXML private void handleTallenna() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Huomautus");
        alert.setHeaderText(null);
        alert.setContentText("Tämä ei toimi vielä!");
        alert.showAndWait(); 
    }
    
   
    @FXML private void handleUusiAsiaks() {
       uusiAsiakas();
    }

@Override
public void initialize(URL arg0, ResourceBundle arg1) {
    
    alusta();
    
}





private Vakuutus vakuutus;
private TextArea areaJasen = new TextArea(); //TODO poista lopussa


private void alusta() {
    chooserAsiakkaat.clear();
    panelJasen.setContent(areaJasen);
    areaJasen.setFont(new Font("Courier New", 12));
    panelJasen.setFitToHeight(true);
    
    chooserAsiakkaat.clear();
    chooserAsiakkaat.addSelectionListener(e -> naytaAsiakas());
}

/**
 * Näyttää listasta valitun asiakkaan tiedot, tilapäisesti yhteen isoon edit-kenttään
 */
protected void naytaAsiakas() {
    Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject();

    if (asiakasKohdalla == null) return;

    areaJasen.setText("");
    try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaJasen)) {
        asiakasKohdalla.tulosta(os);
    }
}


/**
 * asetetaan käytettävä vakuutus
 * @param vakuutus jota käytetään
 */
public void setVakuutus(Vakuutus vakuutus) {
    this.vakuutus = vakuutus;
    
}       


/**
 * Luo uuden asiakkaan jota aletaan editoimaan 
 */
protected void uusiAsiakas() {
    Asiakas uusi = new Asiakas();
    uusi.rekisteroi();
    uusi.naytaAkuTiedoilla();
    try {
        vakuutus.lisaa(uusi);
    } catch (SailoException e) {
        Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
        return;
    }
    hae(uusi.getTunnusNro());
}

/**
 * Hakee jäsenten tiedot listaan
 * @param jnro asiakkaan numero, joka aktivoidaan haun jälkeen
 */
protected void hae(int jnro) {
    chooserAsiakkaat.clear();

   int index = 0;
    for (int i = 0; i < vakuutus.getAsiakkaat(); i++) {
        Asiakas asiakas = vakuutus.annaAsiakas(i);
        if (asiakas.getTunnusNro() == jnro) index = i;
        chooserAsiakkaat.add(asiakas.getNimi(), asiakas);
    }
    chooserAsiakkaat.setSelectedIndex(index); 

}




}