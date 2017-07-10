package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import edu.bluejack16_2.edmusiclo.model.Session;
import edu.bluejack16_2.edmusiclo.model.UserModel;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    Button backProfile, changePassword, successProfile;

    FirebaseAuth firebaseAuth;

    TextView txtEmail, txtUsername;

    void init(){
        firebaseAuth = FirebaseAuth.getInstance();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        backProfile = (Button) findViewById(R.id.backProfile);
        changePassword = (Button) findViewById(R.id.btnChangePassword);
        successProfile = (Button) findViewById(R.id.successProfile);

        txtEmail = (TextView) findViewById(R.id.txtChangeEmail);
        txtUsername= (TextView) findViewById(R.id.txtChangeName);

        if(!firebaseAuth.getCurrentUser().getProviders().get(0).equalsIgnoreCase("password")){
            changePassword.setEnabled(false);
            successProfile.setEnabled(false);
            txtUsername.setEnabled(false);
        }
        Session session = new Session(getBaseContext());
        UserModel userModel = session.getUser();

        txtUsername.setText(userModel.getFullname());
        txtEmail.setText(userModel.getEmail());

        backProfile.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        successProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == backProfile){
            finish();
        }else if(view == changePassword){
            Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
            startActivity(intent);
        }else if(view == successProfile){
            String currentName = txtUsername.getText().toString();

            final Session session = new Session(getBaseContext());
            final UserModel userModel = session.getUser();
            userModel.setFullname(currentName);

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");
            final Query query = dbRef
                    .orderByChild("email")
                    .equalTo(userModel.getEmail());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                        String key =  childSnapshot.getKey();
                        dataSnapshot.getRef().child(key).setValue(userModel);
                    }
                    MainActivity.changeUserName(userModel.getFullname());
                    session.setUser(userModel);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
