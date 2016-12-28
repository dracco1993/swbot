package bot;

import java.util.ArrayList;

/**
 * Created by David on 12/23/2016.
 */
public class MasterBot extends Bot {

    public void run_bot() {

        ArrayList<Bot> bots = new ArrayList<>();

        bots.add(new FaimonBot());
        bots.add(new ArenaBot());

        while (true) {

            for (Bot bot : bots) {
                bot.run_bot();
            }

            // Take a break;
            System.out.println("Taking a break");
            sleep(2000000);

        }

    }


    public static void main(String[] args) {

        MasterBot masterBot = new MasterBot();
        masterBot.run_bot();

    }

}
