package cz.sycha.jspider;

import java.util.Locale;

/**
 * Main class of teh jSpider app, we basically just set the right locale and start the app there
 *
 * @author Jacob Sycha <jakubsycha@gmail.com>
 * @version 1.0
 */
public class Main {

    /**
     * Main method of the app, as said, we just set the right locale there and create the UserInterface class
     *
     * @param args This method takes no arguments!
     */
    public static void main(String[] args) {
        Locale.setDefault(new Locale("cs_CZ")); // Set the right locale (just to be sure...)
        new UserInterface(); // Start the app...
    }
}
