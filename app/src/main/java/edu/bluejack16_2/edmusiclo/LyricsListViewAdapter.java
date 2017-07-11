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
 * Created by Adrian Lukito Lo on 11/07/2017.
 */

public class LyricsListViewAdapter extends BaseAdapter{

    ArrayList<String> lyrics;

    Context context;

    TextView tvMusicLyric;

    public LyricsListViewAdapter(Context context) {
        lyrics = new ArrayList<String>();
        this.context = context;
    }

    public void addLyric(String lyric){
        lyrics.add(lyric);
    }

    @Override
    public int getCount() {
        return lyrics.size();
    }

    @Override
    public Object getItem(int i) {
        return lyrics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.lyrics_row,viewGroup,false);

        Typeface varela = Typeface.createFromAsset(context.getAssets(),"VarelaRound-Regular.ttf");

        tvMusicLyric = (TextView) view.findViewById(R.id.tvMusicLyric);
        tvMusicLyric.setText(lyrics.get(i));
        tvMusicLyric.setTypeface(varela);

        return view;
    }
}
