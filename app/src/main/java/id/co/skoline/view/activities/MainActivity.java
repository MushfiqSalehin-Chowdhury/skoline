package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityMainBinding;
import id.co.skoline.model.response.KlassesResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.viewControllers.interfaces.KlassesListener;
import id.co.skoline.viewControllers.managers.ContentManager;

public class MainActivity extends BaseActivity {

    ActivityMainBinding mainBinding;
    ContentManager contentManager;
    KlassesResponse klassesResponse = new KlassesResponse();
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
            public void onSuccess(KlassesResponse klassesResponse) {
                Log.e("MainActivity", new Gson().toJson(klassesResponse));
                MainActivity.this.klassesResponse = klassesResponse;
                mainBinding.scrollView.setVisibility(View.VISIBLE);
                mainBinding.progressBar.setVisibility(View.GONE);
                generateView();
            }

            @Override
            public void onFailed(String message, int responseCode) {
                Log.e("MainActivity", "message: "+message);
                MainActivity.this.klassesResponse = null;
                showToast(message);
            }

            @Override
            public void startLoading(String requestId) {

            }

            @Override
            public void endLoading(String requestId) {

            }
        });

        mainBinding.searchBar.setOnClickListener(v -> {
            Intent i= new Intent(this, SearchResultActivity.class);
            startActivity(i);
        });

        mainBinding.klass1.setOnClickListener(v -> {
            if(klassesResponse!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("klassResponse", new Gson().toJson(klassesResponse.getKlasses().get(0)));
                startActivity(intent);
            }
        });

        mainBinding.klass2.setOnClickListener(v -> {
            if(klassesResponse!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("klassResponse", new Gson().toJson(klassesResponse.getKlasses().get(1)));
                startActivity(intent);
            }
        });

        mainBinding.klass3.setOnClickListener(v -> {
            if(klassesResponse!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("klassResponse", new Gson().toJson(klassesResponse.getKlasses().get(2)));
                startActivity(intent);
            }
        });

        mainBinding.klass4.setOnClickListener(v -> {
            if(klassesResponse!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("klassResponse", new Gson().toJson(klassesResponse.getKlasses().get(3)));
                startActivity(intent);
            }
        });

        mainBinding.klass5.setOnClickListener(v -> {
            if(klassesResponse!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("klassResponse", new Gson().toJson(klassesResponse.getKlasses().get(4)));
                startActivity(intent);
            }
        });

        mainBinding.klass6.setOnClickListener(v -> {
            if(klassesResponse!=null){
                Intent intent = new Intent(this,SubjectsActivity.class);
                intent.putExtra("klassResponse", new Gson().toJson(klassesResponse.getKlasses().get(5)));
                startActivity(intent);
            }
        });

        mainBinding.bonusImg.setOnClickListener(v -> {
            if (klassesResponse!=null){
            }
        });
    }

    private void generateView() {
        Picasso.with(this).load(ShareInfo.getInstance().getRootBaseUrl()+klassesResponse.getKlasses().get(0).getBannerUrl()).into(mainBinding.klass1);
        Picasso.with(this).load(ShareInfo.getInstance().getRootBaseUrl()+klassesResponse.getKlasses().get(1).getBannerUrl()).into(mainBinding.klass2);
        Picasso.with(this).load(ShareInfo.getInstance().getRootBaseUrl()+klassesResponse.getKlasses().get(2).getBannerUrl()).into(mainBinding.klass3);
        Picasso.with(this).load(ShareInfo.getInstance().getRootBaseUrl()+klassesResponse.getKlasses().get(3).getBannerUrl()).into(mainBinding.klass4);
        Picasso.with(this).load(ShareInfo.getInstance().getRootBaseUrl()+klassesResponse.getKlasses().get(4).getBannerUrl()).into(mainBinding.klass5);
        Picasso.with(this).load(ShareInfo.getInstance().getRootBaseUrl()+klassesResponse.getKlasses().get(5).getBannerUrl()).into(mainBinding.klass6);
        Picasso.with(this).load(ShareInfo.getInstance().getRootBaseUrl()+klassesResponse.getUser().getAvatar()).into(mainBinding.profileImg);
    }


    public void profile(View view) {
        startActivity(new Intent(this,ProfileActivity.class));
    }

}
