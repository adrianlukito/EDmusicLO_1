package edu.bluejack16_2.edmusiclo;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Adrian Lukito Lo on 19/07/2017.
 */

public class CreateNewPlaylistAdapter extends BaseAdapter implements View.OnClickListener{

    ArrayList<String> songTitles;
    ArrayList<String> songArtists;
    ArrayList<String> songAlbums;
    ArrayList<Boolean> isChecked;

    Button playlistCheck;

    Drawable check, uncheck;
    int clickCount;

    RelativeLayout songPlaylistLayout;

    Context context;

    public TextView tvSongTitle, tvSongArtist, tvSongAlbum;

    public CreateNewPlaylistAdapter(Context context) {

        songTitles = new ArrayList<String>();
        songArtists = new ArrayList<String>();
        songAlbums = new ArrayList<String>();
        isChecked = new ArrayList<>();
        this.context = context;
        clickCount = 0;
    }

    public void addSongList(String songTitle, String songArtist, String songAlbum){
        songTitles.add(songTitle);
        songArtists.add(songArtist);
        songAlbums.add(songAlbum);
        isChecked.add(false);
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
            view = inflater.inflate(R.layout.new_playlist_song_list,viewGroup,false);
        }

        check = view.getResources().getDrawable(R.drawable.ic_success);
        uncheck = view.getResources().getDrawable(R.drawable.ic_circle);

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

        final int index = i;
        playlistCheck = (Button) view.findViewById(R.id.playlistCheck);
        songPlaylistLayout = (RelativeLayout) view.findViewById(R.id.songPlaylistLayout);

        songPlaylistLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChecked.set(index,!isChecked.get(index));
                notifyDataSetChanged();
            }
        });

        playlistCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChecked.set(index,!isChecked.get(index));
                //Toast.makeText(context, "checked : " + isChecked + ": count : " + clickCount++, Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        playlistCheck.setBackground(isChecked.get(index) ? check : uncheck);

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

    }
}
