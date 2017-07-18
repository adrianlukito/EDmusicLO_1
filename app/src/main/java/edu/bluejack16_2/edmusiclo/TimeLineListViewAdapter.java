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

/**
 * Created by Adrian Lukito Lo on 16/07/2017.
 */

public class TimeLineListViewAdapter extends BaseAdapter{

    ArrayList<Drawable> timeLineProfilePictures;
    ArrayList<String> timeLineProfileNames;
    ArrayList<String> timeLineCotents;

    TextView tvTimeLineProfileName, tvTimeLineContent;

    Context context;

    public TimeLineListViewAdapter(Context context) {
        timeLineProfilePictures = new ArrayList<Drawable>();
        timeLineProfileNames = new ArrayList<String>();
        timeLineCotents = new ArrayList<String>();
        this.context = context;
    }

    public void addTimeLineList(Drawable timeLineProfilePicture, String timeLineProfileName, String timeLineCotent){
        timeLineProfilePictures.add(timeLineProfilePicture);
        timeLineProfileNames.add(timeLineProfileName);
        timeLineCotents.add(timeLineCotent);
    }

    @Override
    public int getCount() {
        return timeLineProfileNames.size();
    }

    @Override
    public Object getItem(int i) {
        return timeLineProfileNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.time_line_row,viewGroup,false);

        Typeface varela = Typeface.createFromAsset(context.getAssets(),"VarelaRound-Regular.ttf");

        tvTimeLineProfileName = (TextView) view.findViewById(R.id.tvTimeLineProfileName);
        tvTimeLineContent = (TextView) view.findViewById(R.id.tvTimeLineContent);

        tvTimeLineProfileName.setText(timeLineProfileNames.get(i));
        tvTimeLineContent.setText(timeLineCotents.get(i));

        tvTimeLineProfileName.setTypeface(varela);
        tvTimeLineContent.setTypeface(varela);

        return view;
    }
}
