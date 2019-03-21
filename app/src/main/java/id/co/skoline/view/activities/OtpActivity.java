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

        otpBinding.sampleText.setText(R.string.sms_varification_text);
        otpBinding.sampleText.setTextColor(Color.WHITE);
        otpBinding.vCode.setHint(R.string.smsVarification_hint);
        otpBinding.vButton.setText(R.string.varificationButton_text);
        otpBinding.resendCode.setText(R.string.resendCode_text);
        otpBinding.backSignup.setText(R.string.signUpBackButton_text);
    }

    public void backToSignUp(View view) {
       finish();
    }

    public void signIn(View view) {
        startActivity(new Intent(this,SignInActivity.class));
    }
}
