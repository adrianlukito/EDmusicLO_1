package edu.bluejack16_2.edmusiclo;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import edu.bluejack16_2.edmusiclo.state.ContextStateMusic;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicDisplayFragment extends Fragment implements View.OnClickListener{

    Button btnMusicPlaying;
//    Drawable iconLooping, iconNormal, iconRandom;
    ImageView imgAlbum;

    public MusicDisplayFragment() {
        // Required empty public constructor
    }
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//
//        iconLooping = view.getResources().getDrawable(R.drawable.ic_looping);
//        iconNormal = view.getResources().getDrawable(R.drawable.ic_normal);
//        iconRandom = view.getResources().getDrawable(R.drawable.ic_random);
        view = inflater.inflate(R.layout.fragment_music_display, container, false);
        imgAlbum = (ImageView) view.findViewById(R.id.imgViewMusic);

        btnMusicPlaying = (Button) view.findViewById(R.id.btnMusicPlaying);
        btnMusicPlaying.setOnClickListener(this);
        btnMusicPlaying.setBackground(ContextStateMusic.getInstance().getIcon(view));
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == btnMusicPlaying){
            try {
                ContextStateMusic.getInstance().next();

                btnMusicPlaying.setBackground(ContextStateMusic.getInstance().getIcon(view));
            }catch (Exception e){
                Log.d("Errror", e.toString());
            }
        }
    }
}
