package fxVakuutukset;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * salasanan kysymis-dialogi
 * @author olliterava, laidmale
 * @version 1.4.2023
 *
 */
public class EtuIkkunaGUIController implements ModalControllerInterface<String> {
    
    @FXML private PasswordField pField;
    private String vastaus = null;

    
    @FXML private void handleAvaa() {
        vastaus = pField.getText();
        ModalController.closeStage(pField);
    }

    /**
     * cancel napin metodi
     */
    @FXML private void handleCancel() {
        ModalController.closeStage(pField);
    }


    /**
     * palautus metodi vastaukselle
     */
    @Override
    public String getResult() {
        return vastaus;
    }

    /**
     * oletus salasana TODO: poista
     */
    @Override
    public void setDefault(String oletus) {
        pField.setText(oletus);
    }

    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        pField.requestFocus();
    }
    
    
    /**
     * Luodaan salasanankysymisdialog ja palautetaan siihen kirjoitettu salasana tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu salasana
     */
    public static String kysySalasana(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                EtuIkkunaGUIController.class.getResource("EtuIkkunaGUIView.fxml"),
                "vakuutukset",
                modalityStage, oletus);
    }
}
