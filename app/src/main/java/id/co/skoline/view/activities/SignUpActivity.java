package id.co.skoline.view.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySignUpBinding;
import id.co.skoline.model.configuration.ApiHandler;
import id.co.skoline.model.response.SignupErrorResponse;
import id.co.skoline.model.response.UserResponse;
import id.co.skoline.model.utils.GeocoderHandler;
import id.co.skoline.model.utils.LocationAddress;
import id.co.skoline.viewControllers.interfaces.SignupListener;
import id.co.skoline.viewControllers.managers.AuthenticationManager;

public class SignUpActivity extends BaseActivity{

    ActivitySignUpBinding signUpBinding;
    AuthenticationManager authenticationManager;
    Calendar calendar;
    String childName,dob,uName,email,phoneNumber,countryCode;
    Spinner spinner;
    Double lat,lang;
    LocationManager locationManager;
    LocationListener locationListener;
    private FusedLocationProviderClient mfusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    int emailSwitch=0;
    LocationAddress locationAddress;
    GeocoderHandler geocoderHandler;
    int year;
    public static final Pattern PHONE
            = Pattern.compile(
            "(\\+[0-9]+[\\- ]*)?"
                    + "(\\([0-9]+\\)[\\- ]*)?"
                    + "([0-9][0-9\\- ]+[0-9])");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        calendar= Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        geocoderHandler = new GeocoderHandler();
        locationAddress= new LocationAddress();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                getLocation();
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }
            @Override
            public void onProviderEnabled(String s) {
            }
            @Override
            public void onProviderDisabled(String s) {
            }
        };
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            { ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},2);
            }
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            getLocation();
        }
    }
    private void getLocation (){
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            Task location = mfusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Log.d("location","Found");
                        Location currentLocation = (Location) task.getResult();
                        //Log.i("here",String.valueOf(currentLocation.getLatitude())+","+currentLocation.getLongitude());
                        lat = currentLocation.getLatitude();
                        lang =currentLocation.getLongitude();
                        locationAddress.getAddressFromLocation(lat,lang,
                                getApplicationContext(), geocoderHandler);
                    }
                    else
                        Log.d("location","not found");
                }
            });
        }catch(SecurityException e)
        {
            Log.d("notFound", "location not found");
        }
        signUpBinding.location.setText(geocoderHandler.getLocation());
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
                            emailSwitch = 0;
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

        spinner = findViewById(R.id.year);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, arraySpinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("yyyy");
        spinner.setAdapter(adapter);

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
        countryCode=signUpBinding.ccp.getSelectedCountryCode();
        email = signUpBinding.email.getText().toString();


        if(TextUtils.isEmpty(childName)){
            signUpBinding.childName.setError(getString(R.string.emptyField));
        }

        if(TextUtils.isEmpty(uName)){
            signUpBinding.uname.setError(getString(R.string.emptyField));
        }

        if (TextUtils.isEmpty(phoneNumber)){
            signUpBinding.number.setError(getString(R.string.emptyField));
        }


        if(!TextUtils.isEmpty(childName) && !TextUtils.isEmpty(uName) && !TextUtils.isEmpty(phoneNumber)){

            authenticationManager=new AuthenticationManager(this);

            if (isValidPhone(phoneNumber)){
                authenticationManager.signUp(childName,countryCode+phoneNumber,uName,dob,new SignupListener() {

                    @Override
                    public void onSuccess(SignupErrorResponse signupErrorResponseList) {
                        if(signupErrorResponseList.getStatus()==422){
                            showToast(signupErrorResponseList.getMessage());
                        }
                        else{
                            Log.i("success","User Created");
                            Toast.makeText(SignUpActivity.this,getString(R.string.userCreated), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this,OtpActivity.class));
                        }
                    }
                    @Override
                    public void onFailed(String message, int responseCode) {
                        Log.i("invalid",message);
                        showToast(message);
                    }
                    @Override
                    public void startLoading(String requestId) {
                    }

                    @Override
                    public void endLoading(String requestId) {
                    }
                });

            }
            else {
                signUpBinding.number.setError(getString(R.string.invalidNumber));
            }
            if (emailSwitch==1){
                if (!isValidEmail(email)){
                    signUpBinding.email.setError(getString(R.string.invalidEmail));
                }
            }

        }
    }
}
