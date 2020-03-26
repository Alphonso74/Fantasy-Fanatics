package psu.ajm6684.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity  extends AppCompatActivity {



    String Uid;

    FirebaseFirestore firestore;


    Button confirmCreateAccountbutton = (Button) findViewById(R.id.confirmCreateAccount1);

    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);



        final EditText user = (EditText) findViewById(R.id.usernameInputCreateAccount);
        final EditText email = (EditText) findViewById(R.id.emailInputCreateAccount);
        final EditText password = (EditText) findViewById(R.id.passwordInputCreateAccount);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);




        if(firebaseAuth.getCurrentUser() != null){

            Toast.makeText(getApplicationContext(), "Account already created", Toast.LENGTH_LONG).show();


        }

        confirmCreateAccountbutton.setOnClickListener(new View.OnClickListener() {
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

                                documentReference.set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        System.out.println("Success" + Uid);
                                    }
                                });

                                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
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

        startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));

    }
}


