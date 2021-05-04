package cs2450.swingsharp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Spinner code
        String[] arraySpinner = new String[] {
                "2 Cards", "4 Cards", "6 Cards", "8 Cards", "10 Cards",
                "12 Cards", "14 Cards", "16 Cards", "18 Cards", "20 Cards"};
        Spinner s = (Spinner) findViewById(R.id.difficultySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //Play Button code (Switch screens)
        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int difficulty = getDifficulty();
                Intent i = new Intent(getApplicationContext(), PlayScreen.class);
                i.putExtra("difficulty",difficulty);
                startActivity(i);
                //switchActivitiesWithData();
            }
        });




    }
    //Create cards based on the difficulty
    private int getDifficulty(){
        Spinner s = (Spinner) findViewById(R.id.difficultySpinner);
        String difficultyText = s.getSelectedItem().toString();
        difficultyText = difficultyText.substring(0, difficultyText.indexOf(" "));
        int difficulty = Integer.parseInt(difficultyText);

        return difficulty;
    }

    //Switches to PlayScreen
    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, PlayScreen.class);
        startActivity(switchActivityIntent);
    }

    //Switches to PlayScreen with Data
    private void switchActivitiesWithData() {
        Intent switchActivityIntent = new Intent(this, PlayScreen.class);
        switchActivityIntent.putExtra("message", "From: " + MainActivity.class.getSimpleName());
        startActivity(switchActivityIntent);
    }


}