package dev.starryeye.minesweeper.tobe;

import dev.starryeye.minesweeper.tobe.gamelevel.Advanced;
import dev.starryeye.minesweeper.tobe.gamelevel.Beginner;
import dev.starryeye.minesweeper.tobe.gamelevel.GameLevel;
import dev.starryeye.minesweeper.tobe.gamelevel.VeryBeginner;

public class GameApplication {

    public static void main(String[] args) {

        GameLevel gameLevel = new Beginner();

        Minesweeper minesweeper = new Minesweeper(gameLevel);
        minesweeper.initialize();
        minesweeper.run();
    }
}
