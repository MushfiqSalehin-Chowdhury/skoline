package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityResultBinding;

public class ResultActivity extends BaseActivity {

  ActivityResultBinding resultBinding;
  String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultBinding = DataBindingUtil.setContentView(this,R.layout.activity_result);
    }

    @Override
    protected void viewRelatedTask() {

        Intent i = getIntent();
        title = i.getStringExtra ( "key");
        setToolbar(title,true,resultBinding.toolbarBinding);
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
    }
}
