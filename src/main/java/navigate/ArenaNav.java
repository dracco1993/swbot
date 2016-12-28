package navigate;

import locator.NoxFinder;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Region;


public class ArenaNav {

    public void sleep(long ms) {

        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }

    public void navigate() {

        Region nox_region = new NoxFinder().find_nox();
        boolean target_found = false;

        HomeScreenNav homeScreenNav = new HomeScreenNav();

        while (!target_found) {

            homeScreenNav.navigate();

            try {
                Region home_battle = nox_region.wait("home_battle", 3);
                home_battle.click();
            } catch (FindFailed e) {
                e.printStackTrace();
            }

            sleep(2000);

            try {

                Region close_purchase = nox_region.wait("close_purchase", 3);
                close_purchase.click();

                Region follow_up_yes = nox_region.wait("close_purchase_followup_yes", 3);
                follow_up_yes.click();

                sleep(2000);

            } catch (FindFailed e) {
                e.printStackTrace();
            }

            try {
                Region arena = nox_region.wait("arena_loc", 5);
                arena.click();
            } catch (FindFailed e) {
                e.printStackTrace();
            }

            try {

                Region refresh_button = nox_region.wait("refresh_list", 5);
                refresh_button.click();

                Region refresh_followup = nox_region.wait("refresh_warning", 5);

                Region refresh_followup_button = nox_region.wait("refresh_warning_followup", 5);
                refresh_followup_button.click();

            } catch (FindFailed e) {
                e.printStackTrace();
            }

            // Check if arena battle button exists
            try {
                Region arena_battle = nox_region.wait("arena_battle", 5);
                target_found = true;
            } catch (FindFailed e) {
                e.printStackTrace();
            }
        }
    }

    public ArenaNav() {
        ImagePath.add(getClass().getResource("/sw_icons"));
    }

    public static void main(String[] args) {

        ArenaNav arenaNav = new ArenaNav();

        arenaNav.navigate();

    }


}
