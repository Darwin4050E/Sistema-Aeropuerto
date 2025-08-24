package ec.edu.espol.flightcontrol.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import ec.edu.espol.flightcontrol.App;

public class RouteController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("mainView");
    }
}