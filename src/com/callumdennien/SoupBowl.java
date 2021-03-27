package com.callumdennien;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class SoupBowl {
    private static final String url = "https://www.reddit.com/r/wallpapers/top/";

    public ArrayList<String> getSoupToppings() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByClass("SQnoC3ObvgnGjWt90zD9Z");
        ArrayList<String> posts = new ArrayList<>();

        for (Element element : elements) {
            String post = "https://www.reddit.com/" + element.attr("href");
            posts.add(post);
        }

        return posts;
    }
}
