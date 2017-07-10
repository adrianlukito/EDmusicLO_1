package edu.bluejack16_2.edmusiclo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    public MusicLyricFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_music_lyric, container, false);

        RequestData rd = new RequestData();
        RequestData rd2 = new RequestData();

        JSONObject json = null;
        JSONObject json2 = null;
        String lyric="";
        int track_id = 0;
        int artist_id = 0;

        String songTitle = MusicCursor.getInstance().musiccursor.getString(6);
        String songArtist = MusicCursor.getInstance().musiccursor.getString(5);
        String songTitleEncoder = "";
        String songArtistEncoder = "";

        try {
            songTitleEncoder = URLEncoder.encode(songTitle,"UTF-8").replace("+","%20");
            songArtistEncoder = URLEncoder.encode(songArtist,"UTF-8").replace("+","%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        try {
            //json = rd.execute("http://api.musixmatch.com/ws/1.1/track.search?q_track="+songTitleEncoder+"&q_artist="+songArtistEncoder+"&apikey=d7c17cea95ff9386c4eadf423793085f").get().getJSONObject("message").getJSONObject("body").getJSONArray("track_list").getJSONObject(0).getJSONObject("track");
            json = rd.execute("http://api.musixmatch.com/ws/1.1/track.search?q_track="+songTitleEncoder+"&q_artist="+songArtistEncoder+"&apikey=d7c17cea95ff9386c4eadf423793085f").get().getJSONObject("message").getJSONObject("body").getJSONArray("track_list").getJSONObject(0).getJSONObject("track");
            track_id = json.getInt("track_id");
            artist_id = json.getInt("artist_id");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(view.getContext(), songTitleEncoder +"    "+songArtistEncoder + " : " + track_id, Toast.LENGTH_SHORT).show();
        Log.d("asd",songTitleEncoder+" "+songArtistEncoder+" "+track_id+" "+MusicCursor.getInstance().musiccursor.getString(7)+" "+artist_id);
//        Toast.makeText(view.getContext(), track_id, Toast.LENGTH_SHORT).show();

//
//
//        Toast.makeText(getActivity(), MusicCursor.getInstance().musiccursor.getString(6), Toast.LENGTH_SHORT).show();
//
//        try {
//            json2 = rd2.execute("http://api.musixmatch.com/ws/1.1/track.lyrics.get?track_id=15953433&apikey=d7c17cea95ff9386c4eadf423793085f").get().getJSONObject("message").getJSONObject("body").getJSONObject("lyrics");
//            lyric = json.getString("lyrics_body");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Toast.makeText(getActivity(), lyric , Toast.LENGTH_SHORT).show();

        return view;
    }

}
