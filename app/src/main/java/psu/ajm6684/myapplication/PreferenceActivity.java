package psu.ajm6684.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PreferenceActivity  extends AppCompatActivity {


    Switch aSwitch;

    Button backButton;
    Button done;

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser current;
    DocumentReference mode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_page);
        aSwitch = findViewById(R.id.switch1);
        done = findViewById(R.id.logoutButtonPref);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


                AlertDialog.Builder builder = new AlertDialog.Builder(PreferenceActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Do you wish to exit?");
                builder.setTitle("")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final MediaPlayer mp = MediaPlayer.create(PreferenceActivity.this, R.raw.singledribble);
                                mp.start();

//                                Intent confirmPage = new Intent(PreferenceActivity.this, MainActivity.class);
//                                startActivity(confirmPage);
//                                System.exit(1);

                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();

//                                final Handler handler = new Handler();
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        //Do something after 100ms
//
//
//                                    }
//                                }, 500);





                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });





                builder.create();
                builder.show();

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        current = firebaseAuth.getCurrentUser();

        //View currentFocus = getWindow().getCurrentFocus();


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
                                aSwitch.setChecked(false);
                                getDelegate().setLocalNightMode((AppCompatDelegate.MODE_NIGHT_NO));
                                break;
                            }

                            if (s.equals("dark"))
                            {
                                aSwitch.setChecked(true);
                                getDelegate().setLocalNightMode((AppCompatDelegate.MODE_NIGHT_YES));
                                break;
                            }

                        }
                    }
                }
            }
        });



        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    getDelegate().setLocalNightMode((AppCompatDelegate.MODE_NIGHT_YES));

                    firestore.collection("Users").document(current.getUid()).update("mode", "dark");


                }
                else
                {
                    getDelegate().setLocalNightMode((AppCompatDelegate.MODE_NIGHT_NO));
                    firestore.collection("Users").document(current.getUid()).update("mode", "light");
                }

            }
        });

    }


    @Override
    public void onBackPressed() {


        final MediaPlayer mp = MediaPlayer.create(PreferenceActivity.this, R.raw.backboardshot);
        mp.start();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), MyTeamsPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                PreferenceActivity.this.finish();

            }
        }, 600);



    }


}
