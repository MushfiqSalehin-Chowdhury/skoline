package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subjectsBinding = DataBindingUtil.setContentView(this, R.layout.activity_subjects);

        // progressBar=findViewById(R.id.progress);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(SubjectsActivity.this);
        subjectsBinding.Recycle.setLayoutManager(mlayoutManager);
        subjectsBinding.Recycle.setItemAnimator(new DefaultItemAnimator());
       /* dividerItemDecoration= new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        subjectsBinding.Recycle.addItemDecoration(dividerItemDecoration);*/
    }

    @Override
    protected void viewRelatedTask() {
        setToolbar(getString(R.string.class_list), true, subjectsBinding.toolbarBinding);
        Intent intent= getIntent();
        int id= intent.getIntExtra("classId",0);
        color= intent.getStringExtra("klassColor");
        iconPath = intent.getStringExtra("classIcon");
        classTitle=intent.getStringExtra("classTitle");
//        Log.i("kelas id",iconPath.toString());
        contentManager = new ContentManager(this);
        contentManager.getSubjects(id,new SubjectsListener() {
            @Override
            public void onSuccess(List<SubjectResponse> subjectResponseList) {
                Log.e("MainActivity", new Gson().toJson(subjectResponseList));
                SubjectsActivity.this.subjectResponseList = subjectResponseList;
                generateViewSubject(subjectResponseList);
            }

            @Override
            public void onFailed(String message, int responseCode) {
                Log.e("MainActivity", "message: " + message);
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


    }


    private void generateViewSubject(List<SubjectResponse> subjectResponseList) {

      /*  List<String> subjects = new ArrayList<>();

        Log.i("size", String.valueOf(subjectResponseList.size()));*/

       /* for (int i = 0; i < subjectResponseList.size(); i++) {
            subjects.add(subjectResponseList.get(i).getName().toString());
            Log.i("subs:",subjectResponseList.get(i).getName());
        }*/
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+iconPath).into(subjectsBinding.classIcon);
        subjectsBinding.klass.setBackgroundColor(Color.parseColor(color));
        subjectsBinding.classTitle.setText(classTitle);

        subjectsAdapter = new SubjectsAdapter(SubjectsActivity.this,subjectResponseList);
        subjectsBinding.Recycle.setAdapter(subjectsAdapter);
        subjectsAdapter.notifyDataSetChanged();
       /* Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+subjectResponsesList.get(0).getIconUrl()).into(mainBinding.klass1);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(1).getBannerUrl()).into(mainBinding.klass2);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(2).getBannerUrl()).into(mainBinding.klass3);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(3).getBannerUrl()).into(mainBinding.klass4);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(4).getBannerUrl()).into(mainBinding.klass5);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(5).getBannerUrl()).into(mainBinding.klass6);*/
    }
}