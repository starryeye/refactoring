package dev.starryeye.minesweeper.tobe;

import dev.starryeye.minesweeper.tobe.minesweeper.Minesweeper;
import dev.starryeye.minesweeper.tobe.minesweeper.config.GameConfig;
import dev.starryeye.minesweeper.tobe.minesweeper.gamelevel.Beginner;
import dev.starryeye.minesweeper.tobe.minesweeper.gamelevel.VeryBeginner;
import dev.starryeye.minesweeper.tobe.minesweeper.io.ConsoleInputHandler;
import dev.starryeye.minesweeper.tobe.minesweeper.io.ConsoleOutputHandler;

public class GameApplication {

    public static void main(String[] args) {

        GameConfig gameConfig = GameConfig.of(
                new VeryBeginner(),
                new ConsoleInputHandler(),
                new ConsoleOutputHandler()
        );

        Minesweeper minesweeper = new Minesweeper(gameConfig);
        minesweeper.initialize();
        minesweeper.run();
    }

    /**
     * DIP (Dependency Inversion Principle)
     *
     * 참고
     * Spring 의 요소
     * - DI (Dependency Injection) 와 IoC (Inversion of Control)
     *      DI : 두 객체 간의 의존성이 필요한 경우, 런타임 시점에 제 3 자가 의존성을 주입하여 맺어준다.
     *      IoC : 제어의 역전, 프로그램의 흐름을 개발자가 아닌 프레임워크가 담당하도록 한다. (개발자의 코드는 프레임워크의 일부가 되어 프로그램 제어의 주도권이 프레임워크로 가버려서 제어 역전임)
     *      Spring DI(IoC) 컨테이너의 역할
     *          빈(스프링이 관리하는 객체)들을 생성, 소멸, 빈들 간의 의존성 관리 등 생명주기(제어)를 관리한다.
     * - PSA
     * - AOP
     */
}
