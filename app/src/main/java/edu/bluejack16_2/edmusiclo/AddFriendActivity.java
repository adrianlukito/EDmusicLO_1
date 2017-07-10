package edu.bluejack16_2.edmusiclo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import edu.bluejack16_2.edmusiclo.model.Follow;
import edu.bluejack16_2.edmusiclo.model.Session;
import edu.bluejack16_2.edmusiclo.model.UserModel;

public class  AddFriendActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener{

    Button backAddFriend, addFriend;
    TextView txtSearchName;

    SearchView searchAddFriendView;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    DatabaseReference databaseFollowFriendReference;

    RelativeLayout found,notFound;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        try {
            firebaseAuth = FirebaseAuth.getInstance();
            databaseReference = FirebaseDatabase.getInstance().getReference("Users");

            databaseFollowFriendReference = FirebaseDatabase.getInstance().getReference("Follow");

            backAddFriend = (Button) findViewById(R.id.backAddFriend);
            backAddFriend.setOnClickListener(this);

            addFriend = (Button) findViewById(R.id.addFriendButton);
            addFriend.setOnClickListener(this);

            txtSearchName = (TextView) findViewById(R.id.addFriendName);

            found = (RelativeLayout) findViewById(R.id.userFound);
            notFound = (RelativeLayout) findViewById(R.id.userNotFound);

            found.setVisibility(View.INVISIBLE);
            notFound.setVisibility(View.INVISIBLE);

            searchAddFriendView= (SearchView) findViewById(R.id.searchAddFriend);
            searchAddFriendView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String query) {
                    found.setVisibility(View.INVISIBLE);
                    notFound.setVisibility(View.INVISIBLE);
                    final ProgressDialog progressDialog = new ProgressDialog(AddFriendActivity.this);
                    progressDialog.setMessage("Searching...");
                    progressDialog.show();
                    databaseReference.orderByChild("email").equalTo(query)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int data = 0;
                                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                        data = 1;
                                        txtSearchName.setText(childSnapshot.child("fullname").getValue().toString());
                                        found.setVisibility(View.VISIBLE);
                                        email = query;
                                    }
                                    if(data ==0){
                                        notFound.setVisibility(View.VISIBLE);
                                    }
                                    progressDialog.cancel();
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("errorr", e.toString());
        }

    }

    @Override
    public void onClick(View view) {
        if(view == backAddFriend){
            finish();
        }else if(view == addFriend){
            Session session = new Session(getBaseContext());
            Follow follow = new Follow(session.getUser().getEmail(), email);

            String id =  databaseFollowFriendReference.push().getKey();

            databaseFollowFriendReference.child(id).setValue(follow);

        }
    }

    public void searchData(){

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
        {
            switch (keyCode)
            {
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_ENTER:
                    searchData();
                    return true;
                default:
                    break;
            }
        }
        return false;
    }
}
