package navigate;

public class FaimonNav extends Navigate {

    public FaimonNav() {
        super("faimon_volcano", "1_faimon", 2);
    }

    public static void main(String[] args) {

        FaimonNav faimonNav = new FaimonNav();
        faimonNav.navigate();

    }


}
