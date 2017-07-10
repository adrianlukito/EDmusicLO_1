package edu.bluejack16_2.edmusiclo;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import edu.bluejack16_2.edmusiclo.model.Session;
import edu.bluejack16_2.edmusiclo.model.UserModel;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener{

    Button backChangePassword, btnChangePassword;
    TextView oldPassword, newPassword, cnfPassword;

    DatabaseReference reference;

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        session = new Session(getBaseContext());
        reference = FirebaseDatabase.getInstance().getReference("Users");

        backChangePassword = (Button) findViewById(R.id.backChangePassword);
        backChangePassword.setOnClickListener(this);
        btnChangePassword = (Button) findViewById(R.id.btnChangePassword2);
        btnChangePassword.setOnClickListener(this);

        oldPassword = (TextView) findViewById(R.id.txtChangeOldPassword);
        newPassword = (TextView) findViewById(R.id.txtChangeNewPassword);
        cnfPassword = (TextView) findViewById(R.id.txtChangeConfirmNewPassword);

    }

    @Override
    public void onClick(View view) {
        if(view == backChangePassword){

            finish();
        }else if(view == btnChangePassword){

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            final UserModel user = session.getUser();

            Query query = reference
                    .orderByChild("email")
                    .equalTo(user.getEmail());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final DataSnapshot dataSnapshot1 = dataSnapshot;
                    if(!oldPassword.getText().toString().equals(user.getPassword())){
                        progressDialog.cancel();
                        Toast.makeText(ChangePasswordActivity.this, "Old password isnt same", Toast.LENGTH_SHORT).show();
                    }else if(newPassword.getText().toString().trim().equals("") ||
                            cnfPassword.getText().toString().trim().equals("") || newPassword.getText().toString().length() < 6){
                        Toast.makeText(ChangePasswordActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    } else{
                        if(newPassword.getText().toString().equals(cnfPassword.getText().toString())){
                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(user.getEmail(), oldPassword.getText().toString());

                            FirebaseAuth.getInstance().getCurrentUser().reauthenticate(credential)
                                    .addOnCompleteListener(
                                            new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               if (task.isSuccessful()) {
                                                   FirebaseAuth.getInstance().getCurrentUser().updatePassword(newPassword.getText().toString()).
                                                           addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           if (task.isSuccessful()) {
                                                               Toast.makeText(ChangePasswordActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                                               for (DataSnapshot childSnapshot: dataSnapshot1.getChildren()) {
                                                                   user.setPassword(newPassword.getText().toString());
                                                                   String key =  childSnapshot.getKey();
                                                                   dataSnapshot1.getRef().child(key).setValue(user);
                                                               }
                                                               session.setUser(user);
                                                               progressDialog.cancel();

                                                           } else {
                                                               //Log.d(TAG, "Error password not updated")
                                                           }
                                                       }
                                                   });
                                               } else {
                                                   //Log.d(TAG, "Error auth failed")
                                               }
                                           }
                                       });
                            progressDialog.cancel();


                            //finish();
                        }else{
                            progressDialog.cancel();
                            Toast.makeText(ChangePasswordActivity.this, "password and confirm isnt same", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
