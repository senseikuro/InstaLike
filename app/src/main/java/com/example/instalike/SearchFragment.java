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

import com.example.instalike.db.User;
import com.example.instalike.db.UserActions;

import java.util.ArrayList;


public class SearchFragment extends Fragment{
    @Nullable
    private ImageView photo;
    private ImageView heart;
    private SearchAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<User> listUsers;
    private UserActions userActions;
    private View view;
    private SearchView editsearch;
    private int mCurrentUser;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);
        createList();
        buildRecycleView();
        mCurrentUser=getArguments().getInt("CURRENT_USER");
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
        Bundle bundle= new Bundle();
        bundle.putInt("USER_PROFIL",listUsers.get(position).getId());
        bundle.putInt("CURRENT_USER",mCurrentUser);
        selectedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();
    }

    public void createList(){
        userActions= new UserActions(getContext());
        listUsers =new ArrayList<User>();
        listUsers=userActions.getAllUsers();
    }
    public void buildRecycleView(){

        mRecyclerView=view.findViewById(R.id.ListSearchView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(getActivity());
        mAdapter = new SearchAdapter(listUsers);

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