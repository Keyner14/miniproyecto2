package com.example.hellojavafx.view;

import com.example.hellojavafx.controllers.GameController;
import com.example.hellojavafx.models.GameModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The GameView class represents the view for the game.
 * It loads the FXML layout and initializes the corresponding controller.
 *
 * @version 1.0
 */
public class GameView extends Stage
{
    /**
     * The controller associated with this view.
     */
    private GameController gameController;
    /**
     * Constructor for GameView.
     * Loads the FXML layout and initializes the controller with the given game model.
     *
     * @param gameModel the game model to be used by the controller
     * @throws IOException if the FXML file cannot be loaded
     */
    public GameView(GameModel gameModel) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hellojavafx/game-view.fxml"));
        loader.setControllerFactory(param -> new GameController(gameModel));
        Parent root = loader.load();
        // se usa para conectar la vista de Game con el controlador de game
        this.gameController = loader.getController();
        this.setTitle("Sol eclipsado");
        this.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/sol.png")));
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.show();
    }
    /**
     * Gets the controller associated with this view.
     *
     * @return the GameController instance
     */
    public GameController getGameController()
    {
        return this.gameController;
    }
    /**
     * Gets the singleton instance of GameView.
     *
     * @param gameModel the game model to be used by the controller
     * @return the singleton instance of GameView
     * @throws IOException if the FXML file cannot be loaded
     */
    public static GameView getInstance(GameModel gameModel) throws IOException
    {
        return GameViewHolder.INSTANCE = new GameView(gameModel);
    }
    /**
     * Holder class for the singleton instance of GameView.
     */
    private static class GameViewHolder
    {
        private static GameView INSTANCE;
    }
}