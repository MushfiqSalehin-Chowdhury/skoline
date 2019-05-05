package id.co.skoline.view.activities;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityVideoPlayBinding;

public class VideoPlayLegacyActivity extends BaseActivity {

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

        /*videoPlayBinding.videoView.setVideoURI(Uri.parse(videoUrl));
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoPlayBinding.videoView);
        videoPlayBinding.videoView.setMediaController(mediaController);*/

        Log.e("VideoPlayActivity", "link: "+ videoUrl);
        videoPlayBinding.videoView.setVideoURI(Uri.parse(videoUrl));
        videoPlayBinding.videoView.requestFocus();

        videoPlayBinding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
            }
        });

        videoPlayBinding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        MediaController mediaController = new MediaController(VideoPlayLegacyActivity.this);
                        videoPlayBinding.videoView.setMediaController(mediaController);
                        mediaController.setAnchorView(videoPlayBinding.videoView);
                    }
                });
            }
        });

        videoPlayBinding.videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return false;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*cleanupVideo();*/
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
