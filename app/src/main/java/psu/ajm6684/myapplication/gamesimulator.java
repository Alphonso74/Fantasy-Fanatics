package psu.ajm6684.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class gamesimulator extends AppCompatActivity {

    Integer team1Score;
    Integer team2score;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query team = db.collectionGroup("Teams");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamesimulator);

        team1Score = 0;
        team2score = 0;



        Intent intent = getIntent();

        String teamName1 = intent.getStringExtra("Team1").trim();
        String Guard1 = intent.getStringExtra("Guard1");
        String ForwardGuard1 = intent.getStringExtra("ForwardGuard1");
        String GuardForward1 = intent.getStringExtra("GuardForward1");
        String ForwardCenter1 = intent.getStringExtra("ForwardCenter1");
        String Center1 = intent.getStringExtra("Center1");
//        Toast.makeText(gamesimulator.this, "PG " + Guard1, Toast.LENGTH_SHORT).show();


        String teamName2 = intent.getStringExtra("Team2").trim();
        String Guard2 = intent.getStringExtra("Guard2");
        String ForwardGuard2 = intent.getStringExtra("ForwardGuard2");
        String GuardForward2 = intent.getStringExtra("GuardForward2");
        String ForwardCenter2 = intent.getStringExtra("ForwardCenter2");
        String Center2 = intent.getStringExtra("Center2");

//        Toast.makeText(gamesimulator.this, "PG2 " + Guard2, Toast.LENGTH_SHORT).show();

        team.whereEqualTo("TeamName", teamName1).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            Teams note = documentSnapshot.toObject(Teams.class);
//                            note.setDocumentId(documentSnapshot.getId());

                            String guard = note.getGuard().toString();
                            Toast.makeText(gamesimulator.this, "Guard " + guard, Toast.LENGTH_SHORT).show();


//                            String documentId = note.getDocumentId();

//                            data += "ID: " + documentId;

//                            for (String tag : note.getTags().keySet()) {
//                                data += "\n-" + tag;
//                            }

//                            data += "\n\n";
                        }
//                        textViewData.setText(data);
                    }
                });


        db.collectionGroup("Teams").whereEqualTo("TeamName",teamName1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //  Teams team = document.toObject(Teams.class);

                        String teamName = document.get("TeamName").toString();
                        Toast.makeText(gamesimulator.this, teamName, Toast.LENGTH_SHORT).show();
                        String Guard1 = document.get("Guard").toString();
                        String ForwardGuard1 = document.get("ForwardGuard").toString();
                        String GuardForward1 = document.get("GuardForward").toString();
                        String ForwardCenter1 = document.get("ForwardCenter").toString();
                        String Center1 = document.get("Center").toString();
                        Toast.makeText(gamesimulator.this, "Guard " + Guard1, Toast.LENGTH_SHORT).show();



                    }






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






                }


            }
        });

    }

    public void back2Teams(View view) {

        Intent confirmPage = new Intent(this, MyTeamsPage.class);
        startActivity(confirmPage);
    }
}
