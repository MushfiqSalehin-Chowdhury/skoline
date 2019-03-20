package id.co.skoline.view.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySignUpBinding;

public class SignUpActivity extends BaseActivity {

    ActivitySignUpBinding signUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

    }

    @Override
    protected void viewRelatedTask() {
        signUpBinding.sampleText.setText("Hello");
    }
}
