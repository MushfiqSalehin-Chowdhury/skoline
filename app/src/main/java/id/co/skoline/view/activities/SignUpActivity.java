package id.co.skoline.view.activities;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
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
        signUpBinding.sampleText.setText("helooooooooooooooooo"+"\n" +"oooooooooooooooooooo");
        signUpBinding.sampleText.setTextColor(Color.WHITE);
        signUpBinding.name.setHint("Enter Name");
        signUpBinding.email.setHint("example@abc.com");
        signUpBinding.lastname.setHint("Last Name");
        signUpBinding.location.setHint("your location");
        signUpBinding.sampleText1.setText("helooooooooooooooooo"+"\n" +"oooooooooooooooooooo");
        signUpBinding.sampleText2.setText("Hellooooooooooooooooooooooooooooooooooooooooooooooooo");
        signUpBinding.news.setText("helooooooooooooo ?");
        signUpBinding.newss.setText("helooooooooooooooooo"+"\n" +"oooooooooooooooooooo");
        signUpBinding.signup.setText("Dafter");

    }
}
