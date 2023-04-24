package fxVakuutukset;

import java.awt.Desktop;
import java.awt.Dialog;
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
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.control.Button;

import javafx.application.Application;
import javafx.stage.Stage;
import vakuutus.Asiakas;
import vakuutus.Kotivakuutus;
import vakuutus.SailoException;
import vakuutus.Vakuutus;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.List;
import java.util.Optional;


/**
 * 
 * @author olliterava,laidmale
 * @version 3.2.2023
 * Pääikkunan controller-java
 */
public class VakuutuksetGUIController implements Initializable {
    


    @FXML private TextField editNimi;
    @FXML private TextField editHetu;
    @FXML private TextField editKatuosoite;
    @FXML private TextField editPostinumero;    
    
    @FXML private Button lisaaVakuutus;
    @FXML private Button tallenna;
    @FXML private GridPane gridAsiakas;
    @FXML private Button uusiAsiakas;
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelJasen;
    @FXML private ListChooser<Asiakas> chooserAsiakkaat;
    @FXML private GridPane gridKotivakuutus;
    
    private String salasana = "vakuutukset";
   
    @FXML private void handleTallenna() {
        tallenna();
    }

    
   
    @FXML private void handleUusiAsiaks() {
       uusiAsiakas();
    }
    
    @FXML private void handleUusiKotivakuutus(){
       uusiKotivakuutus();
    }
    
    @FXML private void handleAvaa() {
        avaa();
    }

    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    } 

    @FXML private void handleMuokkaaAsiakas() {
        muokkaa(kentta);
    }

    @FXML private void handleMuokkaaKotivakuutus() {
        muokkaaKotivakuutusta();
    }


@Override
public void initialize(URL arg0, ResourceBundle arg1) {
    
    alusta();
    
}



/**
 * Tietojen tallennus
 */
private void tallenna() {
    try {
        vakuutus.tallenna();
    } catch (SailoException e) {
        Dialogs.showMessageDialog(e.getMessage());
    }
}

/**
 * Tarkistetaan onko tallennus tehty
 * @return true jos saa sulkea sovelluksen, false jos ei
 */
public boolean voikoSulkea() {
    tallenna();
    return true;
}



private Vakuutus vakuutus;
private TextArea areaJasen = new TextArea(); //TODO poista lopussa
private static Asiakas apuAsiakas = new Asiakas();
private static Kotivakuutus apuVakuutus = new Kotivakuutus();
private TextField[] edits;
private TextField[] edits2;
private int kentta = 0;




protected void alusta() {
        // panelJasen.setContent(areaJasen);
        panelJasen.setFitToHeight(true);
        
        chooserAsiakkaat.clear();
        chooserAsiakkaat.addSelectionListener(e -> naytaAsiakas());
        
        edits = new TextField[]{editNimi, editHetu, editKatuosoite, editPostinumero};          
    }

   /* // panelJasen.setContent(areaJasen);
    panelJasen.setFitToHeight(true);
    cbKentat.clear(); 
    for (int k=apuAsiakas.ekaKentta(); k<apuAsiakas.getKenttia(); k++) {
        cbKentat.add(apuAsiakas.getKysymys(k));
    }
    cbKentat.setSelectedIndex(0);
    
    chooserAsiakkaat.clear();
    chooserAsiakkaat.addSelectionListener(e -> naytaAsiakas());
    
    
    
    edits = JasenDialogController.luoKentat(gridAsiakas, new Asiakas());      
    for (TextField edit: edits)   
        if ( edit != null ) {   
            edit.setEditable(false);   
            edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaa(getFieldId(edit,kentta)); });  
            edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta)); 
        } 
  
    edits2 = JasenDialogController.luoKentat(gridKotivakuutus, new Kotivakuutus());      
    for (TextField edit: edits)   
        if ( edit != null ) {   
            edit.setEditable(false);   
            edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaa(getFieldId(edit,kentta)); });  
            edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta)); 
        } 
    
    int eka = apuVakuutus.ekaKentta(); 
    int lkm = apuVakuutus.getKenttia(); 
    String[] headings = new String[lkm-eka]; 
    for (int i=0, k=eka; k<lkm; i++, k++) headings[i] = apuVakuutus.getKysymys(k); 
    
    gridKotivakuutus.setOnMouseClicked( e -> { if ( e.getClickCount() > 1 ) muokkaaKotivakuutusta(); } );
    gridKotivakuutus.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaKotivakuutusta();});  */





/**
 * Näyttää listasta valitun asiakkaan tiedot, tilapäisesti yhteen isoon edit-kenttään
 */
