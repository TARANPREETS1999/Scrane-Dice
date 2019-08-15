package com.thephenom.scarnesdice;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private int userOverallScore;
    private int userTurnScore;
    private int comOverallScore;
    private int comTurnScore;

    TextView score;
    ImageView diceImage;
    Button rollButton;
    Button holdButton;
    Button resetButton;

    int dice[]={R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,R.drawable.dice4,R.drawable.dice5,R.drawable.dice6};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score=(TextView) findViewById(R.id.score);
        diceImage=(ImageView) findViewById(R.id.dice_image);
        rollButton=(Button) findViewById(R.id.button_roll);
        holdButton=(Button) findViewById(R.id.button_hold);
        resetButton=(Button) findViewById(R.id.button_reset);


        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: roll button clicked!");
                int randomDice=rollDice();
                randomDice++;
                Log.d(TAG, "onClick: randomDice--->"+randomDice);
                if(randomDice==1){
                    Log.d(TAG, "UserOverall--"+userOverallScore+"userturn--"+userTurnScore+"compOverall--"+comOverallScore+"compturn--"+comTurnScore);
                    score.setText("Your Score: "+userOverallScore +" Computer Score: "+comOverallScore);
                    userTurnScore=0;
                    computerTurn();
                }else{
                    userTurnScore+=randomDice;
                    Log.d(TAG, "UserOverall--"+userOverallScore+"userturn--"+userTurnScore+"compOverall--"+comOverallScore+"compturn--"+comTurnScore);
                    score.setText("Your Score: "+userOverallScore +" Computer Score: "+comOverallScore+" Your Turn Score: "+userTurnScore);
                }
            }

        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: reset clciked");
                userOverallScore=0;
                userTurnScore=0;
                comOverallScore=0;
                comTurnScore=0;

                score.setText("Your Score: "+userOverallScore +" Computer Score: "+comOverallScore);
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: hold clciked");
                userOverallScore+=userTurnScore;
                userTurnScore=0;

                if(userOverallScore>100){
                    score.setText("You Won!!");
                    return;
                }

                Log.d(TAG, "onClick: roll---useroverall--"+userOverallScore+"computerove--"+comOverallScore);
                score.setText("Your Score: "+userOverallScore +" Computer Score: "+comOverallScore);
                computerTurn();
            }
        });
    }

    public int rollDice(){
        Log.d(TAG, "rollDice: called");
        Random random=new Random();
        int randomDice=random.nextInt(6);
        diceImage.setImageResource(dice[randomDice]);
        Log.d(TAG, "rollDice: "+randomDice);
        Log.d(TAG, "rollDice: ends");
        return randomDice;
    }

    private void computerTurn(){
        Log.d(TAG, "computerTurn: starts");
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);
        resetButton.setEnabled(false);

        //while(true){
        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Log.d(TAG, "UserOverall--"+userOverallScore+"userturn--"+userTurnScore+"compOverall--"+comOverallScore+"compturn--"+comTurnScore);
                int randomDice=rollDice();
                randomDice++;
                Log.d(TAG, "computerTurn: randomdice "+randomDice);

                if(randomDice==1){
                    Log.d(TAG, "computerTurn: compter rolled "+randomDice);
                    Toast.makeText(getApplicationContext(),"Computer rolled a "+randomDice,Toast.LENGTH_SHORT).show();
                    comTurnScore=0;
                    userTurn();
                    Log.d(TAG, "computerTurn: exitng compyerturn from dice==1");
                    // break;
                    return;
                }else{
                    Log.d(TAG, "computerTurn: compter rolled "+randomDice);
                    comTurnScore+=randomDice;
                    if(comOverallScore+comTurnScore>100){
                        score.setText("Computer Won!!");
                        return;
                    }
                    if(comTurnScore>=20){
                        userTurn();
                        return;
                        // break;
                    }else{
                        computerTurn();
                    }

                    Toast.makeText(getApplicationContext(),"Computer rolled a "+randomDice,Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "UserOverall--"+userOverallScore+"userturn--"+userTurnScore+"compOverall--"+comOverallScore+"compturn--"+comTurnScore);
                    score.setText("Your Score: "+userOverallScore +" Computer Score: "+comOverallScore+" Computer Turn Score: "+comTurnScore);
                }
            }
        }.start();



     //   }
        Log.d(TAG, "computerTurn: ends");
    }

    private void userTurn(){
        Log.d(TAG, "userTurn: starts");
        comOverallScore+=comTurnScore;
        comTurnScore=0;
        Log.d(TAG, "UserOverall--"+userOverallScore+"userturn--"+userTurnScore+"compOverall--"+comOverallScore+"compturn--"+comTurnScore);
        score.setText("Your Score: "+userOverallScore +" Computer Score: "+comOverallScore);
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
        resetButton.setEnabled(true);
        Log.d(TAG, "userTurn: ends");
    }
}
