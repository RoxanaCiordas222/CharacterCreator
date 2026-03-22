module org.roxi.charactercreator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.google.gson;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens org.roxi.charactercreator to javafx.fxml;
    opens org.roxi.charactercreator.controller to javafx.fxml;
    opens org.roxi.charactercreator.model to com.google.gson;
    exports org.roxi.charactercreator;
    exports org.roxi.charactercreator.controller;
}