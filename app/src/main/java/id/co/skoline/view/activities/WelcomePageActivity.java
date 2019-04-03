package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySigninSignupBinding;

public class WelcomePageActivity extends BaseActivity {

    ActivitySigninSignupBinding signinSignupBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       signinSignupBinding = DataBindingUtil.setContentView(this,R.layout.activity_signin_signup);
    }

    @Override
    protected void viewRelatedTask() {
        signinSignupBinding.appweb.setText(Html.fromHtml(getString(R.string.app_website)));
        signinSignupBinding.appweb.setMovementMethod(LinkMovementMethod.getInstance());

    }


    public void signIn(View view) {
        startActivity(new Intent(this,SignInActivity.class));
    }
    public void signUp(View view) {
        startActivity(new Intent(this,SignUpActivity.class));
    }
}
