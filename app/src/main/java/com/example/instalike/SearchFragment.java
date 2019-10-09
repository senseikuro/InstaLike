package com.example.instalike;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class SearchFragment extends Fragment{
    @Nullable
    private ImageView photo;
    private ImageView heart;
    private SearchAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Post> Posts;
    private View view;
    private SearchView editsearch;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);
        createList();
        buildRecycleView();
        editsearch =view.findViewById(R.id.search);
        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        editsearch.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                          }
                                      }
        );
        return view;
    }

    public void changeItem(int position, String text){
        Fragment selectedFragment= null;
        selectedFragment=new ProfilFragement();
        System.out.println("je suis");
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();
    }

    public void createList(){
        Posts =new ArrayList<Post>();
        Posts.add(new Post("ichiban japan", "super voyage à tokyo",R.drawable.paysage2,"120"));
        Posts.add(new Post("VincentJouanne", "i love BJJ",R.drawable.paysage3,"110"));
        Posts.add(new Post("Florent Brassac", "t'as dead ça chacal",R.drawable.paysage4,"105"));
        Posts.add(new Post("PaullBoveyron", "Je suis une locomotive",R.drawable.paysage5,"23"));
    }
    public void buildRecycleView(){

        mRecyclerView=view.findViewById(R.id.ListSearchView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(getActivity());
        mAdapter = new SearchAdapter(Posts);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position,"clicked");
            }

        });
    }


}