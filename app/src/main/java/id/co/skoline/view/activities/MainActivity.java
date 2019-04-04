package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityMainBinding;
import id.co.skoline.view.adapters.HomepageAdapter;

public class MainActivity extends BaseActivity {

    ActivityMainBinding mainBinding;
    String titleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

    }

    @Override
    protected void viewRelatedTask() {
       /* mainBinding.gridView.setAdapter(new HomepageAdapter(this));*/
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
