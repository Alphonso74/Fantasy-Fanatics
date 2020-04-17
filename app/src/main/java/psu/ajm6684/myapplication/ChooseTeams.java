package psu.ajm6684.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChooseTeams extends AppCompatActivity {

    String team1;
    String team2;
    Button playGame;
    Button[] teamButtons;
    String[] players1 = new String[5];
    String[] player2 = new String[5];
    int count = 0;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users = db.collection("Users");
    FirebaseAuth firebaseAuth;
    ArrayList<String> teams = new ArrayList<>();
    ImageView imageView;
    ViewGroup layout2;
    ViewGroup layout;


    String Guard1;
    String ForwardGuard1;
    String GuardForward1;
    String ForwardCenter1;
    String Center1;


    String Guard2;
    String ForwardGuard2;
    String GuardForward2;
    String ForwardCenter2;
    String Center2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_teams);
        layout = (ViewGroup) findViewById(R.id.teams);
        playGame = (Button) findViewById(R.id.play_game);
        imageView = new ImageView(ChooseTeams.this);

        layout2 = (ViewGroup) findViewById(R.id.matchups);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

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



                        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        final Button btnTag = new Button(ChooseTeams.this);
                        btnTag.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        btnTag.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                        btnTag.setText(teamName);


                        buttonAction(btnTag, layout2, layout);

                        layout.addView(btnTag);
                        //count++;

                }






            }
        }
        });

//        teamButtons = new Button[count];


        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(count != 2){


                    AlertDialog.Builder menuDialog = new AlertDialog.Builder(new ContextThemeWrapper(ChooseTeams.this, android.R.style.Theme_Holo_Dialog));
                    menuDialog.setTitle("You need 2 teams good man!");
                    menuDialog.setMessage("2 teams please, thank you....");
                    menuDialog.setCancelable(true);

                    menuDialog.show();

                }
                else{

                    db.collectionGroup("Teams").whereEqualTo("TeamName",team1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //  Teams team = document.toObject(Teams.class);

                                    String teamName = document.get("TeamName").toString();
                                    Toast.makeText(ChooseTeams.this, teamName, Toast.LENGTH_SHORT).show();
                                      Guard1 = document.get("Guard").toString();
                                      ForwardGuard1 = document.get("ForwardGuard").toString();
                                      GuardForward1 = document.get("GuardForward").toString();
                                      ForwardCenter1 = document.get("ForwardCenter").toString();
                                      Center1 = document.get("Center").toString();
                                    Toast.makeText(ChooseTeams.this, Guard1, Toast.LENGTH_SHORT).show();



                                }






                            }


                        }
                    });


                    db.collectionGroup("Teams").whereEqualTo("TeamName",team2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //  Teams team = document.toObject(Teams.class);

//                                    String teamName = document.get("TeamName").toString();
                                    Guard2 = document.get("Guard").toString();
                                    ForwardGuard2 = document.get("ForwardGuard").toString();
                                    GuardForward2 = document.get("GuardForward").toString();
                                    ForwardCenter2 = document.get("ForwardCenter").toString();
                                    Center2 = document.get("Center").toString();
                                    Toast.makeText(ChooseTeams.this, Guard2, Toast.LENGTH_SHORT).show();


                                }






                            }


                        }
                    });



                    Intent intent = new Intent(ChooseTeams.this, gamesimulator.class);
                    intent.putExtra("Team1",team1);
                    intent.putExtra("Guard1",Guard1);
                    intent.putExtra("ForwardGuard1",ForwardGuard1);
                    intent.putExtra("GuardForward1",GuardForward1);
                    intent.putExtra("ForwardCenter1",ForwardCenter1);
                    intent.putExtra("Center1",Center1);

                    intent.putExtra("Team2",team2);
                    intent.putExtra("Guard2",Guard2);
                    intent.putExtra("ForwardGuard2",ForwardGuard2);
                    intent.putExtra("GuardForward2",GuardForward2);
                    intent.putExtra("ForwardCenter2",ForwardCenter2);
                    intent.putExtra("Center2",Center2);

                    startActivity(intent);

                }


            }
        });

    }


    public void buttonAction(final Button button, final ViewGroup layout, final ViewGroup viewGroup) {


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                count ++ ;

                if(count < 3){

                    if(count == 1){

                        team1 = button.getText().toString();
//                        button.setBackgroundColor(Color.GREEN);
                        //Toast.makeText(ChooseTeams.this, team1, Toast.LENGTH_SHORT).show();
                        viewGroup.removeView(button);
                        layout.addView(button);


                    }else if(count == 2){
//                        button.setBackgroundColor(Color.GREEN);
                        team2 = button.getText().toString();
                        //Toast.makeText(ChooseTeams.this, team2, Toast.LENGTH_SHORT).show();
                        viewGroup.removeView(button);
                        layout.addView(button);

                    }



                }
                else {
                }

            }
        });
    }

    public void clearFeed(View view) {

//        Intent confirmPage = new Intent(this, ChooseTeams.class);
////        startActivity(confirmPage);

        for(int i = 0; i < layout2.getChildCount(); i++){

            View temp = layout2.getChildAt(i);
            layout2.removeViewAt(i);
            layout.addView(temp);

        }
        //layout2.removeAllViews();
        count = 0;

    }

    public void back2Teams(View view) {

        Intent confirmPage = new Intent(this, MyTeamsPage.class);
        startActivity(confirmPage);
    }

    public void playgame(View view) {


    }
}


