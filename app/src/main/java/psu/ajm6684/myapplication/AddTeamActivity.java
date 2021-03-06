package psu.ajm6684.myapplication;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddTeamActivity extends AppCompatActivity {


    Button submit;

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser current;
    DocumentReference mode;
    int dialogCounter = 0;
    
    int backButtonCount = 0;
    Handler handler = new Handler();
    Spinner spinner;

    EditText teamName;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseFirestore nam = FirebaseFirestore.getInstance();
    ArrayList<Teams> teamsList;
    ArrayList<String> availablePlayers;


    ArrayList<String> guard;
    ArrayList<String> forward_guard;
    ArrayList<String> forward_center;
    ArrayList<String> guard_forward;
    ArrayList<String> center;

    boolean one = false;
    boolean two = false;
    boolean three = false;
    boolean four = false;
    boolean five = false;



    boolean sameNam = false;
    public AddTeamActivity() {

    }

    public static AddTeamActivity newInstance() {
        AddTeamActivity fragment = new AddTeamActivity();

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_players);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);








        submit = findViewById(R.id.confirmbtn);
        teamName = findViewById(R.id.teamNameInputAddPlayer);
        spinner = findViewById(R.id.spinner);
        teamsList = new ArrayList<Teams>();
        availablePlayers = new ArrayList<>();


        guard = new ArrayList<>();

        forward_guard = new ArrayList<>();
        forward_center = new ArrayList<>();
        guard_forward = new ArrayList<>();
        center = new ArrayList<>();

        String[] spinnerArray = new String[]{"Guard", "Forward-Guard", "Forward-Center", "Guard-Forward", "Center"};


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);

        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(stringArrayAdapter);




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sameNam = false;

                for (int x = 0; x < availablePlayers.size(); x++) {

                    if (availablePlayers.get(x).contains("Guard") && !availablePlayers.get(x).contains("-")) {
                        one = true;

                    } else if (availablePlayers.get(x).contains("Forward-Guard")) {
                        two = true;

                    } else if (availablePlayers.get(x).contains("Forward-Center")) {
                        three = true;

                    } else if (availablePlayers.get(x).contains("Guard-Forward")) {
                        four = true;

                    } else if (availablePlayers.get(x).contains("Center") && !availablePlayers.get(x).contains("-")) {
                        five = true;

                    }
                }

                System.out.println("1:" + one);
                System.out.println("2:" + two);
                System.out.println("3:" + three);
                System.out.println("4:" + four);
                System.out.println("5:" + five);



                final String userTN = teamName.getText().toString();


                nam.collection("Users").document(current.getUid()).collection("Teams").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {



                                if(document.getData().toString().length() > 0)
                                {
                                    String separateTeamName = document.getData().toString();
                                    String[] tokens = separateTeamName.split(" ");

                                    String dbTeamName = "";
                                    for(int x = 0; x < tokens.length; x++)
                                    {
                                        //System.out.println(tokens[x]);
                                        if(tokens[x].contains("TeamName="))
                                        {
                                            dbTeamName = tokens[x];
                                        }

                                    }
                                    dbTeamName = dbTeamName.replace("TeamName=","");
                                    dbTeamName = dbTeamName.replace(",","");
                                    System.out.println(dbTeamName);
                                    System.out.println(separateTeamName);

                                    if(userTN.equals(dbTeamName))
                                    {

                                        System.out.println("yes");
                                        System.out.println("Current Team name: " + userTN + "\t\t" + "DataBaseTeamName: " + dbTeamName);
                                        sameName();
                                        return;

                                    }
                                    else
                                    {
                                        System.out.println("no");
                                        System.out.println("Current Team name: " + userTN + "\t\t" + "DataBaseTeamName: " + dbTeamName);

                                    }
                                }


                            }
                        }


                        String nameCheckTrim = teamName.getText().toString().trim();
                        int nameSize = nameCheckTrim.length();
                        if (availablePlayers.size() != 5 && nameCheckTrim.isEmpty()) {
                            superMax();
                        } else if (availablePlayers.size() != 5) {
                            notMax();
                        } else if (nameCheckTrim.isEmpty()) {
                            nameMax();
                        } else if (sameNam) {
                            sameName();
                        } else if ((!one || !two || !three || !four || !five) && availablePlayers.size() == 5) {
                            balanceMax();
                        } else {

                            addTeam();
                        }



                    }


                });


                System.out.println("IS IT THE SAME???" + sameNam);





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


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String role = adapterView.getItemAtPosition(i).toString();

                //  String[] spinnerArray = new String[]{"Guard", "Forward-Guard", "Forward-Center", "Guard-Forward", "Center"};
                if (role.equals("Guard")) {
                    dialogCounter++;
                    System.out.println("COUNTER OF DIALOG:" + dialogCounter);

                    if(dialogCounter > 1)
                    {
                        dialogged();
                    }

                    clearLayoutTop();
                    collectionGroupQuery();
                } else if (role.equals("Forward-Guard")) {
                    dialogCounter++;
                    System.out.println("COUNTER OF DIALOG:" + dialogCounter);

                    if(dialogCounter > 1)
                    {
                        dialogged();
                    }
                    clearLayoutTop();
                    collectionGroupQuery2();
                } else if (role.equals("Forward-Center")) {
                    dialogCounter++;
                    System.out.println("COUNTER OF DIALOG:" + dialogCounter);


                    if(dialogCounter > 1)
                    {
                        dialogged();
                    }
                    clearLayoutTop();
                    collectionGroupQuery3();
                } else if (role.equals("Guard-Forward")) {
                    dialogCounter++;
                    System.out.println("COUNTER OF DIALOG:" + dialogCounter);

                    if(dialogCounter > 1)
                    {
                        dialogged();
                    }
                    clearLayoutTop();
                    collectionGroupQuery4();
                } else if (role.equals("Center")) {
                    dialogCounter++;
                    System.out.println("COUNTER OF DIALOG:" + dialogCounter);

                    if(dialogCounter > 1)
                    {
                        dialogged();
                    }
                    clearLayoutTop();
                    collectionGroupQuery5();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






    }

    public void dialogged()
    {
        final LoadingDialog loadingDialog = new LoadingDialog(AddTeamActivity.this);
        loadingDialog.startDialog();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                loadingDialog.dismissDialog();

            }
        }, 1250);
    }


    public void addTeam() {


//        firebaseAuth = FirebaseAuth.getInstance();
//        firestore = FirebaseFirestore.getInstance();
//        current = firebaseAuth.getCurrentUser();
//        mode = firestore.collection("Users").document(current.getUid());
        String userTN = teamName.getText().toString();

        Map<String, Object> userFT = new HashMap<>();


        userFT.put("TeamName", userTN);

//            for(String player: availablePlayers)
//            {
//                String p1 = player;
//                p1 = p1.substring(0,p1.indexOf(":"));
//
//                String n1 = player;
//                n1= n1.substring(n1.indexOf(":") + 2);
//
//                System.out.println(p1 + "\t" + n1);
//                System.out.println("AVAILABLE SIZE: " + availablePlayers.size());
//
//                userFT.put(p1,n1);
//            }


        for (int x = 0; x < availablePlayers.size(); x++) {
            String p1 = availablePlayers.get(x);
            p1 = p1.substring(0, p1.indexOf(":"));

            if (p1.contains("-")) {
                p1 = p1.replaceAll("-", "");
            }

            String n1 = availablePlayers.get(x);
            n1 = n1.substring(n1.indexOf(":") + 2);

            System.out.println(p1 + "\t" + n1);
            System.out.println("AVAILABLE SIZE: " + availablePlayers.size());

            userFT.put(p1, n1);
        }


        mode = firestore.collection("Users").document(current.getUid()).collection("Teams").document();

        mode.set(userFT).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                final MediaPlayer mp = MediaPlayer.create(AddTeamActivity.this, R.raw.singledribble);
                mp.start();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        Intent intent = new Intent(getBaseContext(), MyTeamsPage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        AddTeamActivity.this.finish();

                    }
                }, 500);



            }
        });


        System.out.println("works");


    }


    public void collectionGroupQuery() {

        db.collectionGroup("Players").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int counter = 1;

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //  Teams team = document.toObject(Teams.class);

                        // teamsList.add(team);

                        String pos = document.getData() + "";
                        pos = pos.replaceAll("Position=", "");
                        pos = pos.substring(1, pos.indexOf(","));

                        String name = document.getData() + "";
                        name = name.substring(name.indexOf("Name=") + 5);
                        name = name.substring(0, name.length() - 1);


                        String theWhole = pos + ": " + name;
                        if (theWhole.contains("Guard") && !theWhole.contains("-")) {
                            //   guard.add(theWhole);


                            if (availablePlayers.size() == 0) {
                                generateButton(theWhole);
                            } else {


                                if (availablePlayers.size() == 1) {
                                    System.out.println("SIZE1");
                                    if (availablePlayers.get(0).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }
                                if (availablePlayers.size() == 2) {
                                    System.out.println("SIZE2");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 3) {
                                    System.out.println("SIZE3");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 4) {
                                    System.out.println("SIZE4");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name) || availablePlayers.get(3).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 5) {
                                    System.out.println("SIZE5");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name) || availablePlayers.get(3).contains(name) || availablePlayers.get(4).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }
                            }

                            ///////
                        }


                    }
                        


                        /*
                        //  String[] spinnerArray = new String[]{"Guard", "Forward-Guard", "Forward-Center", "Guard-Forward", "Center"};
                        ArrayList<String> guard;
                        ArrayList<String> forward_guard;
                        ArrayList<String> forward_center;
                        ArrayList<String> guard_forward;
                        ArrayList<String> center;

                         */


                    //////////////////////////////////////    System.out.print(counter + ":\t" + theWhole + "\nam");


                    counter++;

                    //   Log.d("MissionActivity", document.getId() + " => " + document.getData());
                }


            }
        });
    }



    public void collectionGroupQuery2() {

        db.collectionGroup("Players").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {


                        String pos = document.getData() + "";
                        pos = pos.replaceAll("Position=", "");
                        pos = pos.substring(1, pos.indexOf(","));

                        String name = document.getData() + "";
                        name = name.substring(name.indexOf("Name=") + 5);
                        name = name.substring(0, name.length() - 1);


                        String theWhole = pos + ": " + name;

                        if (theWhole.contains("Forward-Guard")) {
                            if (availablePlayers.size() == 0) {
                                generateButton(theWhole);
                            } else {


                                if (availablePlayers.size() == 1) {
                                    System.out.println("SIZE1");
                                    if (availablePlayers.get(0).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }
                                if (availablePlayers.size() == 2) {
                                    System.out.println("SIZE2");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 3) {
                                    System.out.println("SIZE3");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 4) {
                                    System.out.println("SIZE4");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name) || availablePlayers.get(3).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 5) {
                                    System.out.println("SIZE5");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name) || availablePlayers.get(3).contains(name) || availablePlayers.get(4).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }
                            }
                        }
                    }

                } else {

                }
            }
        });
    }


    public void collectionGroupQuery3() {

        db.collectionGroup("Players").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {


                        String pos = document.getData() + "";
                        pos = pos.replaceAll("Position=", "");
                        pos = pos.substring(1, pos.indexOf(","));

                        String name = document.getData() + "";
                        name = name.substring(name.indexOf("Name=") + 5);
                        name = name.substring(0, name.length() - 1);


                        String theWhole = pos + ": " + name;

                        if (theWhole.contains("Forward-Center")) {
                            if (availablePlayers.size() == 0) {
                                generateButton(theWhole);
                            } else {


                                if (availablePlayers.size() == 1) {
                                    System.out.println("SIZE1");
                                    if (availablePlayers.get(0).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }
                                if (availablePlayers.size() == 2) {
                                    System.out.println("SIZE2");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 3) {
                                    System.out.println("SIZE3");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 4) {
                                    System.out.println("SIZE4");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name) || availablePlayers.get(3).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 5) {
                                    System.out.println("SIZE5");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name) || availablePlayers.get(3).contains(name) || availablePlayers.get(4).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }
                            }
                        }
                    }

                } else {

                }
            }
        });
    }


    public void collectionGroupQuery4() {

        db.collectionGroup("Players").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {


                        String pos = document.getData() + "";
                        pos = pos.replaceAll("Position=", "");
                        pos = pos.substring(1, pos.indexOf(","));

                        String name = document.getData() + "";
                        name = name.substring(name.indexOf("Name=") + 5);
                        name = name.substring(0, name.length() - 1);


                        String theWhole = pos + ": " + name;
                        if (theWhole.contains("Guard-Forward")) {
                            if (availablePlayers.size() == 0) {
                                generateButton(theWhole);
                            } else {


                                if (availablePlayers.size() == 1) {
                                    System.out.println("SIZE1");
                                    if (availablePlayers.get(0).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }
                                if (availablePlayers.size() == 2) {
                                    System.out.println("SIZE2");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 3) {
                                    System.out.println("SIZE3");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 4) {
                                    System.out.println("SIZE4");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name) || availablePlayers.get(3).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 5) {
                                    System.out.println("SIZE5");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name) || availablePlayers.get(3).contains(name) || availablePlayers.get(4).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }
                            }
                        }

                    }

                } else {

                }
            }
        });
    }


    public void collectionGroupQuery5() {

        db.collectionGroup("Players").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {


                        String pos = document.getData() + "";
                        pos = pos.replaceAll("Position=", "");
                        pos = pos.substring(1, pos.indexOf(","));

                        String name = document.getData() + "";
                        name = name.substring(name.indexOf("Name=") + 5);
                        name = name.substring(0, name.length() - 1);


                        String theWhole = pos + ": " + name;
                        if (theWhole.contains("Center") && !theWhole.contains("-")) {
                            if (availablePlayers.size() == 0) {
                                generateButton(theWhole);
                            } else {


                                if (availablePlayers.size() == 1) {
                                    System.out.println("SIZE1");
                                    if (availablePlayers.get(0).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }
                                if (availablePlayers.size() == 2) {
                                    System.out.println("SIZE2");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 3) {
                                    System.out.println("SIZE3");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 4) {
                                    System.out.println("SIZE4");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name) || availablePlayers.get(3).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }

                                if (availablePlayers.size() == 5) {
                                    System.out.println("SIZE5");
                                    if (availablePlayers.get(0).contains(name) || availablePlayers.get(1).contains(name) || availablePlayers.get(2).contains(name) || availablePlayers.get(3).contains(name) || availablePlayers.get(4).contains(name)) {

                                    } else {
                                        generateButton(theWhole);
                                    }
                                }
                            }
                        }

                    }

                } else {

                }
            }
        });
    }


    @Override
    public void onBackPressed() {

        if (backButtonCount >= 1) {

            final MediaPlayer mp = MediaPlayer.create(AddTeamActivity.this, R.raw.backboardshot);
            mp.start();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();

                }
            }, 600);




        } else {
            Toast.makeText(this, "Pressing the back button again will lose unsaved progress.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }


    public void generateButton(String title) {
        ImageView imageView = new ImageView(AddTeamActivity.this);

        ViewGroup layout = (ViewGroup) findViewById(R.id.availiableplayersscroller);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final Button btnTag = new Button(AddTeamActivity.this);
        btnTag.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnTag.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        btnTag.setText(title);


        buttonAction(btnTag);

        layout.addView(btnTag);


    }


    public void clearLayoutTop() {
        ViewGroup layout = (ViewGroup) findViewById(R.id.availiableplayersscroller);
        layout.removeAllViews();
    }


    public void generateButton2(String title) {
        ImageView imageView = new ImageView(AddTeamActivity.this);

        ViewGroup layout = (ViewGroup) findViewById(R.id.pickedplayersscroller);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final Button btnTag = new Button(AddTeamActivity.this);

        btnTag.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        btnTag.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        btnTag.setText(title);


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

                                if (availablePlayers.size() == 5) {
                                    max();
                                } else {
                                    System.out.println(button.getText().toString());

                                    if (button.getText().toString().contains("Guard") && !button.getText().toString().contains("-")) {
                                        System.out.println("test1");
                                        //      one = true;
                                    } else if (button.getText().toString().contains("Forward-Guard")) {
                                        System.out.println("test2");
                                        //       two = true;
                                    } else if (button.getText().toString().contains("Forward-Center")) {
                                        System.out.println("test3");
                                        //     three = true;
                                    } else if (button.getText().toString().contains("Guard-Forward")) {
                                        System.out.println("test4");
                                        //     four = true;
                                    } else if (button.getText().toString().contains("Center") && !button.getText().toString().contains("-")) {
                                        System.out.println("test5");
                                        //      five = true;
                                    }


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

    public void max() {
        Toast.makeText(this, "Reached max player limit", Toast.LENGTH_SHORT).show();
    }

    public void notMax() {
        Toast.makeText(this, "Your team needs five players", Toast.LENGTH_SHORT).show();
    }

    public void nameMax() {
        Toast.makeText(this, "Your team needs a name", Toast.LENGTH_SHORT).show();
    }

    public void superMax() {
        Toast.makeText(this, "Your team needs a name and your team needs five players", Toast.LENGTH_LONG).show();
    }

    public void balanceMax() {
        Toast.makeText(this, "Your team needs a player in each position", Toast.LENGTH_LONG).show();
    }

    public void sameName()
    {
        Toast.makeText(this, "Team name is already in used", Toast.LENGTH_SHORT).show();
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
                                System.out.println(button.getText().toString());
                                if (button.getText().toString().contains("Guard") && !button.getText().toString().contains("-")) {
                                    System.out.println("test6");
                                    one = false;
                                } else if (button.getText().toString().contains("Forward-Guard")) {
                                    System.out.println("test7");
                                    two = false;
                                } else if (button.getText().toString().contains("Forward-Center")) {
                                    System.out.println("test8");
                                    three = false;
                                } else if (button.getText().toString().contains("Guard-Forward")) {
                                    System.out.println("test9");
                                    four = false;
                                } else if (button.getText().toString().contains("Center") && !button.getText().toString().contains("-")) {
                                    System.out.println("test10");
                                    five = false;
                                }
                                button.setVisibility(View.GONE);
                                if (availablePlayers.contains(button.getText().toString())) {
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

//    @Override
//    protected void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
//
//        stopService(new Intent(this, MyService.class));
//    }


}
