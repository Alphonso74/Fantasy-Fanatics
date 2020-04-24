package psu.ajm6684.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class createUser extends AppCompatActivity {

    String Uid;

    FirebaseFirestore firestore;
    //
//
    Button confirmButton;
    int backButtonCount = 0;

    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //this one
        int backButtonCount = 0;
        confirmButton = (Button) findViewById(R.id.buttonSignUp);
        final EditText user = (EditText) findViewById(R.id.teamNameInputAddPlayer);
        final EditText email = (EditText) findViewById(R.id.emailInputCreateAccount);
        final EditText password = (EditText) findViewById(R.id.passwordInputCreateAccount);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);


        progressBar.setVisibility(View.INVISIBLE);
        System.out.println("test");


        if (firebaseAuth.getCurrentUser() != null) {

            //   Toast.makeText(getApplicationContext(), "Account already created", Toast.LENGTH_LONG).show();


        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressBar.setVisibility(View.VISIBLE);


                if (password.length() < 6) {

                    password.setError("Password must be longer than 6 characters!");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }


                if (user.getText().toString().isEmpty()
                        || email.getText().toString().isEmpty()
                        || password.getText().toString().isEmpty()) {


                    Toast.makeText(getApplicationContext(), "Empty Credentials", Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else {

                    final String email1 = email.getText().toString().trim();
                    final String password1 = password.getText().toString().trim();
                    final String lightnDark = "light";
                    final String user1 = user.getText().toString().trim();


                    firebaseAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                Uid = firebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = firestore.collection("Users").document(Uid);

                                Map<String, Object> userMap = new HashMap<>();
                                userMap.put("userName", user1);
                                userMap.put("email", email1);
                                userMap.put("mode", lightnDark);


                                documentReference.set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_LONG).show();

                                        progressBar.setVisibility(View.VISIBLE);

                                        System.out.println("Success" + Uid);


                                    }
                                });



                                final MediaPlayer mp = MediaPlayer.create(createUser.this, R.raw.singledribble);
                                mp.start();


                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Do something after 100ms
                                        Intent intent = new Intent(createUser.this, MainActivity.class);
                                        startActivity(intent);


                                    }
                                }, 500);






                            } else {
                                Toast.makeText(getApplicationContext(), "Error!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                progressBar.setVisibility(View.INVISIBLE);

                            }
                        }
                    });


                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (backButtonCount >= 1) {

            final MediaPlayer mp = MediaPlayer.create(createUser.this, R.raw.backboardshot);
            mp.start();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(createUser.this, MainActivity.class));

                }
            }, 600);




        } else {
            Toast.makeText(this, "Pressing the back button again will lose unsaved progress.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
            }



}
