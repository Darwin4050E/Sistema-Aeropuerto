module ec.edu.espol.flightcontrol {
    requires javafx.controls;
    requires javafx.fxml;

    opens ec.edu.espol.flightcontrol.controllers to javafx.fxml;
    exports ec.edu.espol.flightcontrol;
}
