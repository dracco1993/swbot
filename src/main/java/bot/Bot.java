package bot;

import locator.NoxFinder;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.awt.*;
import java.awt.event.InputEvent;

public abstract class Bot {

    protected Region nox_region;

    public abstract void run_bot();

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

    public Bot() {
        nox_region = new NoxFinder().find_nox();
        ImagePath.add(getClass().getResource("/sw_icons"));
        ImagePath.add(getClass().getResource("/rune_imgs"));
    }

}
