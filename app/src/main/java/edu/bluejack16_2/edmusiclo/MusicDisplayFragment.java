package edu.bluejack16_2.edmusiclo;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicDisplayFragment extends Fragment implements View.OnClickListener{

    View btnMusicPlaying;

    Drawable iconLooping, iconNormal, iconRandom;
    ImageView imgAlbum;

    public MusicDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_music_display, container, false);

        iconLooping = view.getResources().getDrawable(R.drawable.ic_looping);
        iconNormal = view.getResources().getDrawable(R.drawable.ic_normal);
        iconRandom = view.getResources().getDrawable(R.drawable.ic_random);

        imgAlbum = (ImageView) view.findViewById(R.id.imgViewMusic);

        btnMusicPlaying = view.findViewById(R.id.btnMusicPlaying);
        btnMusicPlaying.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == btnMusicPlaying){

        }
    }
}
