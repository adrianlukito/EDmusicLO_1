package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import static edu.bluejack16_2.edmusiclo.R.id.tvDiscoverAlbumTitle;

public class GenreActivity extends AppCompatActivity implements View.OnClickListener{

    Button backGenre, btnGenres;

    TextView tvDiscoverGenreTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        backGenre = (Button) findViewById(R.id.backGenre);
        backGenre.setOnClickListener(this);

        btnGenres = (Button) findViewById(R.id.btnGenres);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvDiscoverGenreTitle = (TextView) findViewById(R.id.tvDiscoverGenreTitle);

        tvDiscoverGenreTitle.setTypeface(varela);

        GridView genreListView = (GridView) findViewById(R.id.genreListView);

        final GenreListViewAdapter genreListViewAdapter = new GenreListViewAdapter(getApplicationContext());

        genreListViewAdapter.addGenre("Kpop");
        genreListViewAdapter.addGenre("Rock");
        genreListViewAdapter.addGenre("Pop");
        genreListViewAdapter.addGenre("Jazz");
        genreListViewAdapter.addGenre("EDM");
        genreListViewAdapter.addGenre("Hip-Hop");
        genreListViewAdapter.addGenre("Rnb");
        genreListViewAdapter.addGenre("Acoustic");
        genreListViewAdapter.addGenre("Blues");
        genreListViewAdapter.addGenre("Soul");
        genreListViewAdapter.addGenre("Punk");
        genreListViewAdapter.addGenre("Funk");
        genreListViewAdapter.addGenre("Country");
        genreListViewAdapter.addGenre("Intrumental");
        genreListViewAdapter.addGenre("Classic");
        genreListViewAdapter.addGenre("Indie");

        genreListView.setAdapter(genreListViewAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view == backGenre){
            finish();
        }
    }
}
