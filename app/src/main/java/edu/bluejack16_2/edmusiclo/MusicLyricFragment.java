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

        tvMusicLyric = (TextView) view.findViewById(R.id.tvMusicLyric);

        RequestData rd = new RequestData();
        RequestData rd2 = new RequestData();

        JSONObject json = null;
        JSONObject json2 = null;
        String lyric="";
        int track_id = 0;
        int artist_id = 0;

        String songTitle = MusicCursor.getInstance().musiccursor.getString(6);
        String songArtist = MusicCursor.getInstance().musiccursor.getString(5);

        String songTitleAndArtist = "";
        String songTitleAndArtistEncoder = "";

        if(songArtist.contains("unknown")){
            songTitleAndArtist = songTitle;
        }else{
            songTitleAndArtist = songTitle+" "+songArtist;
        }

        try {
            songTitleAndArtistEncoder = URLEncoder.encode(songTitleAndArtist,"UTF-8").replace("+","%20");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            //json = rd.execute("http://api.musixmatch.com/ws/1.1/track.search?q_track="+songTitleEncoder+"&q_artist="+songArtistEncoder+"&apikey=d7c17cea95ff9386c4eadf423793085f").get().getJSONObject("message").getJSONObject("body").getJSONArray("track_list").getJSONObject(0).getJSONObject("track");
            json = rd.execute("http://api.musixmatch.com/ws/1.1/track.search?q="+songTitleAndArtistEncoder+"&apikey=d7c17cea95ff9386c4eadf423793085f").get().getJSONObject("message").getJSONObject("body").getJSONArray("track_list").getJSONObject(0).getJSONObject("track");
            track_id = json.getInt("track_id");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("asd",songTitleAndArtistEncoder);
//        Toast.makeText(getContext(), songTitleAndArtistEncoder, Toast.LENGTH_SHORT).show();

        try {
            json2 = rd2.execute("http://api.musixmatch.com/ws/1.1/track.lyrics.get?track_id="+track_id+"&apikey=d7c17cea95ff9386c4eadf423793085f").get().getJSONObject("message").getJSONObject("body").getJSONObject("lyrics");
            lyric = json2.getString("lyrics_body");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Toast.makeText(getContext(), lyric, Toast.LENGTH_SHORT).show();

        ListView lyricListView = (ListView) view.findViewById(R.id.lyricsListView);

        lyricListViewAdapter = new LyricsListViewAdapter(getContext());
        lyricListViewAdapter.addLyric(lyric);

        lyricListView.setAdapter(lyricListViewAdapter);

        return view;
    }

}
