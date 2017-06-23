package edu.bluejack16_2.edmusiclo.state;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;

import edu.bluejack16_2.edmusiclo.R;
import edu.bluejack16_2.edmusiclo.model.MusicCursor;

/**
 * Created by Asus on 6/23/2017.
 */

public class LoopingState extends StateMusicPlaying {


    public LoopingState() {
    }

    public Drawable getIcon(View view){
        return view.getResources().getDrawable(R.drawable.ic_looping);
    }

    @Override
    public void doAction() {

    }

    @Override
    public StateMusicPlaying next() {
        return new RandomState();
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
