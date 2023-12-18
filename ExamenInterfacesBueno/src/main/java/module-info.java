module com.example.exameninterfacesbueno {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens com.example.exameninterfacesbueno to javafx.fxml;
    exports com.example.exameninterfacesbueno;
}