package psu.ajm6684.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button confirm;
    Button signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        confirm = findViewById(R.id.confirmMainPage);
        signup = findViewById(R.id.confirmCreateAccount);

    }


    public void confirmChangePage(View view)
    {
        Intent confirmPage = new Intent(view.getContext(), MyTeamActivity.class);
        startActivity(confirmPage);


    }


    public void signupChangePage(View view)
    {
        Intent signupPage = new Intent(view.getContext(), CreateAccountActivity.class);
        startActivity(signupPage);


    }






}
