package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Vector;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;

import static edu.bluejack16_2.edmusiclo.R.id.tvDiscoverAlbumTitle;

public class GenreActivity extends AppCompatActivity implements View.OnClickListener{

    Button backGenre, btnGenres;

    TextView tvDiscoverGenreTitle;

    String sameText ="";

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


        try {
//            if (MusicCursor.getInstance().genreCursor == null) {
//                MusicCursor.getInstance().setGenreCursor(this, "");
//                Log.d("text", "error1");
//            }
//
            Vector<String> test = new Vector<String>();
//
//            for(int i = 0;i < MusicCursor.getInstance().genreString.size(); i++){
//                Log.d("text", MusicCursor.getInstance().genreString.get(i));
//                int validasi = 0;
//                for(int j = 0 ; j < test.size(); j++){
//                    if (MusicCursor.getInstance().genreString.get(i).equals(test.get(j))){
//                        validasi = 1;
//                        break;
//                    }
//                }
//                if(validasi == 0){
//                    genreListViewAdapter.addGenre(MusicCursor.getInstance().genreString.get(i));
//                    test.add(MusicCursor.getInstance().genreString.get(i));
//                }
//            }
            Cursor musiCursor = MusicCursor.getInstance().musiccursor;
            musiCursor.moveToFirst();
            do {
//                Uri uri = MediaStore.Audio.Genres.getContentUriForAudioId("external", Integer.parseInt(musiCursor.getString(0)));
//
//                condi = MediaStore.Audio.Genres.NAME + " = " + condition;
//                genreCursor = activity.getContentResolver().query(uri, new String[]{MediaStore.Audio.Genres.NAME,
//                        MediaStore.Audio.Genres._ID}, condi, null, MediaStore.Audio.Genres.NAME + " ASC");
//
//                while(genreCursor.moveToNext()) {
//                    genreString.add(genreCursor.getString(genreCursor.getPosition()));
//                }
            }while (true);
        }catch (Exception e){
            Log.d("text", toString());
        }

        genreListView.setAdapter(genreListViewAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view == backGenre){
            finish();
        }
    }
}
