package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySubjectsBinding;
import id.co.skoline.databinding.ActivityTopicScreenBinding;
import id.co.skoline.model.response.SubjectResponse;
import id.co.skoline.model.response.TopicResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.view.adapters.SubjectsAdapter;
import id.co.skoline.view.adapters.TopicAdapter;
import id.co.skoline.viewControllers.interfaces.SubjectsListener;
import id.co.skoline.viewControllers.interfaces.TopicsListener;
import id.co.skoline.viewControllers.managers.ContentManager;

public class TopicScreenActivity extends BaseActivity {
    ActivityTopicScreenBinding topicScreenBinding;
    ContentManager contentManager;
    List<TopicResponse> topicResponseList;
    private TopicAdapter topicAdapter;
    DividerItemDecoration dividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicScreenBinding = DataBindingUtil.setContentView(this,R.layout.activity_topic_screen);

        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(TopicScreenActivity.this);
        topicScreenBinding.topicRecycle.setLayoutManager(mlayoutManager);
        topicScreenBinding.topicRecycle.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void viewRelatedTask() {
        setToolbar(getString(R.string.class_list), true, topicScreenBinding.toolbarBinding);
        Intent intent= getIntent();
        Intent iconIntent= getIntent();
        Intent titleIntent = getIntent();
        int id= intent.getIntExtra("subId",0);
        String iconPath= iconIntent.getStringExtra("subIcon");
        String subTitle =titleIntent.getStringExtra("subTitle");

        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+iconPath).into(topicScreenBinding.subjectIcon);
        topicScreenBinding.subjectTitle.setText(subTitle);

        Log.i("kelas id",String.valueOf(id));
        contentManager = new ContentManager(this);
        contentManager.getTopics(id, new TopicsListener() {
            @Override
            public void onSuccess(List<TopicResponse> topicResponseList) {
                Log.e("MainActivity", new Gson().toJson(topicResponseList));
                TopicScreenActivity.this.topicResponseList = topicResponseList;
                generateViewTopics(topicResponseList);
            }

            @Override
            public void onFailed(String message, int responseCode) {
                Log.e("MainActivity", "message: " + message);
                TopicScreenActivity.this.topicResponseList = null;
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
    private void generateViewTopics(List<TopicResponse> topicResponseList) {

      /*  List<String> subjects = new ArrayList<>();

        Log.i("size", String.valueOf(subjectResponseList.size()));*/

       /* for (int i = 0; i < subjectResponseList.size(); i++) {
            subjects.add(subjectResponseList.get(i).getName().toString());
            Log.i("subs:",subjectResponseList.get(i).getName());
        }*/
        topicAdapter = new TopicAdapter(TopicScreenActivity.this,topicResponseList);
        topicScreenBinding.topicRecycle.setAdapter(topicAdapter);
        topicAdapter.notifyDataSetChanged();
       /* Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+subjectResponsesList.get(0).getIconUrl()).into(mainBinding.klass1);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(1).getBannerUrl()).into(mainBinding.klass2);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(2).getBannerUrl()).into(mainBinding.klass3);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(3).getBannerUrl()).into(mainBinding.klass4);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(4).getBannerUrl()).into(mainBinding.klass5);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(5).getBannerUrl()).into(mainBinding.klass6);*/
    }
}
