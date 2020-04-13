package psu.ajm6684.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class gamesimulator extends AppCompatActivity {

    Integer team1Score;
    Integer team2score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamesimulator);

        team1Score = 0;
        team2score = 0;



        Intent intent = getIntent();

        String teamName1 = intent.getStringExtra("Team1");
        String Guard1 = intent.getStringExtra("Guard1");
        String ForwardGuard1 = intent.getStringExtra("ForwardGuard1");
        String GuardForward1 = intent.getStringExtra("GuardForward1");
        String ForwardCenter1 = intent.getStringExtra("ForwardCenter1");
        String Center1 = intent.getStringExtra("Center1");
        Toast.makeText(gamesimulator.this, "Name1 " + teamName1, Toast.LENGTH_SHORT).show();


        String teamName2 = intent.getStringExtra("Team2");
        String Guard2 = intent.getStringExtra("Guard2");
        String ForwardGuard2 = intent.getStringExtra("ForwardGuard2");
        String GuardForward2 = intent.getStringExtra("GuardForward2");
        String ForwardCenter2 = intent.getStringExtra("ForwardCenter2");
        String Center2 = intent.getStringExtra("Center2");

        Toast.makeText(gamesimulator.this, "Name2 " + teamName2, Toast.LENGTH_SHORT).show();

    }

    public void back2Teams(View view) {

        Intent confirmPage = new Intent(this, MyTeamsPage.class);
        startActivity(confirmPage);
    }
}
