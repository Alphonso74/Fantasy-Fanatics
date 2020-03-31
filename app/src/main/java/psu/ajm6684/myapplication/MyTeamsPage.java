package psu.ajm6684.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class MyTeamsPage extends AppCompatActivity {


    Button preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_teams_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        preference = findViewById(R.id.preferenceButton);
        preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToPreference();
            }
        });

    }

    public void moveToPreference()
    {
        Intent confirmPage = new Intent(getApplicationContext(), PreferenceActivity.class);
        startActivity(confirmPage);
    }


}
