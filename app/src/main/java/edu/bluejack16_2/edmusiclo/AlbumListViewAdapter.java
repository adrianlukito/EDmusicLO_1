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
 * Created by Adrian Lukito Lo on 10/07/2017.
 */

public class AlbumListViewAdapter extends BaseAdapter{

    ArrayList<Drawable> albumPictures;
    ArrayList<String> albumNames;
    ArrayList<String> albumArtists;

    TextView tvAlbumName, tvAlbumArtist;

    ImageView imgAlbumPicture;

    Context context;

    public AlbumListViewAdapter(Context context) {
        albumNames = new ArrayList<String>();
        albumArtists = new ArrayList<String>();
        albumPictures = new ArrayList<Drawable>();
        this.context = context;
    }

    public void addAlbumList(String albumName, String albumArtist , Drawable albumPicture){
        albumNames.add(albumName);
        albumArtists.add(albumArtist);
        albumPictures.add(albumPicture);
    }

    @Override
    public int getCount() {
        return albumNames.size();
    }

    @Override
    public Object getItem(int i) {
        return albumNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.album_row,viewGroup,false);

        Typeface varela = Typeface.createFromAsset(context.getAssets(),"VarelaRound-Regular.ttf");

        tvAlbumName = (TextView) view.findViewById(R.id.tvAlbumName);
        tvAlbumArtist = (TextView) view.findViewById(R.id.tvAlbumArtist);

        imgAlbumPicture = (ImageView) view.findViewById(R.id.albumPicture);

        tvAlbumName.setText(albumNames.get(i));
        tvAlbumArtist.setText(albumArtists.get(i));
        imgAlbumPicture.setImageDrawable(albumPictures.get(i));

        tvAlbumName.setTypeface(varela);
        tvAlbumArtist.setTypeface(varela);

        return view;
    }
}
