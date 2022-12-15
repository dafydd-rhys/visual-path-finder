module main.visualpathfinder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens pathfinder to javafx.fxml, javafx.graphics;
    exports pathfinder.controllers;
    opens pathfinder.controllers to javafx.fxml;
}