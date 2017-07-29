package edu.bluejack16_2.edmusiclo;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Adrian Lukito Lo on 29/07/2017.
 */

public class CommentListViewAdapter extends BaseAdapter{

    ArrayList<Drawable> friendPictures;
    ArrayList<String> friendNames;
    ArrayList<String> comments;

    Context context;

    TextView tvFriendName, tvComment;

    CircleImageView imgFriendPicture;

    ImageView test;

    public CommentListViewAdapter(Context context) {
        friendPictures = new ArrayList<Drawable>();
        friendNames = new ArrayList<String>();
        comments = new ArrayList<String>();
        this.context = context;
    }

    public void addComment(Drawable friendPicture, String friendName, String comment){
        friendPictures.add(friendPicture);
        friendNames.add(friendName);
        comments.add(comment);
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.comment_row,viewGroup,false);

        Typeface varela = Typeface.createFromAsset(context.getAssets(),"VarelaRound-Regular.ttf");

        tvFriendName = (TextView) view.findViewById(R.id.tvFriendName);
        tvFriendName.setText(friendNames.get(i));
        tvFriendName.setTypeface(varela);

        tvComment = (TextView) view.findViewById(R.id.tvComment);
        tvComment.setText(comments.get(i));
        tvComment.setTypeface(varela);

        imgFriendPicture = (CircleImageView) view.findViewById(R.id.commentFriendPicture);
        imgFriendPicture.setImageDrawable(friendPictures.get(i));

        return view;
    }
}
