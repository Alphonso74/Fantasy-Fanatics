package psu.ajm6684.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {


    Button confirm;
    Button signup;

    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUiWithUser(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        confirm = findViewById(R.id.confirmMainPage);
        signup = findViewById(R.id.confirmCreateAccount);

        final EditText email = (EditText) findViewById(R.id.emailMainPage);
        final EditText password = (EditText) findViewById(R.id.passwordInputMainPage);

        final ProgressBar  loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar2);

        firebaseAuth = FirebaseAuth.getInstance();

        loadingProgressBar.setVisibility(View.INVISIBLE);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent confirmPage = new Intent(MainActivity.this, createUser.class);
                startActivity(confirmPage);

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent confirmPage = new Intent(MainActivity.this, MyTeamActivity.class);
                startActivity(confirmPage);
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();


                loadingProgressBar.setVisibility(View.VISIBLE);
//                loginViewModel.login(emailEditText.getText().toString(),
//                        passwordEditText.getText().toString());

                if(pass.length() < 6){

                    password.setError("Password must be longer than 6 characters!");

                    loadingProgressBar.setVisibility(View.INVISIBLE);

                    return;
                }

                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(), "Missing Fields Need to Entered", Toast.LENGTH_LONG).show();

                    loadingProgressBar.setVisibility(View.INVISIBLE);
                }


                firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //updateUiWithUser(user);
                            Toast.makeText(getApplicationContext(), "Logged in user: " + mail, Toast.LENGTH_LONG).show();
                           openMyTeamsActivity();

                           loadingProgressBar.setVisibility(View.INVISIBLE);

                        }
                        else{

                            Toast.makeText(getApplicationContext(), "Incorrect Credentials! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            //updateUiWithUser(null);

                            loadingProgressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });


            }
        });
    }




    public void openMyTeamsActivity()
    {
        Intent confirmPage = new Intent(MainActivity.this, MyTeamsPage.class);
        startActivity(confirmPage);


    }


//    public void signupChangePage(View view)
//    {
//        Intent signupPage = new Intent(MainActivity.this, CreateAccountActivity.class);
//        startActivity(signupPage);
//
//
//    }

    private void updateUiWithUser(FirebaseUser user) {
//        String welcome = getString(R.string.welcome) + model.getDisplayName();
//        // TODO : initiate successful logged in experience
//        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }




}
