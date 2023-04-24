package fxVakuutukset;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import vakuutus.Asiakas;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Jäsenelle oma kontrolleri
 * @author vesal
 * @version 20.3.2023
 *
 */
public class JasenDialogController implements ModalControllerInterface<Asiakas>, Initializable {

    @FXML private Label labelVirhe;
    @FXML private TextField editNimi;
    @FXML private TextField editHetu;
    @FXML private TextField editKatuosoite;
    @FXML private TextField editPostinumero;

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

    
    private void alusta() {
        edits = new TextField[]{editNimi, editHetu, editKatuosoite, editPostinumero};  
        editNimi.setOnKeyReleased( e -> kasitteleMuutosJaseneen(editNimi));
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
        if (jasen == null) return;
        edits[0].setText(jasen.getNimi());
        edits[1].setText(jasen.getHetu());
        edits[2].setText(jasen.getKatuosoite());
        edits[3].setText(jasen.getPostinumero());
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
