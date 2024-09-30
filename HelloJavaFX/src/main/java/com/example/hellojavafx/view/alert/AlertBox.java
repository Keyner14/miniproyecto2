package com.example.hellojavafx.view.alert;

import javafx.scene.control.Alert;


public class AlertBox implements AlertBoxInterface {
    public enum AlertType {
        INFORMATION,
        ERROR
    }
    @Override
    public void showAlert(String title, String header, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
