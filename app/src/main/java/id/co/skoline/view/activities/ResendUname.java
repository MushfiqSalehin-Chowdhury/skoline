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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resendUnameBinding= DataBindingUtil.setContentView(this,R.layout.activity_resend_uname);
    }

    @Override
    protected void viewRelatedTask() {
        resendUnameBinding.number.setHint(getString(R.string.number_hint));
        resendUnameBinding.resendUname.setText(getText(R.string.resend_uname));
    }

    public void resendUname(View view) {
        Toast.makeText(this, "User Name Has Been Sent to  "+resendUnameBinding.number.getText().toString(), Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,SignInActivity.class));
        finish();
    }
}
