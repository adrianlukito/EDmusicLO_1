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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;

public class AlbumActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvDiscoverAlbumTitle;

    Button btnBackAlbum;

    Drawable bruno, bts, bigbang, gfriend, apink, got7, suju, suju2,gfriend2, ikon, ikon2, wjsn, snsd, snsd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        try {
            btnBackAlbum = (Button) findViewById(R.id.backAlbum);
            btnBackAlbum.setOnClickListener(this);
            Typeface varela = Typeface.createFromAsset(getAssets(), "VarelaRound-Regular.ttf");

            tvDiscoverAlbumTitle = (TextView) findViewById(R.id.tvDiscoverAlbumTitle);

            tvDiscoverAlbumTitle.setTypeface(varela);

            GridView albumListView = (GridView) findViewById(R.id.albumListView);

            final AlbumListViewAdapter albumListViewAdapter = new AlbumListViewAdapter(getApplicationContext());

//
//            bruno = getResources().getDrawable(R.drawable.bruno_mars);
            String[] projectionAlbum =
                    {
                            MediaStore.Audio.Albums._ID,
                            MediaStore.Audio.Albums.ALBUM_ART,
                            MediaStore.Audio.Albums.ALBUM,
                            MediaStore.Audio.Albums.ARTIST
                    };

            final Cursor albumCursor = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    projectionAlbum, null, null, MediaStore.Audio.Albums.ALBUM + " Asc");

            while (albumCursor.moveToNext()) {
    //            String thisArt = albumCursor.getString(1);
    //            Bitmap bm= BitmapFactory.decodeFile(thisArt);
    //            Drawable drawable = new BitmapDrawable(getResources(), bm);
                albumListViewAdapter.addAlbumList(albumCursor.getString(2), albumCursor.getString(3), null);
            }


            albumListView.setAdapter(albumListViewAdapter);

            albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), AlbumDetailActivity.class);

                    albumCursor.moveToPosition(i);
                    intent.putExtra("album", albumCursor.getString(2));
                    intent.putExtra("album_id", albumCursor.getString(0));

                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Log.d("testa", e.toString());
        }
    }

    @Override
    public void onClick(View view) {
        if(view == btnBackAlbum){
            finish();
        }
    }
}
