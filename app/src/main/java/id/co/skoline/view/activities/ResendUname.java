package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityResendUnameBinding;

public class ResendUname extends BaseActivity{
    ActivityResendUnameBinding resendUnameBinding;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resendUnameBinding= DataBindingUtil.setContentView(this,R.layout.activity_resend_uname);
        phoneNumber= resendUnameBinding.ccp.getSelectedCountryCode()+resendUnameBinding.number.getText();
    }

    @Override
    protected void viewRelatedTask() {

    }

    public void resendUname(View view) {
        Toast.makeText(this,getString(R.string.uniqueName_resend)+phoneNumber, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,SignInActivity.class));
        finish();
    }
}
