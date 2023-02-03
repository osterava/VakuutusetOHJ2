package fxVakuutukset;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author olliterava,laidmale
 * @version 11.1.2023
 *
 */
public class VakuutuksetMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("VakuutuksetGUIView.fxml"));
            final Pane root = ldr.load();
            //final VakuutuksetController vakuutuksetCtrl = (VakuutuksetController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("vakuutukset.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Vakuutukset");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}