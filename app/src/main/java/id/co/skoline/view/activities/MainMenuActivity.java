package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityMainMenuBinding;
import id.co.skoline.databinding.ToolbarBinding;

public class MainMenuActivity extends BaseActivity {

   ActivityMainMenuBinding mainMenuBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainMenuBinding = DataBindingUtil.setContentView(this,R.layout.activity_main_menu);
    }

    @Override
    protected void viewRelatedTask() {
    }

    public void search(View view) {
        Intent i= new Intent(this,ResultActivity.class);
        i.putExtra("key",mainMenuBinding.searchEditFrame.getText().toString());
       // Toast.makeText(this, mainMenuBinding.searchEditFrame.getText(), Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
}
