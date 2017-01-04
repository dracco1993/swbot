package rune_tools;

import rune_info.Rune;
import rune_info.SubStat;
import rune_info.Translator;

import java.util.HashMap;
import java.util.HashSet;

public class RuneEvaluator {

    private final double ODD_SLOT_MIN_SCORE = 0.45;
    private final double EVEN_SLOT_MIN_SCORE = 0.60;

    private HashMap<Integer, Integer> stat_value;
    private HashSet<Integer> support_set;
    private HashSet<Integer> attack_set;
    private int[][] primary_stat_value;
    private int[] sub_max;

    private double evaluate_odd_slot(Rune rune) {

        final double SYNERGY_RATIO = 0.40;
        final double SUBSTAT_QUALITY_RATIO = 0.45;
        final double POTENTIAL_RATIO = 0.15;

        // Assert the numbers add up to around 1
        double min_diff = 0.0005;
        double total = SYNERGY_RATIO + SUBSTAT_QUALITY_RATIO + POTENTIAL_RATIO;
        assert Math.abs(total - 1.0) < min_diff;

        double score = 0;

        // 6 star rune has the maximum potential
        if (rune.getGrade() == 6) score += POTENTIAL_RATIO;

//        System.out.println("After potential: " + score);

        // Evaluate Substat quality
        score += evaluate_substat_quality(rune, SUBSTAT_QUALITY_RATIO);

//        System.out.println("After quality: " + score);

        // Evaluate Synergy
        score += evaluate_substat_synergy(rune, SYNERGY_RATIO);

//        System.out.println("After synergy: " + score);

        return score;

    }

    private double evaluate_even_slot(Rune rune) {

        final double PRIMARY_RATIO = 0.30;
        final double POTENTIAL_RATIO = 0.20;
        final double SYNERGY_WITH_PRIMARY_RATIO = 0.25;
        final double SUBSTAT_QUALITY_RATIO = 0.35;

        // Assert the numbers add up to around 1
        double min_diff = 0.0005;
        double total = PRIMARY_RATIO + SYNERGY_WITH_PRIMARY_RATIO + SUBSTAT_QUALITY_RATIO + POTENTIAL_RATIO;
        assert Math.abs(total - 1.0) < min_diff;

        double score = 0;

        // Primary Stat
        score += evaluate_primary_stat(rune, PRIMARY_RATIO);

        // Potential
        if(rune.getGrade() == 6) score += POTENTIAL_RATIO;

        // Synergy

        // Special case w/ speed
        if (rune.getPrimary_stat() == Rune.SPD) {
            score += evaluate_substat_synergy(rune, SYNERGY_WITH_PRIMARY_RATIO);
        } else {
            score += evaluate_primary_synergy(rune, SYNERGY_WITH_PRIMARY_RATIO);
        }

        //Quality
        score += evaluate_substat_quality(rune, SUBSTAT_QUALITY_RATIO);

        return score;

    }

    private double evaluate_primary_synergy(Rune rune, double synergy_with_primary_ratio) {

        boolean is_support = support_set.contains(rune.getPrimary_stat());

        double ratio = synergy_with_primary_ratio / 5;
        int cnt = 0;

        if (rune.getInnate_stat() != null) {

            if (is_support && support_set.contains(rune.getInnate_stat().getStat())) {
                cnt++;
            } else if(!is_support && attack_set.contains(rune.getInnate_stat().getStat())) {
                cnt++;
            }

        }

        for (SubStat substat : rune.getSub_stats()) {

            if (is_support && support_set.contains(substat.getStat())) {
                cnt++;
            } else if(!is_support && attack_set.contains(substat.getStat())) {
                cnt++;
            }

        }

        return (double)cnt * ratio;

    }

    private double evaluate_primary_stat(Rune rune, double primary_ratio) {
        return ((double)primary_stat_value[rune.getSlot()][rune.getPrimary_stat()] / 100) * primary_ratio;
    }

    private double evaluate_substat_synergy(Rune rune, double max_score) {

        double ratio = max_score / 5;

        int support_cnt = 0;
        int attack_cnt = 0;

        if (rune.getInnate_stat() != null) {
            if (support_set.contains(rune.getInnate_stat().getStat())) {
                support_cnt++;
            } else if (attack_set.contains(rune.getInnate_stat().getStat())) {
                attack_cnt++;
            }
        }

        for (SubStat subStat : rune.getSub_stats()) {
            if (support_set.contains(subStat.getStat())) {
                support_cnt++;
            } else if (attack_set.contains(subStat.getStat())) {
                attack_cnt++;
            }
        }

        int max_cnt = Math.max(support_cnt, attack_cnt);

        return (double) max_cnt * ratio;
    }

