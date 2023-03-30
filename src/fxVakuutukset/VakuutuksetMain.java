package fxVakuutukset;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import vakuutus.Vakuutus;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author olliterava,laidmale
 * @version 21.2.2023
 *
 */
public class VakuutuksetMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("VakuutuksetGUIView.fxml"));
            final Pane root = ldr.load();
            final VakuutuksetGUIController vakuutuksetCtrl = (VakuutuksetGUIController) ldr.getController();
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("vakuutukset.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Vakuutukset");
            

            primaryStage.setOnCloseRequest((event) -> {
                    if ( !vakuutuksetCtrl.voikoSulkea() ) event.consume();
                });
            

            
            Vakuutus vakuutus=new Vakuutus();
            vakuutuksetCtrl.setVakuutus(vakuutus);
            
            
            
            primaryStage.show();
            if ( !vakuutuksetCtrl.avaa() ) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }


      
    }
    
    
    
    

    /**
     * käynnistetään käyttöliittymä
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}