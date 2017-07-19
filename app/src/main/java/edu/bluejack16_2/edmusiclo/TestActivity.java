package edu.bluejack16_2.edmusiclo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
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

//        OkHttpClient client = new OkHttpClient();
//
//        HttpConnection.Request request = new HttpConnection.Request.Builder()
//                .url("https://code.aashari.id/api/musics/lyric/keyword?keyword=peterpan%20semua%20tentang%20kita")
//                .get()
//                .addHeader("authorization", "Bearer 03a316f635757be63d4e30d4d7c49a154063fa4638fe81c46563eb46420ca276")
//                .build();
//
//        HttpConnection.Response response = client.newCall(request).execute();

    }
}
