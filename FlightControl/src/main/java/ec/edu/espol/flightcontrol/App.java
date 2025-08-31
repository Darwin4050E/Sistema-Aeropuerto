package ec.edu.espol.flightcontrol;

import ec.edu.espol.flightcontrol.controllers.UtilController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.application.Platform;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static boolean unsavedChanges = false;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("mainView"), 1280, 720);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("FlightControl");
        UtilController.setupCloseRequestHandler(stage);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    public static void setRoot(Parent root) {
        scene.setRoot(root);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void setUnsavedChanges(boolean status) {
        unsavedChanges = status;
    }
    
    public static boolean hasUnsavedChanges() {
        return unsavedChanges;
    }

    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

}