package edu.bluejack16_2.edmusiclo;


import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongFragment extends Fragment implements View.OnClickListener{

    TextView tvBottomSongTitle, tvBottomSongArtist;
    Button btnPlayPause;

    Drawable imgPlay, imgPause;

    int flag = 0;

    public SongFragment() {
        // Required empty public constructor
    }
    MediaPlayer mp =  new MediaPlayer();

    Cursor musiccursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_song,container,false);

        btnPlayPause = (Button) view.findViewById(R.id.btnPlayPause);
        btnPlayPause.setOnClickListener(this);

        imgPlay = getResources().getDrawable(R.drawable.ic_play);
        imgPause = getResources().getDrawable(R.drawable.ic_pause);

        tvBottomSongTitle = (TextView) view.findViewById(R.id.tvBottomSongTitle);
        tvBottomSongArtist = (TextView) view.findViewById(R.id.tvBottomSongArtist);

        Typeface varela = Typeface.createFromAsset(getActivity().getAssets(),"VarelaRound-Regular.ttf");

        tvBottomSongTitle.setTypeface(varela);
        tvBottomSongArtist.setTypeface(varela);

        final ListView songListView = (ListView) view.findViewById(R.id.songListView);
        final SongListViewAdapter songListViewAdapter = new SongListViewAdapter(getContext());

        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
            }
        });


        songListView.setAdapter(songListViewAdapter);

        //Song


        String[] proj = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE};

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " + MediaStore.Audio.Media.DATA + " Like '%.mp3'";

        musiccursor = getActivity().managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                proj, selection, null, MediaStore.Audio.Media.TITLE + " ASC");

        while (musiccursor.moveToNext()) {
            songListViewAdapter.addSongList(musiccursor.getString(6), musiccursor.getString(5), musiccursor.getString(4));
        }


        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == btnPlayPause && flag == 0){
            btnPlayPause.setBackground(imgPause);
            flag = 1;
        }else if(view == btnPlayPause && flag == 1){
            btnPlayPause.setBackground(imgPlay);
            flag = 0;
        }
    }


}
