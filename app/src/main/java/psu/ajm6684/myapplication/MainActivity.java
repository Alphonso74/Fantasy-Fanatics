package psu.ajm6684.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends Activity {


    Button confirm;
    Button signup;
    private SharedPreference sharedPreferenceObj;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        confirm = findViewById(R.id.confirmMainPage);
        signup = findViewById(R.id.confirmCreateAccount);


        final EditText email = (EditText) findViewById(R.id.emailMainPage);
        final EditText password = (EditText) findViewById(R.id.passwordInputMainPage);

        final ProgressBar loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar2);

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

                //Intent confirmPage = new Intent(MainActivity.this, MyTeamActivity.class);
                //startActivity(confirmPage);
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

                if (pass.length() < 6) {

                    password.setError("Password must be longer than 6 characters!");

                    loadingProgressBar.setVisibility(View.INVISIBLE);

                    return;
                }

                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {

                    //Toast.makeText(getApplicationContext(), "Missing Fields", Toast.LENGTH_LONG).show();
                    Snackbar.make(getCurrentFocus(), "Missing Fields", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    loadingProgressBar.setVisibility(View.INVISIBLE);
                }


                firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //updateUiWithUser(user);
                            //Toast.makeText(getApplicationContext(), "Logged in user: " + mail, Toast.LENGTH_LONG).show();
                            // Snackbar.make(getCurrentFocus(), "Logged in user: " + mail, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            openMyTeamsActivity();

                            loadingProgressBar.setVisibility(View.INVISIBLE);

                        } else {

                            // Toast.makeText(getApplicationContext(), "Incorrect Credentials! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Snackbar.make(getCurrentFocus(), "Incorrect Credentials", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            //updateUiWithUser(null);

                            loadingProgressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });


            }
        });


        sharedPreferenceObj = new SharedPreference(MainActivity.this);
        if (sharedPreferenceObj.getApp_runFirst().equals("FIRST")) {

            final AlertDialog d = new AlertDialog.Builder(MainActivity.this,R.style.MyDialogTheme)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           sharedPreferenceObj.setApp_runFirst("NO");


                        }
                    })
                    .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();

                        }
                    })

                    .setTitle("Fantasy Fanatics")

                    .setMessage("Do you agree to our Terms and Services & Privacy Policies?")
                    .create();

            d.show();

        } else {


            // App is not First Time Launch
        }

    }

    @Override
    public void onBackPressed() {

    }


    public void openMyTeamsActivity() {
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
