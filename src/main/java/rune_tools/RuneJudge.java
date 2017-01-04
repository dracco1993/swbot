package rune_tools;

import rune_info.Rune;

public class RuneJudge {

    private RuneEvaluator runeEvaluator;
    private RuneIdentifier runeIdentifier;

    public boolean judge_rune() {

        Rune rune = runeIdentifier.identify_rune();
        System.out.println("\n" + rune);

        if (rune == null) return false;
        boolean ans = runeEvaluator.keep_rune(rune);

        System.out.println("Score: " + runeEvaluator.find_score(rune));
        System.out.println("Verdict: " + (ans ? "Keep" : "Sell"));

        return ans;

    }

    public RuneJudge() {
        runeEvaluator = new RuneEvaluator();
        runeIdentifier = new RuneIdentifier();
    }

    public static void main(String[] args) {

        RuneJudge runeJudge = new RuneJudge();
        runeJudge.judge_rune();

    }

    public static void sleep(long ms) {

        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }

}
