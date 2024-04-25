module com.example.schoolmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires MaterialFX;


    opens com.example.schoolmanager to javafx.fxml;
    exports com.example.schoolmanager.Controllers;
    opens com.example.schoolmanager.Controllers to javafx.fxml;
    exports com.example.schoolmanager;
}