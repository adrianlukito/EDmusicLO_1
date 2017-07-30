package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.Vector;

import edu.bluejack16_2.edmusiclo.model.Session;

public class PlaylistActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvPlaylistTitle, tvCreateNewPlaylist;

    Button backPlaylist;

    RelativeLayout addNewPlayListLayout;

    DatabaseReference databaseReference;

    Session session;
    ListView playlistListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvPlaylistTitle = (TextView) findViewById(R.id.tvPlaylistTitle);
        tvCreateNewPlaylist = (TextView) findViewById(R.id.tvCreateNewPlaylist);

        tvPlaylistTitle.setTypeface(varela);
        tvCreateNewPlaylist.setTypeface(varela);

        backPlaylist = (Button) findViewById(R.id.backPlaylist);
        backPlaylist.setOnClickListener(this);

        addNewPlayListLayout = (RelativeLayout) findViewById(R.id.addNewPlayListLayout);
        addNewPlayListLayout.setOnClickListener(this);

        session = new Session(getBaseContext());

        playlistListView = (ListView) findViewById(R.id.playlistListView);

        final PlaylistListViewAdapter playlistListViewAdapter = new PlaylistListViewAdapter(getApplicationContext());

        databaseReference = FirebaseDatabase.getInstance().getReference("Playlist");

        databaseReference.orderByChild("email").equalTo(session.getUser().getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                try {
                    JSONArray jsonArray = new JSONArray(dataSnapshot.child("idSongs").getValue().toString());
                    Vector<String> songs = new Vector<String>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        songs.add(jsonArray.getString(i));
                    }

                    playlistListViewAdapter.addPlaylist(dataSnapshot.child("name").getValue().toString(), songs.size()+"");
                }catch (Exception e) {
                    Log.d("testa", e.toString());
                }

                playlistListView.setAdapter(playlistListViewAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        playlistListView.setAdapter(playlistListViewAdapter);
//        databaseReference.orderByChild("email").equalTo(session.getUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot childSnapshot : dataSnapshot.getChildren()){
//                    try {
//                        JSONArray jsonArray = new JSONArray(childSnapshot.child("idSongs").getValue().toString());
//                        Vector<String> songs = new Vector<String>();
//
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            songs.add(jsonArray.getString(i));
//                        }
//
//                        playlistListViewAdapter.addPlaylist(childSnapshot.child("name").getValue().toString(), songs.size()+"");
//                    }catch (Exception e) {
//                        Log.d("testa", e.toString());
//                    }
//                }
//                Log.d("testa", "terer");
//                playlistListView.setAdapter(playlistListViewAdapter);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }

    @Override
    public void onClick(View view) {
        if(view == backPlaylist){
            finish();
        }else if(view == addNewPlayListLayout) {
            Intent intent = new Intent(getApplicationContext(), CreateNewPlaylistActivity.class);
            startActivity(intent);
        }
    }
}
