package bot;

import locator.NoxFinder;
import navigate.FaimonNav;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Region;
import routine.ScenarioBattle;

public class FaimonBot extends Bot {

    public void run_bot() {

        FaimonNav faimonNav = new FaimonNav();
        ScenarioBattle sb = new ScenarioBattle();

        faimonNav.navigate();
        sb.battle_mode();

    }


    public FaimonBot() {
        ImagePath.add(getClass().getResource("/sw_icons"));
    }

    public static void main(String[] args) {

        FaimonBot faimonBot = new FaimonBot();
        faimonBot.run_bot();

    }

}
