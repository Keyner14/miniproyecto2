package com.example.hellojavafx.controllers;

import com.example.hellojavafx.models.GameModel;
import com.example.hellojavafx.view.GameView;
import com.example.hellojavafx.view.alert.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * The GameController class handles the logic and interactions for the sudoku.
 *
 *  @author Keyner Fernando Meneses Recuero
 * @version 1.0
 */
public class GameController {
    /**
     * A list of lists to store the TextArea elements representing the game grid.
     */
    private List<List<TextArea>> textAreas = new ArrayList<>();
    /**
     * The model associated with this controller.
     */
    private GameModel gameModel = new GameModel();
    /**
     * The GridPane that contains the TextArea elements.
     */
    @FXML
    private GridPane contTextArea;
    /**
     * The button that provides hints to the player.
     */
    @FXML
    private Button hintButton;
    /**
     * The label that displays information to the player.
     */
    @FXML
    private Label labelInformation;

    /**
     * Initializes the game grid and sets up the TextArea elements.
     * This method performs the following steps:
     * 1. Configures the column constraints for the GridPane.
     * 2. Configures the row constraints for the GridPane.
     * 3. Sets the horizontal and vertical gaps and padding/margin for the GridPane.
     * 4. Creates and configures TextArea elements for each cell in the 6x6 grid.
     * 5. Adds key event handlers to navigate between TextArea elements using arrow keys.
     * 6. Adds listeners to check for duplicates and winning conditions when the text changes.
     */
    @FXML
    void initialize() {
        for (int i = 0; i < 6; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(100.0 / 6);
            contTextArea.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < 6; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / 6);
            contTextArea.getRowConstraints().add(rowConstraints);
        }

        contTextArea.setHgap(10);
        contTextArea.setVgap(10);
        contTextArea.setPadding(new Insets(30, 0, 0, 170));

