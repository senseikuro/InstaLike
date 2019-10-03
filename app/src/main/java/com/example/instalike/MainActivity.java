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

public class MainActivity extends AppCompatActivity {
    private TextView mWelcome;
    private EditText mNameInput, mPassword;
    private Button mConnexionButton;
    private Button mLoginButton;
    private Typeface typeWelcome;
    private boolean boolPseudo=false;
    private boolean boolPassword=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


            }

            @Override
            public void afterTextChanged(Editable s) {

                System.out.println(mNameInput.getText().toString());
                System.out.println(mPassword.getText().toString());

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
                    boolPseudo=false;

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
                Intent homeActivity=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(homeActivity);
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
