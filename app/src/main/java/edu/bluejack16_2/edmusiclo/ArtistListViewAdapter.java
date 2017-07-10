package edu.bluejack16_2.edmusiclo;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adrian Lukito Lo on 04/07/2017.
 */

public class ArtistListViewAdapter extends BaseAdapter {

    ArrayList<String> artistNames;
    ArrayList<Drawable> artistPictures;

    TextView tvArtistName;

    ImageView artistPicture;

    Context context;

    public ArtistListViewAdapter(Context context) {
        artistNames = new ArrayList<String>();
        artistPictures = new ArrayList<Drawable>();
        this.context = context;
    }

    public void addArtistList(String artistName, Drawable artistPicture){
        artistNames.add(artistName);
        artistPictures.add(artistPicture);
    }

    @Override
    public int getCount() {
        return artistNames.size();
    }

    @Override
    public Object getItem(int i) {
        return artistNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.artist_row,viewGroup,false);

        Typeface varela = Typeface.createFromAsset(context.getAssets(),"VarelaRound-Regular.ttf");

        tvArtistName = (TextView) view.findViewById(R.id.tvArtistName);
        artistPicture = (ImageView) view.findViewById(R.id.artistPicture);

        tvArtistName.setText(artistNames.get(i));
        artistPicture.setImageDrawable(artistPictures.get(i));

        tvArtistName.setTypeface(varela);

        return view;
    }
}
