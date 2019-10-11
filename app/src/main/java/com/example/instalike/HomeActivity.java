package com.example.instalike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {
    private ImageView photo;
    private ImageView heart;
    private PostsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Post> Posts;

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
        BottomNavigationView bottomNav=findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts, new HomeFragment()).commit();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                    selectedFragment).commit();
            return true;
        }
    };


}
