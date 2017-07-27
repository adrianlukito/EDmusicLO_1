package edu.bluejack16_2.edmusiclo;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;
import edu.bluejack16_2.edmusiclo.model.Session;
import edu.bluejack16_2.edmusiclo.model.Timeline;
import edu.bluejack16_2.edmusiclo.state.ContextStateMusic;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{

    public static SongListViewAdapter songListViewAdapter;

    public static TextView tvBottomSongTitle, tvBottomSongArtist;
    Button btnPlayPause;

    Drawable imgPlay, imgPause;
    static ImageView imgCover;

    LinearLayout bottomPlayer;

    static int cPosition;

    int flag = 0;

    View lastView;

    DatabaseReference databaseReference;

    public SongFragment() {

    }

    public static MediaPlayer mediaPlayer;


    public void getPositionMusic(int position){
        cPosition = position;

        tvBottomSongTitle.setText( MusicCursor.getInstance().musiccursor.getString(6));
        tvBottomSongArtist.setText( MusicCursor.getInstance().musiccursor.getString(5));
        tvBottomSongTitle.setSelected(true);
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART, MediaStore.Audio.Albums.ALBUM},
                MediaStore.Audio.Albums._ID+ "= '"+  MusicCursor.getInstance().musiccursor.getString(8)+ "'",
                null,
                null);
        if (cursor.moveToFirst()) {
            String thisArt = cursor.getString(1);
            Bitmap bm= BitmapFactory.decodeFile(thisArt);
            if(bm == null){
                imgCover.setImageResource(R.drawable.ic_music);
            }else {
                imgCover.setImageBitmap(bm);
            }
        }
        btnPlayPause.setBackground(imgPause);
    }

    public void saveFirebaseTimeline(){
        Session session = new Session(getContext());

        Timeline timeline = new Timeline();

        timeline.setUser(session.getUser().getEmail());

        timeline.setSongName(MusicCursor.getInstance().musiccursor.getString(6));
        String id = databaseReference.push().getKey();

        timeline.setId(id);
        timeline.addLike("tedy@gmail.com");
        databaseReference.child(id).setValue(timeline);
    }

    void playingSong(int position){

        MusicCursor.getInstance().musiccursor.moveToPosition(position);
        String path =  MusicCursor.getInstance().musiccursor.getString(1);
        try {
            saveFirebaseTimeline();
        }catch (Exception e){
            Log.d("testa", e.toString());
        }
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        }catch (Exception e){

        }
        mediaPlayer.start();

//        getActivity().startService(new Intent(getContext(), FloatingLyrics.class));

        getPositionMusic(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        databaseReference = FirebaseDatabase.getInstance().getReference("Timeline");

        View view = inflater.inflate(R.layout.fragment_song,container,false);

        bottomPlayer = (LinearLayout) view.findViewById(R.id.bottomPlayer);
        btnPlayPause = (Button) view.findViewById(R.id.btnPlayPause);

        btnPlayPause.setOnClickListener(this);
        bottomPlayer.setOnClickListener(this);

        imgPlay = getResources().getDrawable(R.drawable.ic_play);
        imgPause = getResources().getDrawable(R.drawable.ic_pause);

        imgCover = (ImageView) view.findViewById(R.id.imgSongPicture);

        tvBottomSongTitle = (TextView) view.findViewById(R.id.tvBottomSongTitle);
        tvBottomSongArtist = (TextView) view.findViewById(R.id.tvBottomSongArtist);

        Typeface varela = Typeface.createFromAsset(getActivity().getAssets(),"VarelaRound-Regular.ttf");

        tvBottomSongTitle.setTypeface(varela);
        tvBottomSongArtist.setTypeface(varela);

        final ListView songListView = (ListView) view.findViewById(R.id.songListView);

        songListView.setOnItemClickListener(this);

        if(songListViewAdapter == null){
            songListViewAdapter = new SongListViewAdapter(getContext());
        }
        songListView.setAdapter(songListViewAdapter);

        //Song
        if(MusicCursor.getInstance().musiccursor == null) {
            String[] proj = {MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.SIZE,
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.ALBUM_ID,
                    MediaStore.Audio.Media.ARTIST_ID};

            String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " + MediaStore.Audio.Media.DATA + " Like '%.mp3'";

            MusicCursor.getInstance().musiccursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    proj, selection, null, MediaStore.Audio.Media.TITLE + " ASC");

            while (MusicCursor.getInstance().musiccursor.moveToNext()) {
                songListViewAdapter.addSongList(MusicCursor.getInstance().musiccursor.getString(6),
                        MusicCursor.getInstance().musiccursor.getString(5), MusicCursor.getInstance().musiccursor.getString(4));

            }
        }else {
            if(MusicCursor.getInstance().musiccursor.getPosition()>-1 && MusicCursor.getInstance().musiccursor.getPosition() < MusicCursor.getInstance().musiccursor.getCount()) {
                getPositionMusic(MusicCursor.getInstance().musiccursor.getPosition());
            }
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        if(mediaPlayer != null) {
            try {
                if(MusicCursor.getInstance().musiccursor.getPosition() <0){
                    MusicCursor.getInstance().musiccursor.moveToPosition(cPosition);
                }
                if (view == btnPlayPause && !mediaPlayer.isPlaying()) {
                    btnPlayPause.setBackground(imgPause);
                    mediaPlayer.start();
                } else if (view == btnPlayPause && mediaPlayer.isPlaying()) {
                    btnPlayPause.setBackground(imgPlay);
                    mediaPlayer.pause();
                } else if (view == bottomPlayer) {
                    Intent intent = new Intent(getContext(), MusicActivity.class);
                    intent.putExtra("position", MusicCursor.getInstance().musiccursor.getPosition());
                    intent.putExtra("duration", mediaPlayer.getCurrentPosition());
                    Toast.makeText(getContext(), mediaPlayer.getCurrentPosition()+"", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }catch (Exception e){
            }
        }
    }

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

        playingSong(position);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int position = ContextStateMusic.getInstance().onFinish();
                MusicCursor.getInstance().musiccursor.moveToPosition(position);
                playingSong(position);
            }
        });

    }
}
