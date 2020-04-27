package psu.ajm6684.myapplication;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scores extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView ScoresList;
    Button View;
    CollectionReference Users;
    DocumentReference ajm;

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    DocumentReference mode;

    RecyclerView recyclerView;
    FirebaseUser current;
    String uid;
    FirebaseAuth auth;

    private ArrayList<Scores> scoreList;

    private CollectionReference users = db.collection("Users");
    ArrayList<String> Scores = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        firebaseAuth = FirebaseAuth.getInstance();
        Users = db.collection("Users");
        current = firebaseAuth.getCurrentUser();

        ScoresList = (TextView) findViewById(R.id.scoresList);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        auth = FirebaseAuth.getInstance();
        uid = current.getUid();
        ajm = Users.document(current.getUid());

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
                            if (s.equals("light")) {
                                getDelegate().setLocalNightMode((AppCompatDelegate.MODE_NIGHT_NO));
                                break;
                            }

                            if (s.equals("dark")) {
                                getDelegate().setLocalNightMode((AppCompatDelegate.MODE_NIGHT_YES));
                                break;
                            }

                        }
                    }
                }
            }
        });



        recyclerView = findViewById(R.id.recyclerView);
        scoreList = new ArrayList<Scores>();
        setUpView(uid);
    }

    private void setUpView(String uid) {

        Query specific = Users.document(uid).collection("Teams");
        FirestoreRecyclerOptions<Teams> options = new FirestoreRecyclerOptions.Builder<Teams>().setQuery(specific, Teams.class).build();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
