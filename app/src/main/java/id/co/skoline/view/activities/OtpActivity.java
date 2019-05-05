package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityOtpBinding;
import id.co.skoline.model.response.OtpResponse;
import id.co.skoline.viewControllers.interfaces.OtpListener;
import id.co.skoline.viewControllers.managers.AuthenticationManager;

public class OtpActivity extends BaseActivity {

    ActivityOtpBinding otpBinding;
    AuthenticationManager authenticationManager;
    String phone,uniqueName,otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpBinding= DataBindingUtil.setContentView(this, R.layout.activity_otp);
    }

    @Override
    protected void viewRelatedTask(){
        otpBinding.varifyButton.setTypeface(defaultTypeface);
        otpBinding.backSignupButton.setTypeface(defaultTypeface);
        otpBinding.resendCodeButton.setTypeface(defaultTypeface);

        otpBinding.varifyButton.setOnClickListener(v -> {
            authenticationManager = new AuthenticationManager(this);
            authenticationManager.getOtp(phone, uniqueName, new OtpListener() {
                @Override
                public void onSuccess(OtpResponse otpResponseList) {
                    showToast(otpResponseList.getOtp());
                }

                @Override
                public void onFailed(String message, int responseCode) {

                }

                @Override
                public void startLoading(String requestId) {

                }

                @Override
                public void endLoading(String requestId) {

                }
            });

        });
    }

    public void backToSignUp(View view) {
       finish();
    }

    public void signIn(View view) {

    }

    public void subscriptionProcess(View view)  {
    }
}
