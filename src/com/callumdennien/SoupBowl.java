package com.callumdennien;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class SoupBowl {
    private static final String url = "https://www.reddit.com/r/wallpapers/top/";

    public String getSoupTopping() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByClass("SQnoC3ObvgnGjWt90zD9Z");
        ArrayList<String> posts = new ArrayList<>();

        for (Element element : elements) {
            String post = "https://www.reddit.com/" + element.attr("href");
            posts.add(post);
        }

        return getSoupImage(posts);
    }

    public void spillSoup(String postURL, String path) throws IOException {
        URL url = new URL(postURL);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(path);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    private String getSoupImage(ArrayList<String> posts) throws IOException {
        Random random = new Random();
        int postIndex = random.nextInt(posts.size());

        Document doc = Jsoup.connect(posts.get(postIndex)).get();
        Elements elements = doc.getElementsByClass("may-blank");

        for (Element element : elements) {
            if (element.attr("href").contains("https://i.redd.it/")) {
                return element.attr("href");
            }
        }

        return null;
    }
}
