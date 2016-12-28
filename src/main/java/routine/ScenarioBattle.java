package routine;


import locator.NoxFinder;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Region;

public class ScenarioBattle {

    private final int OUT_OF_ENERGY = -1;

    public int try_victory_options(Region nox_region) {

        // Sell
        try {
            Region sell = nox_region.find("sell");
            sell.click();
        } catch (FindFailed e) {
            e.printStackTrace();
        }

        // Get
        try {
            Region get = nox_region.find("get");
            get.click();
        } catch (FindFailed e) {
            e.printStackTrace();
        }

        // Ok
        try {
            Region ok = nox_region.find("ok");
            ok.click();
        } catch (FindFailed e) {
            e.printStackTrace();
        }

        // Replay
        try {
            Region replay_button = nox_region.find("replay");
            replay_button.click();
        } catch (FindFailed e) {
            e.printStackTrace();
        }

        try {

            Region no_energy = nox_region.find("not_enough_energy");
            Region no_button = nox_region.find("no");
            no_button.click();

            // Out of energy
            return OUT_OF_ENERGY;

//                System.out.println("TAKING A BREAK");
//                sleep(2000000);
//
//                Region replay_button = nox_region.find("replay");
//                replay_button.click();


        } catch (FindFailed e) {
            e.printStackTrace();
        }

        return 1;

    }

    public void try_defeat_options(Region nox_region) {

        // Replay
        try {
            Region replay_button = nox_region.wait("replay", 5);
            replay_button.click();
        } catch (FindFailed e) {
            e.printStackTrace();
        }

        try {

            Region no_energy = nox_region.find("not_enough_energy");
            Region no_button = nox_region.find("no");
            no_button.click();

            System.out.println("TAKING A BREAK");
            sleep(2000000);

            Region replay_button = nox_region.find("replay");
            replay_button.click();


        } catch (FindFailed e) {
            e.printStackTrace();
        }

    }

    public int wait_for_victory(Region nox_region) {

        try {

            // Victory
            Region victory_region = nox_region.wait("victory", 90);

            sleep(5000);

            victory_region.doubleClick();
            victory_region.doubleClick();

            sleep(2000);

            // 1 - we won, -1 = out of energy
            int result = try_victory_options(nox_region);

            return result;


        } catch (FindFailed e) {
            e.printStackTrace();
        }

        // We lost
        return 0;

    }

    public Boolean wait_for_defeat(Region nox_region) {

        try {

            // Defeat
            Region defeat_region = nox_region.wait("defeated", 90);

            sleep(5000);

            Region revive_no = nox_region.wait("revive_no", 5);

            revive_no.click();

            defeat_region.doubleClick();

            try_defeat_options(nox_region);

            return true;

        } catch (FindFailed e) {
            e.printStackTrace();
        }

        return false;

    }


    public void battle_mode() {

        NoxFinder noxFinder = new NoxFinder();
        Region nox_region = noxFinder.find_nox();

        while (true) {

            try {

                Region start_button = nox_region.wait("start_battle", 60);
                start_button.click();

                sleep(10000);

                // Check if auto is on
                try {
                    Region auto_button = nox_region.wait("auto", 5);
                    auto_button.click();
                } catch (FindFailed e) {
                    e.printStackTrace();
                }

                int victory = wait_for_victory(nox_region);

                if (victory == 1) continue;
                else if (victory == OUT_OF_ENERGY) return;

                boolean defeat = wait_for_defeat(nox_region);

                // Something went wrong
                if (!defeat) {
                    return;
                }

            } catch (FindFailed e) {
                e.printStackTrace();
                return;
            }

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

        ScenarioBattle sb = new ScenarioBattle();
        sb.battle_mode();

    }

}
