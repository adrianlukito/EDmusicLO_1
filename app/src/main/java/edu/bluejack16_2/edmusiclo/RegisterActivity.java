package edu.bluejack16_2.edmusiclo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.bluejack16_2.edmusiclo.model.UserModel;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText fullName, email, password, confirm;
    Button signup;
    TextView title, login;
    Drawable lock, user, mail;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullName = (EditText) findViewById(R.id.txtFullName);
        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtPassword);
        confirm = (EditText) findViewById(R.id.txtConfirm);
        signup = (Button) findViewById(R.id.btnSignUp);

        title = (TextView) findViewById(R.id.txtTitle);
        login = (TextView) findViewById(R.id.txtLogin);

        lock = getResources().getDrawable(R.drawable.lock);
        mail = getResources().getDrawable(R.drawable.mail);
        user = getResources().getDrawable(R.drawable.user);
        lock.setBounds(0,0,80,80);
        mail.setBounds(0,0,80,80);
        user.setBounds(0,0,80,80);

        password.setCompoundDrawables(lock,null,null,null);
        confirm.setCompoundDrawables(lock,null,null,null);
        email.setCompoundDrawables(mail,null,null,null);
        fullName.setCompoundDrawables(user,null,null,null);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");
        Typeface banger = Typeface.createFromAsset(getAssets(),"Bangers-Regular.ttf");

        fullName.setTypeface(varela);
        email.setTypeface(varela);
        password.setTypeface(varela);
        confirm.setTypeface(varela);
        signup.setTypeface(varela);
        title.setTypeface(banger);


        progressDialog = new ProgressDialog(this);

        //firebase init
        firebaseAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance().getReference("Users") ;

        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == login){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }else if(view == signup){
            final String emailTxt = email.getText().toString();
            final String passwordTxt = password.getText().toString();
            String confirmTxt = confirm.getText().toString();
            final String fullnameTxt = fullName.getText().toString();

            if(emailTxt.trim().isEmpty() || passwordTxt.trim().isEmpty() || confirmTxt.trim().isEmpty() ||
                    fullnameTxt.trim().isEmpty()){
                Toast.makeText(this, "All field must be filled", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!confirmTxt.equals(passwordTxt)){
                Toast.makeText(this, "Confirm and password must be same", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                progressDialog.setMessage("Register User... Please Wait");
                progressDialog.show();


                firebaseAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id =  database.push().getKey();
                            UserModel newUser = new UserModel(id, fullnameTxt, emailTxt, passwordTxt);
                            database.child(id).setValue(newUser);
                            Toast.makeText(RegisterActivity.this, "Success Register", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Unsuccess input data, try again", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.cancel();
                    }
                });
            }catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error Penting", e.toString());
            }
        }
    }
}
