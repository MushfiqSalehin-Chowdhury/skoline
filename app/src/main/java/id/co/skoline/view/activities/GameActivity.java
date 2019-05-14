package id.co.skoline.view.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityGameBinding;
import id.co.skoline.viewControllers.managers.GameManager;

public class    GameActivity extends BaseActivity {

    ActivityGameBinding gameBinding;
    Activity activityToRedirect;
    GameManager gameManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameBinding = DataBindingUtil.setContentView(this, R.layout.activity_game);

    }

    @Override
    protected void viewRelatedTask() {
        gameManager = new GameManager(this);
        new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                showView(gameBinding.option1, Techniques.SlideInLeft);
                showView(gameBinding.option2, Techniques.SlideInLeft);
                showView(gameBinding.option3, Techniques.SlideInLeft);
                showView(gameBinding.option4, Techniques.SlideInLeft);
            }
        }.start();


        ArrayList<Integer> singleDigitList = gameManager.getMultipleChoicesSingleDigit();
        ArrayList<Integer> doubleDigitList = gameManager.getMultipleChoicesDoubleDigit();
        Log.e("GameActivity", "getMultipleChoicesSingleDigit: " + new Gson().toJson(singleDigitList));
        Log.e("GameActivity", "CorrectAnswer SingleDigit: " + gameManager.generateCorrectAnswerForMultipleChoices(singleDigitList));
        Log.e("GameActivity", "getMultipleChoicesDoubleDigit: " + new Gson().toJson(doubleDigitList));
        Log.e("GameActivity", "CorrectAnswer DoubleDigit: " + gameManager.generateCorrectAnswerForMultipleChoices(doubleDigitList));

        int randomResultSingleDigit = gameManager.generateCorrectAnswerForMultipleChoices(singleDigitList);
        int randomResultDoubleDigit = gameManager.generateCorrectAnswerForMultipleChoices(doubleDigitList);
        ArrayList<Integer> additionParameters = gameManager.generateAdditionParameters(randomResultSingleDigit);
        Log.e("GameActivity", "SingleDigit: " + randomResultSingleDigit);
        Log.e("GameActivity", "Addition Params SingleDigit: " + new Gson().toJson(gameManager.generateAdditionParameters(randomResultSingleDigit)));
        Log.e("GameActivity", "Subtraction Params SingleDigit: " + new Gson().toJson(gameManager.generateSubtractionParameters(randomResultSingleDigit)));
        Log.e("GameActivity", "DoubleDigit: " + randomResultDoubleDigit);
        Log.e("GameActivity", "Addition Params DoubleDigit: " + new Gson().toJson(gameManager.generateAdditionParameters(randomResultDoubleDigit)));
        Log.e("GameActivity", "Subtraction Params DoubleDigit: " + new Gson().toJson(gameManager.generateSubtractionParameters(randomResultDoubleDigit)));

        try {
            gameBinding.gameBackground.setBackground(getDrawableImage("gameAssets/gameBackground.jpg"));
            Random random= new Random();
            gameBinding.option1Img.setBackground(getDrawableImage("gameAssets/"+singleDigitList.get(0)+".png"));
            gameBinding.option2Img.setBackground(getDrawableImage("gameAssets/"+singleDigitList.get(1)+".png"));
            gameBinding.option3Img.setBackground(getDrawableImage("gameAssets/"+singleDigitList.get(2)+".png"));
            gameBinding.option4Img.setBackground(getDrawableImage("gameAssets/"+singleDigitList.get(3)+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameBinding.option1Img.setOnClickListener(v -> {
          if(randomResultSingleDigit==singleDigitList.get(0)){
              try {
                  gameBinding.option1ResultImg.setBackground(getDrawableImage("gameAssets/correct.png"));
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
          else {
              try {
                  gameBinding.option1ResultImg.setImageDrawable(getDrawableImage("gameAssets/wrong.png"));
                  hideView(gameBinding.option1Img, Techniques.SlideOutRight);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
        });
        gameBinding.option2Img.setOnClickListener(v -> {
            if(randomResultSingleDigit==singleDigitList.get(1)){
                try {
                    gameBinding.option2ResultImg.setBackground(getDrawableImage("gameAssets/correct.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    gameBinding.option2ResultImg.setImageDrawable(getDrawableImage("gameAssets/wrong.png"));
                    hideView(gameBinding.option2Img, Techniques.SlideOutRight);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        gameBinding.option3Img.setOnClickListener(v -> {
            if(randomResultSingleDigit==singleDigitList.get(2)){
                try {
                    gameBinding.option3ResultImg.setBackground(getDrawableImage("gameAssets/correct.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    gameBinding.option3ResultImg.setImageDrawable(getDrawableImage("gameAssets/wrong.png"));
                    hideView(gameBinding.option3Img, Techniques.SlideOutRight);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        gameBinding.option4Img.setOnClickListener(v -> {
            if(randomResultSingleDigit==singleDigitList.get(3)){
                try {
                    gameBinding.option4ResultImg.setBackground(getDrawableImage("gameAssets/correct.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    gameBinding.option4ResultImg.setImageDrawable(getDrawableImage("gameAssets/wrong.png"));
                    hideView(gameBinding.option4Img, Techniques.SlideOutRight);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            gameBinding.operation.setImageDrawable(getDrawableImage("gameAssets/plus.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            gameBinding.digit1.setImageDrawable(getDrawableImage("gameAssets/"+additionParameters.get(0)+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            gameBinding.digit2.setImageDrawable(getDrawableImage("gameAssets/"+additionParameters.get(1)+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
