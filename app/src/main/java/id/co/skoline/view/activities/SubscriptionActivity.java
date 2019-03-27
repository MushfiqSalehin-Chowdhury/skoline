package id.co.skoline.view.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySubscriptionBinding;

public class SubscriptionActivity extends BaseActivity{

    ActivitySubscriptionBinding subscriptionBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscriptionBinding= DataBindingUtil.setContentView(this,R.layout.activity_subscription);
    }

    @Override
    protected void viewRelatedTask() {
        subscriptionBinding.subscriptionText.setText(getText(R.string.subscriptionTxt));
        subscriptionBinding.subs1.setText(getText(R.string.subButton1));
        subscriptionBinding.subs2.setText(getText(R.string.subButton2));

    }

    public void sucess(View view) {
        subscriptionBinding.appLogo.setVisibility(View.GONE);
        subscriptionBinding.subs1.setVisibility(View.GONE);
        subscriptionBinding.subs2.setVisibility(View.GONE);
        subscriptionBinding.subscriptionText.setVisibility(View.GONE);
        subscriptionBinding.subsCriptionLogo1.setVisibility(View.VISIBLE);
        subscriptionBinding.subsCriptionLogo.setVisibility(View.GONE);
    }

    public void failed(View view) {
    }
}
