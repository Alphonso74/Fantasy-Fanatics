package psu.ajm6684.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Scores extends AppCompatActivity {

    //private FirebaseFirestore db;

    TextView ScoresList;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter adapter;
    String team1;
    String team2;
    Button btn;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users = db.collection("Users");
    FirebaseAuth firebaseAuth;

    DocumentReference mode;
    FirebaseUser current;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date now = new Date();
                android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

                try {
                    // image naming and path  to include sd card  appending name you choose for file
                    Random rn = new Random();

                    String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

                    // create bitmap screen capture
                    View v1 = getWindow().getDecorView().getRootView();
                    v1.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                    v1.setDrawingCacheEnabled(false);

                    File imageFile = new File(mPath);

                    FileOutputStream outputStream = new FileOutputStream(imageFile);
                    int quality = 100;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();

                    Toast.makeText(Scores.this, "Picture Successfully Saved", Toast.LENGTH_SHORT).show();

                } catch (Throwable e) {
                    // Several error may come out with file handling or DOM
                    e.printStackTrace();
                }
            }
        });

        ScoresList = (TextView) findViewById(R.id.scoresList);

       // db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        current = firebaseAuth.getCurrentUser();
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
            protected void onBindViewHolder(@NonNull ScoreViewHolder scoreViewHolder, int i, @NonNull final ScoreModel scoreModel) {
                scoreViewHolder.list_match.setText(scoreModel.getMatch());
                scoreViewHolder.list_score.setText(scoreModel.getScore());


                scoreViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String teams = scoreModel.getMatch().toString();
                        String[] pairs = teams.split("vs.");

                        int count = 0;
                        for (String team : pairs) {
                            if(count == 0){


                                team1 = team.trim();
                            }
                            else if(count == 1){

                                team2 = team.trim();
                            }

                            count++;
//                            Toast.makeText(Scores.this, team.trim(), Toast.LENGTH_SHORT).show();

//                            System.out.println(team.trim());
                        }
                        AlertDialog.Builder alertDlg = new AlertDialog.Builder(new ContextThemeWrapper(Scores.this, android.R.style.Theme_Holo_Light));
                        alertDlg.setTitle("Rematch?");
                        alertDlg.setMessage("Would you you like to play this game again?");
                        alertDlg.setCancelable(true);

                        alertDlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alertDlg.setPositiveButton("Rematch", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                Intent intent = new Intent(Scores.this, gamesimulator.class);
                                intent.putExtra("Team1",team1);


                                intent.putExtra("Team2",team2);

                                startActivity(intent);


                            }
                        });

                        alertDlg.show();



//                        Toast.makeText(Scores.this, scoreModel.getScore().toString() + "\n" + scoreModel.getMatch().toString(), Toast.LENGTH_SHORT).show();

                    }
                });



            }



        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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

//    @Override
//    protected void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
//
//        stopService(new Intent(this, MyService.class));
//    }
}

