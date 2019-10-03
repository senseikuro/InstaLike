package com.example.instalike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormulaireActivity extends AppCompatActivity {


    private EditText mPseudo,mEmail,mMdp,mConfMdp;
    private Button mConnexionButton;
    private boolean pseudo=false;
    private boolean email=false;
    private boolean mdp=false;
    private boolean mdpConf=false;

    protected void toastResgister(boolean valid,String error){
        if (valid){
            Context contexte=getApplicationContext();
            CharSequence text="Create account";
            int duration= Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(contexte,text,duration);
            toast.show();
        }
        else{
            Context contexte=getApplicationContext();
            CharSequence text=error;
            int duration= Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(contexte,text,duration);
            toast.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);

        mPseudo=(EditText)findViewById(R.id.activity_formulare_speudo);
        mEmail=(EditText)findViewById(R.id.activity_formulare_email);
        mMdp=(EditText)findViewById(R.id.activity_formulare_mdp);
        mConfMdp=(EditText)findViewById(R.id.activity_formulare_confirmation_mdp);
        mConnexionButton=(Button)findViewById(R.id.activity_formulare_btn);

        mConnexionButton.setEnabled(false);

        mPseudo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() != 0)
                    pseudo=true;
                else
                    pseudo=false;
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mdpConf==true && mdp==true && pseudo==true && email==true)
                    mConnexionButton.setEnabled(true);
                if(mdpConf==false || mdp==false || pseudo==false || email==false)
                    mConnexionButton.setEnabled(false);
            }

        });

        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() != 0)
                    email=true;
                else
                    email=false;
            }

            @Override
            public void afterTextChanged(Editable s) {


                if (mdpConf==true && mdp==true && pseudo==true && email==true)
                    mConnexionButton.setEnabled(true);
                if(mdpConf==false || mdp==false || pseudo==false || email==false)
                    mConnexionButton.setEnabled(false);
            }

        });

        mMdp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() != 0)
                    mdp=true;
                else
                    mdp=false;

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mdpConf==true && mdp==true && pseudo==true && email==true)
                    mConnexionButton.setEnabled(true);
                if(mdpConf==false || mdp==false || pseudo==false || email==false)
                    mConnexionButton.setEnabled(false);            }

        });

        mConfMdp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() != 0)
                    mdpConf=true;
                else
                    mdpConf=false;
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mdpConf==true && mdp==true && pseudo==true && email==true)
                    mConnexionButton.setEnabled(true);
                if(mdpConf==false || mdp==false || pseudo==false || email==false)
                    mConnexionButton.setEnabled(false);

            }

        });


        mConnexionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                if (!mMdp.getText().toString().equals(mConfMdp.getText().toString())){
                    toastResgister(false,"Wrong password");
                    mConfMdp.setError("not the same password");
                }
                else{

                    if(mEmail.getText().toString().indexOf('@')!= -1){
                        toastResgister(true,"no error");
                        Intent homeActivity=new Intent(FormulaireActivity.this,HomeActivity.class);
                        startActivity(homeActivity);
                    }
                    else{
                        toastResgister(false,"Wrong emil address");
                        mEmail.setError("@ missing");

                    }
                }

            }

        });
    }
}
