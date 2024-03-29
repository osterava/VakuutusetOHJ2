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
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
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

import java.util.Collection;
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
    @FXML private TextField editPostiosoite; 
    @FXML private TextField editPuhellinnumero;
    @FXML private TextField editKoko;
    @FXML private TextField editInfo;   
    
    @FXML private TextField editPintaala;
    @FXML private TextField editKaytossa; 
    @FXML private TextField editHinta;
    @FXML private TextField editVoimassaolo;
    @FXML private TextField editIrtaimisto;
    @FXML private TextField editAsunto; 
    @FXML private TextField editOmavastuu; 
    
    
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
    @FXML private StringGrid<Asiakas> tablekotivakuutus;
    private String salasana = "vakuutukset";
   
    @FXML private void handlePoistaJasen() {
        poistaAsiakas();
    }
 
    @FXML private void handleTallenna() {
        tallenna();
    }

    @FXML private void handleLisaaKotivakuutus() {
        lisaaKotivakuutus();
    }
   
    @FXML private void handleUusiAsiaks() {
       uusiAsiakas();
    }
    
    @FXML private void handleAvaa() {
        avaa();
    }

    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    } 

    @FXML private void handleMuokkaaAsiakas() {
        muokkaa();
    }

    @FXML private void handleMuokkaaKotivakuutus() {
        muokkaaKotivakuutusta();
    }
    @FXML private void handleTulosta() {
        TulostusController tulostusCtrl = TulostusController.tulosta(null); 
        tulostaValitut(tulostusCtrl.getTextArea());

    }
    
    @FXML private void handleHakuehto() {
                 hae(0); 
            }


@Override
public void initialize(URL arg0, ResourceBundle arg1) {
    
    alusta();
    for (TextField field : edits) {
        field.setEditable(false);
    }
    for (TextField field : edits2) {
        field.setEditable(false);
    }
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



Vakuutus vakuutus;
private TextArea areaJasen = new TextArea(); //TODO poista lopussa
private TextField[] edits;
private TextField[] edits2;
private Asiakas apuAsiakas = new Asiakas();




protected void alusta() {
        // panelJasen.setContent(areaJasen);
        panelJasen.setFitToHeight(true);
        
        cbKentat.clear(); 
        for (int k=apuAsiakas.ekaKentta(); k<apuAsiakas.getKenttia(); k++) {
            cbKentat.add(apuAsiakas.getKysymys(k));
        }
        cbKentat.setSelectedIndex(0);

        
        chooserAsiakkaat.clear();
        chooserAsiakkaat.addSelectionListener(e -> naytaAsiakas());
        
        edits = new TextField[]{editNimi, editHetu, editKatuosoite, editPostinumero,editPostiosoite,editPuhellinnumero,editKoko,editInfo};
        edits2 = new TextField[]{editKaytossa,editPintaala,editHinta,editVoimassaolo,editIrtaimisto,editAsunto,editOmavastuu};
        
        
        
         }


/**
 * Näyttää listasta valitun asiakkaan tiedot, tilapäisesti yhteen isoon edit-kenttään
 */
protected void naytaAsiakas() {
    Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject();

    if (asiakasKohdalla == null) return;

    JasenDialogController.naytaAsiakas(edits, asiakasKohdalla); 
    try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaJasen)) {
        tulosta(os,asiakasKohdalla);
        naytaVakuutukset(asiakasKohdalla);
    }
    catch (IndexOutOfBoundsException ex) {
        System.err.println("Asiakkaalla ei ole vakuutuksia");
        naytaTyhja();
       
    }
    
    
}


/**
 * Näyttää näyttöön asiakkaan vakuutuksen
 * @param jasen
 */
private void naytaVakuutukset(Asiakas jasen) {
    
    List<Kotivakuutus> vakuutuukset = vakuutus.annaKotivakuutus(jasen);
    
    String erotettu = vakuutuukset.get(0).toString();;
    String [] tolpillaErotettu = erotettu.split("\\|");
    
    JasenDialogController.naytaVakuutus(edits2, tolpillaErotettu);
}


