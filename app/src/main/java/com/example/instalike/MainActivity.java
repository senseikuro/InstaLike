package com.example.instalike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instalike.db.Post;
import com.example.instalike.db.PostActions;
import com.example.instalike.db.User;
import com.example.instalike.db.UserActions;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView mWelcome;
    private EditText mNameInput, mPassword;
    private Button mConnexionButton;
    private Button mLoginButton;
    private Typeface typeWelcome;
    private boolean boolPseudo=false;
    private boolean boolPassword=false;
    private UserActions userActions;
    private User user;
    private Post post;
    private PostActions postAction;
    private String mPseudoConnexion, mPasswordConnexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userActions = new UserActions(getApplicationContext());

        mWelcome=(TextView) findViewById(R.id.activity_welcome);
        mNameInput=(EditText) findViewById(R.id.activity_main_name_input);
        mConnexionButton=(Button) findViewById(R.id.activity_main_connexion_btn);
        mLoginButton=(Button)findViewById(R.id.activity_main_login_btn);
        mPassword=(EditText)findViewById(R.id.activity_main_mdp);
        mConnexionButton.setEnabled(false);
        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() != 0)
                    boolPseudo=true;

                else
                    boolPseudo=false;

                mPseudoConnexion=s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {


                if(boolPassword==true && boolPseudo==true)
                    mConnexionButton.setEnabled(true);
                if(boolPassword==false || boolPseudo==false)
                    mConnexionButton.setEnabled(false);



            }

        });

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() != 0)
                    boolPassword=true;
                else
                    boolPassword=false;
                mPasswordConnexion=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(boolPassword && boolPseudo)
                    mConnexionButton.setEnabled(true);
                if(boolPassword==false || boolPseudo==false)
                    mConnexionButton.setEnabled(false);
            }

        });

        mConnexionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                userActions.open();
                User userFromBdd = userActions.getUserWithPseudeo(mPseudoConnexion);
                userActions.close();
               // postAction=new PostActions(getApplicationContext());

               // postAction.open();

                //Si un livre est retourné (donc si le livre à bien été ajouté à la BDD)
                if(userFromBdd != null){

                    if(userFromBdd.getPassword().equals(mPasswordConnexion)){
                        /*Date today = Calendar.getInstance().getTime();
                        post=new Post(userFromBdd.getId(),R.drawable.paysage2,"superbe voyage avec les frérots",today);
                        postAction.insertPost(post);*/
                        Bundle bundle= new Bundle();
                        bundle.putInt("ID_USER",userFromBdd.getId());
                        Intent homeActivity=new Intent(MainActivity.this,HomeActivity.class);
                        homeActivity.putExtras(bundle);
                        startActivity(homeActivity);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),"wrong pseudo or password",Toast.LENGTH_LONG);
                        toast.show();
                    }

                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),"wrong pseudo or password",Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        });
        mLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent inscriptionActivity= new Intent (MainActivity.this,FormulaireActivity.class);
                startActivity(inscriptionActivity);
            }
        });
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(MainActivity.this,"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }
}
