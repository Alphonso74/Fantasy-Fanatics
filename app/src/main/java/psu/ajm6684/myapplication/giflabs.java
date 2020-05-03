package psu.ajm6684.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pl.droidsonroids.gif.GifImageView;

public class giflabs extends AppCompatActivity {
    int[] gifs = {R.drawable.mj,R.drawable.steph, R.drawable.failboy,R.drawable.allen,R.drawable.zingus,R.drawable.ray,R.drawable.lavine,R.drawable.carter,
    R.drawable.sorry,R.drawable.broncavs,R.drawable.jr,R.drawable.zion,R.drawable.morecarter,R.drawable.ew, R.drawable.hardencook,R.drawable.girlfail,R.drawable.spacejam,
    R.drawable.jamescross};

    Button next;
    GifImageView view;
    int currentImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giflabs);



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
}
