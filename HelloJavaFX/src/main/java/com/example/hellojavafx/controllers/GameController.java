package com.example.hellojavafx.controllers;

import com.example.hellojavafx.models.GameModel;

import com.example.hellojavafx.view.alert.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The GameController class handles the game logic and user interactions.
 * It manages the game state and updates the view accordingly.
 *
 * @version 1.0
 */
public class GameController
{
    /**
     * The model representing the game state.
     */
    private GameModel gameModel;
    /**
     * List to store the dynamic TextAreas.
     */
    private List<TextArea> textAreas; // Lista para almacenar los TextArea dinámicos
    /**
     * The stage for the current scene.
     */
    private Stage stage;
    @FXML
    private Button botonIngresarPalabras;

    @FXML
    private Button botonPistas;


    @FXML
    private HBox contenedorTextArea;

    @FXML
    private Label etiquetaInformacion;

    @FXML
    private ImageView imagenEclipse;

    /**
     * Handles the action of the "Ingresar Palabras" button.
     * Validates the input and updates the game state.
     * If the word is completed correctly, it shows an AlertBox indicating the user has won the game and closes the game scene.
     * If the word is not completed correctly, it shows an AlertBox indicating the user has lost and closes the game scene.
     *
     * @param event the action event triggered by the button
     */
    @FXML
    void funcionBotonIngresarPalabras(ActionEvent event)
    {
        boolean todosLosTextAreasLlenos = true;

        for (TextArea textArea : textAreas)
        {
            if (textArea.getText().isEmpty())
            {
                todosLosTextAreasLlenos = false;
                break;
            }

        }
        if(todosLosTextAreasLlenos)
        {
            if (gameModel.getContadoraParaImagenes() < 6)
            {
                System.out.println("Se presionó el botón de ingresar palabras");
                imagenEclipse.setImage(new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/intento" + Integer.toString(gameModel.getContadoraParaImagenes()) + ".png")));
                gameModel.setContadoraParaImagenes();
                String caracterIngresado = "";
                String pasarListaAString = "";

                for (int i = 0; i < gameModel.getLongitud(); i++)
                {
                    caracterIngresado = Character.toString(textAreas.get(i).getText().charAt(0));
                    caracterIngresado = caracterIngresado.toUpperCase();
                    System.out.println("El caracter ingresado es: " + caracterIngresado);
                    System.out.println("Entró al for ");
                    System.out.println(textAreas.get(i).getText().charAt(0));
                    if(caracterIngresado.equals(gameModel.getPalabraConCaracteresAdivinar(i).toUpperCase()))
                    {
                        System.out.println("El caracter es igual al de la palabra a adivinar");
                        System.out.println("El caracter ingresado es: " + caracterIngresado);
                        gameModel.setValorTextArea(i, caracterIngresado);
                        gameModel.setpalabraConCaracterUsuario(i, gameModel.getValorTextArea(i));
                        gameModel.showPalabraConCaracteresUsuario();
                        textAreas.get(i).setEditable(false);

                        System.out.println("Salio del if");
                    }
                    if(quitarTildes(caracterIngresado).equals(gameModel.getPalabraConCaracteresAdivinar(i).toUpperCase()) || caracterIngresado.equals(quitarTildes(gameModel.getPalabraConCaracteresAdivinar(i).toUpperCase())))
                    {
                        System.out.println("El caracter es igual al de la palabra a adivinar");
                        System.out.println("El caracter ingresado es: " + caracterIngresado);
                        gameModel.setValorTextArea(i, gameModel.getPalabraConCaracteresAdivinar(i).toUpperCase());
                        gameModel.setpalabraConCaracterUsuario(i, gameModel.getValorTextArea(i));
                        gameModel.showPalabraConCaracteresUsuario();
                        textAreas.get(i).setEditable(false);

                        System.out.println("Salio del if");

                    }
                    System.out.println("Se borrará el valor que este en el textArea");
                    textAreas.get(i).clear();
                    System.out.println("Se borró el valor que este en el textArea en la posicion: " + i);
                    textAreas.get(i).setText(gameModel.getValorTextArea(i));
                    System.out.println("El valor del textArea es: " + gameModel.getValorTextArea(i));
                }
                pasarListaAString = String.join("",gameModel.getPalabraConCaracteresUsuario());
                System.out.println("La palabra del usuario pasada a string es: " + pasarListaAString);
                gameModel.setPalabraUsuario(pasarListaAString);
            }
            else
            {
                System.out.println("Se acabaron los 5 intentos para adivinar la palabra");
                new AlertBox().showAlert(
                        "¡Perdiste!",
                        "La palabra es incorrecta",
                        "",
                        Alert.AlertType.INFORMATION
                );
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                setStage(stage);
                this.stage.close();

            }
            if(gameModel.comprobarPalabraGanador())
            {
                new AlertBox().showAlert(
                        "¡Ganaste!",
                        "La palabra es correcta",
                        "",
                        Alert.AlertType.INFORMATION
                );
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                setStage(stage);
                this.stage.close();

            }
        }
        else
        {
            new AlertBox().showAlert(
                    "Error",
                    "Todos los campos deben contener una letra.",
                    "",
                    Alert.AlertType.ERROR
            );
        }
    }
    /**
     * Removes diacritical marks (accents) from a given text.
     *
     * @param text the text to be normalized
     * @return the normalized text without diacritical marks
     */
    private String quitarTildes(String text)
    {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }
    /**
     * Handles the action of the "Pistas" button.
     * Provides hints to the user by revealing letters in the word.
     * Only 3 hints are allowed. If a hint is added, an AlertBox shows the added letter.
     * If no more hints are available, an AlertBox indicates that no more hints are available.
     *
     * @param event the action event triggered by the button
     */
    @FXML
    void funcionBotonPistas(ActionEvent event)
    {
        if (gameModel.getContadoraParaPistas() > 0)
        {
            gameModel.setContadoraParaPistas();
            System.out.println("Se presionó el botón de pistas");
            for (int i = 0; i < gameModel.getLongitud(); i++)
            {
                if (gameModel.getPalabraConCaracteresUsuario(i).equals(""))
                {
                    System.out.println("La pista es: " + gameModel.getPalabraConCaracteresAdivinar(i).toUpperCase());
                    gameModel.setValorTextArea(i, gameModel.getPalabraConCaracteresAdivinar(i).toUpperCase());
                    gameModel.setpalabraConCaracterUsuario(i, gameModel.getValorTextArea(i));
                    textAreas.get(i).setText(gameModel.getPalabraConCaracteresUsuario(i));
                    textAreas.get(i).setEditable(false);
                    //gameModel.setpalabraConCaracterUsuario(i,gameModel.getPalabraConCaracteresAdivinar(i).toUpperCase());
                    new AlertBox().showAlert
                            (
                            "Pista",
                            "Se agregó correctamente la letra: " + gameModel.getPalabraConCaracteresAdivinar(i).toUpperCase(),
                            "Quedan " + gameModel.getContadoraParaPistas() + " pistas",
                            Alert.AlertType.INFORMATION
                    );
                    break;
                }
            }
            if(gameModel.comprobarPalabraGanador())
            {
                new AlertBox().showAlert(
                        "¡Ganaste!",
                        "La palabra es correcta",
                        "",
                        Alert.AlertType.INFORMATION
                );
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                setStage(stage);
                this.stage.close();

            }
        }
        else
        {
            System.out.println("Se acabaron las pistas");
            new AlertBox().showAlert(
                    "No tienes más pistas",
                    "Error",
                    "",
                    Alert.AlertType.ERROR
            );

        }


    }
    /**
     * Constructor for GameController.
     * Initializes the game model and the list of TextAreas.
     *
     * @param gameModel the game model to be used by the controller
     */
    public GameController(GameModel gameModel) {
        this.gameModel = gameModel;
        this.textAreas = new ArrayList<>();// Inicializar la lista
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
     * Initializes the controller.
     * Creates dynamic TextAreas based on the length of the word.
     */
    @FXML
    public void initialize()
    {
        crearTextAreasDinamicas(gameModel.getLongitud());

    }
    /**
     * Creates dynamic TextAreas based on the given length.
     * Each TextArea only accepts one character from the Spanish alphabet.
     *
     * @param longitud the length of the word
     */
    private void crearTextAreasDinamicas(int longitud)
    {
        contenedorTextArea.getChildren().clear();
        contenedorTextArea.setSpacing(10);
        contenedorTextArea.setAlignment(Pos.CENTER);
        textAreas.clear(); // Limpiar la lista antes de agregar nuevos TextArea
        for (int i = 0; i < longitud; i++) {
            TextArea textArea = new TextArea();
            textArea.setPrefColumnCount(1);
            textArea.setPrefRowCount(1);
            textArea.setPrefSize(28, 28);
            textArea.setMaxSize(28, 28);
            textArea.setMinSize(28, 28);
            textArea.setTextFormatter(new TextFormatter<String>(change ->
            {
                String newText = change.getControlNewText();
                if (newText.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ]*") && newText.length() <= 1)
                {
                    return change;
                } else
                {
                    return null;
                }
            }));
            final int index = i;
            textArea.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty() && index < textAreas.size() - 1) {
                    textAreas.get(index + 1).requestFocus();
                }
            });
            contenedorTextArea.getChildren().add(textArea);
            textAreas.add(textArea); // Agregar el TextArea al final de la lista
        }
        System.out.println("Se creó un HBOX dinamico de longitud: " + gameModel.getLongitud());
    }
}

