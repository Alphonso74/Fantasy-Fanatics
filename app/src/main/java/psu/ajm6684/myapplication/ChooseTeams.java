package psu.ajm6684.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChooseTeams extends AppCompatActivity {

    String team1;
    String team2;
    Button[] teamButtons;
    int count = 0;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users = db.collection("Users");
    FirebaseAuth firebaseAuth;
    ArrayList<String> teams = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_teams);


        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        String Uid = currentUser.getUid().toString();

        db.collectionGroup("Teams").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //  Teams team = document.toObject(Teams.class);

                        String teamName = document.get("TeamName").toString();
                        //Toast.makeText(ChooseTeams.this, teamName, Toast.LENGTH_SHORT).show();


                        teams.add(teamName);

                        ImageView imageView = new ImageView(ChooseTeams.this);

                        ViewGroup layout = (ViewGroup) findViewById(R.id.teams);
                        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        final Button btnTag = new Button(ChooseTeams.this);
                        btnTag.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        btnTag.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                        btnTag.setText(teamName);


                        buttonAction(btnTag);

                        layout.addView(btnTag);
                        //count++;

                }





            }
        }
        });

//        teamButtons = new Button[count];
    }


    public void buttonAction(final Button button) {


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                count ++ ;

                if(count < 3){

                    if(count == 1){

                        team1 = button.getText().toString();
                        button.setBackgroundColor(Color.GREEN);
                        Toast.makeText(ChooseTeams.this, team1, Toast.LENGTH_SHORT).show();

                    }else if(count == 2){
                        button.setBackgroundColor(Color.GREEN);
                        team2 = button.getText().toString();
                        Toast.makeText(ChooseTeams.this, team2, Toast.LENGTH_SHORT).show();

                    }



                }
                else {
                }

            }
        });
    }

    public void clearFeed(View view) {

        Intent confirmPage = new Intent(this, ChooseTeams.class);
        startActivity(confirmPage);

    }
}