/**
 * Näyttää tyhjän lohkon, jos asiakkaalla ei ole vakuutuksia
 */
private void naytaTyhja() {
   JasenDialogController.naytaTyhja(edits2);
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
    uusi = JasenDialogController.kysyAsiakas(null, uusi); 
    if (uusi == null) return;
    uusi.rekisteroi();
    vakuutus.lisaa(uusi);
    hae(uusi.getTunnusNro());
}

/**
 * Hakee jäsenten tiedot listaan
 * @param jnr asiakkaan numero, joka aktivoidaan haun jälkeen
 */
protected void hae(final int jnr) {
    int jnro = jnr;
    int k = cbKentat.getSelectionModel().getSelectedIndex() + apuAsiakas.ekaKentta();
    String ehto = hakuehto.getText(); // aku  => *aku*   aku*  => aku*
    if (jnro == 0) {
        Asiakas kohdalla = chooserAsiakkaat.getSelectedObject(); 
        if (kohdalla != null) jnro = kohdalla.getTunnusNro();
    }

    if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
    
    chooserAsiakkaat.clear();

    int index = 0;
    Collection<Asiakas> jasenet;
    jasenet = vakuutus.etsi(ehto, k);
    int i = 0;
    for (Asiakas jasen:jasenet) {
        if (jasen.getTunnusNro() == jnro) index = i;
        chooserAsiakkaat.add(jasen.getNimi(), jasen);
        i++;
    }
    chooserAsiakkaat.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
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

/**
 * metodi, jolla voidaan muokata kotivakuutuksia
 */
private void muokkaaKotivakuutusta() {

        Asiakas asi = chooserAsiakkaat.getSelectedObject(); 
        List<Kotivakuutus> kotivakuutus = vakuutus.annaKotivakuutus(asi);
        if(kotivakuutus.size() == 0) return;

        if (VakuutusController.muokkaaVakuutus(null, kotivakuutus.get(0)) == null) return;
        
        hae(asi.getTunnusNro());

}

/**
 * metodi, jolla voidaan lisata kotivakuutus
 */
private void lisaaKotivakuutus() {
    
    Asiakas asi = chooserAsiakkaat.getSelectedObject(); 
    List<Kotivakuutus> kotivakuutus = vakuutus.annaKotivakuutus(asi);
    if(kotivakuutus.size() > 0) return; // TODO: tee mahdollisuus moneen eri vakuutukseen
    
    Kotivakuutus uusi = new Kotivakuutus();
    if (VakuutusController.muokkaaVakuutus(null, uusi) == null) return;
    uusi.rekisteroi();
    vakuutus.lisaa(uusi);
    hae(uusi.getTunnusNro());
}

/**
 * avaa dialogin asiakkaan tietojen muokkaukseen
 */
private void muokkaa() {
        Asiakas asi = chooserAsiakkaat.getSelectedObject(); 
        if (JasenDialogController.kysyAsiakas(null, asi) == null) return;
        hae(asi.getTunnusNro());
    }

/**
 * Tulostaa listassa olevat jäsenet tekstialueeseen
 * @param text alue johon tulostetaan
 */
public void tulostaValitut(TextArea text) {
    try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
        os.println("Tulostetaan kaikki jäsenet");
      /*  Collection<Asiakas> jasenet = vakuutus.etsi("", -1); 
        for (Asiakas jasen:jasenet) { 
            tulosta(os, jasen);
            os.println("\n\n");
        }
    } catch (SailoException ex) { 
        Dialogs.showMessageDialog("Jäsenen hakemisessa ongelmia! " + ex.getMessage()); 
    }*/
}

}

/**
 *  poistaa asiakkaan tiedot rekisteristä
 */
private void poistaAsiakas() {
    
        Asiakas asiakas = chooserAsiakkaat.getSelectedObject(); 
         if ( asiakas == null ) return;
         if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko jäsen: " + asiakas.getNimi(), "Kyllä", "Ei") )
             return;
         vakuutus.poista(asiakas);
         int index = chooserAsiakkaat.getSelectedIndex();
         hae(0);
         chooserAsiakkaat.setSelectedIndex(index);
     }
}