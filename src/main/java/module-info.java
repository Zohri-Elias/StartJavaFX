module appli.todolistfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.jdi;
    requires spring.security.crypto;


    opens appli to javafx.fxml;
    exports appli;
    exports appli.acceuil;
    opens appli.acceuil to javafx.fxml;
}