package rune_info;

import java.util.ArrayList;

public class Rune {

    public final static int FLAT_HP  = 0;
    public final static int PERC_HP = 1;
    public final static int FLAT_ATK = 2;
    public final static int PERC_ATK = 3;
    public final static int FLAT_DEF = 4;
    public final static int PERC_DEF = 5;
    public final static int SPD = 6;
    public final static int CRI_RATE = 7;
    public final static int CRIT_DMG = 8;
    public final static int RES = 9;
    public final static int ACC = 10;

    private int slot;
    private int grade;
    private int rarity;
    private int primary_stat;
    private SubStat innate_stat;
    private ArrayList<SubStat> sub_stats;

    public Rune(int slot, int grade, int rarity, int primary_stat, SubStat innate_stat, ArrayList<SubStat> sub_stats) {
        this.slot = slot;
        this.grade = grade;
        this.rarity = rarity;
        this.primary_stat = primary_stat;
        this.innate_stat = innate_stat;
        this.sub_stats = sub_stats;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        // Slot
        sb.append("Slot: ");
        sb.append(slot);
        sb.append('\n');

        // Grade
        sb.append("Grade: ");
        sb.append(grade);
        sb.append('\n');

        // Rarity
        sb.append("Rarity: ");
        sb.append(Translator.getInstance().translate_rarity(rarity));
        sb.append('\n');

        // Primary
        sb.append("Primary Stat: ");
        sb.append(Translator.getInstance().translate_stat(primary_stat));
        sb.append('\n');

        // Innate
        sb.append("Innate Stat: ");
        sb.append(innate_stat);
        sb.append('\n');

        // Sub Stats
        for (SubStat subStat : sub_stats) {
            sb.append(subStat);
            sb.append('\n');
        }

        return sb.toString();

    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public int getPrimary_stat() {
        return primary_stat;
    }

    public void setPrimary_stat(int primary_stat) {
        this.primary_stat = primary_stat;
    }

    public SubStat getInnate_stat() {
        return innate_stat;
    }

    public void setInnate_stat(SubStat innate_stat) {
        this.innate_stat = innate_stat;
    }

    public ArrayList<SubStat> getSub_stats() {
        return sub_stats;
    }

    public void setSub_stats(ArrayList<SubStat> sub_stats) {
        this.sub_stats = sub_stats;
    }

    @Override
    public boolean equals(Object obj) {

        Rune other = (Rune) obj;

        boolean ret = true;

        ret &= (this.slot == other.slot);
        ret &= (this.grade == other.grade);
        ret &= (this.primary_stat == other.primary_stat);
        ret &= (this.innate_stat == null && other.innate_stat == null) ||
                ((this.innate_stat != null && other.innate_stat != null) && this.innate_stat.equals(other.innate_stat));
        ret &= (this.sub_stats.size() == other.sub_stats.size());

        if (!ret) return ret;

        for (int i = 0; i < this.sub_stats.size(); i++) {
            ret &= this.sub_stats.get(i).equals(other.getSub_stats().get(i));
        }

        return ret;

    }
}
