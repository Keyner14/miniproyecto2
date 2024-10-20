package com.example.hellojavafx.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The GameModel class represents the data and logic for the game.
 * This class is responsible for maintaining the state of the game.
 */
public class GameModel
{
    /**
     * The completed matrix of the game, representing the final solved state.
     */
    private ArrayList<ArrayList<String>> matrixCompleted = new ArrayList<>();
    /**
     * The uncompleted matrix of the game, representing the current state with some cells empty.
     */
    private ArrayList<ArrayList<String>> matrixUncompleted = new ArrayList<>();
    /**
     * A list of numbers used for generating random values in the game.
     */
    private ArrayList<Integer> numbers;
    /**
     * Constructs a new GameModel and initializes the matrices and numbers list.
     */
    public GameModel()
    {
        numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        for (int i = 0; i < 6; i++) {
            ArrayList<String> completedRow = new ArrayList<>(Collections.nCopies(6, ""));
            ArrayList<String> uncompletedRow = new ArrayList<>(Collections.nCopies(6, ""));
            matrixCompleted.add(completedRow);
            matrixUncompleted.add(uncompletedRow);
        }
    }
    /**
     * Resets both the completed and uncompleted matrices.
     */
    public void resetBothMatrix()
    {
        matrixCompleted.clear();
        matrixUncompleted.clear();

    }

