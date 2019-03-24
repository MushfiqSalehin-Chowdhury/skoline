package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bumptech.glide.Glide;

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
        signUpBinding.sampleText.setText(getString(R.string.name_info));
        signUpBinding.sampleText.setTextColor(Color.WHITE);
        signUpBinding.uname.setHint(getString(R.string.name_hint));
        signUpBinding.email.setHint(getString(R.string.email_hint));
        signUpBinding.childName.setHint(getString(R.string.childName_hint));
        signUpBinding.location.setHint(getString(R.string.location_hint));
        signUpBinding.sampleText1.setText(getString(R.string.childName_info));
        signUpBinding.sampleText2.setText(getString(R.string.dob));
        signUpBinding.number.setHint(getString(R.string.number_hint));
        signUpBinding.news.setText(getString(R.string.newspaper_text));
        signUpBinding.terms.setText(Html.fromHtml(getString(R.string.agree_terms_privacy)));
        signUpBinding.terms.setMovementMethod(LinkMovementMethod.getInstance());
        signUpBinding.signup.setText(getString(R.string.signup_button));
        signUpBinding.signup.setTextColor(Color.WHITE);
        signUpBinding.locationInfo.setText(getString(R.string.location_info));



    }

    public void verify(View view) {
        startActivity(new Intent(this,OtpActivity.class));
    }
}
