module com.ci453.atmapp.atmapp_v07a {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires com.google.common;

    opens com.ci453.atmapp.atmapp_v07a to javafx.fxml;
    exports com.ci453.atmapp.atmapp_v07a;
}