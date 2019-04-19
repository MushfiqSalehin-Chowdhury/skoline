package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySignUpBinding;
import id.co.skoline.model.configuration.ApiHandler;
import id.co.skoline.model.response.UserResponse;
import id.co.skoline.viewControllers.interfaces.SignupListener;
import id.co.skoline.viewControllers.managers.AuthenticationManager;

public class SignUpActivity extends BaseActivity {

    ActivitySignUpBinding signUpBinding;
    AuthenticationManager authenticationManager;
    UserResponse userResponseList;
    Calendar calendar;
    String childName,dob,uName,location,email,phoneNumber;
    int year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        calendar= Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
    }

    @Override
    protected void viewRelatedTask() {
       List<Integer> arraySpinner = new ArrayList() {};
        for (int i = 0;i<=18;i++){
            arraySpinner.add(year-i);
        }

        Spinner s = (Spinner) findViewById(R.id.year);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, arraySpinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        Log.d("year",String.valueOf(year));
        signUpBinding.terms.setText(Html.fromHtml(getString(R.string.agree_terms_privacy)));
        signUpBinding.terms.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void verify(View view) {
        childName= signUpBinding.childName.getText().toString();
        uName= signUpBinding.uname.getText().toString();
        dob= signUpBinding.day.getSelectedItem().toString()
                +"-"+signUpBinding.month.getSelectedItem().toString()
                +"-"+signUpBinding.year.getSelectedItem().toString();
        phoneNumber= signUpBinding.number.getText().toString();

        authenticationManager=new AuthenticationManager(this);
        authenticationManager.signUp(childName,phoneNumber,uName,dob,new SignupListener() {
            @Override
            public void onSuccess(UserResponse userResponseList) {
                SignUpActivity.this.userResponseList=userResponseList;
                Log.i("success","User Created");
                signUpUser(userResponseList);
            }
            @Override
            public void onFailed(String message, int responseCode) {
                SignUpActivity.this.userResponseList = null;
                Log.i("failed",message);
            }

            @Override
            public void startLoading(String requestId) {
            }

            @Override
            public void endLoading(String requestId) {
            }
        });

        startActivity(new Intent(this,OtpActivity.class));
    }

    private void signUpUser(UserResponse userResponseList){
    }
}
