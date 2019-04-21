package id.co.skoline.view.activities;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    TopicItemsResponse topicItemsResponseList;
    String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoPlayBinding= DataBindingUtil.setContentView(this,R.layout.activity_video_play);
    }

    @Override
    protected void viewRelatedTask() {
        videoUrl= getIntent().getStringExtra("videoUrl");
        Uri uri;
       videoPlayBinding.videoView.setVideoURI(Uri.parse(videoUrl));
        android.widget.MediaController mc= new MediaController(this);
        mc.setAnchorView(videoPlayBinding.videoView);
        videoPlayBinding.videoView.setMediaController(mc);
        videoPlayBinding.videoView.start();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
