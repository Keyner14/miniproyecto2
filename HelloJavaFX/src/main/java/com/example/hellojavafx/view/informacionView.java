package com.example.hellojavafx.view;

import com.example.hellojavafx.controllers.informacionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The informacionView class represents the view for displaying information.
 * It loads the FXML layout and initializes the corresponding controller.
 *
 * @version 1.0
 */
public class informacionView extends Stage
{
    /**
     * The controller associated with this view.
     */
    private informacionController informacionController;

    /**
     * Constructor for informacionView.
     * Loads the FXML layout and initializes the controller.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public informacionView() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/hellojavafx/informacion-view.fxml")
        );
        Parent root = loader.load();
        // se usa para conectar la vista de Game con el controlador de game
        this.informacionController = loader.getController();
        this.setTitle("Sol eclipsado");
        Scene scene = new Scene(root);
        this.getIcons().add(new Image(
                getClass().getResourceAsStream("/com/example/hellojavafx/images/sol.png")
        ));
        this.setScene(scene);
        this.show();
    }
    /**
     * Gets the controller associated with this view.
     *
     * @return the informacionController instance
     */
    public informacionController getGameController() {
        return this.informacionController;
    }
    /**
     * Gets the singleton instance of informacionView.
     *
     * @return the singleton instance of informacionView
     * @throws IOException if the FXML file cannot be loaded
     */
    public static informacionView getInstance() throws IOException {
        return GameViewHolder.INSTANCE = new informacionView();
    }
    /**
     * Holder class for the singleton instance of informacionView.
     */
    private static class GameViewHolder {
        private static informacionView INSTANCE;
    }
}






















