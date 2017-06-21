package edu.bluejack16_2.edmusiclo;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


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

        ListView songListView = (ListView) view.findViewById(R.id.songListView);
        final SongListViewAdapter songListViewAdapter = new SongListViewAdapter(getContext());

        songListViewAdapter.addSongList("Mr. Chu","A pink","Unknow Album");
        songListViewAdapter.addSongList("Black Widow","Pristin","The 1st Mini Album 'HI! PRISTIN'");
        songListViewAdapter.addSongList("FOOL","WINNER","FATE NUMBER FOR");
        songListViewAdapter.addSongList("Knock Knock","TWICE","TWICEcoaster : LANE 2");
        songListViewAdapter.addSongList("LAST DANCE","Big Bang","MADE");
        songListViewAdapter.addSongList("LOVE ME RIGHT","EXO","The 2nd Album Repackage");
        songListViewAdapter.addSongList("Not Today","BTS","YOU NEVER WALK ALONE");
        songListViewAdapter.addSongList("Whistle","BLACKPINK","SQUARE ONE");
        songListViewAdapter.addSongList("Now, We","Lovelyz","Lovelyz 2nd Album Repackage");
        songListViewAdapter.addSongList("PLAYING WITH FIRE","BLACKPINK","SQUARE TWO");
        songListViewAdapter.addSongList("B-DAY","iKON","NEW KIDS : BEGIN");
        songListViewAdapter.addSongList("I Love You","GOT7","MAD Winter Edition");

        songListView.setAdapter(songListViewAdapter);

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
