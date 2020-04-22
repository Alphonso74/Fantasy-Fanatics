package psu.ajm6684.myapplication;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Scores extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String Scores;
    Button View;


    int count = 0;
    private CollectionReference users = db.collection("Users");
    FirebaseAuth firebaseAuth;
    ArrayList<String> scores = new ArrayList<>();
   
}
