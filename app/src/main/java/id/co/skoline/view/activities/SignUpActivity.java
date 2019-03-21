package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySignUpBinding;

public class SignUpActivity extends BaseActivity {

    ActivitySignUpBinding signUpBinding;
    Spinner day,month,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

    }


    @Override
    protected void viewRelatedTask() {
        signUpBinding.sampleText.setText(R.string.name_info);
        signUpBinding.sampleText.setTextColor(Color.WHITE);
        signUpBinding.uname.setHint(R.string.name_hint);
        signUpBinding.email.setHint(R.string.email_hint);
        signUpBinding.childName.setHint(R.string.childName_hint);
        signUpBinding.location.setHint(R.string.location_hint);
        signUpBinding.sampleText1.setText(R.string.childName_info);
        signUpBinding.sampleText2.setText(R.string.dob);
        signUpBinding.news.setText(R.string.newspaper_text);
        signUpBinding.terms.setText(R.string.terms_text);
        signUpBinding.signup.setText(R.string.signUpBackButton_text);

    }

    public void verify(View view) {
        startActivity(new Intent(this,OtpActivity.class));
    }
}
