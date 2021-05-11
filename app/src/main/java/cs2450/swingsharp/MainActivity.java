package cs2450.swingsharp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean musicOn = false;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Spinner code
        String[] arraySpinner = new String[] {
                "4 Cards", "6 Cards", "8 Cards", "10 Cards",
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

        //Credits Button code (Switch screens)
        Button creditsButton = findViewById(R.id.creditsButton);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getApplicationContext(), CreditsScreen.class);
                startActivity(j); //opens credit screen
            }
        });

        //High Scores Button code (Switch screens)
        Button highScoresButton = findViewById(R.id.highScoresButton);
        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getApplicationContext(), HighScoresScreen.class);
                startActivity(j); //opens credit screen
            }
        });

        //Music button code (turn music on/off)
        Button musicButton = findViewById(R.id.musicButton);
        //AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!musicOn) {
                    if(mediaPlayer == null) {
                        mediaPlayer =  MediaPlayer.create(MainActivity.this, R.raw.suspect);
                        mediaPlayer.setLooping(true);
                        mediaPlayer.start();
                        musicOn = true;
                        musicButton.setText("Music: On");
                        Toast.makeText(MainActivity.this, "Music is now on", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    mediaPlayer.pause();
                    mediaPlayer = null;
                    musicOn = false;
                    musicButton.setText("Music: Off");
                    Toast.makeText(MainActivity.this, "Music is now off", Toast.LENGTH_SHORT).show();
                }

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