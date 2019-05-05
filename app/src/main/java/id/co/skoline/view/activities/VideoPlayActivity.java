package id.co.skoline.view.activities;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.MediaController;
import android.widget.Toast;

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
        videoPlayBinding= DataBindingUtil.setContentView(this,R.layout.activity_video_play);
    }

    @Override
    protected void viewRelatedTask() {
        videoUrl= getIntent().getStringExtra("videoUrl");
        Log.i("videoLink",videoUrl);
        videoPlayBinding.videoView.setVideoPath(videoUrl);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoPlayBinding.videoView);
        videoPlayBinding.videoView.setMediaController(mediaController);

        videoPlayBinding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()  {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.i("VideoPlayActivity", "Duration = " +videoPlayBinding.videoView.getDuration());

                mp.start();
            }
        });

        videoPlayBinding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

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
    public void onBackPressed() {
        super.onBackPressed();
      /*  if(mediaController!=null){
            mediaController.stop
        }*/
    }
}
