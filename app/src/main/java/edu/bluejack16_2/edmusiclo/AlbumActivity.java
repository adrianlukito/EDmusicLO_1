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

        btnBackAlbum = (Button) findViewById(R.id.backAlbum);
        btnBackAlbum.setOnClickListener(this);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvDiscoverAlbumTitle = (TextView) findViewById(R.id.tvDiscoverAlbumTitle);

        tvDiscoverAlbumTitle.setTypeface(varela);

        GridView albumListView = (GridView) findViewById(R.id.albumListView);

        final AlbumListViewAdapter albumListViewAdapter = new AlbumListViewAdapter(getApplicationContext());

        bruno = getResources().getDrawable(R.drawable.bruno_mars);
        bts = getResources().getDrawable(R.drawable.wings);
        bigbang = getResources().getDrawable(R.drawable.bigbang);
        gfriend = getResources().getDrawable(R.drawable.awakening);
        apink = getResources().getDrawable(R.drawable.apink);
        got7 = getResources().getDrawable(R.drawable.just_right);
        gfriend2 = getResources().getDrawable(R.drawable.lol);
        suju = getResources().getDrawable(R.drawable.mrsimple);
        suju2 = getResources().getDrawable(R.drawable.bonamana);
        ikon = getResources().getDrawable(R.drawable.ikon);
        ikon2 = getResources().getDrawable(R.drawable.newkids);
        wjsn = getResources().getDrawable(R.drawable.secret);
        snsd = getResources().getDrawable(R.drawable.the_boys);
        snsd2 = getResources().getDrawable(R.drawable.oh);

        String[] projectionAlbum =
                {
                        MediaStore.Audio.Albums._ID,
                        MediaStore.Audio.Albums.ALBUM_ART,
                        MediaStore.Audio.Albums.ALBUM,
                        MediaStore.Audio.Albums.ARTIST
                };

        final Cursor albumCursor = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                projectionAlbum, null, null, MediaStore.Audio.Albums.ALBUM +" Asc");

        while(albumCursor.moveToNext()){
            String thisArt = albumCursor.getString(1);
            Bitmap bm= BitmapFactory.decodeFile(thisArt);
            Drawable drawable = new BitmapDrawable(getResources(), bm);
            albumListViewAdapter.addAlbumList(albumCursor.getString(2),albumCursor.getString(3), drawable);
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
    }

    @Override
    public void onClick(View view) {
        if(view == btnBackAlbum){
            finish();
        }
    }
}
