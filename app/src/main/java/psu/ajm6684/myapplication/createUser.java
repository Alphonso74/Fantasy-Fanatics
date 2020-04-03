package psu.ajm6684.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    Button confirmButton ;

    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        //this one

        confirmButton = (Button) findViewById(R.id.buttonSignUp);
        final EditText user = (EditText) findViewById(R.id.usernameInputCreateAccount);
        final EditText email = (EditText) findViewById(R.id.emailInputCreateAccount);
        final EditText password = (EditText) findViewById(R.id.passwordInputCreateAccount);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);


        progressBar.setVisibility(View.INVISIBLE);
        System.out.println("test");



        if(firebaseAuth.getCurrentUser() != null){

         //   Toast.makeText(getApplicationContext(), "Account already created", Toast.LENGTH_LONG).show();



        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                progressBar.setVisibility(View.VISIBLE);


                if(password.length() < 6){

                    password.setError("Password must be longer than 6 characters!");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }


                if(user.getText().toString().isEmpty()
                        || email.getText().toString().isEmpty()
                        || password.getText().toString().isEmpty()) {


                    Toast.makeText(getApplicationContext(), "Empty Credentials", Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                else
                {

                    final String email1 = email.getText().toString().trim();
                    final String password1 = password.getText().toString().trim();
                    final String lightnDark = "light";
                    final String user1 = user.getText().toString().trim();


                    firebaseAuth.createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_LONG).show();

                                Uid = firebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = firestore.collection("Users").document(Uid);

                                Map<String,Object> userMap = new HashMap<>();
                                userMap.put("userName",user1);
                                userMap.put("email",email1);
                                userMap.put("mode",lightnDark);





                                documentReference.set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                       Toast.makeText(getApplicationContext(), "Account Created" , Toast.LENGTH_LONG).show();

                                        progressBar.setVisibility(View.VISIBLE);

                                        System.out.println("Success" + Uid);



                                    }
                                });





                                Intent intent = new Intent(createUser.this, MainActivity.class);
                                startActivity(intent);

                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Error!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                progressBar.setVisibility(View.INVISIBLE);

                            }
                        }
                    });





                }
            }
        });


    }


    public void back(View view) {

        startActivity(new Intent(createUser.this, MainActivity.class));

    }

}
