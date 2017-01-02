package routine;


import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import rune_tools.RuneJudge;

public class RuneBattle extends SuperBattle{

    private RuneJudge runeJudge;

    public void try_victory_options() {

        // Check if there is a sell option
        try {

            // If the sell button is found, the drop is a rune
            Match match = nox_region.find("sell");

            // check whether we should sell it or not
            boolean verdict = runeJudge.judge_rune();

            // For testing purposes
            sleep(10000);

            if (verdict) {
                persistent_click("get");
            } else {
                sell_rune();
            }

            return;

        } catch (FindFailed e) {
            e.printStackTrace();
        }

        // If it's a non-rune drop
        persistent_click("ok");

    }

    public void sell_rune() {

        try {

            Region sell_button = nox_region.find("sell");
            randomized_click(sell_button);

            Region yes_sell_button = nox_region.find("yes");
            randomized_click(yes_sell_button);

        } catch (FindFailed e) {
//            e.printStackTrace();
        }

    }


    public boolean post_stage_options() {

        boolean attempt1 = persistent_click("replay");
        if (!attempt1) return false;

        // out of energy
        // yes,
        // Energy Icon
        // YES
        // OK
        // Close
        try {

            Region out_of_energy = nox_region.wait("not_enough_energy", 3);

            System.out.println("Out of Energy");

            Region initial_yes = nox_region.wait("yes", 10);
            randomized_click(initial_yes);

            Region refresh_energy = nox_region.wait("refresh_energy", 10);
            randomized_click(refresh_energy);

            Region yes_refresh = nox_region.wait("yes_refresh", 10);
            randomized_click(yes_refresh);

            Region ok = nox_region.wait("ok", 10);
            randomized_click(ok);

            Region close = nox_region.wait("close_energy_purchase", 10);
            randomized_click(close);

        } catch (FindFailed e) {
            return true;
        }

        return persistent_click("replay");

    }


    public RuneBattle() {
        runeJudge = new RuneJudge();
    }

    public static void main(String[] args) {
        RuneBattle runeBattle = new RuneBattle();
        runeBattle.battle_mode();
    }

}
