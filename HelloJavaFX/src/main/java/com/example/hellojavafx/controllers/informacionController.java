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
     * The model that represents the game state.
     */
    private GameModel gameModel;
    /**
     * The text field where the user inputs the word.
     */
    private Stage stage;
    /**
     * The text field where the user inputs the word.
     */
    @FXML
    private TextField escribirCuadro;
    /**
     * The label for the title.
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
     * Handles the play button action.
     * Validates the input word and initializes the game view if the word is valid.
     *
     * @param event the action event triggered by the play button
     */
    @FXML
    public void onHandlePlayButton(ActionEvent event)
    {

        System.out.println("Pasé por el boton de jugar");
        this.cuadroQueLee();
        if (informacionModel.getPalabraValida())
        {
            try {
                GameView gameview = GameView.getInstance(gameModel);
                gameview.getGameController().initialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

        }
        else
        {
            new AlertBox().showAlert(
                    "Error",
                    "La palabra debe tener entre 6 y 12 caracteres, sin espacios ni números.",
                    "Digite una palabra valida",
                    Alert.AlertType.ERROR
            );
        }
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
     * Reads the input word from the text field and validates it.
     * If the word is valid, it initializes the game model with the word.
     */
    @FXML
    public void cuadroQueLee()
    {
        String palabra = escribirCuadro.getText();

        if(informacionModel.comprobarCondiciones(palabra))
        {
            palabra = palabra.toUpperCase();
            ArrayList<String> palabraArray = new ArrayList<String>();
            for (char c : palabra.toCharArray()) {
                palabraArray.add(String.valueOf(c));
            }
            this.gameModel = new GameModel(palabra.length());
            gameModel.setPalabraAdivinar(palabra);
            gameModel.setLongitud(gameModel.getPalabraAdivinar().length());
            System.out.println("La longitud de la palabra es: " + gameModel.getLongitud());
            gameModel.setPalabraConCaracteresAdivinar(palabraArray);
        }
        else
        {
            System.out.println("La palabra no es valida");
        }

    }



}
