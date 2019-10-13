package com.example.instalike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;



public class HomeActivity extends AppCompatActivity {

    private int id_user;
    private Bundle mBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //Test BDD
        //Création d'une instance de ma classe LivresBDD





        /*HomeFragment fragment = new HomeFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragementTransaction= fragmentManager.beginTransaction();
        fragementTransaction.add(R.id.rvPosts,fragment);
        fragementTransaction.commit();*/

        //On vient récupérer l'ID de l'utilisateur
        id_user=getIntent().getExtras().getInt("ID_USER");
        //on va le passer à l'autre activity
        mBundle= new Bundle();
        mBundle.putInt("CURRENT_USER",id_user);
        mBundle.putInt("USER_PROFIL",id_user);

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


}
