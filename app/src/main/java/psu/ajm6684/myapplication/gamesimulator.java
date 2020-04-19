package psu.ajm6684.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    String Guard1 ;
    String ForwardGuard1 ;
    String GuardForward1 ;
    String ForwardCenter1 ;
    String Center1 ;

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
        //




        team1Score = 0;
        team2score = 0;



        Intent intent = getIntent();

        String teamName1 = intent.getStringExtra("Team1").trim();
//        String Guard1 ;
//        String ForwardGuard1 ;
//        String GuardForward1 ;
//        String ForwardCenter1 ;
//        String Center1 ;
//        Toast.makeText(gamesimulator.this, "Team 1 Guard " + Guard1, Toast.LENGTH_SHORT).show();


        String teamName2 = intent.getStringExtra("Team2").trim();
//        String Guard2 ;
//        String ForwardGuard2 ;
//        String GuardForward2 ;
//        String ForwardCenter2;
//        String Center2 ;



        db.collectionGroup("Teams").whereEqualTo("TeamName",teamName1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //  Teams team = document.toObject(Teams.class);

                        String teamName = document.get("TeamName").toString();
//                        Toast.makeText(gamesimulator.this, teamName, Toast.LENGTH_SHORT).show();
                        String Guard1 = document.get("Guard").toString();
                        String ForwardGuard1 = document.get("ForwardGuard").toString();
                        String GuardForward1 = document.get("GuardForward").toString();
                        String ForwardCenter1 = document.get("ForwardCenter").toString();
                        String Center1 = document.get("Center").toString();
                        Toast.makeText(gamesimulator.this, "Guard " + Guard1, Toast.LENGTH_SHORT).show();



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
                        String Guard2 = document.get("Guard").toString();
                        String ForwardGuard2 = document.get("ForwardGuard").toString();
                        String GuardForward2 = document.get("GuardForward").toString();
                        String ForwardCenter2 = document.get("ForwardCenter").toString();
                        String Center2 = document.get("Center").toString();
                        Toast.makeText(gamesimulator.this, "Guard 2 " + Guard2, Toast.LENGTH_SHORT).show();


                    }






                } else{

                    Log.d("Error", task.getException().toString());

                }


            }
        });

    }

    public void back2Teams(View view) {

        Intent confirmPage = new Intent(this, MyTeamsPage.class);
        startActivity(confirmPage);
    }
}
