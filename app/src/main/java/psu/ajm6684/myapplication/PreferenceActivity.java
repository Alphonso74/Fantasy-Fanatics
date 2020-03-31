package psu.ajm6684.myapplication;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class PreferenceActivity  extends AppCompatActivity {


    Switch aSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.preference_page);

        aSwitch = findViewById(R.id.switch1);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    getDelegate().setLocalNightMode((AppCompatDelegate.MODE_NIGHT_YES));
                }
                else
                {
                    getDelegate().setLocalNightMode((AppCompatDelegate.MODE_NIGHT_NO));
                }



            }
        });

    }
}
