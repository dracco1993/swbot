package routine;

import locator.NoxFinder;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.awt.*;
import java.awt.event.InputEvent;

public abstract class SuperBattle {

    protected Region nox_region;

    public void sleep(long ms) {

        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }

    public boolean persistent_click(String gui_name) {

        // Check if the GUI exists
        try {
            Match match = nox_region.wait(gui_name, 10);
            if(match.getScore() < 0.80) return false;
        } catch (FindFailed e) {
            // Failed to find the GUI
            System.out.println("Failed to find " + gui_name);
            return false;
        }

        // Click the GUI until it disappears
        while (true) {

            System.out.println("Checking if " + gui_name + " exists");

            try {

                Match gui = nox_region.wait(gui_name,1);

                System.out.println("It Exists!");

                if (gui.getScore() < 0.80) return true;

                randomized_click(gui);

                // Wait
                sleep(2000);

            } catch (FindFailed e) {
                // If we failed to find it, the gui has been interacted with (hopefully successfully)
                return true;
            }

        }

    }

    // If we click the exact same spot every single time, it might be a little suspicious, so add some randomization
    // to the location where we click
    public void randomized_click(Region region) {

        try {

            Robot robot = new Robot();
            robot.setAutoDelay(250);

            int dw = (int)(region.getW() * 0.10);
            int dh = (int)(region.getH() * 0.10);

            int w = region.getW() - 2 * dw;
            int h = region.getH() - 2 * dh;

            int x = region.getX() + dw;
            int y = region.getY() + dh;

            int rand_x = x + (int)(Math.random() * w);
            int rand_y = y + (int)(Math.random() * h);

            robot.mouseMove(rand_x, rand_y);

            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    
    public void click_upper_region(Region region) {

        try {

            Robot robot = new Robot();
            robot.setAutoDelay(250);

            int dw = (int)(region.getW() * 0.10);
            int dh = (int)(region.getH() * 0.10);

            int w = region.getW() - 2 * dw;
            int h = region.getH() - 8 * dh;

            int x = region.getX() + dw;
            int y = region.getY() + (int)(0.2 * dh);

            int rand_x = x + (int)(Math.random() * w);
            int rand_y = y + (int)(Math.random() * h);

            robot.mouseMove(rand_x, rand_y);

            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    public void try_victory_options() {

        // Ok
        try {
            Region ok = nox_region.find("ok");
            ok.click();
            return;
        } catch (FindFailed e) {
            e.printStackTrace();
        }

        // Sell
        try {
            Region sell = nox_region.find("sell");
            sell.click();
            return;
        } catch (FindFailed e) {
            e.printStackTrace();
        }

        // Get
        try {
            Region get = nox_region.find("get");
            get.click();
            return;
        } catch (FindFailed e) {
            e.printStackTrace();
        }

    }

    public boolean wait_for_victory() {

        try {

            // Wait for 6 minutes
            Region victory_region = nox_region.wait("victory", 300);

            sleep(2500);

            click_upper_region(nox_region);
            click_upper_region(nox_region);

            sleep(2500);

            try_victory_options();

            return true;

        } catch (FindFailed e) {
            e.printStackTrace();
        }

        return false;

    }

    public Boolean wait_for_defeat() {

        try {

            // Defeat
            Region defeat_region = nox_region.wait("defeated", 300);

            sleep(3000);

            persistent_click("revive_no");

            sleep(3000);

            click_upper_region(nox_region);

            sleep(1500);

            click_upper_region(nox_region);

            return true;

        } catch (FindFailed e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean post_stage_options() {

        boolean attempt1 = persistent_click("replay");
        if (!attempt1) return false;

        try {

            Region out_of_energy = nox_region.wait("not_enough_energy", 3);

            System.out.println("Out of Energy");

            Region no = nox_region.wait("no", 3);
            randomized_click(no);

        } catch (FindFailed e) {
            return true;
        }

        return persistent_click("replay");

    }

    public void battle_mode() {

        while (true) {

            System.out.println("Starting...");

            // Start Battle
            boolean sb_click = persistent_click("start_battle");
            if (!sb_click) {
                return;
            }

            // Wait for game to load stage
            sleep(5000);

            // Check if auto has been enabled
            try {

                Match match = nox_region.find("auto");

                if (match.getScore() > 0.85) {
                    match.click();
                }

            } catch (FindFailed e) {
                e.printStackTrace();
            }

            boolean victory = wait_for_victory();

            if (!victory) {

                boolean defeat = wait_for_defeat();

                // If we failed to find defeat, something went wrong
                if (!defeat) return;

            }

            sleep(3000);

            boolean post_result = post_stage_options();

        }

    }

    public void battle_mode_single() {

        System.out.println("Starting...");

        // Start Battle
        boolean sb_click = persistent_click("start_battle");
        if (!sb_click) {

            // Check if we're at the replay screen
            boolean replay_click = persistent_click("replay");
            if (!replay_click) return;

        }

        // Wait for game to load stage
        sleep(5000);

        // Check if auto has been enabled
        try {

            Match match = nox_region.find("auto");

            if (match.getScore() > 0.85) {
                match.click();
            }

        } catch (FindFailed e) {
            e.printStackTrace();
        }

        boolean victory = wait_for_victory();

        if (!victory) {

            boolean defeat = wait_for_defeat();

            // If we failed to find defeat, something went wrong
            if (!defeat) return;

        }

        sleep(3000);

        post_stage_options();

    }



    public SuperBattle() {
        NoxFinder noxFinder = new NoxFinder();
        nox_region = noxFinder.find_nox();
    }


}
