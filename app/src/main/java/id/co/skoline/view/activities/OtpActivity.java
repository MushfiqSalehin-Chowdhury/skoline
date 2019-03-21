package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityOtpBinding;

public class OtpActivity extends BaseActivity {

    ActivityOtpBinding otpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpBinding= DataBindingUtil.setContentView(this, R.layout.activity_otp);
    }

    @Override
    protected void viewRelatedTask(){

        otpBinding.sampleText.setText(getString(R.string.sms_varification_text));
        otpBinding.sampleText.setTextColor(Color.WHITE);
        otpBinding.vCode.setHint(getString(R.string.smsVarification_hint));
        otpBinding.vButton.setText(getString(R.string.varificationButton_text));
        otpBinding.vButton.setTextColor(Color.WHITE);
        otpBinding.resendCode.setText(getString(R.string.resendCode_text));
        otpBinding.resendCode.setTextColor(Color.WHITE);
        otpBinding.backSignup.setText(getString(R.string.signUpBackButton_text));
        otpBinding.backSignup.setTextColor(Color.WHITE);
    }

    public void backToSignUp(View view) {
       finish();
    }

    public void signIn(View view) {
        startActivity(new Intent(this,SignInActivity.class));
    }
}