    private double evaluate_substat_quality(Rune rune, double max_score) {

        // 4.5 total sub stats
        // Innate is evaluated with a 0.5 weight, while a regular substat is weighed with 1.0.
        double half = max_score / 9.0;
        double whole = half * 2;

        double score = 0;

        // Evaluate innate stat
        if (rune.getInnate_stat() != null) {
            score += evaluate_sub_stat(rune.getInnate_stat()) * half;
        }

        // Evaluate sub stats
        for (SubStat subStat : rune.getSub_stats()) {
            score += evaluate_sub_stat(subStat) * whole;

//            System.out.println(Translator.getInstance().translate_stat(subStat.getStat()) + ' ' + evaluate_sub_stat(subStat) * whole);

        }

        return score;
    }


    public double evaluate_sub_stat(SubStat subStat) {

        // Efficiency x value
        double score = evaluate_efficiency(subStat.getStat(), subStat.getValue()) *
                ((double)stat_value.get(subStat.getStat()) / 100);

        return score;
    }

    public double evaluate_efficiency(int stat, int value) {
        return (double)value / sub_max[stat];
    }

    public boolean is_even(Rune rune) {

        HashSet<Integer> flat_stat = new HashSet<>();

        flat_stat.add(Rune.FLAT_ATK);
        flat_stat.add(Rune.FLAT_DEF);
        flat_stat.add(Rune.FLAT_HP);

        return !flat_stat.contains(rune.getPrimary_stat()) || (rune.getSlot() % 2 == 0);

    }

    public double find_score(Rune rune) {

        double score = 0;

        // check if it's a 4 star rune
        if (rune.getGrade() == -1) return score;

        // Check if it's an even or odd slot rune
        if (is_even(rune)) {
            score = evaluate_even_slot(rune);
        } else {
            score = evaluate_odd_slot(rune);
        }

        return score;

    }

    public boolean keep_rune(Rune rune) {

        double score = find_score(rune);

        if (is_even(rune)) {
            return score >= EVEN_SLOT_MIN_SCORE;
        } else {
            return score >= ODD_SLOT_MIN_SCORE;
        }

    }

    public RuneEvaluator() {

        stat_value = new HashMap<>();

        stat_value.put(Rune.FLAT_HP, 20);
        stat_value.put(Rune.PERC_HP, 85);
        stat_value.put(Rune.FLAT_ATK, 20);
        stat_value.put(Rune.PERC_ATK, 80);
        stat_value.put(Rune.FLAT_DEF, 20);
        stat_value.put(Rune.PERC_DEF, 74);
        stat_value.put(Rune.SPD, 110);
        stat_value.put(Rune.CRI_RATE, 90);
        stat_value.put(Rune.CRIT_DMG, 80);
        stat_value.put(Rune.RES, 65);
        stat_value.put(Rune.ACC, 65);

        support_set = new HashSet<>();
        support_set.add(Rune.FLAT_HP);
        support_set.add(Rune.PERC_HP);
        support_set.add(Rune.FLAT_DEF);
        support_set.add(Rune.PERC_DEF);
        support_set.add(Rune.SPD);
        support_set.add(Rune.RES);
        support_set.add(Rune.ACC);

        attack_set = new HashSet<>();
        attack_set.add(Rune.FLAT_ATK);
        attack_set.add(Rune.PERC_ATK);
        attack_set.add(Rune.SPD);
        attack_set.add(Rune.CRI_RATE);
        attack_set.add(Rune.CRIT_DMG);

        // slot - attribute
        primary_stat_value = new int[7][11];

        // Slot 2
        primary_stat_value[2][Rune.PERC_HP] = 70;
        primary_stat_value[2][Rune.PERC_ATK] = 90;
        primary_stat_value[2][Rune.PERC_DEF] = 50;
        primary_stat_value[2][Rune.SPD] = 100;

        // Slot 4
        primary_stat_value[4][Rune.PERC_HP] = 100;
        primary_stat_value[4][Rune.PERC_ATK] = 70;
        primary_stat_value[4][Rune.PERC_DEF] = 80;
        primary_stat_value[4][Rune.CRI_RATE] = 70;
        primary_stat_value[4][Rune.CRIT_DMG] = 100;

        // Slot 6
        primary_stat_value[6][Rune.PERC_HP] = 100;
        primary_stat_value[6][Rune.PERC_ATK] = 100;
        primary_stat_value[6][Rune.PERC_DEF] = 70;
        primary_stat_value[6][Rune.RES] = 20;
        primary_stat_value[6][Rune.ACC] = 40;

        sub_max = new int[11];
        sub_max[Rune.FLAT_HP] = 3;
        sub_max[Rune.PERC_HP] = 8;
        sub_max[Rune.FLAT_ATK] = 3;
        sub_max[Rune.PERC_ATK] = 8;
        sub_max[Rune.FLAT_DEF] = 3;
        sub_max[Rune.PERC_DEF] = 8;
        sub_max[Rune.SPD] = 6;
        sub_max[Rune.CRI_RATE] = 6;
        sub_max[Rune.CRIT_DMG] = 7;
        sub_max[Rune.RES] = 8;
        sub_max[Rune.ACC] = 8;

    }

}
