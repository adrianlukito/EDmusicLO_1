package edu.bluejack16_2.edmusiclo;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Adrian Lukito Lo on 23/06/2017.
 */

public class FriendListViewAdapter extends BaseAdapter{

    ArrayList<String> names;
    ArrayList<Drawable> pictures;

    Context context;

    TextView friendName;
    CircleImageView friendPicture;

    public FriendListViewAdapter(Context context) {
        names = new ArrayList<String>();
        pictures = new ArrayList<Drawable>();
        this.context = context;
    }

    public void addFriendList(String friendName, Drawable friendPicture){
        names.add(friendName);
        pictures.add(friendPicture);
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.friendrow,viewGroup,false);
        }

        Typeface varela = Typeface.createFromAsset(context.getAssets(),"VarelaRound-Regular.ttf");

        friendName = (TextView) view.findViewById(R.id.friendName);
        friendPicture = (CircleImageView) view.findViewById(R.id.friendPicture);

        friendName.setText(names.get(i));
        friendPicture.setImageDrawable(pictures.get(i));

        friendName.setTypeface(varela);

        return view;
    }
}
