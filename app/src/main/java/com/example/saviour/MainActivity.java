package com.example.saviour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_signIn, btn_signUp;
    private FirebaseAuth mAuth;
    private EditText signInEmail, signInPassword;
    private TextView prograssBarText;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_signIn=(Button) findViewById(R.id.siginInButton);
        btn_signUp=(Button) findViewById(R.id.signUpButton);

        btn_signIn.setOnClickListener(this);
        btn_signUp.setOnClickListener(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mAuth=FirebaseAuth.getInstance();



        signInEmail=(EditText) findViewById(R.id.loginEmail);
        signInPassword=(EditText) findViewById(R.id.loginPassword);
        progressBar=(ProgressBar) findViewById(R.id.progressbar);
        prograssBarText=(TextView) findViewById(R.id.progressBarText);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.siginInButton:
                loginUser();
                break;

            case R.id.signUpButton:
                Intent b;
                b=new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(b);
                break;
        }
    }

    private void loginUser() {

        String loginEmail= signInEmail.getText().toString().trim();
        String loginPassword= signInPassword.getText().toString().trim();

        if(loginEmail.isEmpty())
        {
            signInEmail.setError("Email is required");
            signInEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches())
        {
            signInEmail.setError("Email is not valid");
            signInEmail.requestFocus();
            return;
        }
        if(loginPassword.isEmpty())
        {
            signInPassword.setError("Password is required");
            signInPassword.requestFocus();
            return;
        }

        if(loginPassword.length()<6)
        {
            signInPassword.setError("Password must be 6 letter");
            signInPassword.requestFocus();
            return;
        }
        btn_signIn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        prograssBarText.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(loginEmail,loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    prograssBarText.setVisibility(View.GONE);
                    Intent intent=new Intent(MainActivity.this, UserProfile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    prograssBarText.setVisibility(View.GONE);
                    btn_signIn.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
