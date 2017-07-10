package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import edu.bluejack16_2.edmusiclo.model.Session;

public class FriendActivity extends AppCompatActivity implements View.OnClickListener{

    Drawable pic1, pic2, pic3;

    EditText searchText;
    SearchView searchView;

    Button addFriendButton;
    Button backFriend, btnAddFriend;

    DatabaseReference databaseReference ;

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_friend);

           session = new Session(getBaseContext());

            addFriendButton = (Button) findViewById(R.id.addFriend);
            addFriendButton.setOnClickListener(this);

            backFriend = (Button) findViewById(R.id.backFriend);
            btnAddFriend = (Button) findViewById(R.id.addFriend);

            backFriend.setOnClickListener(this);
            btnAddFriend.setOnClickListener(this);

//            searchView = (SearchView) findViewById(R.id.searchFriend);
//            ((EditText) searchView.findViewById(R.id.searchFriend)).setHintTextColor(Color.WHITE);
//            ((EditText) searchView.findViewById(R.id.searchFriend)).setTextColor(Color.WHITE);
//            ((EditText) searchView.findViewById(R.id.searchFriend)).setHint("Search Friend");

        final ListView friendListView = (ListView) findViewById(R.id.friendListView);
        final FriendListViewAdapter friendListViewAdapter = new FriendListViewAdapter(getApplicationContext());

//        pic1 = getResources().getDrawable(R.drawable.logo16_2v2);
//        pic2 = getResources().getDrawable(R.drawable.signup);
//        pic3 = getResources().getDrawable(R.drawable.toolbar_wallpaper);



            if(!FirebaseAuth.getInstance().getCurrentUser().getProviders().get(0).equals("password")) {
                GraphRequest graphRequest = GraphRequest.newMyFriendsRequest(AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONArrayCallback() {
                            @Override
                            public void onCompleted(JSONArray objects, GraphResponse response) {
                                Log.d("Friendss", objects.toString());
                            }
                        });
            }else{
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Follow");

                databaseReference.orderByChild("followed").equalTo(session.getUser().getEmail()).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                        String thisEmail = childSnapshot.child("followed").getValue().toString();
                                        Log.d("errr", thisEmail);
                                        if (thisEmail.equals(session.getUser().getEmail())) {
                                            FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("email")
                                                    .equalTo(childSnapshot.child("following").getValue().toString()).addListenerForSingleValueEvent(
                                                    new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                                                String email = childSnapshot.child("email").getValue().toString();
                                                                String fullname = childSnapshot.child("fullname").getValue().toString();

                                                                friendListViewAdapter.addFriendList(email, fullname, pic2);
                                                            }
                                                            friendListView.setAdapter(friendListViewAdapter);
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    }
                                            );
                                        }
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
            }

//        friendListViewAdapter.addFriendList("Adrian Lukito Lo",pic2);
//        friendListViewAdapter.addFriendList("Tedy Junaidi",pic2);
//        friendListViewAdapter.addFriendList("Albert Zhuang",pic2);
//        friendListViewAdapter.addFriendList("Alven Gemini Julio",pic2);
//        friendListViewAdapter.addFriendList("Bagas Prakoso",pic2);
//        friendListViewAdapter.addFriendList("Christian Adianto",pic2);
//        friendListViewAdapter.addFriendList("Evy Maria",pic2);
//        friendListViewAdapter.addFriendList("Giovan Saputra",pic2);
//        friendListViewAdapter.addFriendList("Jordan Leonardi",pic2);
//        friendListViewAdapter.addFriendList("Kelvin Asclepius Minor",pic2);
//        friendListViewAdapter.addFriendList("Luciana Dian Santami",pic2);
//        friendListViewAdapter.addFriendList("Misita Pontiasa",pic2);
//        friendListViewAdapter.addFriendList("Mario Viegash",pic2);
//        friendListViewAdapter.addFriendList("Philip Andreas Nugraha",pic2);
//        friendListViewAdapter.addFriendList("Peter Hartawan Suherman",pic2);
//        friendListViewAdapter.addFriendList("Renaldi",pic2);
//        friendListViewAdapter.addFriendList("Kevin Surya Wahyudi",pic2);
//        friendListViewAdapter.addFriendList("Thomas Asril",pic2);
//        friendListViewAdapter.addFriendList("Wimpi Jonathan",pic2);
          friendListView.setAdapter(friendListViewAdapter);
        }catch (Exception e){
            Log.d("errorr", e.toString());
        }
    }


    @Override
    public void onClick(View view) {
        if(view == backFriend){
            finish();
        }else if(view == btnAddFriend){
            Intent intent = new Intent(this, AddFriendActivity.class);
            startActivity(intent);
        }
    }
}
