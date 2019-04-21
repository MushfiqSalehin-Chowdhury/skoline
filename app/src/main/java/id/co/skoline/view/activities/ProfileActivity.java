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
import java.util.Calendar;
import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityProfileBinding;
import id.co.skoline.model.response.TopicItemsResponse;
import id.co.skoline.model.response.UserResponse;
import id.co.skoline.viewControllers.interfaces.UserListerner;
import id.co.skoline.viewControllers.managers.AuthenticationManager;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;

public class ProfileActivity extends BaseActivity {

    ActivityProfileBinding profileBinding;
    AuthenticationManager authenticationManager;
    UserResponse userResponseList;
    private String[] dob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileBinding= DataBindingUtil.setContentView(this,R.layout.activity_profile);

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(2, getResources().getColor(R.color.GreenDark)).setLabel("IPS"+"\n"+"2%"));
        pieData.add(new SliceValue(41, getResources().getColor(R.color.purple)).setLabel("IPA"+"\n"+"41%"));
        pieData.add(new SliceValue(32, getResources().getColor(R.color.colorAccent)).setLabel("MTK"+"\n"+"32%"));
        pieData.add(new SliceValue(15,getResources().getColor(R.color.colorPrimaryDark2)).setLabel("B.ENG"+"\n"+"15%"));
        pieData.add(new SliceValue(10, getResources().getColor(R.color.colorPrimaryDark)).setLabel("B.IND"+"\n"+"10%"));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        profileBinding.chart.setPieChartData(pieChartData);
        profileBinding.englishTitle.setText("English"+"(0/0 video)");
        profileBinding.englishProgress.setProgress(30);
        profileBinding.englishProgressPercent.setText("30%");
        profileBinding.mathProgress.setProgress(50);
        profileBinding.mathProgressPercent.setText("50%");
        profileBinding.socialProgress.setProgress(50);
        profileBinding.socialProgressPercent.setText("10%");
        profileBinding.pendidicanProgress.setProgress(0);
        profileBinding.pendidicanProgressPercent.setText("0%");
        profileBinding.ilmuProgress.setProgress(40);
        profileBinding.ilmuProgressPercent.setText("40%");
        profileBinding.indonesiaProgress.setProgress(80);
        profileBinding.indonesiaProgressPercent.setText("80%");


        authenticationManager= new AuthenticationManager(this);
        authenticationManager.getUsers(new UserListerner() {
            @Override
            public void onSuccess(UserResponse userResponseList) {
                ProfileActivity.this.userResponseList= userResponseList;
                generateViewUser(userResponseList);
            }

            @Override
            public void onFailed(String message, int responseCode) {
                ProfileActivity.this.userResponseList = null;
            }

            @Override
            public void startLoading(String requestId) {

            }

            @Override
            public void endLoading(String requestId) {

            }
        });

    }

    @Override
    protected void viewRelatedTask() {
        setToolbar("Profile",true,profileBinding.toolbarBinding);

    }

    public void showMenu(View view) {
        PopupMenu popup = new PopupMenu(ProfileActivity.this,profileBinding.option);
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
    private void generateViewUser(UserResponse userResponseList) {
        dob = userResponseList.getUser().getBirthDate().toString().split("-");
        String age=getAge(Integer.valueOf(dob[0]),Integer.valueOf(dob[1]),Integer.valueOf(dob[2]));
        profileBinding.nameANDage.setText(userResponseList.getUser().getChildName()+","+age+"Years");

     /*   topicScreenBinding.adventureDetails.setText(topicItemsResponseList.getTopic().getAdventure().getDescription());*/
    }
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        return ageS;
    }
}
