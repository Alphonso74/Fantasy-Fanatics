package psu.ajm6684.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
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


    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            // Here, thisActivity is the current activity
            requestPermissions();
            return false;
        }


    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0
                );

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }


    private boolean checkPermissions2() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            // Here, thisActivity is the current activity
            requestPermissions2();
            return false;
        }


    }


    private void requestPermissions2() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0
                );

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        confirm = findViewById(R.id.confirmMainPage);
        signup = findViewById(R.id.confirmCreateAccount);

//        startService(new Intent(this, MyService.class));
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

                            final MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.singledribble);
                            mp.start();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //updateUiWithUser(user);
                            //Toast.makeText(getApplicationContext(), "Logged in user: " + mail, Toast.LENGTH_LONG).show();
                            // Snackbar.make(getCurrentFocus(), "Logged in user: " + mail, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            openMyTeamsActivity();

                            loadingProgressBar.setVisibility(View.INVISIBLE);

                        } else {

                             Toast.makeText(getApplicationContext(), "Incorrect Credentials! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                            Snackbar.make(this, "Incorrect Credentials", Snackbar.LENGTH_LONG)
//                                    .setAction("Action", null).show();
                            //updateUiWithUser(null);

                            loadingProgressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });


            }
        });


        sharedPreferenceObj = new SharedPreference(MainActivity.this);
        if (sharedPreferenceObj.getApp_runFirst().equals("FIRST")) {

            final AlertDialog d = new AlertDialog.Builder(MainActivity.this)
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           sharedPreferenceObj.setApp_runFirst("NO");


                        }
                    })

                    .setCancelable(false)
                    .setTitle("Fantasy Fanatics")
                        .setMessage("Thank you for downloading Freestyle")
                    .create();

            d.show();

        } else {


            // App is not First Time Launch
        }


//        if (checkPermissions() != true) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
//
//
//        }


        if (checkPermissions2() != true) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);


        }

    }

    @Override
    public void onBackPressed() {

    }


    public void openMyTeamsActivity() {

        if (checkPermissions2() != true) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Allow Access");

            builder.setCancelable(false);
            builder.setMessage("Please allow us to have access to your storage next time")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            builder.create();
            builder.show();
        } else {

            Intent confirmPage = new Intent(MainActivity.this, MyTeamsPage.class);
            startActivity(confirmPage);

        }



    }

//    @Override
//    protected void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
//
//        stopService(new Intent(this, MyService.class));
//    }

//    @Override
//    public void onResume() {
//        super.onResume();  // Always call the superclass method first
//        startService(new Intent(this, MyService.class));
//        // re-sync the clock with player...
//    }


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
