package com.SWEProject.bringwithyou.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.SWEProject.bringwithyou.R;
import com.SWEProject.bringwithyou.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText userMail,userPassword;
    private Button btnLogin;
    private ProgressBar loginProgress;
    private FirebaseAuth mAuth;
    private Intent HomeActivity;
    private ImageView loginPhoto;
    private TextView regText ;
    private Intent registerActivity ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userMail = findViewById(R.id.login_email);
        userPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_btn);
        loginProgress = findViewById(R.id.login_progress);
        loginPhoto = findViewById(R.id.login_photo);
        regText=findViewById(R.id.logText);

        mAuth = FirebaseAuth.getInstance();
        HomeActivity = new Intent(this, Home2.class);
        loginPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterActivity = new Intent(getApplicationContext(), Register.class);
                startActivity(RegisterActivity);
                finish();
            }
        });

        registerActivity = new Intent(this,Register.class);
        regText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registerActivity);
                finish();
            }
        });
        loginProgress.setVisibility(View.INVISIBLE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgress.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);

                final String mail = userMail.getText().toString();
                final String password = userPassword.getText().toString();

                if(mail.isEmpty() || password.isEmpty()){
                    showMessage("please Verify all Fields") ;}
                else {
                    signIn(mail,password);
                }


            }


        });


    }

    private void signIn(final String mail, final String password) {
        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    if(mAuth.getCurrentUser().isEmailVerified()){

                        //checksignIn(mail , password);
//resetpass();
                        loginProgress.setVisibility(View.INVISIBLE);
                        btnLogin.setVisibility(View.VISIBLE);
                        updateUI();
                    }
                    else{
                        showMessage("please Verify your email ") ;
                        loginProgress.setVisibility(View.INVISIBLE);
                        btnLogin.setVisibility(View.VISIBLE);
                    }

                }

                else {
                    showMessage(task.getException().getMessage()) ;
                    loginProgress.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void resetpass() {



    }

    private void checksignIn(final String mail, final String password) {


        FirebaseDatabase
                .getInstance()
                .getReference("users").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                // user.setKey();
                if((password.equals(dataSnapshot.child("password").getValue().toString()) )&&(mail.equals(dataSnapshot.child("email").getValue().toString())) )
               updateUI();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(), text , Toast.LENGTH_LONG).show();

    }

    private void updateUI() {
        startActivity(HomeActivity);
        finish();

    }
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
           // updateUI();
        }
    }
}
