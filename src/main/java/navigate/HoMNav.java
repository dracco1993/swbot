package navigate;

import locator.NoxFinder;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Region;

/**
 * Created by David on 12/24/2016.
 */
public class HoMNav extends Navigate {

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
                Region cairos = nox_region.find(location);
                cairos.click();
            } catch (FindFailed e) {
                e.printStackTrace();
            }

            move_cairos_main_up(nox_region);

            try {
                Region hom = nox_region.find("halls_of_magic");
                hom.click();
            } catch (FindFailed e) {
                e.printStackTrace();
            }

            try {

                Region stage_region = nox_region.find(stage);

                Region button = stage_region.find("battle");
                button.click();

                Region start_button = nox_region.find("start_battle");
                target_found = true;

            } catch (FindFailed e) {
                e.printStackTrace();
            }

        }

    }


    public HoMNav() {
        super("cairos_dungeon", "3_hom", 0);
    }
}
