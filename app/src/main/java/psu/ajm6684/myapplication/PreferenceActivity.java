package psu.ajm6684.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class PreferenceActivity  extends AppCompatActivity {


    Switch aSwitch;

    Button backButton;

    String Uid;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser current;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.preference_page);

        backButton = (Button) findViewById(R.id.backbutton);
        aSwitch = findViewById(R.id.switch1);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        current = firebaseAuth.getCurrentUser();








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


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
