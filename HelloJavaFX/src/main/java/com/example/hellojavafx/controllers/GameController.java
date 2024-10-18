package com.example.hellojavafx.controllers;

import com.example.hellojavafx.models.GameModel;
import com.example.hellojavafx.view.alert.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
     * 3. Sets the horizontal and vertical gaps and padding for the GridPane.
     * 4. Creates and configures TextArea elements for each cell in the 6x6 grid.
     * 5. Adds key event handlers to navigate between TextArea elements using arrow keys.
     * 6. Adds listeners to check for duplicates and winning conditions when the text changes.
     * 7. Randomly assigns initial values to some cells in the grid.
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

        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 2; blockCol++) {
                List<int[]> positions = new ArrayList<>();

                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 3; j++) {
                        positions.add(new int[]{blockRow * 2 + i, blockCol * 3 + j});
                    }
                }

                Collections.shuffle(positions);

                int assigned = 0;
                while (assigned < 2) {
                    int[] pos = positions.get(assigned);
                    int randomNum;

                    do {
                        randomNum = (int) (Math.random() * 6 + 1);
                    } while (!isValidNumber(randomNum, pos[0], pos[1]));

                    textAreas.get(pos[0]).get(pos[1]).setText(String.valueOf(randomNum));
                    textAreas.get(pos[0]).get(pos[1]).setEditable(false);
                    assigned++;
                }
            }
        }
    }
    /**
     * Checks if the player has won the game.
     */
    public void checkIfWon() {
        boolean won = true;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (textAreas.get(i).get(j).getText().isEmpty() ||
                        textAreas.get(i).get(j).getStyle().contains("-fx-border-color: red") ||
                        textAreas.get(i).get(j).getStyle().contains("-fx-text-fill: yellow")) {
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
     * Validates if a number can be placed in a specific cell without violating Sudoku rules.
     *
     * @param number the number to validate
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return true if the number is valid, false otherwise
     */
    public boolean isValidNumber(int number, int row, int col) {
        //checks if the number is already in the same row or column
        for (int i = 0; i < 6; i++) {
            if (textAreas.get(row).get(i).getText().equals(String.valueOf(number)) ||
                    textAreas.get(i).get(col).getText().equals(String.valueOf(number))) {
                return false;
            }
        }
        //checks if the number is already in the same block
        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                if (textAreas.get(startRow + i).get(startCol + j).getText().equals(String.valueOf(number))) {
                    return false;
                }
            }
        }

        return true;
    }
    /**
     * Checks for duplicate values in the same row, column, or block.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param newValue the new value to check for duplicates
     */
    private void checkForDuplicates(int row, int col, String newValue) {
        boolean duplicateFound = false;
        //checks for duplicates in the same row
        for (int i = 0; i < 6; i++) {
            if (i != col && textAreas.get(row).get(i).getText().equals(newValue)) {
                duplicateFound = true;
                break;
            }
        }
        //checks for duplicates in the same column
        if (!duplicateFound) {
            for (int i = 0; i < 6; i++) {
                if (i != row && textAreas.get(i).get(col).getText().equals(newValue)) {
                    duplicateFound = true;
                    break;
                }
            }
        }
        //checks for duplicates in the same block
        if (!duplicateFound) {
            int startRow = (row / 2) * 2;
            int startCol = (col / 3) * 3;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    if (!(startRow + i == row && startCol + j == col) &&
                            textAreas.get(startRow + i).get(startCol + j).getText().equals(newValue)) {
                        duplicateFound = true;
                        break;
                    }
                }
            }
        }
        //If a duplicate is found, set the border color to red and show an error message
        if (duplicateFound) {
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

        if (emptyPositions.isEmpty()) {
            new AlertBox().showAlert(
                    "Error",
                    "No hay espacios vacios en el tablero.",
                    "",
                    Alert.AlertType.ERROR
            );
            return;
        }

        Collections.shuffle(emptyPositions);
        int[] pos = emptyPositions.get(0);
        int row = pos[0];
        int col = pos[1];

        int randomNum;

        do {
            randomNum = (int) (Math.random() * 6 + 1);
        } while (!isValidNumber(randomNum, row, col));


        textAreas.get(row).get(col).setText(String.valueOf(randomNum));


        textAreas.get(row).get(col).setStyle("-fx-text-fill: yellow;");
    }
}