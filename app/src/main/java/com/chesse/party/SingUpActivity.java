package com.chesse.party;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingUpActivity extends AppCompatActivity {

    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPasswordField;

    private Button mRegisterBnt;


    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);


        mAuth = FirebaseAuth.getInstance();

        mDataBase = FirebaseDatabase.getInstance().getReference().child("Users");


        mProgress = new ProgressDialog(this);
        mNameField = (EditText)findViewById(R.id.input_name);
        mEmailField = (EditText)findViewById(R.id.input_email);
        mPasswordField = (EditText)findViewById(R.id.input_password);

        mRegisterBnt = (Button)findViewById(R.id.btn_signup);


        mRegisterBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startRegister();

            }
        });

    }

    private void startRegister() {

        final String name = mNameField.getText().toString().trim();
        String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();


        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){


            mProgress.setMessage("Criando...");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {



                    if (task.isSuccessful()){



                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = mDataBase.child(user_id);
                        current_user_db.child("name").setValue(name);
                        mProgress.dismiss();


                        Intent wellcome = new Intent(SingUpActivity.this,BemVindoActivit.class);
                        wellcome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(wellcome);
                    }

                }
            });

        }
    }


/*

    Metodo para sair da conta
    private void logout(){
        mAuth.signOut();
    }
*/
}

