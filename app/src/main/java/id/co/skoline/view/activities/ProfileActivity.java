package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityProfileBinding;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;

public class ProfileActivity extends BaseActivity {

    ActivityProfileBinding profileBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileBinding= DataBindingUtil.setContentView(this,R.layout.activity_profile);

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(15, Color.BLUE).setLabel("Q1: $10"));
        pieData.add(new SliceValue(25, Color.GRAY).setLabel("Q2: $4"));
        pieData.add(new SliceValue(10, Color.RED).setLabel("Q3: $18"));
        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Q4: $28"));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        profileBinding.chart.setPieChartData(pieChartData);
    }

    @Override
    protected void viewRelatedTask() {
        setToolbar("Profile",true,profileBinding.toolbarBinding);

    }

    public void showMenu(View view) {
        PopupMenu popup = new PopupMenu(ProfileActivity.this,profileBinding.menu);
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {

            if(item.getTitle().equals("Help")){
                startActivity(new Intent(this,HelpActivity.class));
            }
            else if(item.getTitle().equals("Logout")){

                startActivity(new Intent(this,WelcomePageActivity.class));
                finish();
            }
            return true;
        });
        popup.show();//showing popup menu
    }
}
