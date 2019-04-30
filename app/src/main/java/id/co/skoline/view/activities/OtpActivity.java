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
    }

    public void backToSignUp(View view) {
       finish();
    }

    public void signIn(View view) {
        startActivity(new Intent(this,SignInActivity.class));
    }

    public void subscriptionProcess(View view)  {
    }
}
