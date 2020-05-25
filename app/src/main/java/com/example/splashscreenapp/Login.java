package com.example.splashscreenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button callsignup,go;
    ImageView imageView;
    TextView slogan;
    Window window;
    ProgressBar progressBar;
    TextInputLayout username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        callsignup=findViewById(R.id.callSignUp);
        imageView=findViewById(R.id.image);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        slogan=findViewById(R.id.logo_name);
        go=findViewById(R.id.go);
        progressBar=findViewById(R.id.progressbar);
        callsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


    }

    @Override
    protected void onResume() {
        progressBar.setVisibility(View.INVISIBLE);
        super.onResume();
    }

    private boolean ValidateUserName()
    {
        String val=username.getEditText().getText().toString();
        if(val.isEmpty())
        {
            username.setError("Field cannot be empty");
            return false;
        }
        else
        {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }

    }
    private boolean ValidatePassword()
    {
        String val=password.getEditText().getText().toString();
        if(val.isEmpty())
        {
            password.setError("Field cannot be empty");
            return false;
        }
        else
        {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
    public void loginUser()
    {
        if(!ValidateUserName()||!ValidatePassword())
        {
//            Log.d("sattu", "loginUser: working");
            return ;

        }
        else
        {
            isUser();
//            Log.d("sattu", "loginUser: working");
        }
    }

    private void isUser() {

        progressBar.setVisibility(View.VISIBLE);
        final String userEnterdusername=username.getEditText().getText().toString().trim();
        final String userEnterdpassword=password.getEditText().getText().toString().trim();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("user");

        // Check all the username in database that this user will exist
        Query checkuser=reference.orderByChild("username").equalTo(userEnterdusername);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //if your exist datasnapshot is not empty
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    username.setError(null);
                    username.setErrorEnabled(false);
//                    Log.d("sattu", "loginUser: working");
                    String passwordFromDB=dataSnapshot.child(userEnterdusername).child("password").getValue(String.class);
                    if(passwordFromDB.equals(userEnterdpassword))
                    {
//                        Log.d("sattu", "loginUser: working");
//                        password.setError(null);
//                        password.setErrorEnabled(false);
                        String nameFromDB=dataSnapshot.child(userEnterdusername).child("name").getValue(String.class);
                        String emailFromDB=dataSnapshot.child(userEnterdusername).child("email").getValue(String.class);
                        String phoneFromDB=dataSnapshot.child(userEnterdusername).child("phone").getValue(String.class);
                        String usernameFromDB=dataSnapshot.child(userEnterdusername).child("username").getValue(String.class);

                        Log.d("sattu", "loginUser: working");

                        Intent intent=new Intent(getApplicationContext(),UserProfile.class);
                        intent.putExtra("name",nameFromDB);
                        intent.putExtra("email",emailFromDB);
                        intent.putExtra("phone",phoneFromDB);
                        intent.putExtra("username",usernameFromDB);
                        intent.putExtra("password",passwordFromDB);
                        Log.d("sattu", "loginUser: working");
                        startActivity(intent);
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        password.setError("Wrong password");
                        password.requestFocus();
                    }
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    username.setError("No Such user exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
