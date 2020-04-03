package psu.ajm6684.myapplication;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.widget.Button;
import android.widget.Toast;

public class MyTeamsPage extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference Users = db.collection("Users");

    DocumentReference ajm = Users.document("gGUiNmpauHhsnbDe6pYhr47ddB52");

    CollectionReference myTeam = ajm.collection("Teams");



    private Query team = db.collectionGroup("Teams");

    private teamAdapter TeamAdapter;

    private ArrayList<Teams> teamsList;

    FirebaseAuth auth;
    FirebaseUser currentUser ;


//    private Query specific = Users.document().collection("Teams");


    RecyclerView recyclerView ;


    FirebaseUser current;
    Button preference;

    String uid ;

    Button logout;
//    Query specific ;

    //do not delete
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser current;
    DocumentReference mode;
    //do not delete



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_teams_page);

       auth = FirebaseAuth.getInstance();
        current = auth.getCurrentUser();
        uid = current.getUid();


        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
//        currentUser = auth.getCurrentUser();
//        assert currentUser != null;
//        uid = currentUser.getUid();
        //Log.d("test***********", uid);

//        specific = Users.document("gGUiNmpauHhsnbDe6pYhr47ddB52").collection("Teams");


        //do not delete
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        current = firebaseAuth.getCurrentUser();
        //do not delete

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

                        //So what you need to do with your list
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






recyclerView = findViewById(R.id.recycler_view);
        teamsList = new ArrayList<Teams>();

//        Query afs = Users.document().collectionGroup("Teams");

//        collectionGroupQuery();

       setUpView(uid);

        logout = (Button) findViewById(R.id.logoutButton) ;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent confirmPage = new Intent(getApplicationContext(), AddTeamActivity.class);
                startActivity(confirmPage);

            }
        });

        preference = findViewById(R.id.preferenceButton);
        preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToPreference();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }


    public void collectionGroupQuery() {
//       ///  [START fs_collection_group_query]
//        db.collection(myTeam).get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//        @Override
//        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//            // [START_EXCLUDE]
//            for (QueryDocumentSnapshot snap : queryDocumentSnapshots) {
//                Log.d("Data", snap.getId() + " => " + snap.getData());
//
//
//            }
//            // [END_EXCLUDE]
//        }
//    });
    // [END fs_collection_group_query]

//        db.collectionGroup("Teams").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                FirestoreRecyclerOptions<Teams> options = new FirestoreRecyclerOptions.Builder<Teams>().setQuery(team,Teams.class).build();
//
////                List<Teams> mMissionsList = new ArrayList<>();
//                if(task.isSuccessful()){
//                    for(QueryDocumentSnapshot document : task.getResult()) {
//                        Teams team = document.toObject(Teams.class);
//                        teamsList.add(team);
//                        Log.d("MissionActivity", document.getId() + " => " + document.getData());
//                    }
////                    ListView mMissionsListView = (ListView) findViewById(R.id.missionList);
//                    TeamAdapter = new teamAdapter(options);
////                    MissionsAdapter mMissionAdapter = new MissionsAdapter(this, mMissionsList);
//                    recyclerView.setAdapter(TeamAdapter);
//                } else {
//                    Log.d("MissionActivity", "Error getting documents: ", task.getException());
//                }
//            }
//        });
}


    private void setUpView(String uid){

        Query specific = Users.document(uid).collection("Teams");

        Query test = myTeam;


        FirestoreRecyclerOptions<Teams> options = new FirestoreRecyclerOptions.Builder<Teams>().setQuery(specific,Teams.class).build();

        TeamAdapter = new teamAdapter(options);



        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(TeamAdapter);


//        Query query = team ;
//
////        QueryDocumentSnapshot = team;
//
//        System.out.println("HEllllo " + team.get().toString());
//
////        Log.d("Query", team.toString());
//
//
//
//
//        // Query nurses = patients.document().;
//
//
//
//
//
//
//        FirestoreRecyclerOptions<Teams> options = new FirestoreRecyclerOptions.Builder<Teams>().setQuery(query,Teams.class).build();
//
//        TeamAdapter = new teamAdapter(options);
//
//
//
//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(TeamAdapter);
    }


    public void moveToPreference()
    {
        Intent confirmPage = new Intent(getApplicationContext(), PreferenceActivity.class);
        startActivity(confirmPage);
    }

    @Override
    protected void onStart(){
        super.onStart();
        TeamAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        TeamAdapter.stopListening();
    }
    public void onBackPressed() {

        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }



}
