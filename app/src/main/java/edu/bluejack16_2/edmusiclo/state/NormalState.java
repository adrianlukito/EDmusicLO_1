package edu.bluejack16_2.edmusiclo.state;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.logging.LoggingPermission;

import edu.bluejack16_2.edmusiclo.R;

/**
 * Created by Asus on 6/23/2017.
 */

public class NormalState extends StateMusicPlaying {
    @Override
    public void doAction() {

    }

    @Override
    public StateMusicPlaying next() {
        return new LoopingState();
    }


    public Drawable getIcon(View view){
        return view.getResources().getDrawable(R.drawable.ic_normal);
    }

    public int nextMusic(){

        if(cursor.getPosition()+1 > cursor.getCount() -1){
            return 0;
        }
        return  cursor.getPosition()+1;
    }

    @Override
    public int prevMusic() {
        if(cursor.getPosition()-1 < 0){
            return  cursor.getCount()-1;
        }
        return cursor.getPosition()-1;
    }
}
