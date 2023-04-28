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
public class VakuutusController implements ModalControllerInterface<Kotivakuutus>, Initializable {

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
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }

    @Override
    public Kotivakuutus getResult() {
        return jasenKohdalla;
    }

    @Override
    public void setDefault(Kotivakuutus oletus) {
        jasenKohdalla = oletus;
        naytaVakuutus(edits2, jasenKohdalla);   
       
    }

    
    
    @Override
    public void handleShown() {
        //
    }


    @FXML private void handleOK() {
        if ( jasenKohdalla != null && jasenKohdalla.getKaytossa().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        
        ModalController.closeStage(editKaytossa);
  
        }
        
   

    
    @FXML private void handleCancel() {
        jasenKohdalla = null;
        ModalController.closeStage(editKaytossa);
    }

    // =========================================================================================
    
    private Kotivakuutus jasenKohdalla;
    private TextField[] edits;
    private TextField[] edits2;
    
    private void alusta() {
        edits2 = new TextField[]{editKaytossa,editPintaala,editHinta,editVoimassaolo,editIrtaimisto,editAsunto,editOmavastuu};
               int i = 0;
               for (TextField edit : edits2) {
                    final int k = ++i;
                    edit.setOnKeyReleased( e -> kasitteleMuutosAsiakkaaseen(k, (TextField)(e.getSource())));
                }
              
    }
    
    private void kasitteleMuutosAsiakkaaseen(int k, TextField edit) {
                if (jasenKohdalla == null) return;
                 String s = edit.getText();
                 String virhe = null;
                 switch (k) {
                    case 1 : virhe = jasenKohdalla.setKaytossa(s); break;
                    case 2 : virhe = jasenKohdalla.setPala(s); break;
                    case 3 : virhe = jasenKohdalla.setHinta(s); break;
                    case 4 : virhe = jasenKohdalla.setPvmVoimassa(s); break;
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

    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
  
    
    public static void naytaVakuutus(TextField[] edits, Kotivakuutus koti) {
        
        edits[0].setText(koti.getKaytossa());
        edits[1].setText(koti.getPala());
        edits[2].setText(koti.getKK());
        edits[3].setText(koti.getVoimassa());
        edits[4].setText(koti.getIrtaimisto());
        edits[5].setText(koti.getAsunto());
        edits[6].setText(koti.getOmavastuu());
                    }
   
    /**
     * Luodaan jäsenen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static Kotivakuutus muokkaaVakuutus(Stage modalityStage, Kotivakuutus oletus) {
        return ModalController.showModal(VakuutuksetGUIController.class.getResource("LisaaKotivakuutusGUIView.fxml"), "Kotivakuutus", modalityStage, oletus);
    }
      
   
}
