package edu.bluejack16_2.edmusiclo;


import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    Session session;

    boolean isLike = false;
    String keyLike = "";

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
            session = new Session(getContext());

            like = view.getResources().getDrawable(R.drawable.ic_like);
            like_fill = view.getResources().getDrawable(R.drawable.ic_like_fill);

            favoriteDatabaseReference.orderByChild("emailUser").equalTo(session.getUser().getEmail()).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        if(childSnapshot.child("songID").getValue().toString().equals(MusicCursor.getInstance().musiccursor.getString(0))){
                            btnFavMusic.setBackground(like_fill);
                            isLike = true;
                            keyLike = childSnapshot.getKey();
                            Toast.makeText(getActivity(), "wow", Toast.LENGTH_SHORT).show();

                            Log.d("testa", "f "+ childSnapshot.child("songID").getValue().toString()+ " + " +MusicCursor.getInstance().musiccursor.getString(0));
                            break;
                        }else{
                            btnFavMusic.setBackground(like);
                            isLike= false;

                            Log.d("testa", "s "+ childSnapshot.child("songID").getValue().toString()+ " + " + MusicCursor.getInstance().musiccursor.getString(0));
                        }
                    }
                    Log.d("testa", "c "+ isLike);
                    Toast.makeText(getActivity(), "adasdasd", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            favoriteDatabaseReference.orderByChild("emailUser").equalTo(session.getUser().getEmail()).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if(dataSnapshot.child("songID").getValue().toString().equals(MusicCursor.getInstance().musiccursor.getString(0))) {
                        btnFavMusic.setBackground(like_fill);
                        isLike = true;
                        keyLike = dataSnapshot.getKey();
                        Log.d("testa", "kok ke pang");
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    isLike = false;
                    btnFavMusic.setBackground(like);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

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

            Log.d("testa", "b "+ isLike);
            if(!isLike) {
                FavoriteSong favoriteSong = new FavoriteSong(session.getUser().getEmail(), Integer.parseInt(MusicCursor.
                        getInstance().musiccursor.getString(0)));

                String id = favoriteDatabaseReference.push().getKey();

                favoriteDatabaseReference.child(id).setValue(favoriteSong);

            }else{
                favoriteDatabaseReference.child(keyLike).getRef().removeValue();
            }
        }
    }
}
