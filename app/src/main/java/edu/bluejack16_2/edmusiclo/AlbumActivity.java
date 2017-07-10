package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

        albumListViewAdapter.addAlbumList("B'Sides","Bruno Mars",bruno);
        albumListViewAdapter.addAlbumList("Wings","BTS",bts);
        albumListViewAdapter.addAlbumList("MADE","Big Bang",bigbang);
        albumListViewAdapter.addAlbumList("THE AWAKENING","GFriend",gfriend);
        albumListViewAdapter.addAlbumList("Pink Doll","Apink",apink);
        albumListViewAdapter.addAlbumList("Just Right","GOT7",got7);
        albumListViewAdapter.addAlbumList("Secret","WJSN",wjsn);
        albumListViewAdapter.addAlbumList("LOL","GFriend",gfriend2);
        albumListViewAdapter.addAlbumList("Mr.Simple","Super Junior",suju);
        albumListViewAdapter.addAlbumList("Bonamana","Super Junior",suju2);
        albumListViewAdapter.addAlbumList("IKON","iKON",ikon);
        albumListViewAdapter.addAlbumList("NEW KIDS BEGIN","iKON",ikon2);
        albumListViewAdapter.addAlbumList("The Boys","SNSD",snsd);
        albumListViewAdapter.addAlbumList("Oh!","SNSD",snsd2);


        albumListView.setAdapter(albumListViewAdapter);

        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), AlbumDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == btnBackAlbum){
            finish();;
        }
    }
}
