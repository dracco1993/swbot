package navigate;

import locator.NoxFinder;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Region;

import java.awt.*;
import java.awt.event.InputEvent;


public abstract class Navigate {

    protected String location, stage;
    protected int move_right;

    public void sleep(long ms) {

        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }

    public void move_screen_right(Region nox_region) {

        try {

            Robot robot = new Robot();
            robot.setAutoDelay(500);

            int mid_x = (int)(nox_region.getW() * 0.50) + nox_region.getX();
            int mid_y = (int)(nox_region.getH() * 0.50) + nox_region.getY();

            int left_x = nox_region.getX() + 10;
            int left_y = mid_y;

            robot.mouseMove(mid_x, mid_y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

            robot.mouseMove(left_x, left_y);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    public void move_cairos_main_up(Region nox_region) {

        try {

            Robot robot = new Robot();
            robot.setAutoDelay(1000);

            int mid_x = (int)(nox_region.getW() * 0.25) + nox_region.getX();
            int mid_y = (int)(nox_region.getH() * 0.50) + nox_region.getY();

            int t_x = mid_x;
            int t_y = nox_region.getY();

            robot.mouseMove(mid_x, mid_y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

            robot.mouseMove(t_x, t_y);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        } catch (AWTException e) {
            e.printStackTrace();
        }

    }



    public void click_middle(Region nox_region) {

        try {

            Robot robot = new Robot();
            robot.setAutoDelay(500);

            int mid_x = (int)(nox_region.getW() * 0.50) + nox_region.getX();
            int mid_y = (int)(nox_region.getH() * 0.50) + nox_region.getY();

            robot.mouseMove(mid_x, mid_y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    public void click_bot_left(Region nox_region) {

        try {

            Robot robot = new Robot();
            robot.setAutoDelay(500);

            int x = nox_region.getX();
            int y = (int)(nox_region.getH() * 0.98) + nox_region.getY();

            robot.mouseMove(x, y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        } catch (AWTException e) {
            e.printStackTrace();
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

            for (int i = 0; i < move_right; i++) {
                move_screen_right(nox_region);
            }

            try {
                Region loc_region = nox_region.find(location);
                loc_region.click();
            } catch (FindFailed e) {
                e.printStackTrace();
            }

            try {

                Region faimon_1 = nox_region.find(stage);

                Region button = faimon_1.find("battle");
                button.click();

                Region start_button = nox_region.find("start_battle");
                target_found = true;

            } catch (FindFailed e) {
                e.printStackTrace();
            }

        }

    }

    public Navigate(String location, String stage, int move_right) {

        this.location = location;
        this.stage = stage;
        this.move_right = move_right;

        ImagePath.add(getClass().getResource("/sw_icons"));

    }

}
