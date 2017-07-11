package edu.bluejack16_2.edmusiclo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Adrian Lukito Lo on 11/07/2017.
 */

public class FloatingLyrics extends FloatingLyricsActivity/*Service*/{

    @Override
    void setUpWindow() {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 1.0f;
        params.dimAmount = 0f;
        getWindow().setAttributes(params);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int w = size.x;
        int h = size.y;

        if (h > w) {
            getWindow().setLayout((int) (w * .9), (int) (h * .7));
        } else {
            getWindow().setLayout((int) (w * .7), (int) (h * .8));
        }

//        Intent fullApp = new Intent(context,FloatingLyricsActivity.class);
//        fullApp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(fullApp);
    }

    //    WindowManager wm;
//    RelativeLayout rl;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
//
//        rl = new RelativeLayout(this);
//
//        RelativeLayout.LayoutParams rlParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
//        rl.setBackgroundColor(Color.rgb(128,128,128));
//        rl.setLayoutParams(rlParameters);
//
//
//        WindowManager.LayoutParams parameters = new WindowManager.LayoutParams(800,800,WindowManager.LayoutParams.TYPE_PHONE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
//        parameters.x = 0;
//        parameters.y = 0;
//        parameters.gravity = Gravity.CENTER;
//
//        wm.addView(rl,parameters);
//    }

}
