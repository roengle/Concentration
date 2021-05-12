package cs2450.swingsharp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs2450.swingsharp.util.NameScorePair;

public class HighScoresScreen extends AppCompatActivity {
    int difficulty;
    String scoreOne;
    String scoreTwo;
    String scoreThree;
    final String FILE_NAME = "highscores.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_scores_screen);

        //Back Button code (Return to main screen)
        Button backButton = findViewById(R.id.backButton2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Get extras
        Bundle extras = getIntent().getExtras();
        //Null check
        if(extras !=null){
            //Set our difficulty
            this.difficulty = extras.getInt("difficulty");
        }
        TextView difficultyDebugText = findViewById(R.id.currentDifficultyText2);
        difficultyDebugText.setText("Difficulty: " + difficulty + " Cards");            //set difficulty text

        //Set the top three scorers
        setTopScores();
    }

    /**
     * Sets the top three scores for the current difficulty. The difficulty is designated in the
     * start menu.
     */
    private void setTopScores() {
        //Initialize objects to be setup later
        FileInputStream fis = null;
        String input = null;
        //Setup our fileinput stream
        try {
            //Open file stream
            fis = openFileInput(FILE_NAME);
            //Create a new input stream reader and buffered reader
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            //File will only contain one line, so read only once.
            input = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Initialize JSON array that will contain individual JSON objects representing user-score pairs.
        JSONArray scoreData = null;
        //Initialize a list of NameScorePair that will be sorted to find the top 3
        List<NameScorePair> nameScorePairs = new ArrayList<>();
        try {
            //Get the respective JSON array for our difficulty
            scoreData = new JSONObject(input).getJSONArray(String.format("%s-scores", difficulty));
            //Loop through each JSONObject in the JSONArray
            for(int i = 0; i < scoreData.length(); i++){
                //Get the JSONObject
                JSONObject element = scoreData.getJSONObject(i);
                //Create a new NameScorePair for the JSONObject
                NameScorePair nameScorePair = new NameScorePair(element.getString("username"), element.getInt("score"));
                //Add the name-score pair to the list
                nameScorePairs.add(nameScorePair);
            }
        } catch (JSONException e) {
            Log.d("json-error", e.getMessage());
        }

        //Sort the list by values(since it implements Comparable, it can be sorted)
        Collections.sort(nameScorePairs);
        //Get values from list based on size of list.
        if(nameScorePairs.size() == 1){
            scoreOne = String.format("%s - %d", nameScorePairs.get(0).getName(), nameScorePairs.get(0).getScore());
            scoreTwo = "";
            scoreThree = "";
        }
        else if (nameScorePairs.size() == 2){
            scoreOne = String.format("%s - %d", nameScorePairs.get(0).getName(), nameScorePairs.get(0).getScore());
            scoreTwo = String.format("%s - %d", nameScorePairs.get(1).getName(), nameScorePairs.get(1).getScore());
            scoreThree = "";
        }
        else if(nameScorePairs.size() >= 3){
            scoreOne = String.format("%s - %d", nameScorePairs.get(0).getName(), nameScorePairs.get(0).getScore());
            scoreTwo = String.format("%s - %d", nameScorePairs.get(1).getName(), nameScorePairs.get(1).getScore());
            scoreThree = String.format("%s - %d", nameScorePairs.get(2).getName(), nameScorePairs.get(2).getScore());
        }
        //Debug message to make sure everything appears correctly.
        Log.d("scores-info", scoreOne + ", " + scoreTwo + ", " + scoreThree);

        TextView scoreOneText = findViewById(R.id.scoreOne);
        scoreOneText.setText(scoreOne);
        TextView scoreTwoText = findViewById(R.id.scoreTwo);
        scoreTwoText.setText(scoreTwo);
        TextView scoreThreeText = findViewById(R.id.scoreThree);
        scoreThreeText.setText(scoreThree);

    }
}
