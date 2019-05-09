package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityGameBinding;

public class GameActivity extends BaseActivity {

    ActivityGameBinding gameBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameBinding = DataBindingUtil.setContentView(this, R.layout.activity_game);
    }

    @Override
    protected void viewRelatedTask() {
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
    }
}
