package edu.bluejack16_2.edmusiclo.state;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;

/**
 * Created by Asus on 6/23/2017.
 */

public abstract class StateMusicPlaying  {

    Cursor cursor = MusicCursor.getInstance().musiccursor;

    public Drawable icon;

    public  abstract Drawable getIcon(View view);

    public abstract  int onFinish();

    public abstract StateMusicPlaying next();

    public abstract  int nextMusic();

    public  abstract  int prevMusic();

}
