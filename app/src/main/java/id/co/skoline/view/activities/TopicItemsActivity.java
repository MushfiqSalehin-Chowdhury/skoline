package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityTopicItemsBinding;
import id.co.skoline.model.response.SubjectResponse;
import id.co.skoline.model.response.TopicItemsResponse;
import id.co.skoline.model.response.TopicResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.viewControllers.interfaces.TopicItemsListener;
import id.co.skoline.viewControllers.managers.ContentManager;

public class TopicItemsActivity extends BaseActivity {

    ActivityTopicItemsBinding topicItemsBinding;
    ContentManager contentManager;
    TopicItemsResponse topicItemsResponseList;
    TopicResponse topicResponse;
    int id;
    String classColor,adventureBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicItemsBinding= DataBindingUtil.setContentView(this,R.layout.activity_topic_items);
    }

    @Override
    protected void viewRelatedTask() {
        setToolbar(getString(R.string.adventure_list), true, topicItemsBinding.toolbarBinding);

        topicResponse = new Gson().fromJson(getIntent().getStringExtra("topicResponse"), TopicResponse.class);
        classColor = getIntent().getStringExtra("classColor");
        adventureBanner = getIntent().getStringExtra("topicBanner");


        contentManager = new ContentManager(this);
        contentManager.getAdventure(topicResponse.getId(),new TopicItemsListener() {
            @Override
            public void onSuccess(TopicItemsResponse topicItemsResponseList) {
                Log.e("TopicItems", new Gson().toJson(topicItemsResponseList));
                TopicItemsActivity.this.topicItemsResponseList= topicItemsResponseList;
                generateViewAdvanture(topicItemsResponseList);
            }


            @Override
            public void onFailed(String message, int responseCode) {
                Log.e("Topic", "message: " + message);
                TopicItemsActivity.this.topicItemsResponseList = null;
            }

            @Override
            public void startLoading(String requestId) {
                showProgressDialog(getString(R.string.loading_data), false);
            }

            @Override
            public void endLoading(String requestId) {
                dismissProgressDialog();
            }
        });
    }

    private void generateViewAdvanture(TopicItemsResponse topicItemsResponseList) {
        Picasso.with(this).load(ShareInfo.getInstance().getRootBaseUrl()+adventureBanner).into(topicItemsBinding.advanture);
        topicItemsBinding.adventureTitle.setText(topicItemsResponseList.getTopic().getAdventure().getTitle());
        topicItemsBinding.adventureDetails.setText(topicItemsResponseList.getTopic().getAdventure().getDescription());
        topicItemsBinding.klass.setBackgroundColor(Color.parseColor(classColor));
        topicItemsBinding.topicVideoHeadline.setText(getString(R.string.topicVideoHeadline));
    }
    public void showVideo(View view) {
        Intent intent=new Intent(this,VideoPlayActivity.class);
        intent.putExtra("videoUrl",ShareInfo.getInstance().getRootBaseUrl()+topicItemsResponseList.getTopic().getAdventure().getVideoLink());
        intent.putExtra("videoId",topicItemsResponseList.getTopic().getAdventure().getId());
        startActivity(intent);
    }
}
