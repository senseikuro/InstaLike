package com.example.instalike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.instalike.db.Notify;
import com.example.instalike.db.NotifyActions;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private int id_user;
    private ImageView mNotification;
    private Bundle mBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        id_user=getIntent().getExtras().getInt("ID_USER");
        //on va le passer à l'autre activity
        mBundle= new Bundle();
        mBundle.putInt("CURRENT_USER",id_user);
        mBundle.putInt("USER_PROFIL",id_user);


        mNotification=findViewById(R.id.activity_main_notification);

        NotifyActions notifyActions= new NotifyActions(getApplicationContext());

        if (notifyActions.isNotify(id_user)){
            mNotification.setImageResource(R.drawable.ic_notifications_active_black_24dp);
        }
        else{
            mNotification.setImageResource(R.drawable.ic_notifications_black_24dp);
        }
        mNotification.setOnClickListener(this);

        BottomNavigationView bottomNav=findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Fragment selectedFragment= null;
        selectedFragment=new HomeFragment();
        selectedFragment.setArguments(mBundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts, selectedFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment= null;
            switch(menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment=new HomeFragment();
                    break;
                case R.id.nav_favorites:
                    selectedFragment=new FavoriteFragment();
                    break;
                case R.id.nav_search:
                    selectedFragment=new SearchFragment();
                    break;

                case R.id.nav_profile:
                    selectedFragment=new ProfilFragement();
                    break;
                case R.id.nav_publish_post:
                    selectedFragment=new PublishPicsFragment();
            }
            selectedFragment.setArguments(mBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                    selectedFragment).commit();
            return true;
        }
    };


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.activity_main_notification:
                Fragment selectedFragment= null;
                selectedFragment=new NotificationFragment();
                selectedFragment.setArguments(mBundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                        selectedFragment).commit();

                break;
        }
    }
}
