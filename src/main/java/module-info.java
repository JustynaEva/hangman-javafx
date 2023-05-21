module com.example.hangmanwithfront {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.hangmanjavafx to javafx.fxml;
    exports com.example.hangmanjavafx;
}