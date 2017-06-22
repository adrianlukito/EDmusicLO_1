package edu.bluejack16_2.edmusiclo;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class FriendActivity extends AppCompatActivity {

    Drawable pic1, pic2, pic3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        ListView friendListView = (ListView) findViewById(R.id.friendListView);
        final FriendListViewAdapter friendListViewAdapter = new FriendListViewAdapter(getApplicationContext());

        pic1 = getResources().getDrawable(R.drawable.logo16_2v2);
        pic2 = getResources().getDrawable(R.drawable.nav_drawer_wallpaper2);
        pic3 = getResources().getDrawable(R.drawable.toolbar_wallpaper);

        friendListViewAdapter.addFriendList("Adrian Lukito Lo",pic1);
        friendListViewAdapter.addFriendList("Tedy Junaidi",pic1);
        friendListViewAdapter.addFriendList("Albert Zhuang",pic1);
        friendListViewAdapter.addFriendList("Alven Gemini Julio",pic1);
        friendListViewAdapter.addFriendList("Bagas Prakoso",pic1);
        friendListViewAdapter.addFriendList("Christian Adianto",pic1);
        friendListViewAdapter.addFriendList("Evy Maria",pic1);
        friendListViewAdapter.addFriendList("Giovan Saputra",pic1);
        friendListViewAdapter.addFriendList("Jordan Leonardi",pic1);
        friendListViewAdapter.addFriendList("Kelvin Asclepius Minor",pic1);
        friendListViewAdapter.addFriendList("Luciana Dian Santami",pic1);
        friendListViewAdapter.addFriendList("Misita Pontiasa",pic1);
        friendListViewAdapter.addFriendList("Mario Viegash",pic1);
        friendListViewAdapter.addFriendList("Philip Andreas Nugraha",pic1);
        friendListViewAdapter.addFriendList("Peter Hartawan Suherman",pic1);
        friendListViewAdapter.addFriendList("Renaldi",pic1);
        friendListViewAdapter.addFriendList("Kevin Surya Wahyudi",pic1);
        friendListViewAdapter.addFriendList("Thomas Asril",pic1);
        friendListViewAdapter.addFriendList("Wimpi Jonathan",pic1);

        friendListView.setAdapter(friendListViewAdapter);
    }
}
