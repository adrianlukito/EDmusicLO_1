package edu.bluejack16_2.edmusiclo;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by Adrian Lukito Lo on 21/06/2017.
 */

public class SongListViewAdapter extends BaseAdapter implements Filterable{

    ArrayList<String> songTitles;

    ArrayList<String> cloneSongTitles;
    ArrayList<String> songArtists;
    ArrayList<String> songAlbums;

    ArrayList<Integer> originalPosition;
    int count = 0;

    Context context;

    public TextView tvSongTitle, tvSongArtist, tvSongAlbum;

    public SongListViewAdapter(Context context) {
        songTitles = new ArrayList<String>();
        songArtists = new ArrayList<String>();
        songAlbums = new ArrayList<String>();
        cloneSongTitles = new ArrayList<String>();
        originalPosition = new ArrayList<Integer>();
        this.context = context;
    }

    public void addSongList(String songTitle, String songArtist, String songAlbum){
        songTitles.add(songTitle);
        songArtists.add(songArtist);
        songAlbums.add(songAlbum);
        cloneSongTitles.add(songTitle);
        originalPosition.add(count);
        count++;
    }

    public SongListViewAdapter getThisItem(){
        return this;
    }

    @Override
    public int getCount() {
        return songTitles.size();
    }


    @Override
    public Object getItem(int i) {
        return songTitles.get(i);
    }

    public int getIndex(String title){
        return cloneSongTitles.indexOf(title);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.songrow,viewGroup,false);
        }

        Typeface varela = Typeface.createFromAsset(context.getAssets(),"VarelaRound-Regular.ttf");

        tvSongTitle = (TextView) view.findViewById(R.id.tvSongTitle);
        tvSongArtist = (TextView) view.findViewById(R.id.tvSongArtist);
        tvSongAlbum = (TextView) view.findViewById(R.id.tvSongAlbum);

        tvSongTitle.setText(songTitles.get(i));
        tvSongArtist.setText(songArtists.get(i));
        tvSongAlbum.setText(songAlbums.get(i));

        tvSongTitle.setTypeface(varela);
        tvSongArtist.setTypeface(varela);
        tvSongAlbum.setTypeface(varela);

        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results  = new FilterResults();
                ArrayList<String> FilteredArrayNames = new ArrayList<String>();

                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < cloneSongTitles.size(); i++) {
                    String title = cloneSongTitles.get(i);
                    if (title.toLowerCase().startsWith(constraint.toString()))  {
                        FilteredArrayNames.add(title);
                    }
                }
                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                songTitles = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
