package psu.ajm6684.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Scores extends AppCompatActivity {

    private FirebaseFirestore db;

    TextView ScoresList;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        ScoresList = (TextView) findViewById(R.id.scoresList);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycler_View);

        Query query = db.collection("Scores");

        FirestoreRecyclerOptions<ScoreModel> options = new FirestoreRecyclerOptions.Builder<ScoreModel>()
                .setQuery(query, ScoreModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ScoreModel, ScoreViewHolder>(options) {
            @NonNull
            @Override
            public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_list, parent, false);
                return new ScoreViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ScoreViewHolder scoreViewHolder, int i, @NonNull ScoreModel scoreModel) {
                scoreViewHolder.list_match.setText(scoreModel.getMatch());
                scoreViewHolder.list_score.setText(scoreModel.getScore());
            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {

        final MediaPlayer mp = MediaPlayer.create(Scores.this, R.raw.backboardshot);
        mp.start();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), MyTeamsPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Scores.this.finish();

            }
        }, 600);

    }

    private class ScoreViewHolder extends RecyclerView.ViewHolder {

        private TextView list_match;
        private TextView list_score;

        public ScoreViewHolder(@NonNull View itemView){
            super(itemView);
            list_match = itemView.findViewById(R.id.list_match);
            list_score = itemView.findViewById(R.id.list_score);
        }

    }

    @Override
    protected void onStop(){

        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart(){

        super.onStart();
        adapter.startListening();
    }

}

