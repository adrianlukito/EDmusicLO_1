package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ArtistActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvDiscoverArtistTitle;

    Drawable bruno, bts, bigbang, gfriend, apink;

    Button btnBackArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        try {
            btnBackArtist = (Button) findViewById(R.id.backArtist);
            btnBackArtist.setOnClickListener(this);

            Typeface varela = Typeface.createFromAsset(getAssets(), "VarelaRound-Regular.ttf");

            tvDiscoverArtistTitle = (TextView) findViewById(R.id.tvDiscoverArtistTitle);

            tvDiscoverArtistTitle.setTypeface(varela);

            ListView artistListView = (ListView) findViewById(R.id.artistListView);

            final ArtistListViewAdapter artistListViewAdapter = new ArtistListViewAdapter(getApplicationContext());


            String[] projectionAlbum =
                    {
                            MediaStore.Audio.Artists._ID,
                            MediaStore.Audio.Artists.ARTIST
                    };
            final Cursor artistCursor = getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                    projectionAlbum, null, null, MediaStore.Audio.Albums.ARTIST + " Asc");

            while (artistCursor.moveToNext()) {
//                String thisArt = artistCursor.getString(1);
//                Bitmap bm = BitmapFactory.decodeFile(thisArt);
//                Drawable drawable = new BitmapDrawable(getResources(), bm);
                artistListViewAdapter.addArtistList(artistCursor.getString(1), null);
            }

            artistListView.setAdapter(artistListViewAdapter);

            artistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), ArtistDetailActivity.class);
                    artistCursor.moveToPosition(i);
                    intent.putExtra("artist", artistCursor.getString(1));
                    intent.putExtra("artist_id", artistCursor.getString(0));
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Log.d("testa", e.toString());
        }
    }

    @Override
    public void onClick(View view) {
        if(view == btnBackArtist){
            finish();
        }
    }
}
