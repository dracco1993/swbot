package locator;

import org.sikuli.script.*;

public class NoxFinder {

    private Screen screen;

    public Region find_nox() {

        try {

            Region top_left = screen.find("nox.png");
            Region bot_right = screen.find("nox_bot_right");

            double tl_x = top_left.getX();
            double tl_y = top_left.getY() + top_left.getH() * 1.5;

            double br_x = bot_right.getX() - bot_right.getW() * 0.5;
            double br_y = bot_right.getY() + bot_right.getH() * 0.95;

//            Location top_left_point = new Location(tl_x, tl_y);
//            Location bot_right_point = new Location(br_x, br_y);

            int ret_x = (int) Math.round(tl_x);
            int ret_y =(int) Math.round(tl_y);

//            System.out.println(top_left_point.getY() + " " + ret_y);

            int ret_w = (int) Math.round(br_x - tl_x);
            int ret_h = (int) Math.round(br_y - tl_y);

//            System.out.println(ret_x + " : " + ret_y + " : " + ret_w + " : " + ret_h);

            Region ret = new Region(ret_x, ret_y, ret_w, ret_h);

            return ret;

        } catch (FindFailed e) {

            e.printStackTrace();

        }

        return null;

    }


    public NoxFinder() {

        screen = new Screen();
        ImagePath.add(getClass().getResource("/sw_icons"));

    }

    public static void main(String[] args) {

        NoxFinder noxFinder = new NoxFinder();
        Region nox_region = noxFinder.find_nox();

        nox_region.highlight(5);

    }

}
