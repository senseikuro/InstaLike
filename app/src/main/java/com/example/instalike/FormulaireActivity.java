package com.example.instalike;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instalike.db.User;
import com.example.instalike.db.UserActions;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

public class FormulaireActivity extends AppCompatActivity {


    private EditText mPseudo,mEmail,mMdp,mConfMdp,mName,mSurname;
    private Button mConnexionButton;
    private boolean pseudo=false;
    private boolean email=false;
    private boolean mdp=false;
    private boolean mdpConf=false;
    private boolean name=false;
    private boolean surname=false;
    private UserActions userActions;
    protected void toastResgister(boolean valid, String texte){
        if (valid){
            Context contexte=getApplicationContext();
            //CharSequence text="Create account";
            int duration= Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(contexte,texte,duration);
            toast.show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);

        mPseudo=findViewById(R.id.activity_formulare_speudo);
        mEmail=findViewById(R.id.activity_formulare_email);
        mMdp=findViewById(R.id.activity_formulare_mdp);
        mConfMdp=findViewById(R.id.activity_formulare_confirmation_mdp);
        mName=findViewById(R.id.activity_formulare_name);
        mSurname=findViewById(R.id.activity_formulare_surname);
        mConnexionButton=findViewById(R.id.activity_formulare_btn);

        mConnexionButton.setEnabled(false);

        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() != 0)
                    name=true;
                else
                    name=false;
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mdpConf==true && mdp==true && pseudo==true && email==true && name==true && surname==true)
                    mConnexionButton.setEnabled(true);
                if(mdpConf==false || mdp==false || pseudo==false || email==false || name==false || surname==false)
                    mConnexionButton.setEnabled(false);
            }

        });


        mSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() != 0)
                    surname=true;
                else
                    surname=false;
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mdpConf==true && mdp==true && pseudo==true && email==true && name==true && surname==true)
                    mConnexionButton.setEnabled(true);
                if(mdpConf==false || mdp==false || pseudo==false || email==false || name==false || surname==false)
                    mConnexionButton.setEnabled(false);
            }

        });


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

                if (mdpConf==true && mdp==true && pseudo==true && email==true && name==true && surname==true)
                    mConnexionButton.setEnabled(true);
                if(mdpConf==false || mdp==false || pseudo==false || email==false || name==false || surname==false)
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


                if (mdpConf==true && mdp==true && pseudo==true && email==true && name==true && surname==true)
                    mConnexionButton.setEnabled(true);
                if(mdpConf==false || mdp==false || pseudo==false || email==false || name==false || surname==false)
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

                if (mdpConf==true && mdp==true && pseudo==true && email==true && name==true && surname==true)
                    mConnexionButton.setEnabled(true);
                if(mdpConf==false || mdp==false || pseudo==false || email==false || name==false || surname==false)
                    mConnexionButton.setEnabled(false);       }

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

                if (mdpConf==true && mdp==true && pseudo==true && email==true && name==true && surname==true)
                    mConnexionButton.setEnabled(true);
                if(mdpConf==false || mdp==false || pseudo==false || email==false || name==false || surname==false)
                    mConnexionButton.setEnabled(false);

            }

        });


        mConnexionButton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v){


                if (!mMdp.getText().toString().equals(mConfMdp.getText().toString())){
                    toastResgister(false,"Wrong password");
                    mConfMdp.setError("not the same password");
                }
                else{

                    if(mEmail.getText().toString().indexOf('@')!= -1){
                        userActions = new UserActions(getApplicationContext());

                        userActions.open();
                        User tempuser=userActions.getUserWithPseudeo(mPseudo.getText().toString());
                        //System.out.println(tempuser.getId());
                        int id=-1;

                        try {
                            id=tempuser.getId();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                            if (id>0){
                                toastResgister(true,"Username already exist");
                            }
                            else{
                                toastResgister(true,"You create your account");
                                Date today = Calendar.getInstance().getTime();

                                Drawable imageDefault= getDrawable(R.drawable.user); // the drawable (Captain Obvious, to the rescue!!!)
                                Bitmap bitmap = ((BitmapDrawable)imageDefault).getBitmap();
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                byte[] bitmapdata = stream.toByteArray();
                                User newUser=new User(mName.getText().toString(),mSurname.getText().toString(),mEmail.getText().toString(),mMdp.getText().toString(), mPseudo.getText().toString(),bitmapdata, null, today.toString());

                                userActions.insertUser(newUser);
                                userActions.close();

                                Intent homeActivity=new Intent(FormulaireActivity.this,HomeActivity.class);
                                startActivity(homeActivity);
                            }


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
