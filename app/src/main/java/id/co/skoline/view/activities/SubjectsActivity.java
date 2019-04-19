package id.co.skoline.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySubjectsBinding;
import id.co.skoline.model.response.KlassesResponse;
import id.co.skoline.model.response.SubjectResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.view.adapters.SubjectsAdapter;
import id.co.skoline.viewControllers.interfaces.SubjectsListener;
import id.co.skoline.viewControllers.managers.ContentManager;

public class SubjectsActivity extends BaseActivity {

    ActivitySubjectsBinding subjectsBinding;
    ContentManager contentManager;
    List<SubjectResponse> subjectResponseList;
    private SubjectsAdapter subjectsAdapter;
    DividerItemDecoration dividerItemDecoration;
    String iconPath,color,classTitle;
    KlassesResponse klassesResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subjectsBinding = DataBindingUtil.setContentView(this, R.layout.activity_subjects);

        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(SubjectsActivity.this);
        subjectsBinding.subjectRecycle.setLayoutManager(mlayoutManager);
        subjectsBinding.subjectRecycle.setItemAnimator(new DefaultItemAnimator());
    }
    @Override
    protected void viewRelatedTask() {
        setToolbar(getString(R.string.class_list), true, subjectsBinding.toolbarBinding);
        Intent intent= getIntent();
        klassesResponse = new Gson().fromJson(getIntent().getStringExtra("klassResponse"), KlassesResponse.class);

        contentManager = new ContentManager(this);
        contentManager.getSubjects(klassesResponse.getId(),new SubjectsListener() {
            @Override
            public void onSuccess(List<SubjectResponse> subjectResponseList) {
                Log.e("SubjectActivity", new Gson().toJson(subjectResponseList));
                SubjectsActivity.this.subjectResponseList = subjectResponseList;
                generateViewSubject(subjectResponseList);
            }
            @Override
            public void onFailed(String message, int responseCode) {
                Log.e("SubjectActivity", "message: " + message);
                SubjectsActivity.this.subjectResponseList = null;
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

        subjectsBinding.subjectRecycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SubjectsActivity.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void generateViewSubject(List<SubjectResponse> subjectResponseList) {

        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponse.getIconUrl()).into(subjectsBinding.classIcon);
        subjectsBinding.klass.setBackgroundColor(Color.parseColor(klassesResponse.getColorCode()));
        subjectsBinding.classTitle.setText(klassesResponse.getName());

        subjectsAdapter = new SubjectsAdapter(SubjectsActivity.this,subjectResponseList);
        subjectsBinding.subjectRecycle.setAdapter(subjectsAdapter);
        subjectsAdapter.notifyDataSetChanged();
        subjectsAdapter.setOnItemClickListener(new SubjectsAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(SubjectResponse subjectResponse) {
                Intent intent = new Intent(SubjectsActivity.this, TopicScreenActivity.class);
                intent.putExtra("subjectResponse", new Gson().toJson(subjectResponse));
                intent.putExtra("classIcon", klassesResponse.getIconUrl());
                intent.putExtra("classTitle",klassesResponse.getName());
                intent.putExtra("classColor",klassesResponse.getColorCode());
                startActivity(intent);
            }
        });
    }
}