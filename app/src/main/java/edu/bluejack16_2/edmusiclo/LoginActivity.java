package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtEmail, txtPassword;
    Button btnLogin;
    TextView txtTitle, txtSignup;
    Drawable lock, mail;

    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;

    CallbackManager callbackManager;

    LoginButton loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);



        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtSignup = (TextView) findViewById(R.id.txtSignUp);

        lock = getResources().getDrawable(R.drawable.lock);
        mail = getResources().getDrawable(R.drawable.mail);
        lock.setBounds(0,0,80,80);
        mail.setBounds(0,0,80,80);

        txtPassword.setCompoundDrawables(lock,null,null,null);
        txtEmail.setCompoundDrawables(mail,null,null,null);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");
        Typeface banger = Typeface.createFromAsset(getAssets(),"Bangers-Regular.ttf");

        txtEmail.setTypeface(varela);
        txtPassword.setTypeface(varela);
        btnLogin.setTypeface(varela);
        txtTitle.setTypeface(banger);

        btnLogin.setOnClickListener(this);
        txtSignup.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signOut();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    String email = txtEmail.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("email", email);

                    Toast.makeText(LoginActivity.this, firebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                }
            }
        };

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.loginFacebook_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handle(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handle(AccessToken token){
        AuthCredential authCredential = FacebookAuthProvider.getCredential(token.getToken());

        Toast.makeText(this, token.getUserId() + " " + token.getToken(), Toast.LENGTH_LONG).show();

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Auth is failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onClick(View view) {
        if(view == txtSignup){
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        }else if(view == btnLogin){
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            if(email.equals("")){
                Toast.makeText(this, "Email must be filled", Toast.LENGTH_SHORT).show();
            }else if(password.equals("")){
                Toast.makeText(this, "Password must be filled", Toast.LENGTH_SHORT).show();
            }else{
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Problem sign in", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }
}
