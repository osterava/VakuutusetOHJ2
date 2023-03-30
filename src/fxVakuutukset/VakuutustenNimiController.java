package fxVakuutukset;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

    public class VakuutustenNimiController {

        @FXML
        private Button PeruutaNappi;

        @FXML
        private Button okNappi;

        @FXML
        void handleAvaa() {
            ModalController.closeStage(okNappi);
        }

        @FXML
        void handleTallenna(ActionEvent event) {
            //
        }

    
    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                VakuutustenNimiController.class.getResource("EtuIkkunaGUIView.fxml"),
                "Vakuutukset",
                modalityStage, oletus);
    }
}
