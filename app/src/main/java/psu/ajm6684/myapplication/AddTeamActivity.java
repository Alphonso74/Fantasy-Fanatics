package psu.ajm6684.myapplication;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class AddTeamActivity extends  AppCompatActivity{


    Button submit;
    TextView teamName;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser current;
    DocumentReference mode;
    int backButtonCount = 0;
    Handler handler = new Handler();



  FirebaseFirestore db = FirebaseFirestore.getInstance();
  ArrayList<Teams> teamsList;
    ArrayList<String> availablePlayers;

    public AddTeamActivity()
    {

    }

    public static AddTeamActivity newInstance() {
        AddTeamActivity fragment = new AddTeamActivity();

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_players);

        submit = findViewById(R.id.confirmbtn);
        teamName = findViewById(R.id.teamNameSubmit);
        teamsList = new ArrayList<Teams>();
        availablePlayers = new ArrayList<String>();



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
    collectionGroupQuery();

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        },5000);



    }


    public void collectionGroupQuery() {

        db.collectionGroup("Players").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
        int counter = 1;

                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Teams team = document.toObject(Teams.class);

                            teamsList.add(team);

                          String pos = document.getData() + "";
                          pos = pos.replaceAll("Position=","");
                          pos = pos.substring(1,pos.indexOf(","));

                          String name = document.getData() + "";
                          name = name.substring(name.indexOf("Name=")+5);
                          name = name.substring(0,name.length() - 1);


                        String theWhole = pos + ": " + name;

                          System.out.print(counter + ":\t" + theWhole + "\n");
                    generateButton(theWhole);


                        counter++;

                     //   Log.d("MissionActivity", document.getId() + " => " + document.getData());
                    }

                } else {
                   // Log.d("MissionActivity", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        if(backButtonCount >= 1)
        {
           finish();
        }
        else
        {
            Toast.makeText(this, "Pressing the back button again will lose unsaved progress.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }


    public void generateButton(String title) {
        ImageView imageView = new ImageView(AddTeamActivity.this);

        ViewGroup layout = (ViewGroup) findViewById(R.id.availiableplayersscroller);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final Button btnTag = new Button(AddTeamActivity.this );

        btnTag.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //btnTag.setBackgroundColor(Color.parseColor("#1D1D1D"));
        btnTag.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        btnTag.setText(title);
        //btnTag.setTextColor(Color.WHITE);

        //buttonAction(btnTag);

        layout.addView(btnTag);


    }


}
