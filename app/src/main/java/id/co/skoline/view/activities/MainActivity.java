package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityMainBinding;
import id.co.skoline.model.response.KlassesResponse;
import id.co.skoline.view.adapters.HomepageAdapter;
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
       /* mainBinding.gridView.setAdapter(new HomepageAdapter(this));*/
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

           }
        });

        mainBinding.klass2.setOnClickListener(v -> {
            if(klassesResponseList!=null){

            }
        });

        mainBinding.klass3.setOnClickListener(v -> {
            if(klassesResponseList!=null){

            }
        });

        mainBinding.klass4.setOnClickListener(v -> {
            if(klassesResponseList!=null){

            }
        });

        mainBinding.klass5.setOnClickListener(v -> {
            if(klassesResponseList!=null){

            }
        });

        mainBinding.klass6.setOnClickListener(v -> {
            if(klassesResponseList!=null){

            }
        });

    }

    private void generateView(List<KlassesResponse> klassesResponseList) {
        Picasso.with(this).load(klassesResponseList.get(0).getBannerUrl()).into(mainBinding.klass1);
        Picasso.with(this).load(klassesResponseList.get(1).getBannerUrl()).into(mainBinding.klass2);
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
