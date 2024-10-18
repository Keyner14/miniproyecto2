package com.example.hellojavafx.controllers;

import com.example.hellojavafx.models.GameModel;
import com.example.hellojavafx.models.informacionModel;
import com.example.hellojavafx.view.GameView;
import com.example.hellojavafx.view.alert.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The informacionController class handles the user interactions and updates the model accordingly.
 * It validates the input word and initializes the game view if the word is valid.
 * It also manages the stage for the current scene.
 *
 * @version 1.0
 */
public class informacionController
{
    /**
     * The model that validates the input word.
     */
    private informacionModel informacionModel;
    /**
     * The text field where the user inputs the word.
     */
    private Stage stage;
    /**
     * The text field where the user inputs the word.
     */

    @FXML
    private Label titleLabel;
    /**
     * The label for the nickname.
     */
    @FXML
    private Label nicknameLabel;

    /**
     * Constructor for informacionController.
     * Initializes the informacionModel.
     */
    public informacionController()
    {
        this.informacionModel = new informacionModel();
    }
    /**
     * Sets the stage for the current scene.
     *
     * @param stage the stage to be set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Handles the play button action.
     * Validates the input word and initializes the game view if the word is valid.
     *
     * @param event the action event triggered by the play button
     */
    @FXML
    public void onHandlePlayButton(ActionEvent event)
    {
        AlertBox alertBox = new AlertBox();
        boolean confirmed = alertBox.showConfirmation("Confirmacion", "¿Estas seguro que quieres jugar?");
        if (confirmed)
        {
            try
            {
                GameView gameview = GameView.getInstance();
                gameview.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }



    }

}
