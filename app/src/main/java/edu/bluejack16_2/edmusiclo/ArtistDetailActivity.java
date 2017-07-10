package edu.bluejack16_2.edmusiclo;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ArtistDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvDiscoverArtistTitle;

    Button btnBackArtistDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artist);

        btnBackArtistDetail = (Button) findViewById(R.id.backArtistDetail);
        btnBackArtistDetail.setOnClickListener(this);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvDiscoverArtistTitle = (TextView) findViewById(R.id.tvDiscoverArtistTitle);

        tvDiscoverArtistTitle.setTypeface(varela);

        ListView artistSongListView = (ListView) findViewById(R.id.artistSongListView);

        final ArtistSongListViewAdapter artistSongListViewAdapter = new ArtistSongListViewAdapter(getApplicationContext());

        artistSongListViewAdapter.addArtistSongList("24K Magic (R3hab Remix)", "24K Magic (R3hab Remix)");
        artistSongListViewAdapter.addArtistSongList("Versace On The Floor","Versace On The Floor (Bruno Mars vs. David)");
        artistSongListViewAdapter.addArtistSongList("That's What I Like","That's What I Like (Alan Walker Remix)");
        artistSongListViewAdapter.addArtistSongList("Uptown Funk","Uptown Funk");
        artistSongListViewAdapter.addArtistSongList("Treasure","Summer Songs");
        artistSongListViewAdapter.addArtistSongList("It Will Rain","Acoustic Rain");
        artistSongListViewAdapter.addArtistSongList("When I Was Your Man","Now That's What I Call 30 Years");
        artistSongListViewAdapter.addArtistSongList("Lock Out Of Heaven","Wine & Chocolates");
        artistSongListViewAdapter.addArtistSongList("Just The Way You Are","Mum");
        artistSongListViewAdapter.addArtistSongList("Nothin' On You","R&B Anthems");
        artistSongListViewAdapter.addArtistSongList("The Lazy Song","The Lazy Song");
        artistSongListViewAdapter.addArtistSongList("Grenade","The Grenade Sessions");
        artistSongListViewAdapter.addArtistSongList("Talking To The Moon","Doo-Wops & Hooligans");
        artistSongListViewAdapter.addArtistSongList("Count On Me","Doo-Wops & Hooligans");

        artistSongListView.setAdapter(artistSongListViewAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view == btnBackArtistDetail){
            finish();
        }
    }
}
