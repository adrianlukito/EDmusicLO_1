package edu.bluejack16_2.edmusiclo;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adrian Lukito Lo on 28/07/2017.
 */

public class PlaylistListViewAdapter extends BaseAdapter{

    ArrayList<String> playlistNames;
    ArrayList<String> totalSongs;

    Context context;

    TextView tvPlaylistName, tvTotalSong, tvMyPlaylist;

    public PlaylistListViewAdapter(Context context) {
        playlistNames = new ArrayList<String>();
        totalSongs = new ArrayList<String>();
        this.context = context;
    }

    public void addPlaylist(String playlistName, String totalSong){
        playlistNames.add(playlistName);
        totalSongs.add(totalSong);
    }

    @Override
    public int getCount() {
        return playlistNames.size();
    }

    @Override
    public Object getItem(int i) {
        return playlistNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.playlist_row,viewGroup,false);

        Typeface varela = Typeface.createFromAsset(context.getAssets(),"VarelaRound-Regular.ttf");

        tvPlaylistName = (TextView) view.findViewById(R.id.tvPlaylistName);
        tvPlaylistName.setText(playlistNames.get(i));
        tvPlaylistName.setTypeface(varela);

        tvTotalSong = (TextView) view.findViewById(R.id.tvTotalSong);
        tvTotalSong.setText(totalSongs.get(i));
        tvTotalSong.setTypeface(varela);

        tvMyPlaylist = (TextView) view.findViewById(R.id.tvMyPlaylist);
        tvMyPlaylist.setText("My Playlists");
        tvMyPlaylist.setTypeface(varela);

        return view;
    }
}
