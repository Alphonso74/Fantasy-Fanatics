package psu.ajm6684.myapplication;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
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

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser current;
    DocumentReference mode;
    int backButtonCount = 0;
    Handler handler = new Handler();

    EditText teamName;


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
        teamName = findViewById(R.id.teamNameInputAddPlayer);
        teamsList = new ArrayList<Teams>();
        availablePlayers = new ArrayList<String>();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(availablePlayers.size() != 5 && teamName.getText().toString().isEmpty())
                {
                 superMax();
                }
                else if(availablePlayers.size() != 5)
                {
                   notMax();
                }
                else if(teamName.getText().toString().isEmpty())
                {
                 nameMax();
                }
                else
                {
                    addTeam();
                }


            }
        });



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



    public void addTeam()
    {

    }



    /*
    If you are using CollectionReference's add() method, it means that it:

Adds a new document to this collection with the specified POJO as contents, assigning it a document ID automatically.

If you want to get the document id that is generated and use it in your reference, then use DocumentReference's set() method:

Overwrites the document referred to by this DocumentRefere

Like in following lines of code:

String id = db.collection("collection_name").document().getId();
db.collection("collection_name").document(id).set(object);


     */


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

                      //////////////////////////////////////    System.out.print(counter + ":\t" + theWhole + "\n");
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

        buttonAction(btnTag);

        layout.addView(btnTag);


    }

    public void generateButton2(String title) {
        ImageView imageView = new ImageView(AddTeamActivity.this);

        ViewGroup layout = (ViewGroup) findViewById(R.id.pickedplayersscroller);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final Button btnTag = new Button(AddTeamActivity.this );

        btnTag.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //btnTag.setBackgroundColor(Color.parseColor("#1D1D1D"));
        btnTag.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        btnTag.setText(title);
        //btnTag.setTextColor(Color.WHITE);

        buttonAction2(btnTag);

        layout.addView(btnTag);


    }


    public void buttonAction(final Button button) {


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(AddTeamActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to add this player?");

                builder.setTitle((button.getText().toString()))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if(availablePlayers.size() == 5)
                                {
                                    max();
                                }
                                else
                                {
                                    button.setVisibility(View.GONE);
                                    availablePlayers.add(button.getText().toString());
                                    generateButton2(button.getText().toString());

                                }




                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                builder.create();
                builder.show();

            }
        });



    }

    public  void max()
    {
        Toast.makeText(this, "Reached max player limit", Toast.LENGTH_SHORT).show();
    }

    public  void notMax()
    {
        Toast.makeText(this, "Your team needs five players", Toast.LENGTH_SHORT).show();
    }

    public  void nameMax()
    {
        Toast.makeText(this, "Your team needs a name", Toast.LENGTH_SHORT).show();
    }

    public void superMax()
    {
        Toast.makeText(this, "Your team needs a name and your team needs five players", Toast.LENGTH_LONG).show();
    }






    public void buttonAction2(final Button button) {


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(AddTeamActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to remove this player?");

                builder.setTitle((button.getText().toString()))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                button.setVisibility(View.GONE);
                                if (availablePlayers.contains(button.getText().toString()))
                                {
                                    generateButton(button.getText().toString());
                                    availablePlayers.remove(button.getText().toString());
                                }


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                builder.create();
                builder.show();

            }
        });



    }


}
