package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityHelpBinding;
import id.co.skoline.databinding.ActivityWelcomeBinding;

public class WelcomePageActivity extends BaseActivity {

    ActivityWelcomeBinding welcomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       welcomeBinding = DataBindingUtil.setContentView(this,R.layout.activity_welcome);
    }

    @Override
    protected void viewRelatedTask() {
        welcomeBinding.appweb.setText(Html.fromHtml(getString(R.string.app_website)));
        welcomeBinding.appweb.setMovementMethod(LinkMovementMethod.getInstance());
    }
    public void signIn(View view) {
        startActivity(new Intent(this,SignInActivity.class));
        finish();
    }
    public void signUp(View view) {
        startActivity(new Intent(this,SignUpActivity.class));
    }
}
