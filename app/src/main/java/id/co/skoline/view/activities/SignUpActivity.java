package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySignUpBinding;
import id.co.skoline.model.configuration.ApiHandler;
import id.co.skoline.model.response.UserResponse;
import id.co.skoline.viewControllers.interfaces.SignupListener;
import id.co.skoline.viewControllers.managers.AuthenticationManager;

public class SignUpActivity extends BaseActivity{

    ActivitySignUpBinding signUpBinding;
    AuthenticationManager authenticationManager;
    UserResponse userResponseList;
    Calendar calendar;
    String childName,dob,uName,location,email,phoneNumber;
    int emailSwitch=0;
    int year;
    public static final Pattern PHONE
            = Pattern.compile(                      // sdd = space, dot, or dash
            "(\\+[0-9]+[\\- ]*)?"        // +<digits><sdd>*
                    + "(\\([0-9]+\\)[\\- ]*)?"   // (<digits>)<sdd>*
                    + "([0-9][0-9\\- ]+[0-9])");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        calendar= Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
    }

    @Override
    protected void viewRelatedTask() {
        signUpBinding.emailSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            signUpBinding.email.setEnabled(true);
                            emailSwitch=1;
                        } else {
                            signUpBinding.email.setEnabled(false);
                            emailSwitch=0;
                        }

                    }
                }
        );

        signUpBinding.termsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    signUpBinding.signup.setEnabled(true);
                    signUpBinding.signup.setTextColor(Color.WHITE);

                } else {
                    signUpBinding.signup.setEnabled(false);
                    signUpBinding.signup.setTextColor(Color.GRAY);
                }
            }
        });
       List<Integer> arraySpinner = new ArrayList() {};
        for (int i = 18;i>=0;i--){
            arraySpinner.add(year-i);
        }

        Spinner s = (Spinner) findViewById(R.id.year);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, arraySpinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setPrompt("yyyy");
        s.setAdapter(adapter);

        Log.d("year",String.valueOf(year));
        signUpBinding.terms.setText(Html.fromHtml(getString(R.string.agree_terms_privacy)));
        signUpBinding.terms.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidPhone(CharSequence target) {
        return (!TextUtils.isEmpty(target) && PHONE.matcher(target).matches());
    }


    public void verify(View view) {
        childName= signUpBinding.childName.getText().toString();
        uName= signUpBinding.uname.getText().toString();
        dob= signUpBinding.day.getSelectedItem().toString()
                +"-"+signUpBinding.month.getSelectedItem().toString()
                +"-"+signUpBinding.year.getSelectedItem().toString();
        phoneNumber= signUpBinding.number.getText().toString();
        email = signUpBinding.email.getText().toString();


        if(TextUtils.isEmpty(childName)){
            signUpBinding.childName.setError("Can't be Empty");
        }

        if(TextUtils.isEmpty(uName)){
            signUpBinding.uname.setError("Can't be Empty");
        }

        if (TextUtils.isEmpty(phoneNumber)){
            signUpBinding.number.setError("Can't be Empty");
        }


        if(!TextUtils.isEmpty(childName) && !TextUtils.isEmpty(uName) && !TextUtils.isEmpty(phoneNumber)){


            authenticationManager=new AuthenticationManager(this);
            authenticationManager.signUp(childName,phoneNumber,uName,dob,new SignupListener() {
                @Override
                public void onSuccess(UserResponse userResponseList) {
                    SignUpActivity.this.userResponseList=userResponseList;
                    Log.i("success","User Created");
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


            if (emailSwitch==1){
                if (!isValidEmail(email)){
                    signUpBinding.email.setError("invalid Email");
                }
                else {
                    startActivity(new Intent(this,OtpActivity.class));
                    finish();
                }
            }
            else{
                if (!isValidPhone(phoneNumber)){
                signUpBinding.number.setError("invalid Phone Number");
                }
                else {
                    startActivity(new Intent(this,OtpActivity.class));
                    finish();
                }

            }
        }
    }
}
