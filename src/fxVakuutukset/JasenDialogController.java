package fxVakuutukset;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import vakuutus.Asiakas;
import vakuutus.Kotivakuutus;
import vakuutus.Vakuutus;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Jäsenelle oma kontrolleri
 * @author Osterava
 * @version 20.3.2023
 *
 */
public class JasenDialogController implements ModalControllerInterface<Asiakas>, Initializable {

    @FXML private Label labelVirhe;
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
    private static Vakuutus vakuutus;
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }

    @Override
    public Asiakas getResult() {
        return jasenKohdalla;
    }

    @Override
    public void setDefault(Asiakas oletus) {
        jasenKohdalla = oletus;
        naytaJasen(edits, jasenKohdalla);   
        
       
    }

    
    
    @Override
    public void handleShown() {
        //
    }


    @FXML private void handleOK() {
        if ( jasenKohdalla != null && jasenKohdalla.getNimi().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        
        ModalController.closeStage(editNimi);
    }

    
    @FXML private void handleCancel() {
        jasenKohdalla = null;
        ModalController.closeStage(editNimi);
    }

    // =========================================================================================
    
    private Asiakas jasenKohdalla;
    private TextField[] edits;
    private TextField[] edits2;
    
    private void alusta() {
        edits = new TextField[]{editNimi, editHetu, editKatuosoite, editPostinumero,editPostiosoite,editPuhellinnumero,editKoko,editInfo};
               int i = 0;
               for (TextField edit : edits) {
                    final int k = ++i;
                    edit.setOnKeyReleased( e -> kasitteleMuutosAsiakkaaseen(k, (TextField)(e.getSource())));
                }
               edits2 = new TextField[]{editKaytossa,editPintaala,editHinta,editVoimassaolo,editIrtaimisto,editAsunto,editOmavastuu};
               
    }
    
    private void kasitteleMuutosAsiakkaaseen(int k, TextField edit) {
                if (jasenKohdalla == null) return;
                 String s = edit.getText();
                 String virhe = null;
                 switch (k) {
                    case 1 : virhe = jasenKohdalla.setNimi(s); break;
                    case 2 : virhe = jasenKohdalla.setHetu(s); break;
                    case 3 : virhe = jasenKohdalla.setKatuosoite(s); break;
                    case 4 : virhe = jasenKohdalla.setPostinumero(s); break;
                    default:
                 }
                 if (virhe == null) {
                     Dialogs.setToolTipText(edit,"");
                     edit.getStyleClass().removeAll("virhe");
                     naytaVirhe(virhe);
                 } else {
                     Dialogs.setToolTipText(edit,virhe);
                     edit.getStyleClass().add("virhe");
                     naytaVirhe(virhe);
                 }
             }

         /**
          * Tyhjentään tekstikentät 
          * @param edits tauluko jossa tyhjennettäviä tektsikenttiä
          */
         public static void tyhjenna(TextField[] edits) {
             for (TextField edit : edits)
                edit.setText("");
        }

    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
  
    
    /**
     * Käsitellään jäseneen tullut muutos
     * @param edit muuttunut kenttä
     */
    private void kasitteleMuutosJaseneen(TextField edit) {
        if (jasenKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = jasenKohdalla.setNimi(s);
        if (virhe == null) {
            naytaVirhe(virhe);
        } else {
            naytaVirhe(virhe);
        }
    }
    
    
    
    /**
     * Näytetään jäsenen tiedot TextField komponentteihin
     * @param edits taulukko jossa tekstikenttiä
     * @param jasen näytettävä jäsen
     */
     public static void naytaJasen(TextField[] edits, Asiakas jasen) {
                edits[0].setText(jasen.getNimi());
                edits[1].setText(jasen.getHetu());
                edits[2].setText(jasen.getKatuosoite());
                edits[3].setText(jasen.getPostinumero());
                edits[4].setText(jasen.getKaupunki());
                edits[5].setText(jasen.getPuhelin());
                edits[6].setText(jasen.getKoti());
                edits[7].setText(jasen.getInfo());
                     }
                    
    
     /**
      * Näytetään jäsenen tiedot TextField komponentteihin
      * @param edits taulukko jossa tekstikenttiä
      * @param asiakas näytettävä vakuutus
      */
      public static void naytaVakuutus(TextField[] edits, Asiakas asiakas) {
          vakuutus = VakuutuksetGUIController.getVakuutus();
          
          List<Kotivakuutus> vakuutuukset = vakuutus.annaKotivakuutus(asiakas);
          
          
          String erotettu = vakuutuukset.get(0).toString();;
          String [] tolpillaErotettu = erotettu.split("|");

                 edits[0].setText(tolpillaErotettu[0]);
                 edits[1].setText(tolpillaErotettu[1]);
                 edits[2].setText(tolpillaErotettu[2]);
                 edits[3].setText(tolpillaErotettu[3]);
                 edits[4].setText(tolpillaErotettu[4]);
                 edits[5].setText(tolpillaErotettu[5]);
                 edits[6].setText(tolpillaErotettu[6]);
                 
                      }
                     
                      
   
    
    /**
     * Luodaan jäsenen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static Asiakas kysyJasen(Stage modalityStage, Asiakas oletus) {
        return ModalController.showModal(VakuutuksetGUIController.class.getResource("LisaaAsiakasGUIView.fxml"), "Asiakas", modalityStage, oletus);
    }
}
