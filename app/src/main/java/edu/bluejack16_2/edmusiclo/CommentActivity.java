package edu.bluejack16_2.edmusiclo;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.bluejack16_2.edmusiclo.model.Comment;
import edu.bluejack16_2.edmusiclo.model.Session;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvCommentTitle;
    Button backComment;
    ImageButton btnInsertComment;

    EditText tvCommentText;

    Drawable bruno, bts, bigbang, gfriend, apink;

    DatabaseReference databaseReference;
    Session session;

    String keyTimeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_comment);

            Typeface varela = Typeface.createFromAsset(getAssets(), "VarelaRound-Regular.ttf");

            tvCommentTitle = (TextView) findViewById(R.id.tvCommentTitle);

            tvCommentTitle.setTypeface(varela);

            tvCommentText = (EditText) findViewById(R.id.tvCommentText);


            backComment = (Button) findViewById(R.id.backComment);
            btnInsertComment = (ImageButton) findViewById(R.id.btnInsertComment);
            backComment.setOnClickListener(this);
            btnInsertComment.setOnClickListener(this);


            bruno = getResources().getDrawable(R.drawable.bruno_mars);

            bruno.setBounds(0, 0, 100, 100);

            final ListView commentListView = (ListView) findViewById(R.id.commentListView);


            session = new Session(getBaseContext());

            databaseReference = FirebaseDatabase.getInstance().getReference("Timeline");

        try {
            Intent intent = getIntent();
            keyTimeline = intent.getExtras().getString("keyTimeline");

            databaseReference.orderByChild("id").equalTo(keyTimeline).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    final CommentListViewAdapter commentListViewAdapter = new CommentListViewAdapter(getApplicationContext());
                    Log.d("testa", "Asda");
                    for (DataSnapshot childSnapshot : dataSnapshot.child(keyTimeline).child("comment").getChildren()) {
                        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users");
                        final String commentText = childSnapshot.child("text").getValue().toString();
                        String email = childSnapshot.child("userEmail").getValue().toString();
                        Log.d("testa", email);
                        userReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    commentListViewAdapter.addComment(null, childSnapshot.child("fullname").getValue().toString(),
                                            commentText);
                                }

                                commentListView.setAdapter(commentListViewAdapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }catch (Exception e){
            Log.d("testa", e.toString());
        }
    }

    @Override
    public void onClick(View view) {
        if(view == backComment){
            finish();
        }else if(view == btnInsertComment){
            String key = databaseReference.child(keyTimeline).child("comment").push().getKey();

            Comment comment = new Comment();
            comment.setUserEmail(session.getUser().getEmail());
            comment.setText(tvCommentText.getText().toString());
            databaseReference.child(keyTimeline).child("comment").child(key).setValue(comment);
        }
    }
}
