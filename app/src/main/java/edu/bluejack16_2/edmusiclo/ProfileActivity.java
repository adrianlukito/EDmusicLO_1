package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    Button backProfile, changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backProfile = (Button) findViewById(R.id.backProfile);
        changePassword = (Button) findViewById(R.id.btnChangePassword);

        backProfile.setOnClickListener(this);
        changePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == backProfile){
            finish();
        }else if(view == changePassword){
            Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
            startActivity(intent);
        }
    }
}
