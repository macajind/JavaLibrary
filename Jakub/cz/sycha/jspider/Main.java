package cz.sycha.jspider;

import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        // Set the right locale (just to be sure...)
        Locale.setDefault(new Locale("cs_CZ"));

        // RUn the init function...
        new Main().init();
    }

    public void init() {
        new Parser().getMovie("http://www.fdb.cz/film/hon-jagten/91831");
    }
}
