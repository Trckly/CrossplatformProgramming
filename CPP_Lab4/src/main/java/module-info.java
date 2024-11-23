module org.app.cpp_lab {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.cpp_lab4 to javafx.fxml;
    exports org.cpp_lab4;
}