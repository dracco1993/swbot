import rune_info.Rune;
import rune_info.SubStat;
import rune_tools.RuneEvaluator;

import java.util.ArrayList;

/**
 * Created by David on 12/28/2016.
 */
public class RuneEvaluatorTest {



    public static void process() {

        RuneEvaluator runeEvaluator = new RuneEvaluator();

        ArrayList<SubStat> s1 = new ArrayList<>();
        s1.add(new SubStat(Rune.PERC_HP, 4));
        s1.add(new SubStat(Rune.SPD, 4));
        s1.add(new SubStat(Rune.PERC_DEF, 4));

        Rune r1 = new Rune(1, 6, s1.size(), Rune.FLAT_HP, null, s1);

        System.out.println("Score: " + runeEvaluator.find_score(r1));

        ArrayList<SubStat> s2 = new ArrayList<>();
        s2.add(new SubStat(Rune.CRI_RATE, 4));
        s2.add(new SubStat(Rune.SPD, 4));
        s2.add(new SubStat(Rune.CRIT_DMG, 7));

        Rune r2 = new Rune(3, 6, s2.size(), Rune.FLAT_HP, null, s2);

        System.out.println("Score: " + runeEvaluator.find_score(r2));

        ArrayList<SubStat> s3 = new ArrayList<>();
        s3.add(new SubStat(Rune.SPD, 6));
        s3.add(new SubStat(Rune.PERC_HP, 8));

        Rune r3 = new Rune(4, 6, s3.size(), Rune.PERC_DEF, null, s3);

        System.out.println("Score: " + runeEvaluator.find_score(r3));


        ArrayList<SubStat> s4 = new ArrayList<>();
        s4.add(new SubStat(Rune.SPD, 5));
        s4.add(new SubStat(Rune.PERC_DEF, 7));
        s4.add(new SubStat(Rune.ACC, 7));

        Rune r4 = new Rune(4, 6, s4.size(), Rune.PERC_HP, null, s4);

        System.out.println("Score: " + runeEvaluator.find_score(r4));



    }

    public static void main(String[] args) {

        process();

    }


}
