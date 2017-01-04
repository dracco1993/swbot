package bot;

import routine.RuneBattle;

public class RuneBot {

    private static void routine() {

        while (true) {

            RuneBattle runeBattle = new RuneBattle();
            runeBattle.battle_mode();

        }

    }

    public static void main(String[] args) {
        routine();
    }

}
