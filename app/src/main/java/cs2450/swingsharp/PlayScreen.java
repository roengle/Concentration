package cs2450.swingsharp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class PlayScreen extends AppCompatActivity {

    String[] wordBank = new String[] {
            "Sheep", "Monkey", "Rooster", "Dog", "Boar",
            "Rat", "Ox", "Tiger", "Rabbit", "Dragon"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_screen);

        createCards();

        //End Game Button code (Return to main screen)
        Button endGameButton = findViewById(R.id.endGameButton);
        endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //clear grid
            }
        });

    }

    //Creates buttons/Cards based on the difficulty
    public void createCards(){
        Bundle extras = getIntent().getExtras();
        int difficulty = 0;
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

        //Creates an X amount of cards based on the array of words (max of 5rows x 4col)
        int tempNum;
        for(int i = 1; i <= difficulty; i++){
            if(i >= 1 && i <= 4) {
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }
                button.setText(word);
                button.setHeight(100);
                tablerow1.addView(button, tlp);
            }
            if(i >= 5 && i <= 8){
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }
                button.setText(word);
                tablerow2.addView(button, tlp);
            }
            if(i >= 9 && i <= 12){
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }
                button.setText(word);
                tablerow3.addView(button, tlp);
            }
            if(i >= 13 && i <= 16){
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }
                button.setText(word);
                tablerow4.addView(button, tlp);
            }
            if(i >= 17){
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }
                button.setText(word);
                tablerow5.addView(button, tlp);
            }
        }


    }





}