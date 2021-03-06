package psu.ajm6684.myapplication;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {



    Handler handler = new Handler();
    @Override
    protected void onStart() {
        super.onStart();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },3700);
            }
        });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.splash_activity);

        startService(new Intent(this, MyService.class));

    }
//    private static int SPLASH_TIME_OUT = 4000;
//    Animation topAnim, bottomAnim, rotateAnim;
//    ImageView image;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_splash_screen);
//        getSupportActionBar().hide();
//
//        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
//        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
//        rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
//
//        image = findViewById(R.id.imageView2);
//        image.setAnimation(topAnim);
//        image.setAnimation(rotateAnim);
//
//        new Handler().postDelayed(new Runnable(){
//
//            @Override
//            public void run(){
//                Intent splashIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
//                startActivity(splashIntent);
//                SplashScreenActivity.this.finish();
//            }
//
//        },SPLASH_TIME_OUT);
//
//
//    }
}
