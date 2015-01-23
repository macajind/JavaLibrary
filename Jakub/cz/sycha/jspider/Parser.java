package cz.sycha.jspider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class is used for parsing various requests...
 */

public class Parser {

    private Loader loader = new Loader();

    /**
     * Get the movie info from specified URL and print it out in a console...
     * @param movie Name of the movie...
     */
    public void getMovie(String movie) {
        String html = this.loader.loadFromURL(movie);
        Document doc = Jsoup.parse(html);

        Element title = doc.select("div.zakladni_info > div.left > h1 > a").first();
        Element origTitle = doc.select("div.text > div.infotext > h2.title_next").first();
        Elements meta = doc.select("div.text > div.infotext > div.row");
        Element plot = doc.select("#zbytek").first();

        System.out.println("Český název: " + title.ownText());
        System.out.println("Originální název: " + origTitle.ownText());

        //Blank line
        System.out.println();

        //Iterate over all elements with basic movie description and print them out in Key: Value style...
        for(Element elem : meta) {
            System.out.println(elem.getElementsByClass("left_text").text() + " " + elem.getElementsByClass("right_text").text());
        }

        System.out.println();

        System.out.println("Popis filmu:");
        System.out.println(plot.text().replace("< Zobrazit méně", "").replace("oficiální text distributora,", "").replace("zobrazit všechny obsahy", "").replace("(2)", ""));

        System.out.println();
    }
}
