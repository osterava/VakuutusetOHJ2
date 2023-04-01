package fxVakuutukset;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * Kystään kerhon nimi ja luodaan tätä varten dialogi.
 * 
 * @author vesal
 * @version 2.1.2016
 */
public class EtuIkkunaGUIController implements ModalControllerInterface<String> {
    
    @FXML private PasswordField pField;
    private String vastaus = null;

    
    @FXML private void handleAvaa() {
        vastaus = pField.getText();
        ModalController.closeStage(pField);
    }

    
    @FXML private void handleCancel() {
        ModalController.closeStage(pField);
    }


    @Override
    public String getResult() {
        return vastaus;
    }

    
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
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                EtuIkkunaGUIController.class.getResource("EtuIkkunaGUIView.fxml"),
                "vakuutukset",
                modalityStage, oletus);
    }
}
