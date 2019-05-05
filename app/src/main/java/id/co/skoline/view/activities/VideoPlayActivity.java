package id.co.skoline.view.activities;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;

import java.io.IOException;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityVideoPlayBinding;
import id.co.skoline.model.response.TopicItemsResponse;
import id.co.skoline.model.response.TopicResponse;
import id.co.skoline.model.utils.ShareInfo;

public class VideoPlayActivity extends BaseActivity {

    ActivityVideoPlayBinding videoPlayBinding;


    // Current playback position (in milliseconds).
    private int mCurrentPosition = 0;
    // Tag for the instance state bundle.
    private static final String PLAYBACK_TIME = "play_time";
    private String videoUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide Status Bar.
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        videoPlayBinding= DataBindingUtil.setContentView(this,R.layout.activity_video_play);

        videoUrl = getIntent().getStringExtra("videoUrl");

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        // Set up the media controller widget and attach it to the video view.
        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(videoPlayBinding.videoView);
        videoPlayBinding.videoView.setMediaController(controller);
    }

    @Override
    protected void viewRelatedTask() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Load the media each time onStart() is called.
        initializePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoPlayBinding.videoView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, videoPlayBinding.videoView.getCurrentPosition());
    }

    private void initializePlayer() {
        // Buffer and decode the video sample.
        Uri videoUri = getMedia(videoUrl);
        videoPlayBinding.videoView.setVideoURI(null);
        videoPlayBinding.videoView.setVideoURI(videoUri);

        // Listener for onPrepared() event (runs after the media is prepared).
        videoPlayBinding.videoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        // Restore saved position, if available.
                        if (mCurrentPosition > 0) {
                            videoPlayBinding.videoView.seekTo(mCurrentPosition);
                        } else {
                            // Skipping to 1 shows the first frame of the video.
                            videoPlayBinding.videoView.seekTo(1);
                        }

                        // Start playing!
                        videoPlayBinding.videoView.start();
                    }
                });

        // Listener for onCompletion() event (runs after media has finished
        // playing).
        videoPlayBinding.videoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        // Return the video position to the start.
                        //videoPlayBinding.videoView.seekTo(0);
                    }
                });
    }


    // Release all media-related resources. In a more complicated app this
    // might involve unregistering listeners or releasing audio focus.
    private void releasePlayer() {
        videoPlayBinding.videoView.stopPlayback();
    }

    // Get a Uri for the media sample regardless of whether that sample is
    // embedded in the app resources or available on the internet.
    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // Media name is an external URL.
            return Uri.parse(mediaName);
        } else {
            // Media name is a raw resource embedded in the app.
            return Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + mediaName);
        }
    }
}
