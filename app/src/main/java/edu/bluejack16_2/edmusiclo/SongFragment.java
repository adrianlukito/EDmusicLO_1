package edu.bluejack16_2.edmusiclo;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

    LinearLayout bottomPlayer;

    int flag = 0;

    public SongFragment() {
        // Required empty public constructor
    }
    MediaPlayer mp =  new MediaPlayer();
    MediaPlayer mediaPlayer;

    Cursor musiccursor;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_song,container,false);

        bottomPlayer = (LinearLayout) view.findViewById(R.id.bottomPlayer);
        btnPlayPause = (Button) view.findViewById(R.id.btnPlayPause);

        btnPlayPause.setOnClickListener(this);
        bottomPlayer.setOnClickListener(this);

        imgPlay = getResources().getDrawable(R.drawable.ic_play);
        imgPause = getResources().getDrawable(R.drawable.ic_pause);

        tvBottomSongTitle = (TextView) view.findViewById(R.id.tvBottomSongTitle);
        tvBottomSongArtist = (TextView) view.findViewById(R.id.tvBottomSongArtist);

        Typeface varela = Typeface.createFromAsset(getActivity().getAssets(),"VarelaRound-Regular.ttf");

        tvBottomSongTitle.setTypeface(varela);
        tvBottomSongArtist.setTypeface(varela);

        final ListView songListView = (ListView) view.findViewById(R.id.songListView);
        final SongListViewAdapter songListViewAdapter = new SongListViewAdapter(getContext());

        songListView.setAdapter(songListViewAdapter);

        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            View lastView;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(lastView != null) {
                    TextView title = (TextView) lastView.findViewById(R.id.tvSongAlbum);
                    title.setSelected(false);
                    lastView = view;
                }else{
                    lastView = view;
                }
                TextView title = (TextView) view.findViewById(R.id.tvSongAlbum);
                title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                title.setMarqueeRepeatLimit(-1);
                title.setSelected(true);

                musiccursor.moveToPosition(position);
                String path = musiccursor.getString(1);

                try {
                    if(mediaPlayer !=null) {
                        mediaPlayer.stop();
                    }                                                                                                     
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                }catch (Exception e){

                }

                tvBottomSongTitle.setText(musiccursor.getString(2));
                tvBottomSongArtist.setText(musiccursor.getString(5));

                btnPlayPause.setBackground(imgPause);
            }
        });
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
        if(view == btnPlayPause && !mediaPlayer.isPlaying()){
            btnPlayPause.setBackground(imgPause);

            String path = musiccursor.getString(1);
            int length = mediaPlayer.getCurrentPosition();
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.seekTo(length);
                mediaPlayer.start();
            }catch (Exception e){

            }
        }else if(view == btnPlayPause && mediaPlayer.isPlaying()){
            btnPlayPause.setBackground(imgPlay);

            String path = musiccursor.getString(1);

            try {
                if(mediaPlayer !=null) {
                    mediaPlayer.stop();
                }
            }catch (Exception e){

            }
        }else if(view == bottomPlayer){
            Intent intent = new Intent(getContext(), MusicActivity.class);
            startActivity(intent);
        }
    }


}
