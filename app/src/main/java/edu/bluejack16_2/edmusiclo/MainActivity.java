package edu.bluejack16_2.edmusiclo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.icu.text.LocaleDisplayNames;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.bluejack16_2.edmusiclo.model.MusicCursor;
import edu.bluejack16_2.edmusiclo.model.Session;
import edu.bluejack16_2.edmusiclo.model.UserModel;
import edu.bluejack16_2.edmusiclo.state.ContextStateMusic;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    Drawable song, discover, timeline;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    String name;
    String email;
    TextView txtUserProfileEmail;
    public static TextView txtuserProfileName;

    ProgressDialog progress;

    UserModel user;

    Session session;



   public static void changeUserName(String name){
        txtuserProfileName.setText(name);
    }

    void initFirebase(){
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");

        session = new Session(getBaseContext());

        try {

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

            View header=navigationView.getHeaderView(0);
            txtUserProfileEmail = (TextView)header.findViewById(R.id.txtUserProfileEmail);
            txtuserProfileName= (TextView)header.findViewById(R.id.txtUserProfileName);


            email = firebaseAuth.getCurrentUser().getEmail();

            //Toast.makeText(this, firebaseAuth.getCurrentUser().getProviderId(), Toast.LENGTH_SHORT).show();

            Log.d("asdf", firebaseAuth.getCurrentUser().getProviders().get(0));


            if(firebaseAuth.getCurrentUser().getProviders().get(0).equalsIgnoreCase("password")){
                databaseReference.orderByChild("email").equalTo(email)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Log.d("asdf", "asd");
                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                                    name = childSnapshot.child("fullname").getValue().toString();
                                    user = childSnapshot.getValue(UserModel.class);
                                }
                                //Log.d("asdf", user.getFullname());

                                txtuserProfileName.setText(name);
                                session.setUser(user);
                                progress.cancel();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }else {
                name = firebaseAuth.getCurrentUser().getDisplayName();
                txtuserProfileName.setText(name);
                progress.cancel();
                user = new UserModel();
                user.setEmail(firebaseAuth.getCurrentUser().getEmail());
                user.setFullname(firebaseAuth.getCurrentUser().getDisplayName());
                session.setUser(user);
            }
            txtUserProfileEmail.setText(email);
        }catch (Exception e ){
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = new ProgressDialog(this);
        progress.setTitle("Loading...");

        progress.show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Typeface varela = Typeface.createFromAsset(getAssets(),"VarelaRound-Regular.ttf");

        //txtUserProfileEmail = (TextView) findViewById(R.id.txtUserProfileEmail);
        //txtUserProfileEmail.setTypeface(varela);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();

        song = getResources().getDrawable(R.drawable.song);
        discover = getResources().getDrawable(R.drawable.discover);
        timeline = getResources().getDrawable(R.drawable.timeline);
        song.setBounds(0,0,80,80);
        discover.setBounds(0,0,80,80);
        timeline.setBounds(0,0,80,80);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new SongFragment(),"Music");
        viewPagerAdapter.addFragment(new FilterFragment(),"Discover");
        viewPagerAdapter.addFragment(new TimeLineFragment(),"Timeline");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(song);
        tabLayout.getTabAt(1).setIcon(discover);
        tabLayout.getTabAt(2).setIcon(timeline);

        initFirebase();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_friend){
            try {
                Intent intent = new Intent(getApplicationContext(), FriendActivity.class);
                startActivity(intent);
            }catch (Exception e){
                Log.d("asdf", e.toString());
            }
        }else if(id == R.id.nav_floating_lyrics){
            Intent intent = new Intent(getApplicationContext(), FloatingLyricsActivity.class);
            //Intent intent = new Intent(getApplicationContext(), TestActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
