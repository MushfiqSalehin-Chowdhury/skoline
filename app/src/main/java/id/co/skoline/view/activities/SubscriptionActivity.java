package id.co.skoline.view.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySubscriptionBinding;
import id.co.skoline.model.response.SubscriptionResponse;
import id.co.skoline.viewControllers.interfaces.ConfirmSubscriptionListener;
import id.co.skoline.viewControllers.interfaces.SubscriptionListener;
import id.co.skoline.viewControllers.managers.AuthenticationManager;

public class
SubscriptionActivity extends BaseActivity{

    ActivitySubscriptionBinding subscriptionBinding;
    AuthenticationManager authenticationManager;
    int trailVersionId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscriptionBinding = DataBindingUtil.setContentView(this,R.layout.activity_subscription);
    }

    @Override
    protected void viewRelatedTask() {
        authenticationManager = new AuthenticationManager(this);
        authenticationManager.getSubscriptionList(new SubscriptionListener() {
            @Override
            public void onSuccess(List<SubscriptionResponse> subscriptionResponses) {
                Log.e("SubscriptionActivity", new Gson().toJson(subscriptionResponses));
                for(int i=0;i<subscriptionResponses.size();i++){
                    if(subscriptionResponses.get(i).getCategory().equals("trial")){
                        trailVersionId = subscriptionResponses.get(i).getId();
                    }
                }
            }

            @Override
            public void onFailed(String message, int responseCode) {
                Log.e("SubscriptionActivity", message);
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

        subscriptionBinding.btnTrialVersion.setOnClickListener(v -> activeTrialVersion(trailVersionId));
    }

    private void activeTrialVersion(int trailVersionId) {
        authenticationManager.confirmSubscription(String.valueOf(trailVersionId), new ConfirmSubscriptionListener() {
            @Override
            public void onSuccess(String message) {
                showToast(getString(R.string.subcription_successful));
                goToHome();
            }

            @Override
            public void onFailed(String message, int responseCode) {
                showToast(message);
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
    }
}
