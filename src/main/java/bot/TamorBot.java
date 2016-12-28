package bot;

import navigate.FaimonNav;
import navigate.TamorNav;
import routine.ScenarioBattle;

public class TamorBot extends Bot  {

    public void run_bot() {

        TamorNav tamorNav = new TamorNav();
        ScenarioBattle sb = new ScenarioBattle();

        tamorNav.navigate();
        sb.battle_mode();

    }

}
