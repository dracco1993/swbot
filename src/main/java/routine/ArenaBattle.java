package routine;


import locator.NoxFinder;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Region;

public class ArenaBattle {

    public Boolean wait_for_victory(Region nox_region) {

        try {

            // Victory
            Region victory_region = nox_region.wait("victory", 60);

            sleep(4000);

            victory_region.doubleClick();
            sleep(1000);

            victory_region.doubleClick();
            sleep(1000);

            return true;

        } catch (FindFailed e) {
            e.printStackTrace();
        }

        return false;

    }

    public Boolean wait_for_defeat(Region nox_region) {

        try {

            // Defeat
            Region defeat_region = nox_region.wait("defeated", 60);

            sleep(4000);

            defeat_region.doubleClick();
            sleep(1000);

            defeat_region.doubleClick();
            sleep(1000);

            return true;

        } catch (FindFailed e) {
            e.printStackTrace();
        }

        return false;
    }

    public void battle_mode() {

        NoxFinder noxFinder = new NoxFinder();
        Region nox_region = noxFinder.find_nox();

        try {

            Region start_button = nox_region.wait("start_arena_battle", 600);
            start_button.click();

            sleep(10000);

            // Check if auto is on
            try {
                Region auto_button = nox_region.wait("auto", 5);
                auto_button.click();
            } catch (FindFailed e) {
                e.printStackTrace();
            }

            boolean victory = wait_for_victory(nox_region);

            if (victory) return;

            boolean defeat = wait_for_defeat(nox_region);

            if (!defeat) {
                return;
            }

        } catch (FindFailed e) {
            e.printStackTrace();
        }

    }

    public void sleep(long ms) {

        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }

    public static void main(String[] args) {

        ArenaBattle ab = new ArenaBattle();
        ab.battle_mode();


    }

}
