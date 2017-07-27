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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.bluejack16_2.edmusiclo.model.FavoriteSong;
import edu.bluejack16_2.edmusiclo.model.MusicCursor;
import edu.bluejack16_2.edmusiclo.model.Session;
import edu.bluejack16_2.edmusiclo.state.ContextStateMusic;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicDisplayFragment extends Fragment implements View.OnClickListener{

    Button btnMusicPlaying, btnFavMusic;
//    Drawable iconLooping, iconNormal, iconRandom;
    ImageView imgAlbum;

    Drawable like_fill, like;

    DatabaseReference favoriteDatabaseReference;

    public MusicDisplayFragment() {
        // Required empty public constructor
    }
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        try {

            view = inflater.inflate(R.layout.fragment_music_display, container, false);
            imgAlbum = (ImageView) view.findViewById(R.id.imgViewMusic);
            favoriteDatabaseReference = FirebaseDatabase.getInstance().getReference("Favorite");
            btnMusicPlaying = (Button) view.findViewById(R.id.btnMusicPlaying);
            btnFavMusic = (Button) view.findViewById(R.id.btnFavMusic);
            btnFavMusic.setOnClickListener(this);
            btnMusicPlaying.setOnClickListener(this);
            btnMusicPlaying.setBackground(ContextStateMusic.getInstance().getIcon(view));

            like = view.getResources().getDrawable(R.drawable.ic_like);
            like_fill = view.getResources().getDrawable(R.drawable.ic_like_fill);

        }catch (Exception e){
            Log.d("testa", "af: " + e.toString());
        }
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
        }else if(v == btnFavMusic){
            btnFavMusic.setBackground(like_fill);

            Session session = new Session(getContext());

            FavoriteSong favoriteSong = new FavoriteSong(session.getUser().getEmail(), Integer.parseInt( MusicCursor.
                    getInstance().musiccursor.getString(0)));

            String id =  favoriteDatabaseReference.push().getKey();

            favoriteDatabaseReference.child(id).setValue(favoriteSong);
        }
    }
}
