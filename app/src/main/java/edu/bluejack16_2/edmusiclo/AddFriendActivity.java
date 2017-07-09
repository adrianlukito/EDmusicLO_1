package edu.bluejack16_2.edmusiclo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddFriendActivity extends AppCompatActivity implements View.OnClickListener{

    Button backAddFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        backAddFriend = (Button) findViewById(R.id.backAddFriend);
        backAddFriend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == backAddFriend){
            finish();
        }
    }
}
