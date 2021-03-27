package com.callumdennien;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class SoupBowl {
    private static final String url = "https://www.reddit.com/r/wallpapers/hot/";

    public String getSoupTopping() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByClass("SQnoC3ObvgnGjWt90zD9Z");
        ArrayList<String> posts = new ArrayList<>();

        for (Element element : elements) {
            String post = "https://www.reddit.com/" + element.attr("href");
            posts.add(post);
        }

        String postURL = getSoupImage(posts);

        return postURL;
    }

    private String getSoupImage(ArrayList<String> posts) throws IOException {
        int random = (int) (Math.random() * (posts.size() - 1 + 1) + 1);

        Document doc = Jsoup.connect(posts.get(random)).get();
        Elements elements = doc.getElementsByClass("may-blank");

        for (Element element : elements) {
            if (element.attr("href").contains("https://i.redd.it/")) {
                return element.attr("href");
            }
        }

        return null;
    }
}
