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

    public GameView() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hellojavafx/game-view.fxml"));
        Parent root = loader.load();
        this.gameController = loader.getController();
        this.setTitle("Sudoku");
        this.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/sudoku.png")));
        Scene scene = new Scene(root);
        this.setScene(scene);
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

    public static GameView getInstance() throws IOException
    {
        return GameViewHolder.INSTANCE = new GameView();
    }
    /**
     * Holder class for the singleton instance of GameView.
     */
    private static class GameViewHolder
    {
        private static GameView INSTANCE;
    }
}