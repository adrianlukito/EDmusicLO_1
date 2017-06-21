package edu.bluejack16_2.edmusiclo;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Adrian Lukito Lo on 21/06/2017.
 */

public class SongListViewAdapter extends BaseAdapter{

    ArrayList<String> songTitles;
    ArrayList<String> songArtists;
    ArrayList<String> songAlbums;

    Context context;

    TextView tvSongTitle, tvSongArtist, tvSongAlbum;

    public SongListViewAdapter(Context context) {
        songTitles = new ArrayList<String>();
        songArtists = new ArrayList<String>();
        songAlbums = new ArrayList<String>();
        this.context = context;
    }

    public void addSongList(String songTitle, String songArtist, String songAlbum){
        songTitles.add(songTitle);
        songArtists.add(songArtist);
        songAlbums.add(songAlbum);
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.songrow,viewGroup,false);
        }

        Typeface varela = Typeface.createFromAsset(context.getAssets(),"VarelaRound-Regular.ttf");

        tvSongTitle = (TextView) view.findViewById(R.id.tvSongTitle);
        tvSongArtist = (TextView) view.findViewById(R.id.tvSongArtist);
        tvSongAlbum = (TextView) view.findViewById(R.id.tvSongAlbum);

        tvSongTitle.setText(songTitles.get(i));
        tvSongArtist.setText(songArtists.get(i));
        tvSongAlbum.setText(songAlbums.get(i));

        tvSongTitle.setTypeface(varela);
        tvSongArtist.setTypeface(varela);
        tvSongAlbum.setTypeface(varela);

        return view;
    }
}
