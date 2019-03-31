package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySearchResultBinding;

public class SearchResultActivity extends BaseActivity {

 ActivitySearchResultBinding resultBinding;
  String titleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultBinding = DataBindingUtil.setContentView(this,R.layout.activity_search_result);
    }

    @Override
    protected void viewRelatedTask() {

        Intent i = getIntent();
        titleToolbar = i.getStringExtra ( "key");
        setToolbar(titleToolbar,true,resultBinding.toolbarBinding);
        Toast.makeText(this, titleToolbar, Toast.LENGTH_SHORT).show();
    }
}
