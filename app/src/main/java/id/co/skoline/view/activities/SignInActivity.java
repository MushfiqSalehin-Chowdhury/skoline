package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySignInBinding;
import id.co.skoline.model.response.TokenResponse;
import id.co.skoline.model.response.UserResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.viewControllers.interfaces.SignInListener;
import id.co.skoline.viewControllers.interfaces.SignupListener;
import id.co.skoline.viewControllers.managers.AuthenticationManager;

public class SignInActivity extends BaseActivity {

    ActivitySignInBinding signInBinding;
    AuthenticationManager authenticationManager;
    String uName,dob,accessToken;
    UserResponse userResponseList;
    TokenResponse tokenResponseList;

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
        if (TextUtils.isEmpty(signInBinding.uname.getText().toString()) || TextUtils.isEmpty(signInBinding.dob.getText().toString()) )  {
            if(TextUtils.isEmpty(signInBinding.uname.getText().toString())){
                signInBinding.unameError.setVisibility(View.VISIBLE);
            }
            if(!TextUtils.isEmpty(signInBinding.uname.getText().toString())){
                signInBinding.unameError.setVisibility(View.GONE);
            }
            if(TextUtils.isEmpty(signInBinding.dob.getText().toString())){
                signInBinding.dobError.setVisibility(View.VISIBLE);
            }
            if(!TextUtils.isEmpty(signInBinding.dob.getText().toString())){
                signInBinding.dobError.setVisibility(View.GONE);
            }
        }
        else {
            uName = signInBinding.uname.getText().toString();
            dob = signInBinding.dob.getText().toString();

            authenticationManager = new AuthenticationManager(this);
            authenticationManager.signIn(uName, dob, new SignInListener() {
                @Override
                public void onSuccess(TokenResponse tokenResponseList) {
                    SignInActivity.this.tokenResponseList = tokenResponseList;
                    new CountDownTimer(3000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            showProgressDialog("Sigining In", false);
                        }
                        @Override
                        public void onFinish() {
                            dismissProgressDialog();
                            setToken(tokenResponseList);
                        }
                    }.start();
                    Log.i("Token","Token Recieved");
                }
                @Override
                public void onFailed(String message, int responseCode) {
                    Toast.makeText(SignInActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                    signInBinding.unameError.setVisibility(View.VISIBLE);
                    signInBinding.dobError.setVisibility(View.VISIBLE);
                }
                @Override
                public void startLoading(String requestId) {

                }
                @Override
                public void endLoading(String requestId) {
                }
            });
        }
    }
    private void setToken (TokenResponse  tokenResponseList){
        ShareInfo.getInstance().setAuthenticationToken(this,tokenResponseList.getToken().toString());
        goToHome();
    }
}