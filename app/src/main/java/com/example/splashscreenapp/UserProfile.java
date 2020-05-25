package com.example.splashscreenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    TextView top_name,top_username;
    TextInputLayout below_name,below_phone,below_email,below_password;
    Button update;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Log.d("sattu", "loginUser: working");
        top_name=findViewById(R.id.fullname);
        top_username=findViewById(R.id.username);
        below_name=findViewById(R.id.below_name);
        below_phone=findViewById(R.id.below_phone);
        below_email=findViewById(R.id.below_email);
        below_password=findViewById(R.id.below_password);
        update=findViewById(R.id.update);
        imageButton=findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserProfile.this,Login.class);
                startActivity(intent);
            }
        });
        Log.d("sattu", "loginUser: working");
        showdata();
    }

    private void showdata() {
        Intent intent=getIntent();
        String user_username=intent.getStringExtra("username");
        String user_name=intent.getStringExtra("name");
        String user_email=intent.getStringExtra("email");
        String user_phone=intent.getStringExtra("phone");
        String user_password=intent.getStringExtra("password");
        top_name.setText(user_name);
        top_username.setText(user_username);
        below_name.getEditText().setText(user_name);
        below_phone.getEditText().setText(user_phone);
        below_email.getEditText().setText(user_email);
        below_password.getEditText().setText(user_password);

        Log.d("sattu", "loginUser: working");
    }
}
