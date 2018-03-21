package com.example.kushal.scarneapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static  int overall_player_score;
    public static int overall_computer_score;
    public static int turn_computer_score;
    public static int turn_player_score;

    ImageView diceView;
    TextView scoreView;
    Button rollButton,holdButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * My code below.
     * -->Roll is the onCLick method for the roll button, which generates the random value of die from the array.
     * --> Used by the player.
     * @param view
     */
    public void roll(View view)
    {
        scoreView=findViewById(R.id.scoreView);
        diceView=findViewById(R.id.diceView);

        // Array of image resources of the dice values.
        int image_resources[]={R.drawable.dice1,R.drawable.dice2,
                R.drawable.dice3,R.drawable.dice4,
                R.drawable.dice5,R.drawable.dice6};


        int random_choice=new Random().nextInt(image_resources.length);

        // simple animation
        diceView.animate().rotationBy(50f).setDuration(100);

        diceView.setImageResource(image_resources[random_choice]);

        int pt=random_choice+1;

        // Checks if dice is not 1 and winning condition is not met(50 points),it then increments the players score.
        //if the random value generated is not one.It carries out various updates ,
        // adds the player's turn score until he holds
        if(pt!=1&&turn_player_score<50)
        {
            turn_player_score+=pt;
            scoreView.setText("Your score is : "+overall_player_score+
                               "\n"+"Computer score is: "+overall_computer_score+
                               "\n"+"Your turn score is: "+turn_player_score);

        }

        // If player's score exceeds 50 it declares the winner.
        if(turn_player_score>=50)
        {
            scoreView.setText("Your score is : "+turn_player_score+"\n"+"Computer score is: "+overall_computer_score);
            Toast.makeText(this, "Player Wins", Toast.LENGTH_SHORT).show();
        }

        // .If the dice is 1, the player loses his turn and loses all his turn's points.
        // Game control now shifts to the computer.
        else if(pt==1)
        {
             //overall_player_score+=turn_player_score;
            turn_player_score=0;
            scoreView.setText("Your score is : "+overall_player_score+"\n"+"Computer score is: "+overall_computer_score);

            //Handler introduces time-delay for computer's turn so that the player can make out whether the computer has
            // played or not.
            // Slows down computer's turn.
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    computerTurn();
                }
            },5000);

        }
    }


    /**
     * My code below.
     * Hold is clicked once the player feels satisfied of his turn's score.
     * That score gets added to his total score.
     * @param view
     */
    public void hold(View view)
    {
        if(overall_player_score==0&&turn_player_score==0)
        {
            Toast.makeText(this, "Invalid Move", Toast.LENGTH_SHORT).show();
        }
        else {
            overall_player_score += turn_player_score;
            turn_player_score = 0;
            scoreView.setText("Your score is: " + overall_player_score + "\n" + "Computer score is: " + overall_computer_score);
            if (overall_player_score >= 100)
                Toast.makeText(this, "Player Wins", Toast.LENGTH_SHORT).show();
            else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        computerTurn();
                    }
                },5000);
            }
        }
    }

    /**
     * Resets Everything
     * For a new game.
     * @param view
     */
    public void reset(View view)
    {
        overall_player_score=0;
        overall_computer_score=0;
        turn_player_score=0;
        turn_computer_score=0;
        scoreView.setText("Your score is: "+overall_player_score+"\n"+"Computer score is: "+overall_computer_score);
    }

    /**
     * My code below.
     * computerTurn method pre-defines how the computer is going to play.
     * Even the computer can randomly run into a "1" dice value;in which case the control is passed back to the player
     * The increment logic / hold logic of the computer is pre-defined.
     * The computer plays until he runs into "1" or until its turn score becomes greater than 10;
     * after which it will hold automatically.
     */
    public void  computerTurn()
    {
        rollButton=findViewById(R.id.button);
        rollButton.setEnabled(false);
        holdButton=findViewById(R.id.button2);
        holdButton.setEnabled(false);

        int random_point=0;
        while(random_point!=1)
        {
            random_point=new Random().nextInt(6);
            Log.i("computer_dice_value: ",random_point+"");
            turn_computer_score+=random_point;

            if(turn_computer_score>10)
              break;
        }

        if(random_point==1)
        {
            turn_computer_score=0;
            Toast.makeText(this, "Computer rolled a 1", Toast.LENGTH_SHORT).show();
            scoreView.setText("Your score is: "+overall_player_score+"\n"+"Computer score is: "+overall_computer_score);
        }
        else
        {
            overall_computer_score+=turn_computer_score;
            if(overall_computer_score>=50) {
                scoreView.setText("Your score is: " + overall_player_score + "\n" + "Computer score is: " + overall_computer_score);
                Toast.makeText(this, "Computer  Wins", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Computer holds", Toast.LENGTH_SHORT).show();
                scoreView.setText("Your score is: " + overall_player_score + "\n" + "Computer score is: " + overall_computer_score);
                turn_computer_score = 0;
            }
        }



        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
    }
}
