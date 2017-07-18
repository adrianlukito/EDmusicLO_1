package edu.bluejack16_2.edmusiclo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;

public class TestActivity extends AppCompatActivity {

    TextView testLyric;

    String songTitleAndArtist = "";
    String songTitleAndArtistEncoder = "";
    String songTitle,songArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testLyric = (TextView) findViewById(R.id.testLyric);

        ParseURL url = new ParseURL();

        songTitle = MusicCursor.getInstance().musiccursor.getString(6);
        songArtist = MusicCursor.getInstance().musiccursor.getString(5);


        String lyric="";

        if(songArtist.contains("unknown")){
            songTitleAndArtist = songTitle;
        }else{
            songTitleAndArtist = songTitle+" "+songArtist;
        }

        try {
            songTitleAndArtistEncoder = URLEncoder.encode(songTitleAndArtist,"UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        getWebsite();

        try {
            lyric = url.execute("http://search.azlyrics.com/search.php?q="+songTitleAndArtistEncoder).get();
            //lyric = url.execute("http://search.azlyrics.com/search.php?q=All+of+Me+John+Legend").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//
//        Log.d("asd",lyric);
//        try {
//            testLyric.setText(url.execute("http://search.azlyrics.com/search.php?q="+songTitleAndArtistEncoder).get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }

//    void getWebsite(){
//        StringBuilder builder = new StringBuilder();
//        try {
//            Document doc = Jsoup.connect("http://search.azlyrics.com/search.php?q="+songTitleAndArtistEncoder).get();
//            Elements links = doc.select("td");
//
//            for (Element link : links) {
//                builder.append("Link : ").append(link.attr("href"));
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        testLyric.setText(builder.toString());
//    }
}
