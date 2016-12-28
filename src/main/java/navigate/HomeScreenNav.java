package navigate;

import locator.NoxFinder;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Region;

import java.util.ArrayList;

public class HomeScreenNav extends Navigate{

    public int check_button(Region nox_region, String button_name) {

        try {
            Region ok = nox_region.wait(button_name, 3);
            ok.click();
            return 1;
        } catch (FindFailed e) {
            e.printStackTrace();
        }

        return 0;

    }

    public void navigate() {

        Region nox_region = new NoxFinder().find_nox();
        boolean target_found = false;

        ArrayList<String> buttons = new ArrayList<>();

        buttons.add("ok");
        buttons.add("touch_to_start");
        buttons.add("x_button");
        buttons.add("back_button");
        buttons.add("return_home");
        buttons.add("close_purchase");
        buttons.add("close_purchase_followup_yes");

        while (!target_found) {

            int num_changes = 0;

            for (String button : buttons) {
                num_changes += check_button(nox_region, button);
            }

            // Check if target is found
            try {

                Region home_battle = nox_region.wait("home_battle", 3);
                num_changes++;

                target_found = true;

                System.out.println("Success!");

            } catch (FindFailed e) {
                e.printStackTrace();
            }

            // If no changes have occurred, click the bottom left corner
            if (num_changes == 0) {
                click_bot_left(nox_region);
            }

        }

    }


    public HomeScreenNav() {
       super("", "", 0);
    }


    public static void main(String[] args) {
        HomeScreenNav hsn = new HomeScreenNav();
        hsn.navigate();
    }

}
