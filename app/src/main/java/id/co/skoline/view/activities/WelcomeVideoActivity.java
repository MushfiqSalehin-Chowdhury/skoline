package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityWelcomeVideoBinding;
import id.co.skoline.model.utils.ShareInfo;

public class WelcomeVideoActivity extends BaseActivity{

    ActivityWelcomeVideoBinding welcomeVideoBinding;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        welcomeVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_video);
    }

    @Override
    protected void viewRelatedTask() {

        welcomeVideoBinding.videoView.setVideoPath("android.resource://"+getPackageName()+ "/"+R.raw.skoline_bumper);
        welcomeVideoBinding.videoView.start();
        mediaController = new MediaController(this);
        mediaController.setAnchorView(welcomeVideoBinding.videoView);
        welcomeVideoBinding.videoView.setMediaController(mediaController);
        welcomeVideoBinding.videoView.setOnPreparedListener(mp -> mp.start());
        welcomeVideoBinding.videoView.setOnCompletionListener(mp -> {
            /*mp.stop();
            mp.release();*/
            goToNextPage();
        });
        mediaController.setVisibility(View.GONE);
        welcomeVideoBinding.videoView.setOnErrorListener((mp, what, extra) -> false);

    }

    public void nextPage(View view) {
        goToNextPage();
    }

    private void goToNextPage(){
        if (TextUtils.isEmpty(ShareInfo.getInstance().getAuthenticationToken(this))){
            startActivity(new Intent(this,WelcomePageActivity.class));
            finish();
        }
        else{
            goToHome();
            finish();
        }
    }
}
