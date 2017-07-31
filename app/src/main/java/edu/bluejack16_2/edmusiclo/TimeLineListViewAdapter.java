package edu.bluejack16_2.edmusiclo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.icu.text.LocaleDisplayNames;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Vector;

import edu.bluejack16_2.edmusiclo.model.Session;

/**
 * Created by Adrian Lukito Lo on 16/07/2017.
 */

public class TimeLineListViewAdapter extends BaseAdapter implements View.OnClickListener{

    ArrayList<Drawable> timeLineProfilePictures;
    ArrayList<String> timeLineProfileNames;
    ArrayList<String> timeLineCotents;
    ArrayList<String> timeLineID;

    TextView tvTimeLineProfileName, tvTimeLineContent;
    Button likeButton, commentButton;

    Context context;
    Fragment fragment;

    DatabaseReference databaseReference;

    int index;

    Session session;
    public TimeLineListViewAdapter(Context context, Fragment fragment) {
        timeLineProfilePictures = new ArrayList<Drawable>();
        timeLineProfileNames = new ArrayList<String>();
        timeLineCotents = new ArrayList<String>();
        timeLineID = new ArrayList<String>();
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference("Timeline");
        session = new Session(context);
        this.fragment = fragment;
    }

    public void addTimeLineList(Drawable timeLineProfilePicture, String timeLineProfileName, String timeLineCotent, String id){
        timeLineProfilePictures.add(timeLineProfilePicture);
        timeLineProfileNames.add(timeLineProfileName);
        timeLineCotents.add(timeLineCotent);
        timeLineID.add(id);
    }

    @Override
    public int getCount() {
        return timeLineProfileNames.size();
    }

    @Override
    public Object getItem(int i) {
        return timeLineProfileNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.time_line_row,viewGroup,false);

        Typeface varela = Typeface.createFromAsset(context.getAssets(),"VarelaRound-Regular.ttf");

        tvTimeLineProfileName = (TextView) view.findViewById(R.id.tvTimeLineProfileName);
        tvTimeLineContent = (TextView) view.findViewById(R.id.tvTimeLineContent);

        tvTimeLineProfileName.setText(timeLineProfileNames.get(i));
        tvTimeLineContent.setText(timeLineCotents.get(i));

        tvTimeLineProfileName.setTypeface(varela);
        tvTimeLineContent.setTypeface(varela);

        index = i;

        commentButton = (Button) view.findViewById(R.id.btnComment);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CommentActivity.class);
                intent.putExtra("keyTimeline", timeLineID.get(i));
                view.getContext().startActivity(intent);
            }
        });

        likeButton = (Button) view.findViewById(R.id.btnLike);
        likeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                databaseReference.orderByChild("id").equalTo(timeLineID.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            try {
                                int flag = 0;
                                JSONArray jsonArray = new JSONArray(childSnapshot.child("likes").getValue().toString());
                                Vector<String> likes = new Vector<String>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    if(jsonArray.getString(i).equals(session.getUser().getEmail())){
                                        flag = 1;
                                    }else {
                                        likes.add(jsonArray.getString(i));
                                    }
                                }
                                if(flag == 0) {
                                    likes.add(session.getUser().getEmail());
                                }
                                childSnapshot.child("likes").getRef().setValue(likes);
                                notification(childSnapshot.child("user").getValue().toString());
                            }catch (Exception e)
                            {
                                Log.d("testa", e.toString());

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == likeButton) {
            Log.d("testa", index+"");
            Toast.makeText(context, index+"", Toast.LENGTH_SHORT).show();
        }else if(v == commentButton){

        }
    }

    void notification(String name){
        try {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.bruno_mars)
                    .setContentTitle(name+ " have like your post")
                    .setContentText("");

            NotificationManager mNotificationManager = (NotificationManager) fragment.getActivity().getSystemService(context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(0, mBuilder.build());
        }catch (Exception e){
        }
    }
}
