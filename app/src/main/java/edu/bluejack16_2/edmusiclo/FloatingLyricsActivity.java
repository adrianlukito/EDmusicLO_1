package edu.bluejack16_2.edmusiclo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FloatingLyricsActivity extends AppCompatActivity {

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        setUpWindow();
        setContentView(R.layout.activity_floating_lyrics);
    }

    void setUpWindow(){

    }
}
