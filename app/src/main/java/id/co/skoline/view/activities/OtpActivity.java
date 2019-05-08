package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityOtpBinding;
import id.co.skoline.model.response.OtpResponse;
import id.co.skoline.model.response.TokenResponse;
import id.co.skoline.model.response.VerifyOtpResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.viewControllers.interfaces.OtpListener;
import id.co.skoline.viewControllers.interfaces.VerifyOtpListener;
import id.co.skoline.viewControllers.managers.AuthenticationManager;

public class OtpActivity extends BaseActivity {

    ActivityOtpBinding otpBinding;
    AuthenticationManager authenticationManager;
    String phone,uniqueName,otp;
    OtpResponse otpResponse;
    VerifyOtpResponse verifyOtpResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpBinding= DataBindingUtil.setContentView(this, R.layout.activity_otp);
    }

    @Override
    protected void viewRelatedTask(){
        Intent intent = getIntent();
        phone=intent.getStringExtra("phone");
        uniqueName=intent.getStringExtra("uniqueName");
        Log.i("phone",phone+uniqueName);
        otpBinding.varifyButton.setTypeface(defaultTypeface);
        otpBinding.backSignupButton.setTypeface(defaultTypeface);
        otpBinding.resendCodeButton.setTypeface(defaultTypeface);

        otpBinding.varifyButton.setOnClickListener(v -> {
            authenticationManager = new AuthenticationManager(this);
            authenticationManager.getOtp(phone, uniqueName, new OtpListener() {
                @Override
                public void onSuccess(OtpResponse otpResponse) {
                    OtpActivity.this.otpResponse=otpResponse;
                 //   Log.i("otp",otpResponse.getToken());
                    showToast(otpResponse.getOtp().toString());
                    setToken(otpResponse,phone,uniqueName);

                }

                @Override
                public void onFailed(String message, int responseCode) {
                    showToast(message);
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
    private void setToken (OtpResponse otpResponse,String phone,String uniqueName){
       //ShareInfo.getInstance().setAuthenticationToken(this,otpResponse.getToken());

        authenticationManager = new AuthenticationManager(this);
        authenticationManager.checkOtp(phone, uniqueName, otpResponse.getOtp(), new VerifyOtpListener() {
            @Override
            public void onSuccess(VerifyOtpResponse verifyOtpResponse) {
                ShareInfo.getInstance().setAuthenticationToken(OtpActivity.this,verifyOtpResponse.getToken());
                Log.i("token",verifyOtpResponse.getToken());
                goToHome();
                OtpActivity.this.finish();
            }

            @Override
            public void onFailed(String message, int responseCode) {
                //showToast(message);

            }

            @Override
            public void startLoading(String requestId) {

            }

            @Override
            public void endLoading(String requestId) {

            }
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
