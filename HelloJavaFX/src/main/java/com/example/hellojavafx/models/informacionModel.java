package com.example.hellojavafx.models;

/**
 * The informacionModel class represents a model that validates a word based on specific conditions.
 * @author Keyner Fernando Meneses Recuero
 * @version 1.0
 */

public class informacionModel
{
    /**
     * Indicates whether the word is valid based on the specified conditions.
     * @serialField
     */
    private boolean palabraValida;

    /**
     * Sets the validity of the word.
     *
     * @param palabraValida the validity of the word
     * @return the updated validity of the word
     */
    public boolean setPalabraValida(boolean palabraValida)
    {
        return this.palabraValida = palabraValida;
    }
    /**
     * Gets the validity of the word.
     *
     * @return the validity of the word
     */
    public boolean getPalabraValida()
    {
        return this.palabraValida;
    }
    /**
     * Checks if the given word meets the specified conditions.
     * The word must be between 6 and 12 characters long and contain no spaces or numbers.
     *
     * @param palabra the word to be checked
     * @return {@code true} if the word meets the conditions, {@code false} otherwise
     */
    public boolean comprobarCondiciones(String palabra)
    {
        boolean esCorrecto = false;
        try
        {
            if (!palabra.matches("^[a-zA-ZñÑáéíóúÁÉÍÓÚ]{6,12}$"))
            {
                throw new IllegalArgumentException("La palabra debe tener entre 6 y 12 caracteres, sin espacios ni números.");
            }
            esCorrecto = true;
            this.setPalabraValida(esCorrecto);
            System.out.println("Entré al final del bloque try");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
            esCorrecto = false;
            this.setPalabraValida(esCorrecto);
            System.out.println("Entré al final del bloque catch");
        }
        System.out.println(this.getPalabraValida());
        return this.getPalabraValida();
    }


}


