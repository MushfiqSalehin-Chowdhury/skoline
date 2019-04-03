package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySignInBinding;

public class SignInActivity extends BaseActivity {

    ActivitySignInBinding signInBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
    }

    @Override
    protected void viewRelatedTask() {

    }

    public void resendUname(View view) {
        startActivity(new Intent(this, ResendUname.class));
        finish();
    }

    public void signIn(View view) {
      /*  if(TextUtils.isEmpty(signInBinding.uname.getText().toString())){
            signInBinding.unameError.setVisibility(View.VISIBLE);
        } else if(TextUtils.isEmpty(signInBinding.dob.getText().toString())){
            signInBinding.dobError.setVisibility(View.VISIBLE);
        } else {*/
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "SignIn Process", Toast.LENGTH_SHORT).show();
        //}
    }
}
