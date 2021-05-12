package cs2450.swingsharp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class PlayScreen extends AppCompatActivity {

    String[] wordBank = new String[] {
            "Sheep", "Monkey", "Rooster", "Dog", "Boar",
            "Rat", "Ox", "Tiger", "Rabbit", "Dragon"};
    int score = 0;
    boolean secondCard = false;
    boolean wrongPair = false;
    String lastCard = "";
    String username = "";
    int difficulty;
    Button[] cards;
    Button[] incorrectPair = new Button[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_screen);

        cards = createCards();

        //New Game Button code (Return to main screen)
        Button newGameButton = findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                finish();
            }
        });

        //End Game Button code (show all answers)
        Button endGameButton = findViewById(R.id.endGameButton);
        endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Button button : cards){
                    button.setTextColor(Color.WHITE);
                }
                //Prompt user to enter a name
                AlertDialog.Builder builder = new AlertDialog.Builder(PlayScreen.this);
                builder.setTitle("New Score! Please enter a name!");

                // Set up the input
                final EditText input = new EditText(PlayScreen.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        username = input.getText().toString();
                        //send to highscores screen
                        saveScore(score, username);
                        dialog.cancel();
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        //Try Again button code (Re-enables all buttons)
        Button tryAgainButton = findViewById(R.id.tryAgainButton);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wrongPair) {
                    for (Button button : incorrectPair) {
                        button.setTextColor(Color.TRANSPARENT);
                    }
                    for (Button button : cards) {
                        button.setEnabled(true);
                    }
                    wrongPair = false;
                }
            }
        });


    }

    //Creates buttons/Cards based on the difficulty
    private Button[] createCards(){
        score = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            difficulty = extras.getInt("difficulty");
        }

        TextView difficultyDebugText = findViewById(R.id.currentDifficultyText);
        difficultyDebugText.setText("Difficulty: " + difficulty + " Cards");

        TableRow tablerow1 = (TableRow)findViewById(R.id.tablerow1);
        TableRow tablerow2 = (TableRow)findViewById(R.id.tablerow2);
        TableRow tablerow3 = (TableRow)findViewById(R.id.tablerow3);
        TableRow tablerow4 = (TableRow)findViewById(R.id.tablerow4);
        TableRow tablerow5 = (TableRow)findViewById(R.id.tablerow5);
        TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        tlp.gravity = Gravity.CENTER_HORIZONTAL;

        //Creates an array of words used in the current game
        Random r = new Random();
        String[] chosenWords = new String[difficulty];
        for (int i = 0; i < difficulty / 2; i++) {  //Ex: if 14 cards, pick out 7 names
            chosenWords[i] = wordBank[i];
            chosenWords[i+(difficulty/2)] = wordBank[i];
        }


        int tempNum;
        int row1_start = 1;
        int row1_end = 4;
        int row2_start = 5;
        int row2_end = 8;
        int row3_start = 9;
        int row3_end = 12;
        int row4_start = 13;
        int row4_end = 16;
        int row5_start = 17;
        int button_height = 260;
        int button_width = 60;

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            row1_start = 1;
            row1_end = 5;
            row2_start = 6;
            row2_end = 10;
            row3_start = 11;
            row3_end = 15;
            row4_start = 16;
            row4_end = 20;
            row5_start = 25;
            button_height = 60;
            button_width = 100;
        }
        //Creates an X amount of cards based on the array of words (max of 5rows x 4col)
        Button[] cards = new Button[difficulty];
        for(int i = 1; i <= difficulty; i++){
            //First row
            if(i >= row1_start && i <= row1_end) {
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }

                button.setText(word);
                //button.setHeight(button_height);
                //button.setWidth(button_width);
                //button.setBackgroundResource(R.drawable.hmm);

                cards[i-1] = button;
                createCardOnClick(button);
                tablerow1.addView(button, tlp);
            }
            //Second row
            if(i >= row2_start && i <= row2_end){
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }
                button.setText(word);
               // button.setHeight(button_height);
                //button.setWidth(button_width);
                cards[i-1] = button;
                createCardOnClick(button);
                tablerow2.addView(button, tlp);
            }
            //Third row
            if(i >= row3_start && i <= row3_end){
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }
                button.setText(word);
              //  button.setHeight(button_height);
              //  button.setWidth(button_width);
                cards[i-1] = button;
                createCardOnClick(button);
                tablerow3.addView(button, tlp);
            }
            //Fourth row
            if(i >= row4_start && i <= row4_end){
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }
                button.setText(word);
             //   button.setHeight(button_height);
             //   button.setWidth(button_width);
                cards[i-1] = button;
                createCardOnClick(button);
                tablerow4.addView(button, tlp);
            }
            //Fifth row
            if(i >= row5_start){
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }
                button.setText(word);
              //  button.setHeight(button_height);
              //  button.setWidth(button_width);
                cards[i-1] = button;
                createCardOnClick(button);
                tablerow5.addView(button, tlp);
            }
        }

        return cards;

    }

    //Used to change score
    private void addScore(int points){
        TextView scoreText = findViewById(R.id.scoreText);
        if(points < 0 && score == 0){
            score += 0;
        }
        else if(score >= 0){
            score += points;
            scoreText.setText("Score: " + score);
        }
    }

    //Creates an onClick Listener on each button
    private void createCardOnClick(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button.getCurrentTextColor() == Color.TRANSPARENT) {
                    if (!secondCard) {
                        lastCard = button.getText().toString();
                        button.setTextColor(Color.WHITE);
                        button.setEnabled(false);
                        secondCard = true;
                        incorrectPair[0] = button;
                    } else {
                        if (button.getText().toString().equals(lastCard)) {
                            button.setTextColor(Color.WHITE);
                            button.setEnabled(false);
                            addScore(2);
                            incorrectPair = new Button[2];
                        } else {
                            button.setTextColor(Color.WHITE);
                            for (int j = 0; j < cards.length; j++) {
                                cards[j].setEnabled(false);
                            }
                            addScore(-1);
                            incorrectPair[1] = button;
                            wrongPair = true;
                        }
                        secondCard = false;
                    }
                }

            }
        });
        button.setTextColor(Color.TRANSPARENT);
    }


    /**
     * Saves the score with the associated username in highscores.json. If highscores.json does not
     * exist, a new file is created with template json. The following is an example of how the JSON
     * file is structured.
     *
     * PRECONDITION: Username needs to be set before calling this method.
     *
     * @param score the score the player has
     * @param name the username of the player
     */
    private void saveScore(int score, String name){
        //Pointer to file name
        final String FILE_NAME = "highscores.json";
        //Initialize empty stream to read input in
        String input = null;
        //Create new file input stream
        FileInputStream fis = null;

        //Read input from json file
        try {
            //Setup our fileinput stream
            fis = openFileInput(FILE_NAME);
            //Create a new input stream reader and buffered reader
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            //File will only contain one line, so read only once.
            input = br.readLine();
        } catch (FileNotFoundException e) {
            //File doesn't exist, create file.
            Log.d("new-json-file", "File does not exist, creating new file highscores.json with template JSON.");
            //Create a new file output stream
            FileOutputStream fos = null;
            //Template JSON text which contains all the fields we need, which can accept new scores
            String baseText = "{\"4-scores\":[],\"6-scores\":[],\"8-scores\":[],\"10-scores\":[]," +
                    "\"12-scores\":[],\"14-scores\":[],\"16-scores\":[],\"18-scores\":[],\"20-scores\":[]}";
            try {
                //Open the file output stream
                fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                //Write to the file
                fos.write(baseText.getBytes());
                //Notify user
                Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } finally{
                try {
                    //Close file output stream
                    fos.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            //Close input stream when done
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Create new JSONObject
        JSONObject jsonData = null;
        try {
            jsonData = new JSONObject(input);
        } catch (JSONException e) {
            Log.d("json-error", e.getMessage());
        }
        //Add current score to JSON object
        String query = String.format("%s-scores", this.difficulty);
        try {
            JSONArray scoreArray = jsonData.getJSONArray(String.format("%s-scores", difficulty));
            JSONObject entry = new JSONObject().put("username", username).put("score", score);
            scoreArray.put(entry);
            Log.d("json-insert","Put " + entry.toString() + " into " + difficulty + "-scores.");

        } catch (JSONException e) {
            Log.d("json-error", e.getMessage());
        }
        //Write JSON string to file
        FileOutputStream fos = null;
        try {
            //Create a new output stream
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            //Write the single-line JSON output to the file
            fos.write(jsonData.toString().getBytes());

            //Output message to user
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                //Close the file output stream
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}