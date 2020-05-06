package psu.ajm6684.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

import pl.droidsonroids.gif.GifImageView;

public class giflabs extends AppCompatActivity {
    int[] gifs = {R.drawable.mj,R.drawable.steph, R.drawable.failboy,R.drawable.allen,R.drawable.zingus,R.drawable.ray,R.drawable.lavine,R.drawable.carter,
    R.drawable.sorry,R.drawable.broncavs,R.drawable.jr,R.drawable.zion,R.drawable.morecarter,R.drawable.ew, R.drawable.hardencook,R.drawable.girlfail,R.drawable.spacejam,
    R.drawable.jamescross};

    Button next;
    GifImageView view;
    int currentImage;


    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser current;
    DocumentReference mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giflabs);




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







        view = (GifImageView)  findViewById(R.id.gifviewer);
        next = (Button) findViewById(R.id.nextButton);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImage++;
                currentImage = currentImage % gifs.length;

                view.setImageResource(gifs[currentImage]);

            }
        });
    }

    @Override
    public void onBackPressed() {


            final MediaPlayer mp = MediaPlayer.create(giflabs.this, R.raw.backboardshot);
            mp.start();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();

                }
            }, 600);


    }

//    @Override
//    protected void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
//
//        stopService(new Intent(this, MyService.class));
//    }

}
