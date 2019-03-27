package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityWelcomeVideoBinding;

public class WelcomeVideoActivity extends BaseActivity{

    ActivityWelcomeVideoBinding welcomeVideoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        welcomeVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_video);
    }

    @Override
    protected void viewRelatedTask() {

        welcomeVideoBinding.videoView.setVideoPath("android.resource://"+getPackageName()+ "/"+R.raw.skoline_bumper);
        welcomeVideoBinding.videoView.start();

        new CountDownTimer(17000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() { }
        }.start();

    }

    public void stream(View view) {
        startActivity(new Intent(WelcomeVideoActivity.this,SigninSignupActivity.class));
        finish();
    }
}
