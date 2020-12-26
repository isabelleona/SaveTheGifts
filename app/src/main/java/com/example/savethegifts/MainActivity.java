package com.example.savethegifts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //Elements
    private TextView scoreLabel, startLabel;
    private ImageView bag, gift1, gift1_2, gift2, gift3, coal1, coal2;

    //Size
    private int screenHeight;
    private int screenWidth;
    private int frameWidth;
    private int boxHeigth, boxWidth;

    //Position
    private float boxX;
    private float gift1X, gift1Y;
    private float gift1_2X, gift1_2Y;
    private float gift2X, gift2Y;
    private float gift3X, gift3Y;
    private float coal1X, coal1Y, coal2X, coal2Y;

    //Score
    private int score;

    //Timer
    private Timer timer = new Timer();
    private Handler handler = new Handler();

    //Status
    private boolean action_flg = false;
    private boolean start_flg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreLabel = findViewById(R.id.scoreLabel);
        startLabel = findViewById(R.id.startLabel);
        bag = findViewById(R.id.bag);
        gift1 = findViewById(R.id.gift1);
        gift1_2 = findViewById(R.id.gift1_2);
        gift2 = findViewById(R.id.gift2);
        gift3 = findViewById(R.id.gift3);
        coal1 = findViewById(R.id.coal1);
        coal2 = findViewById(R.id.coal2);

        //Screen Size
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        //Initial Positions
        gift1.setX(-80.0f);
        gift1.setY(-80.0f);
        gift1_2.setX(-80.0f);
        gift1_2.setY(-80.0f);
        gift2.setX(-80.0f);
        gift2.setY(-80.0f);
        gift3.setX(-80.0f);
        gift3.setY(-80.0f);
        coal1.setX(-80.0f);
        coal1.setY(-80.0f);
        coal2.setX(-80.0f);
        coal2.setY(-80.0f);

        scoreLabel.setText("Score : " + score);
    }

    public void changePos() {

        hitCheck();

        //Gift 1 - most common gift
        gift1Y += 12;
        if (gift1Y > screenHeight) {
            gift1Y = -20;
            gift1X = (float)Math.floor(Math.random()*(frameWidth - gift1.getWidth()));
        }
        gift1.setX(gift1X);
        gift1.setY(gift1Y);

        gift1_2Y += 15;
        if (gift1_2Y > screenHeight) {
            gift1_2Y = -50;
            gift1_2X = (float)Math.floor(Math.random()*(frameWidth - gift1.getWidth()));
        }
        gift1_2.setX(gift1_2X);
        gift1_2.setY(gift1_2Y);

        //Gift 2 - a bit more rare gift
        gift2Y += 20;
        if (gift2Y > screenHeight) {
            gift2Y = -200;
            gift2X = (float)Math.floor(Math.random()*(frameWidth - gift2.getWidth()));
        }
        gift2.setX(gift2X);
        gift2.setY(gift2Y);

        //Gift 3 -rarest gift
        gift3Y += 30;
        if (gift3Y > screenHeight) {
            gift3Y = -20000;
            gift3X = (float)Math.floor(Math.random()*(frameWidth - gift3.getWidth()));
        }
        gift3.setX(gift3X);
        gift3.setY(gift3Y);

        //Coal1
        coal1Y += 16;
        if (coal1Y > screenHeight) {
            coal1Y = -20;
            coal1X = (float)Math.floor(Math.random()*(frameWidth - coal1.getWidth()));
        }
        coal1.setX(coal1X);
        coal1.setY(coal1Y);

        //Coal2
        coal2Y += 22;
        if (coal2Y > screenHeight) {
            coal2Y = -500;
            coal2X = (float)Math.floor(Math.random()*(frameWidth - coal2.getWidth()));
        }
        coal2.setX(coal2X);
        coal2.setY(coal2Y);

        if (action_flg) {
            //Touching
            boxX -= 20;
        } else {
            //Releasing
            boxX += 20;
        }

        if (boxX < 0) boxX = 0;
        if (boxX > frameWidth - boxWidth) boxX = frameWidth - boxWidth;

        bag.setX(boxX);

        scoreLabel.setText("Score: " + score);

    }

    public void hitCheck() {
        //Gift 1 - 10 points
        float gift1CenterX = gift1X + gift1.getWidth() / 2.0f;
        float gift1CenterY = gift1Y + gift1.getHeight() / 2.0f;

        if (screenHeight >= gift1CenterY && gift1CenterY >= screenHeight-boxHeigth &&
                boxX <= gift1CenterX && gift1CenterX <= boxX + boxWidth) {
            gift1Y = screenHeight + 100.0f;
            score += 10;
        }

        float gift1_2CenterX = gift1_2X + gift1_2.getWidth() / 2.0f;
        float gift1_2CenterY = gift1_2Y + gift1_2.getHeight() / 2.0f;

        if (screenHeight >= gift1_2CenterY && gift1_2CenterY >= screenHeight-boxHeigth &&
                boxX <= gift1_2CenterX && gift1_2CenterX <= boxX + boxWidth) {
            gift1_2Y = screenHeight + 100.0f;
            score += 10;
        }

        //Gift 2 - 20 points
        float gift2CenterX = gift2X + gift2.getWidth() / 2.0f;
        float gift2CenterY = gift2Y + gift2.getHeight() / 2.0f;

        if (screenHeight >= gift2CenterY && gift2CenterY >= screenHeight-boxHeigth &&
                boxX <= gift2CenterX && gift2CenterX <= boxX + boxWidth) {
            gift2Y = screenHeight + 100.0f;
            score += 20;
        }

        //Gift 3 - 30 points
        float gift3CenterX = gift3X + gift3.getWidth() / 2.0f;
        float gift3CenterY = gift3Y + gift3.getHeight() / 2.0f;

        if (screenHeight >= gift3CenterY && gift3CenterY >= screenHeight-boxHeigth &&
                boxX <= gift3CenterX && gift3CenterX <= boxX + boxWidth) {
            gift3Y = screenHeight + 100.0f;
            score += 30;
        }

        //Coal - you loose
        float coal1CenterX = coal1X + coal1.getWidth() / 2.0f;
        float coal1CenterY = coal1Y + coal1.getHeight() / 2.0f;
        float coal2CenterX = coal2X + coal2.getWidth() / 2.0f;
        float coal2CenterY = coal2Y + coal2.getHeight() / 2.0f;

        if ((screenHeight -(boxHeigth/2) >= coal1CenterY && coal1CenterY >= screenHeight-boxHeigth &&
                boxX <= coal1CenterX && coal1CenterX <= boxX + boxWidth) ||
                (screenHeight -(boxHeigth/2)  >= coal2CenterY && coal2CenterY >= screenHeight-boxHeigth &&
                boxX <= coal2CenterX && coal2CenterX <= boxX + boxWidth)) {
            //End of the game
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            //Shows ResultActivity
            Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!start_flg){
            start_flg = true;

            //Frame width
            FrameLayout frameLayout = findViewById(R.id.frame);
            frameWidth = frameLayout.getMeasuredWidth();

            //Bag
            boxX = bag.getX();
            boxHeigth = bag.getHeight();
            boxWidth = bag.getWidth();

            //Hidden Start Label
            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);

        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;
            }
        }
        return super.onTouchEvent(event);
    }
}
