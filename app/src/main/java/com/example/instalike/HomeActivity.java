package com.example.instalike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.instalike.db.User;
import com.example.instalike.db.UserActions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.*;
import android.widget.Toast;

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
        UserActions userActions = new UserActions(this);

        //Création d'un livre
        Date today = Calendar.getInstance().getTime();
        User user = new User("JOUANNE", "Vincent", "jouanne.vincent@gmail.com" , "123456", "Latruhm", today);

        //On ouvre la base de données pour écrire dedans
        userActions.open();
        //On insère le livre que l'on vient de créer
        userActions.insertUser(user);

        //Pour vérifier que l'on a bien créé notre livre dans la BDD
        //on extrait le livre de la BDD grâce au titre du livre que l'on a créé précédemment
        User userFromBdd = userActions.getUserWithPseudeo(user.getPseudeo());
        //Si un livre est retourné (donc si le livre à bien été ajouté à la BDD)
        if(userFromBdd != null){
            //On affiche les infos du livre dans un Toast
            Toast.makeText(this, userFromBdd.toString(), Toast.LENGTH_LONG).show();
            //On modifie le titre du livre
            userFromBdd.setPseudeo("J'ai modifié le pseudeo du user");
            //Puis on met à jour la BDD
            userActions.updateUser(userFromBdd.getId(), userFromBdd);
        }
        userActions.close();





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
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                    selectedFragment).commit();
            return true;
        }
    };


}
