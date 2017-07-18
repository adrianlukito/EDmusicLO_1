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

public class FloatingLyricsActivity extends AppCompatActivity implements View.OnClickListener{

    public Context context;

    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_lyrics);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
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
