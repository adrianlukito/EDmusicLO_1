package edu.bluejack16_2.edmusiclo;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimeLineFragment extends Fragment {

    Drawable bruno, bts, bigbang, gfriend, apink, got7, suju, suju2,gfriend2, ikon, ikon2, wjsn, snsd, snsd2;

    public TimeLineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_time_line,container,false);

        ListView timeLineListView = (ListView) view.findViewById(R.id.timelineListView);

        final TimeLineListViewAdapter timeLineListViewAdapter = new TimeLineListViewAdapter(getContext());

        bruno = getResources().getDrawable(R.drawable.bruno_mars);
//        bts = getResources().getDrawable(R.drawable.wings);
//        bigbang = getResources().getDrawable(R.drawable.bigbang);
//        gfriend = getResources().getDrawable(R.drawable.awakening);
//        apink = getResources().getDrawable(R.drawable.apink);
//        got7 = getResources().getDrawable(R.drawable.just_right);
//        gfriend2 = getResources().getDrawable(R.drawable.lol);
//        suju = getResources().getDrawable(R.drawable.mrsimple);
//        suju2 = getResources().getDrawable(R.drawable.bonamana);
//        ikon = getResources().getDrawable(R.drawable.ikon);
//        ikon2 = getResources().getDrawable(R.drawable.newkids);
//        wjsn = getResources().getDrawable(R.drawable.secret);
//        snsd = getResources().getDrawable(R.drawable.the_boys);
//        snsd2 = getResources().getDrawable(R.drawable.oh);

        timeLineListViewAdapter.addTimeLineList(bruno,"Adrian Lukito Lo","Now Listening to Uptown Funk");
        timeLineListViewAdapter.addTimeLineList(bruno,"Adrian Lukito Lo","Now Listening to Uptown Funk");
        timeLineListViewAdapter.addTimeLineList(bruno,"Adrian Lukito Lo","Now Listening to Uptown Funk");
        timeLineListViewAdapter.addTimeLineList(bruno,"Adrian Lukito Lo","Now Listening to Uptown Funk");
        timeLineListViewAdapter.addTimeLineList(bruno,"Adrian Lukito Lo","Now Listening to Uptown Funk");
        timeLineListViewAdapter.addTimeLineList(bruno,"Adrian Lukito Lo","Now Listening to Uptown Funk");
        timeLineListViewAdapter.addTimeLineList(bruno,"Adrian Lukito Lo","Now Listening to Uptown Funk");
        timeLineListViewAdapter.addTimeLineList(bruno,"Adrian Lukito Lo","Now Listening to Uptown Funk");
        timeLineListViewAdapter.addTimeLineList(bruno,"Adrian Lukito Lo","Now Listening to Uptown Funk");
        timeLineListViewAdapter.addTimeLineList(bruno,"Adrian Lukito Lo","Now Listening to Uptown Funk");

        timeLineListView.setAdapter(timeLineListViewAdapter);

        return view;
    }

}
