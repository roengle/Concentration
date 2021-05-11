package cs2450.swingsharp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
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
    int score = 0;
    boolean secondCard = false;
    boolean wrongPair = false;
    String lastCard = "";
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
                //send to highscores screen
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

        //Creates an X amount of cards based on the array of words (max of 5rows x 4col)
        int tempNum;
        Button[] cards = new Button[difficulty];
        for(int i = 1; i <= difficulty; i++){
            //First row
            if(i >= 1 && i <= 4) {
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }
                button.setText(word);
                cards[i-1] = button;
                createCardOnClick(button);
                tablerow1.addView(button, tlp);
            }
            //Second row
            if(i >= 5 && i <= 8){
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }
                button.setText(word);
                cards[i-1] = button;
                createCardOnClick(button);
                tablerow2.addView(button, tlp);
            }
            //Third row
            if(i >= 9 && i <= 12){
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }
                button.setText(word);
                cards[i-1] = button;
                createCardOnClick(button);
                tablerow3.addView(button, tlp);
            }
            //Fourth row
            if(i >= 13 && i <= 16){
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }
                button.setText(word);
                cards[i-1] = button;
                createCardOnClick(button);
                tablerow4.addView(button, tlp);
            }
            //Fifth row
            if(i >= 17){
                Button button = new Button(this);
                String word=null;
                while(word==null){
                    tempNum = r.nextInt(chosenWords.length);
                    word = chosenWords[tempNum];
                    chosenWords[tempNum] = null;
                }
                button.setText(word);
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
                if(!secondCard){
                    lastCard = button.getText().toString();
                    button.setTextColor(Color.WHITE);
                    button.setEnabled(false);
                    secondCard = true;
                    incorrectPair[0] = button;
                }
                else{
                    if(button.getText().toString().equals(lastCard)){
                        button.setTextColor(Color.WHITE);
                        button.setEnabled(false);
                        addScore(2);
                        incorrectPair = new Button[2];
                    }
                    else{
                        button.setTextColor(Color.WHITE);
                        for(int j = 0; j < cards.length; j++){
                            cards[j].setEnabled(false);
                        }
                        addScore(-1);
                        incorrectPair[1] = button;
                        wrongPair = true;
                    }
                    secondCard = false;
                }

            }
        });
        button.setTextColor(Color.TRANSPARENT);
    }




}