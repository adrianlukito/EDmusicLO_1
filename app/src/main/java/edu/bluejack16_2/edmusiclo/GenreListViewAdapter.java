package edu.bluejack16_2.edmusiclo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.method.Touch;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Adrian Lukito Lo on 10/07/2017.
 */

public class GenreListViewAdapter extends BaseAdapter{

    ArrayList<String> genres;

    Context context;

    public TextView tvSongTitle, tvSongArtist, tvSongAlbum;

    public GenreListViewAdapter(Context context) {
        genres = new ArrayList<String>();
        this.context = context;
    }

    public void addGenre(String genre){
        genres.add(genre);
    }

    @Override
    public int getCount() {
        return genres.size();
    }

    @Override
    public Object getItem(int i) {
        return genres.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.genre_row,viewGroup,false);
        }

        Typeface varela = Typeface.createFromAsset(context.getAssets(),"VarelaRound-Regular.ttf");

        final Button btnGenres = (Button) view.findViewById(R.id.btnGenres);
        btnGenres.setText(genres.get(i));
        btnGenres.setTypeface(varela);
        btnGenres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), GenreDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("genreName",btnGenres.getText());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
