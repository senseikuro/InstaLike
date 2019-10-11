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
    private String mPseudoConnexion, mPasswordConnexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userActions = new UserActions(this);

        //Création d'un livre
        //Date today = Calendar.getInstance().getTime();
        //user = new User("JOUANNE", "Vincent", "jouanne.vincent@gmail.com" , "123456", "Latruhm", today);

        //On ouvre la base de données pour écrire dedans
        //On insère le livre que l'on vient de créer

        //Pour vérifier que l'on a bien créé notre livre dans la BDD
        //on extrait le livre de la BDD grâce au titre du livre que l'on a créé précédemment
        /*User userFromBdd = userActions.getUserWithPseudeo("f");
        //Si un livre est retourné (donc si le livre à bien été ajouté à la BDD)
        if(userFromBdd != null){
            //On affiche les infos du livre dans un Toast
            //Toast.makeText(this, userFromBdd.toString(), Toast.LENGTH_LONG).show();
            //On modifie le titre du livre
            userFromBdd.setPseudeo("J'ai modifié le pseudeo du user");
            //Puis on met à jour la BDD
            userActions.updateUser(userFromBdd.getId(), userFromBdd);
        }
        userActions.close();*/







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
                //Si un livre est retourné (donc si le livre à bien été ajouté à la BDD)
                if(userFromBdd != null){
                    System.out.println(userFromBdd.getPassword());
                    System.out.println(mPasswordConnexion);
                    System.out.println(userFromBdd.getPassword()==mPasswordConnexion);
                    if(userFromBdd.getPassword().equals(mPasswordConnexion)){
                        Intent homeActivity=new Intent(MainActivity.this,HomeActivity.class);
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
                userActions.close();
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
}
