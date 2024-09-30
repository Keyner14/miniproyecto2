package com.example.hellojavafx.models;

import java.util.ArrayList;

/**
 * The GameModel class represents a model that manages the game state.
 * It contains the word to be guessed, the user's input, and the state of the game.
 * It also manages the number of attempts and the number of hints available.
 * It initializes the game state and updates the state based on user input.
 * It also checks if the user has guessed the word correctly.
 * @version 1.0
 */
public class GameModel
{
    /**
     * Counter for the number of images displayed.
     */

    private  int contadoraParaImagenes = 1;
    /**
     * Counter for the number of hints available.
     */
    private  int contadoraParaPistas = 2;
    /**
     * The word to be guessed.
     */
    private String palabraAdivinar;

    /**
     * The word entered by the user.
     */
    private String palabraUsuario;
    /**
     * List of characters in the word to be guessed.
     */
    private ArrayList<String> palabraConCaracteresAdivinar;
    /**
     * List of characters entered by the user.
     */
    private ArrayList<String> palabraConCaracteresUsuario;
    /**
     * List of values for the text areas.
     */
    private ArrayList<String> valoresTextAreas;
    /**
     * Length of the word to be guessed.
     */
    private int longitud;

    /**
     * Constructor for GameModel.
     * Initializes the game state with the given word length.
     *
     * @param longitud the length of the word to be guessed
     */
    public GameModel(int longitud)
    {
        this.palabraUsuario = "";
        this.longitud = longitud;
        this.valoresTextAreas = new ArrayList<>(longitud);
        this.palabraConCaracteresAdivinar = new ArrayList<>(longitud);
        this.palabraConCaracteresUsuario = new ArrayList<>(longitud);
        for (int i = 0; i < this.longitud; i++)
        {
            valoresTextAreas.add(""); // Inicializar la lista con cadenas vacías
            palabraConCaracteresUsuario.add(""); // Inicializar la lista con cadenas vacías
            palabraConCaracteresAdivinar.add(""); // Inicializar la lista con cadenas vacías
        }
    }
    /**
     * Gets the counter for the number of hints available.
     *
     * @return the number of hints available
     */
    public int getContadoraParaPistas()
    {
        return this.contadoraParaPistas;
    }
    /**
     * Decreases the counter for the number of hints available by one.
     */
    public void setContadoraParaPistas()
    {
        this.contadoraParaPistas--;
    }
    /**
     * Gets the value of the text area at the specified index.
     *
     * @param index the index of the text area
     * @return the value of the text area
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public String getValorTextArea(int index) {
        if (index >= 0 && index < this.valoresTextAreas.size()) {
            return this.valoresTextAreas.get(index);
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
    }
    /**
     * Checks if the user's word matches the word to be guessed.
     *
     * @return true if the words match, false otherwise
     */
    public boolean comprobarPalabraGanador()
    {
        System.out.println("La palabra del usuario es: " + this.palabraUsuario);
        System.out.println("La palabra a adivinar es: " + this.palabraAdivinar);
        if (this.palabraUsuario.equals(this.palabraAdivinar))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * Sets the value of the text area at the specified index.
     *
     * @param index the index of the text area
     * @param valor the value to be set
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void setValorTextArea(int index, String valor) {
        if (index >= 0 && index < valoresTextAreas.size()) {
            System.out.println("El valor del textArea es: " + valor);
            System.out.println("El valor del index es: " + index);
            valoresTextAreas.set(index,valor);
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
    }
    /**
     * Sets the character entered by the user at the specified index.
     *
     * @param index the index of the character
     * @param valor the character to be set
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void setpalabraConCaracterUsuario(int index, String valor) {
        if (index >= 0 && index < this.longitud){
            System.out.println("El valor del textArea es: " + valor);
            System.out.println("El valor del index es: " + index);
            palabraConCaracteresUsuario.set(index,valor);
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
    }
    /**
     * Gets the counter for the number of images displayed.
     *
     * @return the number of images displayed
     */
    public int getContadoraParaImagenes()
    {
        return this.contadoraParaImagenes;
    }
    /**
     * Increases the counter for the number of images displayed by one.
     */
    public void setContadoraParaImagenes()
    {
        this.contadoraParaImagenes++;
    }
    /**
     * Sets the length of the word to be guessed.
     *
     * @param longitud the length of the word
     */
    public void setLongitud(int longitud)
    {
        this.longitud = longitud;
    }
    /**
     * Gets the length of the word to be guessed.
     *
     * @return the length of the word
     */
    public int getLongitud()
    {
        return this.longitud;
    }
    /**
     * Sets the list of characters in the word to be guessed.
     *
     * @param palabraConCaracteresAdivinar the list of characters
     */
    public void setPalabraConCaracteresAdivinar(ArrayList<String> palabraConCaracteresAdivinar)
    {
        this.palabraConCaracteresAdivinar = palabraConCaracteresAdivinar;
    }
    /**
     * Gets the character in the word to be guessed at the specified index.
     *
     * @param index the index of the character
     * @return the character at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public String getPalabraConCaracteresAdivinar(int index) {
        if (index >= 0 && index < this.longitud) {
            return this.palabraConCaracteresAdivinar.get(index);
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
    }
    /**
     * Gets the character entered by the user at the specified index.
     *
     * @param index the index of the character
     * @return the character at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public String getPalabraConCaracteresUsuario(int index) {
        if (index >= 0 && index < this.longitud) {
            return this.palabraConCaracteresUsuario.get(index);
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
    }
    /**
     * Prints the list of characters entered by the user to the terminal.
     */
    public void showPalabraConCaracteresUsuario() {
        System.out.println("El valor de la palabra con caracteres del usuario es: " + this.palabraConCaracteresUsuario);
    }
    /**
     * Gets the list of characters entered by the user.
     *
     * @return the list of characters entered by the user
     */
    public ArrayList<String> getPalabraConCaracteresUsuario()
    {
        return this.palabraConCaracteresUsuario;
    }
    /**
     * Sets the word to be guessed.
     *
     * @param palabraAdivinar the word to be guessed
     */
    public void setPalabraAdivinar(String palabraAdivinar)
    {
        this.palabraAdivinar = palabraAdivinar;
    }
    /**
     * Gets the word to be guessed.
     *
     * @return the word to be guessed
     */
    public String getPalabraAdivinar() {
        return this.palabraAdivinar;
    }
    /**
     * Sets the word entered by the user.
     *
     * @param palabraUsuario the word entered by the user
     */
    public void setPalabraUsuario(String palabraUsuario)
    {
        this.palabraUsuario = palabraUsuario;
    }

}
