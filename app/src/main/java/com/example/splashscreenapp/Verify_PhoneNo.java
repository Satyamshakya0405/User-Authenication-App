package com.example.splashscreenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Verify_PhoneNo extends AppCompatActivity {
        String verificationCodeBysystem;
    Button verify;
    EditText OTP;
    ProgressBar proggbar;
    String name,password,email,username,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify__phone_no);
        verify=findViewById(R.id.verify_btn);
        OTP=findViewById(R.id.verification_code_entered_by_user);
        proggbar=findViewById(R.id.progress_bar);
        proggbar.setVisibility(View.GONE);
         phone=getIntent().getStringExtra("phoneNo");
         email=getIntent().getStringExtra("email");
         name=getIntent().getStringExtra("name");
         username=getIntent().getStringExtra("username");
         password=getIntent().getStringExtra("password");
            SendOtptouser(phone);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=OTP.getText().toString();
                if(code.isEmpty()||code.length()<6){
                    OTP.setError("WRONG OTP");
                    OTP.requestFocus();
                    return;
                }
                proggbar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });
    }

    private void SendOtptouser(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBysystem=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if(code!=null)
            {
                proggbar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Verify_PhoneNo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    private  void verifyCode(String codeByuser)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationCodeBysystem,codeByuser);
        signInUserByCredentials(credential);

    }

        private void signInUserByCredentials(PhoneAuthCredential credential)
        {
            FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(Verify_PhoneNo.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {

                        Intent intent=new Intent(Verify_PhoneNo.this,Login.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        UserHelperClass helperClass=new UserHelperClass(name,username,email,phone,password);
                        FirebaseDatabase.getInstance().getReference().child("user").child(username).setValue(helperClass);
                        Toast.makeText(Verify_PhoneNo.this,"data added",Toast.LENGTH_SHORT).show();

                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Verify_PhoneNo.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });




        }
}

