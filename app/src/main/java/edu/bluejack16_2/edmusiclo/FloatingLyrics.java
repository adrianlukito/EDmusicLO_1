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
import android.widget.RelativeLayout;

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
        headerView.findViewById(R.id.btnClose).setOnClickListener(this);

        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        parameters = new WindowManager.LayoutParams(800,800,WindowManager.LayoutParams.TYPE_PHONE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        parameters.x = 0;
        parameters.y = 0;
        parameters.gravity = Gravity.CENTER;

        wm.addView(rootView,parameters);
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

                if (xDiff < 5 && yDiff < 5) {
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
            expandView.setVisibility(View.GONE);
        }else{
            expandView.setVisibility(View.VISIBLE);
        }
    }
}
