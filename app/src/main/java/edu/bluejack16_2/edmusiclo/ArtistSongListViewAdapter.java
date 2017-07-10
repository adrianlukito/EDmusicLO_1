package edu.bluejack16_2.edmusiclo;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adrian Lukito Lo on 03/07/2017.
 */

public class ArtistSongListViewAdapter extends BaseAdapter{

    ArrayList<String> songTitles;
    ArrayList<String> songAlbums;

    Drawable divider;

    Context context;

    public TextView tvSongTitle, tvSongAlbum;

    public ArtistSongListViewAdapter(Context context) {
        songTitles = new ArrayList<String>();
        songAlbums = new ArrayList<String>();
        this.context = context;
    }

    public void addArtistSongList(String songTitle, String songAlbum){
        songTitles.add(songTitle);
        songAlbums.add(songAlbum);
    }

    public ArtistSongListViewAdapter getThisItem(){
        return this;
    }

    @Override
    public int getCount() {
        return songTitles.size();
    }


    @Override
    public Object getItem(int i) {
        return songTitles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);

        Typeface varela = Typeface.createFromAsset(context.getAssets(),"VarelaRound-Regular.ttf");
        if(i == 0){
            if(view == null){
                view = inflater.inflate(R.layout.artist_picture,viewGroup,false);
            }
        }else{
            view = inflater.inflate(R.layout.song_artist_row, viewGroup, false);

            tvSongTitle = (TextView) view.findViewById(R.id.tvSongTitle);
            tvSongAlbum = (TextView) view.findViewById(R.id.tvSongAlbum);

            tvSongTitle.setText(songTitles.get(i-1));
            tvSongAlbum.setText(songAlbums.get(i-1));

            tvSongTitle.setTypeface(varela);
            tvSongAlbum.setTypeface(varela);
        }

        return view;
    }
}
