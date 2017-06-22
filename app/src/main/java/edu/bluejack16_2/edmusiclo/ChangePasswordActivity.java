package edu.bluejack16_2.edmusiclo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener{

    Button backChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        backChangePassword = (Button) findViewById(R.id.backChangePassword);
        backChangePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == backChangePassword){
            finish();
        }
    }
}
