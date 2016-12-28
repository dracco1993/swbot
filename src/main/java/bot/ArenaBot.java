package bot;

import locator.NoxFinder;
import navigate.ArenaNav;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import routine.ArenaBattle;

import java.util.Iterator;


public class ArenaBot extends Bot {

    public void run_bot() {

        ArenaBattle arenaBattle = new ArenaBattle();
        ArenaNav arenaNav = new ArenaNav();
        arenaNav.navigate();

        Region nox_region = new NoxFinder().find_nox();

        try {

            Iterator<Match> matches = nox_region.findAll("arena_battle");

            while (matches.hasNext()) {

                Match curr = matches.next();
                curr.click();

                sleep(4000);

                try {

                    // Not enough arena wings
                    Region not_enough_arena = nox_region.wait("not_enough_arena", 3);
                    Region no = nox_region.wait("no", 3);

                    no.click();

                    return;

                } catch (FindFailed e) {
                    e.printStackTrace();
                }

                arenaBattle.battle_mode();

            }

        } catch (FindFailed e) {
            e.printStackTrace();
        }

    }


    public ArenaBot() {
        ImagePath.add(getClass().getResource("/sw_icons"));
    }


    public static void main(String[] args) {
        ArenaBot arenaBot = new ArenaBot();
        arenaBot.run_bot();
    }

}
