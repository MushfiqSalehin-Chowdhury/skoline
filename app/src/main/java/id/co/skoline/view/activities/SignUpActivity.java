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

public class SignUpActivity extends BaseActivity {

    ActivitySignUpBinding signUpBinding;
    Calendar calendar;
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
        startActivity(new Intent(this,OtpActivity.class));
    }
}
