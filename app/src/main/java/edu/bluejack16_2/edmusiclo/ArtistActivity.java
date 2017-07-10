package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        btnBackArtist = (Button) findViewById(R.id.backArtist);
        btnBackArtist.setOnClickListener(this);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvDiscoverArtistTitle = (TextView) findViewById(R.id.tvDiscoverArtistTitle);

        tvDiscoverArtistTitle.setTypeface(varela);

        ListView artistListView = (ListView) findViewById(R.id.artistListView);

        final ArtistListViewAdapter artistListViewAdapter = new ArtistListViewAdapter(getApplicationContext());

        bruno = getResources().getDrawable(R.drawable.bruno_mars);
        bts = getResources().getDrawable(R.drawable.bts);
        bigbang = getResources().getDrawable(R.drawable.bigbang);
        gfriend = getResources().getDrawable(R.drawable.gfriend);
        apink = getResources().getDrawable(R.drawable.apink);

        artistListViewAdapter.addArtistList("Bruno Mars",bruno);
        artistListViewAdapter.addArtistList("BTS",bts);
        artistListViewAdapter.addArtistList("Big Bang",bigbang);
        artistListViewAdapter.addArtistList("G-friend",gfriend);
        artistListViewAdapter.addArtistList("A-pink",apink);

        artistListView.setAdapter(artistListViewAdapter);

        artistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ArtistDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == btnBackArtist){
            finish();
        }
    }
}
