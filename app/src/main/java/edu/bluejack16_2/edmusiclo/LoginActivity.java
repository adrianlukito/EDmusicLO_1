package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtEmail, txtPassword;
    Button btnLogin;
    TextView txtTitle, txtSignup;
    Drawable lock, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void onClick(View view) {
        if(view == txtSignup){
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        }else if(view == btnLogin){
            if(txtEmail.equals("")){
                Toast.makeText(this, "Email must be filled", Toast.LENGTH_SHORT).show();
            }else if(txtPassword.equals("")){
                Toast.makeText(this, "Password must be filled", Toast.LENGTH_SHORT).show();
            }else{
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        }
    }
}
