package bot;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;
import routine.SuperBattle;

public class InceptionBot extends Bot {

    @Override
    public void run_bot() {

        Screen screen = new Screen();

        while (true) {

            try {

                Match sb1 = nox_region.find("start_battle");

                Match java_icon = screen.find("java_icon");

                java_icon.doubleClick();

                // around 30 seconds
                sleep(30000);

                Match sb2 = nox_region.wait("start_battle", 420);

                sleep(15000);

            } catch (FindFailed e) {

                e.printStackTrace();

                boolean replay_available = persistent_click("replay");
                if (!replay_available) return;

            }

        }

    }


    public static void main(String[] args) {

        InceptionBot inceptionBot = new InceptionBot();
        inceptionBot.run_bot();

    }



}
