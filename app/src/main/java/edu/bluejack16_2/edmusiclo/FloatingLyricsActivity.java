package edu.bluejack16_2.edmusiclo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;

public class FloatingLyricsActivity extends AppCompatActivity implements View.OnClickListener{

    public Context context;

    Button btnStart;

    TextView tvMusicLyric;
    LyricsListViewAdapter lyricListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_lyrics);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);

        tvMusicLyric = (TextView) findViewById(R.id.tvMusicLyric);



        initFloating();
    }

    @Override
    public void onClick(View view) {
        if(view == btnStart){
            initFloating();
        }
    }

    private void initFloating(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)){
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package: "+getPackageName()));
            startActivityForResult(intent,101);
        }else{
            startFloating();
        }
    }

    private void startFloating() {
        Intent intent = new Intent(this, FloatingLyrics.class);
        finish();
        startService(intent);
    }
}
