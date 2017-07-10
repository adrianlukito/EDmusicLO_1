package edu.bluejack16_2.edmusiclo;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.bluejack16_2.edmusiclo.model.Session;

/**
 * Created by Adrian Lukito Lo on 23/06/2017.
 */

public class FriendListViewAdapter extends BaseAdapter{

    ArrayList<String> names;
    ArrayList<Drawable> pictures;
    ArrayList<String> emails;

    Context context;

    TextView friendName;
    CircleImageView friendPicture;

    DatabaseReference databaseReference;

    public FriendListViewAdapter(Context context) {
        names = new ArrayList<String>();
        pictures = new ArrayList<Drawable>();
        emails = new ArrayList<String>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Follow");
        this.context = context;
    }

    public void addFriendList(String friendName, Drawable friendPicture){
        names.add(friendName);
        pictures.add(friendPicture);
    }

    public void addFriendList(String email, String friendName, Drawable friendPicture){
        names.add(friendName);
        pictures.add(friendPicture);
        emails.add(email);
    }

    public String getEmail(int i){
        return emails.get(i);
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.friendrow,viewGroup,false);
        }

        Typeface varela = Typeface.createFromAsset(context.getAssets(),"VarelaRound-Regular.ttf");

        friendName = (TextView) view.findViewById(R.id.friendName);
        friendPicture = (CircleImageView) view.findViewById(R.id.friendPicture);

        friendName.setText(names.get(i));
        friendPicture.setImageDrawable(pictures.get(i));

        friendName.setTypeface(varela);

        Button deleteButton = (Button) view.findViewById(R.id.btnDeleteFriend);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Session session = new Session(context);
                    databaseReference.orderByChild("followed").equalTo(session.getUser().getEmail())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    try {
                                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                            if (childSnapshot.child("following").getValue().toString().equals(emails.get(i))) {
                                                childSnapshot.getRef().removeValue();
                                                names.remove(i);
                                                emails.remove(i);
                                               // pictures.remove(i);
                                                notifyDataSetChanged();
                                            }
                                        }
                                    }catch (Exception e){
                                        Log.d("gen", e.toString());
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                }catch (Exception e){
                    Log.d("gen", e.toString());
                }
            }
        });


        return view;
    }
}
