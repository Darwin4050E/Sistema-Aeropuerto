package ec.edu.espol.sistemaaeropuerto.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import ec.edu.espol.sistemaaeropuerto.App;

public class RouteController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("mainView");
    }
}