protected void naytaAsiakas() {
    Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject();

    if (asiakasKohdalla == null) return;

    areaJasen.setText("");
    try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaJasen)) {
        tulosta(os,asiakasKohdalla);
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
    uusi = JasenDialogController.kysyJasen(null, uusi); 
    if (uusi == null) return;
    uusi.rekisteroi();
    vakuutus.lisaa(uusi);
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

/** 
 * Tekee uuden tyhjän kotivakuutuksen editointia varten 
 */ 
public void uusiKotivakuutus() { 
    
   /* Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject(); 
    if ( asiakasKohdalla == null ) return;

    Kotivakuutus uusi = new Kotivakuutus(asiakasKohdalla.getTunnusNro());
    uusi = LisaaAsiakasGUIController.kysyTietue(null, uusi, 0);
    if ( uusi == null ) return;
    uusi.rekisteroi();
    try {
        vakuutus.lisaa(uusi);
    } catch (SailoException e) {
        // Näytä dialogi
    }
    naytaKotivakuutus(asiakasKohdalla); */
        
} 

/**
 * Alustaa kerhon lukemalla sen valitun nimisestä tiedostosta
 * @param nimi tiedosto josta kerhon tiedot luetaan
 */
protected void lueTiedosto(String nimi) {
        try {
            vakuutus.lueTiedostosta(nimi);
            hae(0);
        } catch (SailoException e) {
            
            e.printStackTrace();
        }
       
}



/**
 * Kysytään tiedoston nimi ja luetaan se
 * @return true jos onnistui, false jos ei
 */
public boolean avaa() {
    String salainen = EtuIkkunaGUIController.kysySalasana(null, salasana);
    String uusinimi = salainen;
    lueTiedosto(uusinimi);
    return true;
}


private void naytaKotivakuutus(Asiakas asiakas) {
    gridKotivakuutus.getChildren().clear();
    if (asiakas == null) return;
    List<Kotivakuutus> kotivakuutus = vakuutus.annaKotivakuutus(asiakas);
    if (kotivakuutus.size() == 0) return;
    for (Kotivakuutus koti: kotivakuutus) 
        naytaVakuutus(koti);
}


private void naytaVakuutus(Kotivakuutus koti) {
   /* int kenttia = koti.getKenttia(); 
    String[] rivi = new String[kenttia-koti.ekaKentta()]; 
    for (int i=0, k=koti.ekaKentta(); k < kenttia; i++, k++) 
        rivi[i] = koti.anna(k); 
    gridKotivakuutus.addRow(koti,rivi); */
}



/**
 * Tulostaa asiakkaan tiedot
 * @param os tietovirta johon tulostetaan
 * @param asiakas tulostettava jäsen
 */
public void tulosta(PrintStream os, final Asiakas asiakas) {
    os.println("----------------------------------------------");
    asiakas.tulosta(os);
    os.println("----------------------------------------------");
    List<Kotivakuutus> kotivakuutus = vakuutus.annaKotivakuutus(asiakas);
    for (Kotivakuutus koti: kotivakuutus) 
        koti.tulosta(os);
    os.println("----------------------------------------------");
}


private void muokkaaKotivakuutusta() {
   /* Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject(); 
    int r = gridKotivakuutus;
    if ( r < 0 ) return; 
    Kotivakuutus har = gridKotivakuutus.getObject();
    if ( har == null ) return;
    int k = gridKotivakuutus.getColumnNr()+har.ekaKentta();
    try {
        har = LisaaAsiakasGUIController.kysyTietue(null, har.clone(), k);
        if ( har == null ) return;
        vakuutus.korvaaTaiLisaa(har); 
        naytaKotivakuutus(asiakasKohdalla); 
        tableKotivakuutus.selectRow(r); 
    } catch (CloneNotSupportedException  e) { /* kloonaus 
    } catch (SailoException e) {
        Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
    }
    */
}


private void muokkaa(int k) {
   /* Asiakas asiakas = chooserAsiakkaat.getSelectedObject(); 
    if (asiakas == null) return;
    try {
        asiakas = asiakas.clone();
    } catch (CloneNotSupportedException e) {
        // Ei voi tapahtua
    }
    asiakas = LisaaAsiakasGUIController.kysyTietue(null, asiakas, k);
    if (asiakas == null) return;
    try {
        vakuutus.korvaaTaiLisaa(asiakas);
    } catch (SailoException e) {
        // TODO: näytä dialogi virheestä
    }
    hae(asiakas.getTunnusNro());*/
}








}