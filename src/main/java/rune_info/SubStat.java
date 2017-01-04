package rune_info;

public class SubStat {

    public SubStat(int stat, int value) {
        this.stat = stat;
        this.value = value;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private int stat, value;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(Translator.getInstance().translate_stat(stat));
        sb.append(" : ");
        sb.append(value);

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        SubStat other = (SubStat) obj;
        return this.stat == other.stat && this.value == other.value;
    }
}