        for (int i = 0; i < 6; i++) {
            List<TextArea> row = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                TextArea textArea = new TextArea();
                textArea.setPrefSize(40, 40);
                textArea.setMaxSize(40, 40);
                textArea.setMinSize(40, 40);

                if (j % 3 == 0 && j != 0) {
                    GridPane.setMargin(textArea, new Insets(0, 7, 0, 7));
                }

                if (i % 2 == 0 && i != 0) {
                    GridPane.setMargin(textArea, new Insets(10, 0, 0, 0));
                }

                textArea.setTextFormatter(new TextFormatter<String>(change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("[1-6]?")) {
                        return change;
                    } else {
                        return null;
                    }
                }));

                final int indexI = i;
                final int indexJ = j;

                textArea.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case UP:
                            if (indexI > 0) {
                                textAreas.get(indexI - 1).get(indexJ).requestFocus();
                            }
                            break;
                        case DOWN:
                            if (indexI < 5) {
                                textAreas.get(indexI + 1).get(indexJ).requestFocus();
                            }
                            break;
                        case LEFT:
                            if (indexJ > 0) {
                                textAreas.get(indexI).get(indexJ - 1).requestFocus();
                            }
                            break;
                        case RIGHT:
                            if (indexJ < 5) {
                                textAreas.get(indexI).get(indexJ + 1).requestFocus();
                            }
                            break;
                        default:
                            break;
                    }
                });
                textArea.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.isEmpty()) {
                        checkForDuplicates(indexI, indexJ, newValue);
                        checkAndUpdateValue(indexI, indexJ, newValue);
                        checkIfWon();
                    } else {
                        resetBorders(indexI, indexJ);
                    }
                });

                row.add(textArea);
                contTextArea.add(textArea, j, i);
            }
            textAreas.add(row);
        }

    }
    /**
     * Handles the action event for starting a new game.
     * Displays a confirmation dialog to the user. If confirmed, it clears the current game state,
     * sets up a new game matrix, inserts random values into the uncompleted matrix, and updates the TextAreas.
     *
     * @param event the action event triggered by the new game button
     */
    @FXML
    void OnActionNewGameButton(ActionEvent event) {
        AlertBox alertBox = new AlertBox();
        boolean confirmed = alertBox.showConfirmation("Confirmacion", "¿Estas seguro que quieres comenzar un nuevo juego?, (Si ya has empezado un juego se borrará tu progreso actual)");
        if (confirmed)
        {
            clearBoth();
            int option = gameModel.getNumberAleatory(0); // Assuming getNumberAleatory returns a number between 0 and 4
            gameModel.SetMatrix(option);
            gameModel.insertRandomValuesIntoMatrixUncompleted();
            updateTextAreas();
        }
    }
    /**
     * Updates the TextAreas with values from the uncompleted matrix.
     * Sets the text and makes the TextAreas non-editable if they contain a value.
     */
    public void updateTextAreas() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                String value = gameModel.getMatrixUncompleted().get(i).get(j);
                if (!value.isEmpty()) {
                    textAreas.get(i).get(j).setText(value);
                    textAreas.get(i).get(j).setEditable(false);
                }
            }
        }
    }

    /**
     * Checks if the new value matches the completed matrix value at the specified row and column.
     * If it matches, inserts the value into the uncompleted matrix.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param newValue the new value to check and update
     */
    private void checkAndUpdateValue(int row, int col, String newValue) {
        if (newValue.equals(gameModel.getMatrixCompleted().get(row).get(col))) {
            gameModel.insertValueIntoMatrixUncompleted(row, col, newValue);
        }
    }
    /**
     * Clears the text and makes all TextAreas editable.
     * Resets both the completed and uncompleted matrices in the game model.
     */
    public void clearBoth() {
        for (List<TextArea> row : textAreas) {
            for (TextArea textArea : row) {
                textArea.setText("");
                textArea.setEditable(true);
            }
        }
        gameModel.resetBothMatrix();
    }

    /**
     * Checks if the player has won the game, if has won shows an alertbox.
     */
    public void checkIfWon() {
        boolean won = true;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (!gameModel.getMatrixUncompleted().get(i).get(j).equals(gameModel.getMatrixCompleted().get(i).get(j))) {
                    won = false;
                    break;
                }
            }
            if (!won) break;
        }

        if (won) {
            new AlertBox().showAlert(
                    "Ganaste",
                    "!Has completado el sudoku!",
                    "",
                    Alert.AlertType.INFORMATION
            );

            Stage stage = (Stage) contTextArea.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Checks for duplicate values in the same row, column, or block.
     *
     * @param row      the row index of the cell
     * @param col      the column index of the cell
     * @param newValue the new value to check for duplicates
     */
    private void checkForDuplicates(int row, int col, String newValue) {
        boolean duplicateFound = gameModel.isValidNumber(newValue, row, col);
        //If a duplicate is found, set the border color to red and show an error message
        if (!duplicateFound) {
            textAreas.get(row).get(col).setStyle("-fx-border-color: red");
            new AlertBox().showAlert(
                    "Error",
                    "El numero ingresado viola las reglas del Sudoku",
                    "",
                    Alert.AlertType.ERROR
            );
        } else {
            textAreas.get(row).get(col).setStyle("");
        }
    }

    /**
     * Resets the border style of a cell.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     */
    private void resetBorders(int row, int col) {
        textAreas.get(row).get(col).setStyle("");
    }

    /**
     * Handles the action event for the hint button.
     *
     * @param event the action event
     */
    @FXML
    void OnActionHintButton(ActionEvent event) {
        List<int[]> emptyPositions = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (textAreas.get(i).get(j).getText().isEmpty()) {
                    emptyPositions.add(new int[]{i, j});
                }
            }
        }

        Collections.shuffle(emptyPositions);

        if (!emptyPositions.isEmpty()) {
            int[] pos = emptyPositions.get(0);
            int row = pos[0];
            int col = pos[1];

            String value = gameModel.getMatrixCompleted().get(row).get(col);
            gameModel.insertValueIntoMatrixUncompleted(row, col, value);

            textAreas.get(row).get(col).setText(value);
            textAreas.get(row).get(col).setEditable(false);
        }
    }
}