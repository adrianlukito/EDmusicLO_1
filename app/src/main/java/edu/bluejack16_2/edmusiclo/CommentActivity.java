package edu.bluejack16_2.edmusiclo;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvCommentTitle;
    Button backComment;
    ImageButton btnInsertComment;

    Drawable bruno, bts, bigbang, gfriend, apink;

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

        bruno = getResources().getDrawable(R.drawable.bruno_mars);

        bruno.setBounds(0,0,100,100);

        ListView commentListView = (ListView) findViewById(R.id.commentListView);

        final CommentListViewAdapter commentListViewAdapter = new CommentListViewAdapter(getApplicationContext());

        commentListViewAdapter.addComment(bruno,"Bruno Mars","I love your song");
        commentListViewAdapter.addComment(bruno,"Bangtan Boys","They will have concert this week");
        commentListViewAdapter.addComment(bruno,"Bigbang","I heard they will comeback soon");
        commentListViewAdapter.addComment(bruno,"GFriend","We have similiar typr of song <3");
        commentListViewAdapter.addComment(bruno,"Apink","Naeun Saranghae");

        commentListView.setAdapter(commentListViewAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view == backComment){
            finish();
        }
    }
}
