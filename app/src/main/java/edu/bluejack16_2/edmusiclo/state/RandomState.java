package edu.bluejack16_2.edmusiclo.state;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import edu.bluejack16_2.edmusiclo.R;
import edu.bluejack16_2.edmusiclo.model.MusicCursor;

/**
 * Created by Asus on 6/23/2017.
 */

public class RandomState extends StateMusicPlaying {

    ArrayList<Integer> positions = new ArrayList<Integer>();

    int position;

    Random random = new Random();

    int swapRandom(int max, int min){
       return  random.nextInt(max-min+1)+min;
    }

    public  RandomState(){
        for(int i = 0 ; i < MusicCursor.getInstance().musiccursor.getCount(); i++){
            positions.add(i);
            Collections.swap(positions, i, swapRandom(i,0));
        }

        for(int i = 0; i < positions.size();i++){
            Log.d("tampilNomor:" , positions.get(i)+"");
        }
    }
    @Override
    public void doAction() {

    }
    @Override
    public StateMusicPlaying next() {
        return new NormalState();
    }
    @Override
    public int nextMusic() {

        int nextPlay = positions.indexOf((Integer)MusicCursor.getInstance().musiccursor.getPosition())+1;
        Log.d("nextplay : ", nextPlay+"");
        if(nextPlay > positions.size()-1){
            nextPlay = positions.get(0);
        }
        return positions.get(nextPlay);
    }

    public Drawable getIcon(View view){
        return view.getResources().getDrawable(R.drawable.ic_random);
    }

    @Override
    public int prevMusic() {
        int prevPlay = positions.indexOf((Integer)MusicCursor.getInstance().musiccursor.getPosition())-1;
        if(prevPlay < 0){
            prevPlay = positions.get(positions.size()-1);
        }
        return positions.get(prevPlay);

    }
}
