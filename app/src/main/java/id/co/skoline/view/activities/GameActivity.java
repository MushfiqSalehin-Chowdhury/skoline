package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;

import java.util.ArrayList;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityGameBinding;
import id.co.skoline.viewControllers.managers.GameManager;

public class GameActivity extends BaseActivity {

    ActivityGameBinding gameBinding;
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

        gameBinding.option1.setOnClickListener(v -> hideView(gameBinding.option1, Techniques.SlideOutRight));
        gameBinding.option2.setOnClickListener(v -> hideView(gameBinding.option2, Techniques.SlideOutRight));
        gameBinding.option3.setOnClickListener(v -> hideView(gameBinding.option3, Techniques.SlideOutRight));
        gameBinding.option4.setOnClickListener(v -> hideView(gameBinding.option4, Techniques.SlideOutRight));

        ArrayList<Integer> singleDigitList = gameManager.getMultipleChoicesSingleDigit();
        ArrayList<Integer> doubleDigitList = gameManager.getMultipleChoicesDoubleDigit();
        Log.e("GameActivity", "getMultipleChoicesSingleDigit: " + new Gson().toJson(singleDigitList));
        Log.e("GameActivity", "CorrectAnswer SingleDigit: " + gameManager.generateCorrectAnswerForMultipleChoices(singleDigitList));
        Log.e("GameActivity", "getMultipleChoicesDoubleDigit: " + new Gson().toJson(doubleDigitList));
        Log.e("GameActivity", "CorrectAnswer DoubleDigit: " + gameManager.generateCorrectAnswerForMultipleChoices(doubleDigitList));

        int randomResultSingleDigit = gameManager.generateCorrectAnswerForMultipleChoices(singleDigitList);
        int randomResultDoubleDigit = gameManager.generateCorrectAnswerForMultipleChoices(doubleDigitList);
        Log.e("GameActivity", "SingleDigit: " + randomResultSingleDigit);
        Log.e("GameActivity", "Addition Params SingleDigit: " + new Gson().toJson(gameManager.generateAdditionParameters(randomResultSingleDigit)));
        Log.e("GameActivity", "Subtraction Params SingleDigit: " + new Gson().toJson(gameManager.generateSubtractionParameters(randomResultSingleDigit)));
        Log.e("GameActivity", "DoubleDigit: " + randomResultDoubleDigit);
        Log.e("GameActivity", "Addition Params DoubleDigit: " + new Gson().toJson(gameManager.generateAdditionParameters(randomResultDoubleDigit)));
        Log.e("GameActivity", "Subtraction Params DoubleDigit: " + new Gson().toJson(gameManager.generateSubtractionParameters(randomResultDoubleDigit)));

        gameManager.getMultipleChoicesDoubleDigit();
    }
}
