package rune_tools;

import javafx.scene.transform.Translate;
import locator.NoxFinder;
import org.sikuli.basics.Settings;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import rune_info.Rune;
import rune_info.SubStat;
import rune_info.Translator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.*;

public class RuneIdentifier {

    private Region nox_region;
    private RuneEvaluator runeEvaluator;

    public Region locate_border() {

        Region ret = null;

        try {

            Region top_edge = nox_region.find("top_edge");
            Region bot_edge = nox_region.find("bot_edge");
            Region left_edge = nox_region.find("left_edge");
            Region right_edge = nox_region.find("right_edge");

            int x = top_edge.getX() + 5;
            int y = top_edge.getY();
            int w = (int)(top_edge.getW() * 0.99);
            int h = left_edge.getH();

            ret = new Region(x, y, w, h);

        } catch (FindFailed e) {
            e.printStackTrace();
        }

        return ret;

    }

    public int eval_slot(Region rune_region) {

        int x = rune_region.getX() + (int)(rune_region.getW() * 0.08);
        int y = rune_region.getY() + (int)(rune_region.getH() * 0.17);
        int w = (int)(rune_region.getW() * 0.16);
        int h = (int)(rune_region.getH() * 0.15);

        Region top_section = new Region(x, y, w, h);

//        top_section.highlight();

        ArrayList<String> slot_images = new ArrayList<>();
        slot_images.add("slot_1");
        slot_images.add("slot_2");
        slot_images.add("slot_3");
        slot_images.add("slot_4");
        slot_images.add("slot_5");
        slot_images.add("slot_6");

        Settings.MinSimilarity = 0;

        int hslot = 0;
        double hscore = 0;

        for (int i = 0; i < slot_images.size(); i++) {

            try {


                Match slot = top_section.find(slot_images.get(i));

                System.out.println("slot " + (i + 1) + ' ' +  slot.getScore());

                if (slot.getScore() > hscore) {
                    hslot = i + 1;
                    hscore = slot.getScore();
                }


            } catch (FindFailed e) {
//                System.out.println("It's not slot " + (i + 1));
            }

        }

        Settings.MinSimilarity = 0.70;

        return hslot;

    }

    public int eval_grade(Region rune_region) {

        int x = rune_region.getX();
        int y = rune_region.getY() + (int)(rune_region.getH() * 0.13);
        int w = (int)(rune_region.getW() * 0.25);
        int h = (int)(rune_region.getH() * 0.20);

        Region grade_region = new Region(x, y, w, h);

        ArrayList<String> grade_images = new ArrayList<>();
        grade_images.add("6_star");
        grade_images.add("5_star");

        for (int i = 0; i < grade_images.size(); i++) {

            int grade = 6 - i;

            try {

                Match grade_image = grade_region.find(grade_images.get(i));
                if(grade_image.getScore() < 0.90) continue;

                return grade;

            } catch (FindFailed e) {
//                System.out.println("it's not grade " + grade);
            }

        }

        return -1;

    }

    public int eval_primary_stat(Region rune_region) {

        int x = rune_region.getX() + (int)(rune_region.getW() * 0.25);
        int y = rune_region.getY() + (int)(rune_region.getH() * 0.13);
        int w = (int)(rune_region.getW() * 0.50);
        int h = (int)(rune_region.getH() * 0.10);

        Region primary_region = new Region(x, y, w, h);

        ArrayList<String> primary_images = new ArrayList<>();

        primary_images.add("primary_hp");
        primary_images.add("primary_atk");
        primary_images.add("primary_def");
        primary_images.add("primary_speed");
        primary_images.add("primary_crit_rate");
        primary_images.add("primary_crit_dmg");
        primary_images.add("primary_resist");
        primary_images.add("primary_accuracy");

        for (int i = 0; i < primary_images.size(); i++) {

            int idx = -1;

            try {

                Match primary_stat = primary_region.find(primary_images.get(i));

//                System.out.println(Translator.getInstance().translate_stat(i + 3) + " " + primary_stat.getScore());

                if (primary_stat.getScore() < 0.83) continue;

                if (i < 3) {

                    idx = i * 2;

                    // Try to find a percent sign
                    Match percent = primary_region.find("primary_percent");

                    // It's a percent rune
                    if (percent.getScore() > 0.80) idx++;

                } else {

                    // Add the offset
                    idx = i + 3;

                }

            } catch (FindFailed e) {
//                System.out.println("tested: " + i);
            }

            if (idx != -1) return idx;

        }

        return -1;

    }

    public SubStat eval_substat_stat(Region rune_region, Region sub_region) {

        ArrayList<String> sub_images = new ArrayList<>();

        sub_images.add("sub_hp");
        sub_images.add("sub_atk");
        sub_images.add("sub_def");
        sub_images.add("sub_speed");
        sub_images.add("sub_crit_rate");
        sub_images.add("sub_crit_dmg");
        sub_images.add("sub_resist");
        sub_images.add("sub_accuracy");

        for (int i = 0; i < sub_images.size(); i++) {

            int idx = -1;

            try {

                Match sub_stat = sub_region.find(sub_images.get(i));

//                System.out.println(i + " : " + sub_stat.getScore());

                if (sub_stat.getScore() < 0.80) continue;

                if (i < 3) {

                    idx = i * 2;

                    // Try to find a percent sign
                    Match percent = sub_region.find("sub_percent");

                    // It's a percent rune
                    if (percent.getScore() > 0.80) idx++;

                } else {

                    // Add the offset
                    idx = i + 3;

                }

            } catch (FindFailed e) {
//                System.out.println("tested: " + sub_images.get(i));
            }

            if (idx != -1) {

                int val = 0;

                // Flat Stat, the value is not a concern
                if (idx == 0 || idx == 2 || idx == 4) {

                    return new SubStat(idx, 1);

                } else {

                    try {

                        Region plus_region = sub_region.find("sub_plus");

                        int x = plus_region.getX() + plus_region.getW();
                        int y = sub_region.getY();
                        int w = plus_region.getW() + 2;
                        int h = sub_region.getH();

                        Region number_region = new Region(x, y, w, h);

                        val = find_value(number_region);

                    } catch (FindFailed e) {
                        e.printStackTrace();
                    }

                }

                return new SubStat(idx, val);

            }

        }

        return null;

    }

