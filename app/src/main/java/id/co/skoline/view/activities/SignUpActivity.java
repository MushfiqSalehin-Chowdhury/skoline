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
        signUpBinding.terms.setText(Html.fromHtml(getString(R.string.agree_terms_privacy)));
        signUpBinding.terms.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void verify(View view) {
        startActivity(new Intent(this,OtpActivity.class));
    }
}
