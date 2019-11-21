package com.example.instalike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.instalike.db.NotifyActions;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private int id_user;
    private ImageView mNotification,mParamtre;
    private Bundle mBundle;
    private boolean isActivity;
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
        mParamtre=findViewById(R.id.activity_home_parametre);
        NotifyActions notifyActions= new NotifyActions(getApplicationContext());

        if (notifyActions.isNotify(id_user)){
            mNotification.setImageResource(R.drawable.ic_notifications_active_black_24dp);
        }
        else{
            mNotification.setImageResource(R.drawable.ic_notifications_black_24dp);
        }
        mNotification.setOnClickListener(this);


        mParamtre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openContextMenu(v);
            }
        });
        registerForContextMenu(mParamtre);

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
                    finish();
                    startActivity(getIntent());
                    return true;
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

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(contextMenu,view,menuInfo);
            /*contextMenu.add(Menu.NONE,MENU_SUPPRIMER,1,"supprimer le post");
            contextMenu.add(Menu.NONE,MENU_EDIT,1,"Editer le post");*/
        getMenuInflater().inflate(R.menu.menu_parameter,contextMenu);

    }

    public boolean onContextItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_parameter_log_out:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
    public void logout(){
        AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                .setTitle("do you want to log out?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplication(),MainActivity.class);
                    startActivity(intent);


                    }
                })
                .setNegativeButton("Cancel",null)
                .create();
        dialog.show();
    }
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
            case R.id.activity_home_parametre:

        }
    }
}