    int find_value(Region num_region) {

        ArrayList<String> number_images  = new ArrayList<>();

        number_images.add("sub_4");
        number_images.add("sub_5");
        number_images.add("sub_6");
        number_images.add("sub_7");
        number_images.add("sub_8");

        for (int i = 0; i < number_images.size(); i++) {

            try {

                Match m = num_region.find(number_images.get(i));
//                System.out.println("Num: " + (i + 4) + " : " + m.getScore());

                if (m.getScore() < 0.85) continue;

                // If the find was successful
                return i + 4;

            } catch (FindFailed e) {
//                System.out.println("not number " + (i + 4) );
            }

        }

        return 0;
    }



    public Region find_innate_region(Region rune_region) {

        int x = rune_region.getX() + (int)(rune_region.getW() * 0.25);
        int y = rune_region.getY() + (int)(rune_region.getH() * 0.23);
        int w = (int)(rune_region.getW() * 0.50);
        int h = (int)(rune_region.getH() * 0.10);

        Region ret = new Region(x, y, w, h);

        return ret;

    }

    public Region find_sub1_region(Region rune_region) {

        int x = rune_region.getX() + (int)(rune_region.getW() * 0.07);
        int y = rune_region.getY() + (int)(rune_region.getH() * 0.34);
        int w = (int)(rune_region.getW() * 0.50);
        int h = (int)(rune_region.getH() * 0.07);

        Region ret = new Region(x, y, w, h);

        return ret;
    }

    public Region find_sub2_region(Region rune_region) {

        int x = rune_region.getX() + (int)(rune_region.getW() * 0.07);
        int y = rune_region.getY() + (int)(rune_region.getH() * 0.41);
        int w = (int)(rune_region.getW() * 0.50);
        int h = (int)(rune_region.getH() * 0.07);

        Region ret = new Region(x, y, w, h);

        return ret;
    }

    public Region find_sub3_region(Region rune_region) {

        int x = rune_region.getX() + (int)(rune_region.getW() * 0.07);
        int y = rune_region.getY() + (int)(rune_region.getH() * 0.48);
        int w = (int)(rune_region.getW() * 0.50);
        int h = (int)(rune_region.getH() * 0.07);

        Region ret = new Region(x, y, w, h);

        return ret;
    }

    public Region find_sub4_region(Region rune_region) {

        int x = rune_region.getX() + (int)(rune_region.getW() * 0.07);
        int y = rune_region.getY() + (int)(rune_region.getH() * 0.55);
        int w = (int)(rune_region.getW() * 0.50);
        int h = (int)(rune_region.getH() * 0.07);

        Region ret = new Region(x, y, w, h);

        return ret;
    }


    public Rune identify_rune() {

        ExecutorService executor = Executors.newCachedThreadPool();

        final Region rune_region = locate_border();

        // Define
        Callable<Integer> slot_call = () -> {
            return eval_slot(rune_region);
        };

        Callable<Integer> grade_call = () -> {
            return eval_grade(rune_region);
        };

        Callable<Integer> primary_call = () -> {
            return eval_primary_stat(rune_region);
        };

        Callable<SubStat> innate_call = () -> {
            return eval_substat_stat(rune_region, find_innate_region(rune_region));
        };


        ArrayList<Region> sub_regions = new ArrayList<>();
        sub_regions.add(find_sub1_region(rune_region));
        sub_regions.add(find_sub2_region(rune_region));
        sub_regions.add(find_sub3_region(rune_region));
        sub_regions.add(find_sub4_region(rune_region));

        // Run
        ArrayList<Future<SubStat>> substat_futures = new ArrayList<>();

        for (int i = 0; i < 4; i++) {

            final int idx = i;

            Callable<SubStat> subStatCallable = () -> {
                return eval_substat_stat(rune_region, sub_regions.get(idx));
            };

            substat_futures.add(executor.submit(subStatCallable));

        }

        Future<Integer> future_slot = executor.submit(slot_call);
        Future<Integer> future_grade = executor.submit(grade_call);
        Future<Integer> future_primary = executor.submit(primary_call);
        Future<SubStat> future_innate = executor.submit(innate_call);

        // Get
        try {

            int slot = future_slot.get();
            int grade = future_grade.get();
            int primary = future_primary.get();

            SubStat si = future_innate.get();

            ArrayList<SubStat> sub_stats = new ArrayList<>();

            for (int i = 0; i < 4; i++) {

                SubStat curr = substat_futures.get(i).get();

                if (curr != null) sub_stats.add(substat_futures.get(i).get());
            }

            int rarity = sub_stats.size();

            Rune ret = new Rune(slot, grade, rarity, primary, si, sub_stats);

            return ret;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        return null;

    }

    public RuneIdentifier() {

        ImagePath.add(getClass().getResource("/sw_icons"));
        ImagePath.add(getClass().getResource("/rune_imgs"));
        nox_region = new NoxFinder().find_nox();

        runeEvaluator = new RuneEvaluator();

    }

    public static void main(String[] args) {

        RuneIdentifier runeIdentifier = new RuneIdentifier();

    }

}
