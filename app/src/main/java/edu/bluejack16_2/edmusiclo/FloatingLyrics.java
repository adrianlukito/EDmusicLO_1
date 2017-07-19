package edu.bluejack16_2.edmusiclo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;

/**
 * Created by Adrian Lukito Lo on 11/07/2017.
 */

public class FloatingLyrics extends Service implements View.OnClickListener, View.OnTouchListener{

    WindowManager wm;
    WindowManager.LayoutParams parameters;
    View rootView;
    View headerView;
    View expandView;
    int initX, initY;
    float touchX, touchY;
    LyricsListViewAdapter lyricListViewAdapter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        rootView = LayoutInflater.from(this).inflate(R.layout.floating_lyrics,null);

        headerView = rootView.findViewById(R.id.header);
        expandView = rootView.findViewById(R.id.expanded_view);

        headerView.findViewById(R.id.headApp).setOnClickListener(this);
        headerView.findViewById(R.id.headApp).setOnTouchListener(this);
        expandView.findViewById(R.id.btnClose).setOnClickListener(this);

        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        parameters = new WindowManager.LayoutParams(900,1500,WindowManager.LayoutParams.TYPE_PHONE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        parameters.x = 0;
        parameters.y = 0;
        parameters.gravity = Gravity.CENTER;

        wm.addView(rootView,parameters);

        RequestData rd = new RequestData();

        JSONArray json = null;
        String lyric="";

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
            json = rd.execute("https://code.aashari.id/api/musics/lyric/keyword?keyword="+songTitleAndArtistEncoder).get().getJSONArray("lyric");

            for(int i=0;i<json.length();i++){
                lyric = lyric+"\n"+json.getString(i);
                //Log.d("lyric",json.getString(i));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListView lyricListView = (ListView) expandView.findViewById(R.id.floatingLyricView);

        lyricListViewAdapter = new LyricsListViewAdapter(getApplicationContext());
        lyricListViewAdapter.addLyric(lyric);

        lyricListView.setAdapter(lyricListViewAdapter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if(rootView != null){
            wm.removeView(rootView);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnClose){
            stopSelf();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                initX = parameters.x;
                initY = parameters.y;
                touchX = motionEvent.getRawX();
                touchY = motionEvent.getRawY();
                return true;
            case MotionEvent.ACTION_MOVE:
                parameters.x = initX + (int)(motionEvent.getRawX() - touchX);
                parameters.y = initY + (int)(motionEvent.getRawY() - touchY);
                wm.updateViewLayout(rootView,parameters);
                return true;
            case MotionEvent.ACTION_UP:
                int xDiff = (int)(motionEvent.getRawX() - touchX);
                int yDiff = (int)(motionEvent.getRawY() - touchY);

                if (xDiff < 1 && yDiff < 1) {
                    if (view.getId() == R.id.headApp) {
                        initExpand();
                    }
                }
                break;
        }
        return false;
    }

    private boolean isExpanded() {
        return expandView.getVisibility() == View.VISIBLE ? true : false;
    }

    void initExpand(){
        if(isExpanded()){
            parameters.width = 200;
            parameters.height = 200;
            parameters.gravity = Gravity.LEFT;
            wm.updateViewLayout(rootView,parameters);
            expandView.setVisibility(View.GONE);
        }else{
            parameters.width = 900;
            parameters.height = 1500;
            parameters.gravity = Gravity.CENTER;
            wm.updateViewLayout(rootView,parameters);
            expandView.setVisibility(View.VISIBLE);
        }
    }
}
