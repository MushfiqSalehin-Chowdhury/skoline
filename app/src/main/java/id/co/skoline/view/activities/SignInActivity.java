package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivitySignInBinding;
import id.co.skoline.model.response.TokenResponse;
import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.viewControllers.interfaces.SignInListener;
import id.co.skoline.viewControllers.managers.AuthenticationManager;

public class SignInActivity extends BaseActivity {

    ActivitySignInBinding signInBinding;
    AuthenticationManager authenticationManager;
    String uName,dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
    }
    @Override
    protected void viewRelatedTask() {
        signInBinding.dob.addTextChangedListener(new TextWatcher() {
            int previousLength = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                previousLength = signInBinding.dob.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if ((previousLength < length) && (length == 2 || length == 5)) {
                    s.append("/");
                }
            }
        });

        // set custom font
        signInBinding.signin.setTypeface(defaultTypeface);

        /*signInBinding.uname.setText("salehin_mushfiq");
        signInBinding.dob.setText("09/10/2019");*/
    }
    public void resendUname(View view) {
        startActivity(new Intent(this, ResendUniqueNameActivity.class));
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
        } else {
            uName = signInBinding.uname.getText().toString();
            dob = signInBinding.dob.getText().toString();

            authenticationManager = new AuthenticationManager(this);
            authenticationManager.signIn(uName, dob, new SignInListener() {
                @Override
                public void onSuccess(TokenResponse tokenResponseList) {
                    if(tokenResponseList.getStatus()==200){
                        setToken(tokenResponseList);
                    } else if(tokenResponseList.getStatus()==404) {
                        showToast(tokenResponseList.getMessage());
                    }
                }
                @Override
                public void onFailed(String message, int responseCode) {
                    Toast.makeText(SignInActivity.this, getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();
                    signInBinding.unameError.setVisibility(View.VISIBLE);
                    signInBinding.dobError.setVisibility(View.VISIBLE);
                }
                @Override
                public void startLoading(String requestId) {
                    showProgressDialog(getString(R.string.signin_you_in), false);
                }
                @Override
                public void endLoading(String requestId) {
                    dismissProgressDialog();
                }
            });
        }
    }

    private void setToken (TokenResponse tokenResponseList){
        ShareInfo.getInstance().setAuthenticationToken(this, tokenResponseList.getToken());
        goToHome();
        this.finish();
    }
}