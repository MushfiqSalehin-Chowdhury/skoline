package id.co.skoline.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import id.co.skoline.R;

public class WelcomeVideoActivity extends BaseActivity{

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_video);
        videoView = (VideoView)findViewById(R.id.videoView);

    }

    @Override
    protected void viewRelatedTask() {

        videoView.setVideoPath("android.resource://"+getPackageName()+ "/"+R.raw.skoline_bumper);
        videoView.start();


        new CountDownTimer(17000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {


            }

            @Override
            public void onFinish() {
            }
        }.start();

    }

    public void stream(View view) {
        startActivity(new Intent(WelcomeVideoActivity.this,SigninSignupActivity.class));
        finish();

    }
}
