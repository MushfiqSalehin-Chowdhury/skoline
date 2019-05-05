package id.co.skoline.view.activities;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityVideoPlayBinding;
import id.co.skoline.model.response.TopicItemsResponse;
import id.co.skoline.model.response.TopicResponse;
import id.co.skoline.model.utils.ShareInfo;

public class VideoPlayActivity extends BaseActivity {

    ActivityVideoPlayBinding videoPlayBinding;
    private String videoUrl;

    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide Status Bar.
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        videoPlayBinding= DataBindingUtil.setContentView(this,R.layout.activity_video_play);
    }

    @Override
    protected void viewRelatedTask() {
        videoUrl= getIntent().getStringExtra("videoUrl");
        Log.i("videoLink",videoUrl);

        videoPlayBinding.videoView.setVideoURI(Uri.parse(videoUrl));
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoPlayBinding.videoView);
        videoPlayBinding.videoView.setMediaController(mediaController);
        videoPlayBinding.videoView.start();

        videoPlayBinding.videoView.setOnPreparedListener(mp -> {
            Log.i("VideoPlayActivity", "Duration = " +videoPlayBinding.videoView.getDuration());
            mp.start();
        });

        videoPlayBinding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            }
        });

        videoPlayBinding.videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {

                return false;
            }
        });

        videoPlayBinding.videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                mp.stop();
                mp.release();
                Log.i("VideoPlayActivity",String.valueOf(what));
                Log.i("VideoPlayActivity",String.valueOf(extra));
                return false;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        cleanupVideo();
    }

    private void cleanupVideo(){
        videoPlayBinding.videoView.stopPlayback();
        videoPlayBinding.videoView.clearAnimation();
        videoPlayBinding.videoView.suspend(); // clears media player
        videoPlayBinding.videoView.setVideoURI(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
