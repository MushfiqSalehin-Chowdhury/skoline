package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import id.co.skoline.R;
import id.co.skoline.databinding.ActivityMainBinding;
import id.co.skoline.model.response.KlassesResponse;
import id.co.skoline.model.response.SubjectResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.viewControllers.interfaces.KlassesListener;
import id.co.skoline.viewControllers.managers.ContentManager;

public class MainActivity extends BaseActivity {

    ActivityMainBinding mainBinding;
    ContentManager contentManager;
    List<KlassesResponse> klassesResponseList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
    }

    @Override
    protected void viewRelatedTask() {
        setToolbar(getString(R.string.class_list), true, mainBinding.toolbarBinding);

        mainBinding.scrollView.setVisibility(View.GONE);
        mainBinding.progressBar.setVisibility(View.VISIBLE);

        contentManager = new ContentManager(this);
        contentManager.getKlasses(new KlassesListener() {
            @Override
            public void onSuccess(List<KlassesResponse> klassesResponseList) {
                Log.e("MainActivity", new Gson().toJson(klassesResponseList));
                MainActivity.this.klassesResponseList = klassesResponseList;
                mainBinding.scrollView.setVisibility(View.VISIBLE);
                mainBinding.progressBar.setVisibility(View.GONE);
                generateView(klassesResponseList);
            }

            @Override
            public void onFailed(String message, int responseCode) {
                Log.e("MainActivity", "message: "+message);
                MainActivity.this.klassesResponseList = null;
                showToast(message);
            }

            @Override
            public void startLoading(String requestId) {

            }

            @Override
            public void endLoading(String requestId) {

            }
        });

        mainBinding.klass1.setOnClickListener(v -> {
            if(klassesResponseList!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("klassResponse", new Gson().toJson(klassesResponseList.get(0)));
                startActivity(intent);
            }
        });

        mainBinding.klass2.setOnClickListener(v -> {
            if(klassesResponseList!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("klassResponse", new Gson().toJson(klassesResponseList.get(1)));
                startActivity(intent);
            }
        });

        mainBinding.klass3.setOnClickListener(v -> {
            if(klassesResponseList!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("klassResponse", new Gson().toJson(klassesResponseList.get(2)));
                startActivity(intent);
            }
        });

        mainBinding.klass4.setOnClickListener(v -> {
            if(klassesResponseList!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("klassResponse", new Gson().toJson(klassesResponseList.get(3)));
                startActivity(intent);
            }
        });

        mainBinding.klass5.setOnClickListener(v -> {
            if(klassesResponseList!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("klassResponse", new Gson().toJson(klassesResponseList.get(4)));
                startActivity(intent);
            }
        });

        mainBinding.klass6.setOnClickListener(v -> {
            if(klassesResponseList!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("klassResponse", new Gson().toJson(klassesResponseList.get(5)));
                startActivity(intent);
            }
        });

        mainBinding.bonusImg.setOnClickListener(v -> {
            if (klassesResponseList!=null){
            }
        });
    }

    private void generateView(List<KlassesResponse> klassesResponseList) {
        Picasso.with(this).load(ShareInfo.getInstance().getRootBaseUrl()+klassesResponseList.get(0).getBannerUrl()).into(mainBinding.klass1);
        Picasso.with(this).load(ShareInfo.getInstance().getRootBaseUrl()+klassesResponseList.get(1).getBannerUrl()).into(mainBinding.klass2);
        Picasso.with(this).load(ShareInfo.getInstance().getRootBaseUrl()+klassesResponseList.get(2).getBannerUrl()).into(mainBinding.klass3);
        Picasso.with(this).load(ShareInfo.getInstance().getRootBaseUrl()+klassesResponseList.get(3).getBannerUrl()).into(mainBinding.klass4);
        Picasso.with(this).load(ShareInfo.getInstance().getRootBaseUrl()+klassesResponseList.get(4).getBannerUrl()).into(mainBinding.klass5);
        Picasso.with(this).load(ShareInfo.getInstance().getRootBaseUrl()+klassesResponseList.get(5).getBannerUrl()).into(mainBinding.klass6);
    }

    public void search(View view) {
        Intent i= new Intent(this, SearchResultActivity.class);
        i.putExtra("key" ,mainBinding.searchEditFrame.getText().toString());
        startActivity(i);
    }

    public void profile(View view) {
        startActivity(new Intent(this,ProfileActivity.class));
    }

}
