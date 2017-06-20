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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText fullName, email, password, confirm;
    Button signup;
    TextView title, login;
    Drawable lock, user, mail;

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

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == login){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
}
