package edu.bluejack16_2.edmusiclo;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Adrian Lukito Lo on 17/07/2017.
 */

public class ParseURL extends AsyncTask<String,Void,String>{
//    public String word;
    String lyricLink = "";
    @Override
    protected String doInBackground(String... strings) {

        try {
            Document doc = Jsoup.connect(strings[0]).get();
            Element element = doc.select("td").get(1);
            Element link = element.select("a").first();
            lyricLink = link.attr("href");
            Log.d("test",element.toString());
            Log.d("test1",link.toString());
            Log.d("test2",lyricLink);
//            Elements element = doc.select("td");
//            word = doc.text();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Document doc2 = Jsoup.connect(lyricLink).get();
            Element element2 = doc2.select("div.col-xs-12").get(1);
            Element element3 = element2.select("div").get(7);
            String element4 = element3.html();
            Log.d("el4",element4);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
