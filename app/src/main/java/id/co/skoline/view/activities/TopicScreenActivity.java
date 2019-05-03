package id.co.skoline.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySubjectsBinding;
import id.co.skoline.databinding.ActivityTopicScreenBinding;
import id.co.skoline.model.response.KlassesResponse;
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
    String classIconPath,classTitle,classColor;
    int classId;
    SubjectResponse subjectResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicScreenBinding = DataBindingUtil.setContentView(this,R.layout.activity_topic_screen);

        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(TopicScreenActivity.this);
        topicScreenBinding.topicRecycle.setLayoutManager(mlayoutManager);
        topicScreenBinding.topicRecycle.setItemAnimator(new DefaultItemAnimator());
        dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.recycle_view_devider));
        topicScreenBinding.topicRecycle.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void viewRelatedTask() {
        setToolbar(getString(R.string.topic_list), true, topicScreenBinding.toolbarBinding);
        Intent intent= getIntent();
        classIconPath= intent.getStringExtra("classIcon");
        classTitle=intent.getStringExtra("classTitle");
        classColor=intent.getStringExtra("classColor");
        classId = intent.getIntExtra("classId",0);
        subjectResponse = new Gson().fromJson(getIntent().getStringExtra("subjectResponse"), SubjectResponse.class);

        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+subjectResponse.getIconUrl()).into(topicScreenBinding.subjectIcon);
        topicScreenBinding.subjectTitle.setText(subjectResponse.getName());
        topicScreenBinding.subjectTitle.setTextColor(Color.parseColor(classColor));
        topicScreenBinding.classTitle.setText(classTitle);
        topicScreenBinding.klass.setBackgroundColor(Color.parseColor(classColor));

        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+classIconPath).into(topicScreenBinding.classIcon);
       // Log.i("kelas id",String.valueOf(id));
        contentManager = new ContentManager(this);
        contentManager.getTopics(classId,subjectResponse.getId(), new TopicsListener() {
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
        topicAdapter = new TopicAdapter(TopicScreenActivity.this,topicResponseList);
        topicScreenBinding.topicRecycle.setAdapter(topicAdapter);
        topicAdapter.notifyDataSetChanged();
        topicAdapter.setOnItemClickListener(new TopicAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(TopicResponse topicResponse) {
                Intent intent = new Intent(TopicScreenActivity.this,TopicItemsActivity.class);
                intent.putExtra("topicResponse",new Gson().toJson(topicResponse));
                intent.putExtra("classColor",classColor);
                intent.putExtra("topicBanner",topicResponse.getBannerUrl());
                startActivity(intent);
            }
        });
    }
}
