package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityMainBinding;
import id.co.skoline.model.response.KlassesResponse;
import id.co.skoline.model.response.SubjectResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.view.adapters.SubjectsAdapter;
import id.co.skoline.viewControllers.interfaces.KlassesListener;
import id.co.skoline.viewControllers.interfaces.SubjectsListener;
import id.co.skoline.viewControllers.managers.ContentManager;

public class MainActivity extends BaseActivity {

    ActivityMainBinding mainBinding;
    ContentManager contentManager;
    List<KlassesResponse> klassesResponseList = new ArrayList<>();
    List<SubjectResponse> subjectResponseList= new ArrayList<>();
    ShareInfo shareInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
    }

    @Override
    protected void viewRelatedTask() {
       /* mainBinding.gridView.setAdapter(new SubjectsAdapter(this));*/
        setToolbar(getString(R.string.class_list), true, mainBinding.toolbarBinding);
        contentManager = new ContentManager(this);
        contentManager.getKlasses(new KlassesListener() {
            @Override
            public void onSuccess(List<KlassesResponse> klassesResponseList) {
                Log.e("MainActivity", new Gson().toJson(klassesResponseList));
                MainActivity.this.klassesResponseList = klassesResponseList;
                generateView(klassesResponseList);
            }

            @Override
            public void onFailed(String message, int responseCode) {
                Log.e("MainActivity", "message: "+message);
                MainActivity.this.klassesResponseList = null;
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

        mainBinding.klass1.setOnClickListener(v -> {
            if(klassesResponseList!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("classId",klassesResponseList.get(0).getId());
                startActivity(intent);
            }
        });

        mainBinding.klass2.setOnClickListener(v -> {
            if(klassesResponseList!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("classId",klassesResponseList.get(1).getId());
                startActivity(intent);
            }
        });

        mainBinding.klass3.setOnClickListener(v -> {
            if(klassesResponseList!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("classId",klassesResponseList.get(2).getId());
                startActivity(intent);
            }
        });

        mainBinding.klass4.setOnClickListener(v -> {
            if(klassesResponseList!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("classId",klassesResponseList.get(3).getId());
                startActivity(intent);
            }
        });

        mainBinding.klass5.setOnClickListener(v -> {
            if(klassesResponseList!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("classId",klassesResponseList.get(4).getId());
                startActivity(intent);
            }
        });

        mainBinding.klass6.setOnClickListener(v -> {
            if(klassesResponseList!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("classId",klassesResponseList.get(5).getId());
                startActivity(intent);
            }
        });

        mainBinding.bonusImg.setOnClickListener(v -> {
            if (klassesResponseList!=null){

            }
        });

    }

    private void generateView(List<KlassesResponse> klassesResponseList) {
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(0).getBannerUrl()).into(mainBinding.klass1);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(1).getBannerUrl()).into(mainBinding.klass2);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(2).getBannerUrl()).into(mainBinding.klass3);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(3).getBannerUrl()).into(mainBinding.klass4);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(4).getBannerUrl()).into(mainBinding.klass5);
        Picasso.with(this).load(ShareInfo.getInstance().getBaseUrl()+klassesResponseList.get(5).getBannerUrl()).into(mainBinding.klass6);
    }




    public void search(View view) {
        Intent i= new Intent(this, SearchResultActivity.class);
        i.putExtra("key" ,mainBinding.searchEditFrame.getText().toString());
        // Toast.makeText(this, mainMenuBinding.searchEditFrame.getText(), Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

    public void profile(View view) {
        startActivity(new Intent(this,ProfileActivity.class));
    }

}
