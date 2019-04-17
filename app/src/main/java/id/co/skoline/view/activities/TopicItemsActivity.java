package id.co.skoline.view.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityTopicItemsBinding;
import id.co.skoline.model.response.TopicItemsResponse;
import id.co.skoline.viewControllers.interfaces.TopicItemsListener;
import id.co.skoline.viewControllers.managers.ContentManager;

public class TopicItemsActivity extends BaseActivity {

   ActivityTopicItemsBinding topicScreenBinding;
    ContentManager contentManager;
    TopicItemsResponse topicItemsResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        topicScreenBinding= DataBindingUtil.setContentView(this,R.layout.activity_topic_items);

    }

    @Override
    protected void viewRelatedTask() {


        contentManager = new ContentManager(this);
        contentManager.getAdvanture(new TopicItemsListener() {
            @Override
            public void onSuccess(TopicItemsResponse topicItemsResponseList) {
                Log.e("MainActivity", new Gson().toJson(topicItemsResponseList));
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

        topicScreenBinding.adventureTitle.setText(topicItemsResponseList.getTopic().getAdventure().getTitle());
        topicScreenBinding.adventureDetails.setText(topicItemsResponseList.getTopic().getAdventure().getDescription());
    }

    public void showVideo(View view) {

    }
}
