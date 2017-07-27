package edu.bluejack16_2.edmusiclo;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvCommentTitle;
    Button backComment;
    ImageButton btnInsertComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        tvCommentTitle = (TextView) findViewById(R.id.tvCommentTitle);

        tvCommentTitle.setTypeface(varela);

        backComment = (Button) findViewById(R.id.backComment);
        btnInsertComment = (ImageButton) findViewById(R.id.btnInsertComment);
        backComment.setOnClickListener(this);
        btnInsertComment.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == backComment){
            finish();
        }
    }
}
