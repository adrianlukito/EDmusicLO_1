package edu.bluejack16_2.edmusiclo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicLyricFragment extends Fragment {

    TextView tvMusicLyric;

    LyricsListViewAdapter lyricListViewAdapter;

    public MusicLyricFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_music_lyric, container, false);
        try {
            tvMusicLyric = (TextView) view.findViewById(R.id.tvMusicLyric);

            RequestData rd = new RequestData();

            JSONArray json = null;
            String lyric = "";

            String songTitle = MusicCursor.getInstance().musiccursor.getString(6);
            String songArtist = MusicCursor.getInstance().musiccursor.getString(5);

            String songTitleAndArtist = "";
            String songTitleAndArtistEncoder = "";

            if (songArtist.contains("unknown")) {
                songTitleAndArtist = songTitle;
            } else {
                songTitleAndArtist = songTitle + " " + songArtist;
            }

            try {
                songTitleAndArtistEncoder = URLEncoder.encode(songTitleAndArtist, "UTF-8").replace("+", "%20");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                json = rd.execute("https://code.aashari.id/api/musics/lyric/keyword?keyword=" + songTitleAndArtistEncoder).get().getJSONArray("lyric");

                for (int i = 0; i < json.length(); i++) {
                    lyric = lyric + "\n" + json.getString(i);
                    //Log.d("lyric",json.getString(i));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ListView lyricListView = (ListView) view.findViewById(R.id.lyricsListView);

            lyricListViewAdapter = new LyricsListViewAdapter(getContext());
            lyricListViewAdapter.addLyric(lyric);

            lyricListView.setAdapter(lyricListViewAdapter);
        }catch (Exception e){
            Log.d("testa", e.toString());
        }
        return view;
    }

}