    /**
     * Validates if a number can be placed in a specific cell without violating Sudoku rules.
     *
     * @param number the number to validate
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return true if the number is valid, false otherwise
     */
    public boolean isValidNumber(String number, int row, int col) {
        String numberStr = String.valueOf(number);

        // Check if the number is already in the same row or column
        for (int i = 0; i < 6; i++) {
            // Verifica si hay un número igual en la fila o columna
            if ((matrixUncompleted.get(row).get(i).equals(numberStr) ||
                    matrixUncompleted.get(i).get(col).equals(numberStr)) && i != col && i != row) {
                return false;
            }
        }
        // Check if the number is already in the same block
        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;
        ArrayList<String> blockElements = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int currentRow = startRow + i;
                int currentCol = startCol + j;

                if (currentRow == row && currentCol == col) {
                    continue;
                }

                blockElements.add(matrixUncompleted.get(currentRow).get(currentCol));
            }
        }

        if(blockElements.contains(numberStr)) {
            return false;
        }

        return true;
    }
    /**
     * Sets the completed matrix based on the given option.
     *
     * @param option the option to select the sudoku board
     */
    public void SetMatrix(int option)
    {
        if(option == 1)
        {
            matrixCompleted.add(new ArrayList<>(List.of("6", "1", "4", "2", "5", "3")));
            matrixCompleted.add(new ArrayList<>(List.of("2", "5", "3", "4", "6", "1")));
            matrixCompleted.add(new ArrayList<>(List.of("3", "2", "1", "5", "4", "6")));
            matrixCompleted.add(new ArrayList<>(List.of("4", "6", "5", "1", "3", "2")));
            matrixCompleted.add(new ArrayList<>(List.of("1", "4", "6", "3", "2", "5")));
            matrixCompleted.add(new ArrayList<>(List.of("5", "3", "2", "6", "1", "4")));
        }
        if(option == 2)
        {
            matrixCompleted.add(new ArrayList<>(List.of("6", "2", "5", "1", "4", "3")));
            matrixCompleted.add(new ArrayList<>(List.of("4", "1", "3", "5", "2", "6")));
            matrixCompleted.add(new ArrayList<>(List.of("1", "5", "6", "2", "3", "4")));
            matrixCompleted.add(new ArrayList<>(List.of("3", "4", "2", "6", "5", "1")));
            matrixCompleted.add(new ArrayList<>(List.of("2", "6", "4", "3", "1", "5")));
            matrixCompleted.add(new ArrayList<>(List.of("5", "3", "1", "4", "6", "2")));
        }
        if(option == 3)
        {
            matrixCompleted.add(new ArrayList<>(List.of("2", "5", "1", "4", "3", "6")));
            matrixCompleted.add(new ArrayList<>(List.of("3", "6", "4", "1", "5", "2")));
            matrixCompleted.add(new ArrayList<>(List.of("4", "2", "6", "5", "1", "3")));
            matrixCompleted.add(new ArrayList<>(List.of("5", "1", "3", "2", "6", "4")));
            matrixCompleted.add(new ArrayList<>(List.of("1", "3", "2", "6", "4", "5")));
            matrixCompleted.add(new ArrayList<>(List.of("6", "4", "5", "3", "2", "1")));
        }
        if(option == 4)
        {
            matrixCompleted.add(new ArrayList<>(List.of("6", "5", "2", "4", "1", "3")));
            matrixCompleted.add(new ArrayList<>(List.of("1", "4", "3", "2", "5", "6")));
            matrixCompleted.add(new ArrayList<>(List.of("3", "1", "6", "5", "2", "4")));
            matrixCompleted.add(new ArrayList<>(List.of("5", "2", "4", "6", "3", "1")));
            matrixCompleted.add(new ArrayList<>(List.of("4", "3", "5", "1", "6", "2")));
            matrixCompleted.add(new ArrayList<>(List.of("2", "6", "1", "3", "4", "5")));
        }
        if(option == 5)
        {
            matrixCompleted.add(new ArrayList<>(List.of("2", "5", "1", "6", "4", "3")));
            matrixCompleted.add(new ArrayList<>(List.of("6", "3", "4", "5", "2", "1")));
            matrixCompleted.add(new ArrayList<>(List.of("1", "2", "5", "3", "6", "4")));
            matrixCompleted.add(new ArrayList<>(List.of("4", "6", "3", "2", "1", "5")));
            matrixCompleted.add(new ArrayList<>(List.of("3", "4", "6", "1", "5", "2")));
            matrixCompleted.add(new ArrayList<>(List.of("5", "1", "2", "4", "3", "6")));
        }
    }
    /**
     * Returns the completed matrix.
     *
     * @return the completed matrix
     */
    public ArrayList<ArrayList<String>> getMatrixCompleted()
    {
        return matrixCompleted;
    }
    /**
     * Returns the uncompleted matrix.
     *
     * @return the uncompleted matrix
     */
    public ArrayList<ArrayList<String>> getMatrixUncompleted()
    {
        return matrixUncompleted;
    }

    /**
     * Inserts a value into the uncompleted matrix at the specified row and column.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param value the value to insert
     */
    public void insertValueIntoMatrixUncompleted(int row, int col, String value) {
        // Ensure the matrixUncompleted has enough rows
        while (matrixUncompleted.size() <= row) {
            matrixUncompleted.add(new ArrayList<>());
        }

        // Ensure the specific row has enough columns
        while (matrixUncompleted.get(row).size() <= col) {
            matrixUncompleted.get(row).add("");
        }

        // Insert the value
        matrixUncompleted.get(row).set(col, value);
    }

    /**
     * Inserts random values from the completed matrix into the uncompleted matrix.
     */
    // Ensure matrixUncompleted has the same structure as matrixCompleted
    public void insertRandomValuesIntoMatrixUncompleted() {
        // Initialize matrixUncompleted if not already initialized
        if (matrixUncompleted.isEmpty()) {
            for (int i = 0; i < 6; i++) {
                matrixUncompleted.add(new ArrayList<>(Collections.nCopies(6, "")));
            }
        }

        // Iterate over each 2x3 block
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 2; blockCol++) {
                List<int[]> positions = new ArrayList<>();
                // Collect all positions in the current 2x3 block
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 3; j++) {
                        positions.add(new int[]{blockRow * 2 + i, blockCol * 3 + j});
                    }
                }
                // Shuffle positions to get random ones
                Collections.shuffle(positions);
                // Insert 2 random values from matrixCompleted to matrixUncompleted
                for (int k = 0; k < 2; k++) {
                    int[] pos = positions.get(k);
                    int row = pos[0];
                    int col = pos[1];
                    // Check if the indices are within bounds
                    if (row < matrixCompleted.size() && col < matrixCompleted.get(row).size()) {
                        matrixUncompleted.get(row).set(col, matrixCompleted.get(row).get(col));
                    }
                }
            }
        }
    }
    /**
     * Returns a random number from the numbers list.
     *
     * @param i the index to retrieve the number from
     * @return the random number
     */
    public int getNumberAleatory(int i)
    {
        Collections.shuffle(numbers);
        return numbers.get(i);
    }

}
