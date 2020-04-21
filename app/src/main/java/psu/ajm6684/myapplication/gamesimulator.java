package psu.ajm6684.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class gamesimulator extends AppCompatActivity {

    Integer team1Score;
    Integer team2score;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query team = db.collectionGroup("Teams");

    int possession = 0; //Even means team1 has the ball, Odd means team2 has the ball

    Button playGame;
    TextView gameFeed;

    String teamName1;
    String Guard1 ;
    String ForwardGuard1 ;
    String GuardForward1 ;
    String ForwardCenter1 ;
    String Center1 ;

    String teamName2;
    String Guard2 ;
    String ForwardGuard2 ;
    String GuardForward2 ;
    String ForwardCenter2;
    String Center2 ;


    //for dark mode
    DocumentReference mode;
    FirebaseUser current;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    //


    @Override
    public void onBackPressed() {

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamesimulator);

        playGame = (Button) findViewById(R.id.rungame);
        gameFeed = (TextView) findViewById(R.id.dataFeed);


        team1Score = 0;
        team2score = 0;


        //for dark mode
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        current = firebaseAuth.getCurrentUser();
        mode = firestore.collection("Users").document(current.getUid());

        mode.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<String> list = new ArrayList<>();

                        Map<String, Object> map = document.getData();
                        if (map != null) {
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                list.add(entry.getValue().toString());
                            }
                        }


                        for (String s : list) {

                            if(s.equals("light"))
                            {

                                getDelegate().setLocalNightMode((AppCompatDelegate.MODE_NIGHT_NO));
                                break;
                            }

                            if (s.equals("dark"))
                            {
                                getDelegate().setLocalNightMode((AppCompatDelegate.MODE_NIGHT_YES));
                                break;
                            }

                        }
                    }
                }
            }
        });

        Intent intent = getIntent();

        teamName1 = intent.getStringExtra("Team1").trim();



        teamName2 = intent.getStringExtra("Team2").trim();




        db.collectionGroup("Teams").whereEqualTo("TeamName",teamName1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //  Teams team = document.toObject(Teams.class);

                        String teamName = document.get("TeamName").toString();
//                        Toast.makeText(gamesimulator.this, teamName, Toast.LENGTH_SHORT).show();
                        Guard1 = document.get("Guard").toString();
                        ForwardGuard1 = document.get("ForwardGuard").toString();
                        GuardForward1 = document.get("GuardForward").toString();
                        ForwardCenter1 = document.get("ForwardCenter").toString();
                        Center1 = document.get("Center").toString();
//                        Toast.makeText(gamesimulator.this, "Guard " + Guard1, Toast.LENGTH_SHORT).show();

                    }

                }

                else{

                    Log.d("Error", task.getException().toString());

                }


            }
        });


        db.collectionGroup("Teams").whereEqualTo("TeamName",teamName2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
//                        Toast.makeText(gamesimulator.this, "Guard 2 " + Guard2, Toast.LENGTH_SHORT).show();


                    }


                } else{

                    Log.d("Error", task.getException().toString());

                }


            }
        });



        playGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                playGame.setAlpha(.5f);
                playGame.setClickable(false);

//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //Do something after 100ms

                        tipOff();
//                    }
//                }, 10000);



                final Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        Toast.makeText(gamesimulator.this, "The First Quarter Is Now Underway!", Toast.LENGTH_SHORT).show();

                        firstQ();
                    }
                }, 15000);


//                final Handler handler3 = new Handler();
//                handler3.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //Do something after 100ms
//
//                        secondQ();
//                    }
//                }, 50000);
//
//
//                final Handler handler4 = new Handler();
//                handler4.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //Do something after 100ms
//                        halftime();
//                    }
//                }, 70000);
//
//
//                final Handler handler5 = new Handler();
//                handler5.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //Do something after 100ms
//
//                        thirdQ();
//                    }
//                }, 90000);
//
//
//                final Handler handler6 = new Handler();
//                handler6.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //Do something after 100ms
//
//                        fourthQ();
//                    }
//                }, 110000);
//
//                final Handler handler7 = new Handler();
//                handler7.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //Do something after 100ms
//
//                        endGame();
//                    }
//                }, 130000);
//



            }
        });






    }

    private void tipOff(){

        gameFeed.append("Welcome to Today's game!\n" );
        gameFeed.append("\nToday's match up is between....\n"
                + teamName1 + " and " + teamName2 +"\n");

        gameFeed.append("\n"+teamName1+"'s" + " Starting Five! \n");

        gameFeed.append("\nStarting at the Guard Position\n" + Guard1+"!\n" );
        gameFeed.append("\nStarting at the Shooting Guard Position\n" + GuardForward1+"!\n" );
        gameFeed.append("\nStarting at the Small Forward Position\n" + ForwardGuard1+"!\n" );
        gameFeed.append("\nStarting at the Power Forward Position\n" + ForwardCenter1+"!\n" );
        gameFeed.append("\nStarting at the Center Position\n" + Center1+"!\n" );



        gameFeed.append("\n"+teamName2+"'s" + " Starting Five! \n");

        gameFeed.append("\nStarting at the Guard Position\n" + Guard2+"!\n" );
        gameFeed.append("\nStarting at the Shooting Guard Position\n" + GuardForward2+"!\n" );
        gameFeed.append("\nStarting at the Small Forward Position\n" + ForwardGuard2+"!\n" );
        gameFeed.append("\nStarting at the Power Forward Position\n" + ForwardCenter2+"!\n" );
        gameFeed.append("\nStarting at the Center Position\n" + Center2+"!\n" );



        gameFeed.append("\nLets get ready for Tip off!\n\n" );
        gameFeed.append("\n15 seconds.......\n\n" );



    }

    private void firstQ() {

        gameFeed.append("\nGet Ready.......\n" );



       int tip = (int)Math.round(Math.random());

       if(tip == 0){

           gameFeed.append("\n" + teamName1 + " Won the tip!\n" );

           possession = possession + 2;

       }
       else{

           gameFeed.append("\n" + teamName2 + " Won the tip!\n" );
           possession++;

       }

//        Toast.makeText(gamesimulator.this, "Start of the First Quarter", Toast.LENGTH_SHORT).show();



    }


    private void secondQ() {

        Toast.makeText(gamesimulator.this, "Start of the Second Quarter", Toast.LENGTH_SHORT).show();

    }

   private void halftime(){

       Toast.makeText(gamesimulator.this, "Halftime", Toast.LENGTH_SHORT).show();

   }


   private void thirdQ(){

       Toast.makeText(gamesimulator.this, "Start of the Third Quarter", Toast.LENGTH_SHORT).show();

   }

    private void fourthQ(){

        Toast.makeText(gamesimulator.this, "Start of the Fourth Quarter", Toast.LENGTH_SHORT).show();

    }

    private void endGame(){

        Toast.makeText(gamesimulator.this, "Final Score - " , Toast.LENGTH_SHORT).show();

    }

    public void back2Teams(View view) {

        Intent confirmPage = new Intent(this, MyTeamsPage.class);
        startActivity(confirmPage);
    }
}
