package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
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
        signInBinding= DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
    }

    @Override
    protected void viewRelatedTask() {
        signInBinding.sampleText5.setText(getText(R.string.dob));
        signInBinding.uname.setHint(getString(R.string.name_hint));
        signInBinding.signin.setText(getText(R.string.signin_button));
        signInBinding.signin.setTextColor(Color.WHITE);
        signInBinding.forgotUname.setText(getText(R.string.forgot_Uname_text));
        signInBinding.dob.setHint(getString(R.string.dob_hint));

    }


    public void resendUname(View view) {

            startActivity(new Intent(this, ResendUname.class));
            finish();
    }

    public void signIn(View view) {
        if(TextUtils.isEmpty(signInBinding.uname.getText().toString())
                && TextUtils.isEmpty(signInBinding.dob.getText().toString()) ){

            signInBinding.unameError.setText(getText(R.string.unameError));
            signInBinding.dobError.setText(getText(R.string.dobError));
            signInBinding.dobError.setVisibility(View.VISIBLE);
            signInBinding.unameError.setVisibility(View.VISIBLE);
        }
        else {

            startActivity(new Intent(this,MainMenuActivity.class));
            Toast.makeText(this, "SignIn Process", Toast.LENGTH_SHORT).show();
        }
    }
}
