package edu.bluejack16_2.edmusiclo;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.bluejack16_2.edmusiclo.model.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimeLineFragment extends Fragment {

    Drawable bruno, bts, bigbang, gfriend, apink, got7, suju, suju2,gfriend2, ikon, ikon2, wjsn, snsd, snsd2;

    public TimeLineFragment() {
        // Required empty public constructor
    }


    Session session;

    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_time_line,container,false);

        final ListView timeLineListView = (ListView) view.findViewById(R.id.timelineListView);

        final TimeLineListViewAdapter timeLineListViewAdapter = new TimeLineListViewAdapter(getContext());

        bruno = getResources().getDrawable(R.drawable.bruno_mars);
//        bts = getResources().getDrawable(R.drawable.wings);
//        bigbang = getResources().getDrawable(R.drawable.bigbang);
//        gfriend = getResources().getDrawable(R.drawable.awakening);
//        apink = getResources().getDrawable(R.drawable.apink);
//        got7 = getResources().getDrawable(R.drawable.just_right);
//        gfriend2 = getResources().getDrawable(R.drawable.lol);
//        suju = getResources().getDrawable(R.drawable.mrsimple);
//        suju2 = getResources().getDrawable(R.drawable.bonamana);
//        ikon = getResources().getDrawable(R.drawable.ikon);
//        ikon2 = getResources().getDrawable(R.drawable.newkids);
//        wjsn = getResources().getDrawable(R.drawable.secret);
//        snsd = getResources().getDrawable(R.drawable.the_boys);
//        snsd2 = getResources().getDrawable(R.drawable.oh);

        session = new Session(getContext());

        databaseReference = FirebaseDatabase.getInstance().getReference("Follow");

        databaseReference.orderByChild("followed").equalTo(session.getUser().getEmail()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                String thisEmail = childSnapshot.child("following").getValue().toString();
                                Log.d("errr", thisEmail);
                                //if (thisEmail.equals(session.getUser().getEmail())) {
                                FirebaseDatabase.getInstance().getReference().child("Timeline").orderByChild("user")
                                        .equalTo(thisEmail).addValueEventListener(
                                        new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                                    String email = childSnapshot.child("user").getValue().toString();
                                                    String fullname = childSnapshot.child("songName").getValue().toString();
                                                    String id = childSnapshot.child("id").getValue().toString();
                                                    timeLineListViewAdapter.addTimeLineList(bruno,email,fullname, id);
                                                }

                                                timeLineListView.setAdapter(timeLineListViewAdapter);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        }
                                );
                                //}
                            }
                            Log.d("errrFinish", "asf");
                        }catch (Exception e){
                            Log.d("Errror", e.toString());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
        return view;
    }

}
