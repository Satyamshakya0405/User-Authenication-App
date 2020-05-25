package com.example.splashscreenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    Window window;
    ImageView imageview;
    TextView logo, tagline;
    TextInputLayout name, username, password, phone, email;
    Button go, already;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        imageview = findViewById(R.id.singup_image);
        logo = findViewById(R.id.signup_logo);
        tagline = findViewById(R.id.signup_tagline);
        name = findViewById(R.id.signup_fullname);
        username = findViewById(R.id.signup_username);
        password = findViewById(R.id.signup_password);
        phone = findViewById(R.id.signup_Phone);
        email = findViewById(R.id.signup_email);
        go = findViewById(R.id.signup_go);
        already = findViewById(R.id.signup_alreadybutton);

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this,Login.class);
                startActivity(intent);
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateName()||!validateUserName()||!validateEmail()||!validatePhone()||!validatePassword())
                {
                    return ;
                }
                //get all the values
                String Name=name.getEditText().getText().toString();

                String Username=username.getEditText().getText().toString();
                String Password=password.getEditText().getText().toString();
                String Phone=phone.getEditText().getText().toString();
                String Email=email.getEditText().getText().toString();
                UserHelperClass helperClass=new UserHelperClass(Name,Username,Email,Phone,Password);
                FirebaseDatabase.getInstance().getReference().child("user").child(Username).setValue(helperClass);
                Toast.makeText(SignUp.this,"data added",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private Boolean validateName()
    {
        String val=name.getEditText().getText().toString();
        if(val.isEmpty())
        {
            name.setError("Field cannot be empty");
            return false;
        }
        else
        {
            name.setError(null);
            name.setErrorEnabled(true);
            return true;
        }
    }
    private Boolean validateUserName()
    {
        String val=username.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if(val.isEmpty())
        {
            username.setError("Field cannot be empty");
            return false;
        }
        else if(val.length()<=4)
        {
            username.setError("Too short");
            return false;
        }
        else if(val.length()>=15)
        {
            username.setError("Username Too Long");
            return false;
        }
        else if(!val.matches(noWhiteSpace))
        {
            username.setError("White spaces are not allowed");
            return false;
        }
        else
        {
            username.setError(null);
            username.setErrorEnabled(true);
            return true;
        }
    }
    private Boolean validatePhone()
    {
        String val=email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty())
        {
            email.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(emailPattern))
        {
            email.setError("Invalid Email");
            return false;
        }
        else
        {
            email.setError(null);
            email.setErrorEnabled(true);
            return true;
        }
    }
    private Boolean validateEmail() {
        String val = phone.getEditText().getText().toString();

        if (val.isEmpty()) {
            phone.setError("Field cannot be empty");
            return false;
        } else {
            phone.setError(null);
            phone.setErrorEnabled(true);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            password.setError("Password is too weak");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
//    public void registeruser(View view) {
//
////        if(!validateName()||!validateUserName()||!validateEmail()||!validatePhone()||!validatePassword())
////        {
////            return ;
////        }
//        String Name = name.getEditText().getText().toString();
//        String UserName = username.getEditText().getText().toString();
//        String Password = password.getEditText().getText().toString();
//        String Phone = phone.getEditText().getText().toString();
//        String Email = email.getEditText().getText().toString();
//        UserHelperClass helperClass=new UserHelperClass(Name,UserName,Email,Phone,Password);
//        FirebaseDatabase.getInstance().getReference().child("user").child(Phone).setValue(helperClass);
//    }
}
