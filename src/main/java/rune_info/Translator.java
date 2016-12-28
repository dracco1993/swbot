package rune_info;

import java.util.HashMap;

public class Translator {

    private static Translator instance = null;
    private HashMap<Integer, String> smap;
    private HashMap<Integer, String> rmap;

    private Translator() {

        smap = new HashMap<>();
        smap.put(0, "FLAT_HP");
        smap.put(1, "HP_%");
        smap.put(2, "FLAT_ATK");
        smap.put(3, "ATK_%");
        smap.put(4, "FLAT_DEF");
        smap.put(5, "DEF_%");
        smap.put(6, "SPD");
        smap.put(7, "CRI_RATE");
        smap.put(8, "CRIT_DMG");
        smap.put(9, "RES" );
        smap.put(10, "ACC");

        rmap = new HashMap<>();
        rmap.put(2, "Rare");
        rmap.put(3, "Epic");
        rmap.put(4, "Legendary");

    }

    public static Translator getInstance() {

        if (instance == null) {
            instance = new Translator();
        }

        return instance;

    }

    public String translate_stat(int val) {
        return smap.get(val);
    }
    public String translate_rarity(int val) {
        return rmap.get(val);
    }

}
