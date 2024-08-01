package dev.starryeye.minesweeper.tobe.config;

import dev.starryeye.minesweeper.tobe.gamelevel.GameLevel;
import dev.starryeye.minesweeper.tobe.io.InputHandler;
import dev.starryeye.minesweeper.tobe.io.OutputHandler;

public class GameConfig {

    private final GameLevel gameLevel;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    private GameConfig(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {
        this.gameLevel = gameLevel;
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public static GameConfig of(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {
        return new GameConfig(gameLevel, inputHandler, outputHandler);
    }

    public GameLevel getGameLevel() {
        return gameLevel;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public OutputHandler getOutputHandler() {
        return outputHandler;
    }
}
