package bot;

import navigate.HoMNav;
import routine.ScenarioBattle;


public class HoMBot extends Bot {

    public void run_bot() {

        HoMNav tamorNav = new HoMNav();
        ScenarioBattle sb = new ScenarioBattle();

        tamorNav.navigate();
        sb.battle_mode();

    }
}
