package id.co.skoline.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.hardware.biometrics.BiometricPrompt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.regex.Pattern;

import id.co.skoline.R;
import id.co.skoline.databinding.ActivityResendUnameBinding;
import id.co.skoline.viewControllers.interfaces.ForgetUniqueNameListerner;
import id.co.skoline.viewControllers.managers.AuthenticationManager;

public class ResendUname extends BaseActivity{

    ActivityResendUnameBinding resendUnameBinding;
    String phoneNumber;
    AuthenticationManager authenticationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resendUnameBinding= DataBindingUtil.setContentView(this,R.layout.activity_resend_uname);
    }

    @Override
    protected void viewRelatedTask() {
        authenticationManager = new AuthenticationManager(this);
        resendUnameBinding.resendUname.setTypeface(defaultTypeface);
        resendUnameBinding.resendUname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(resendUnameBinding.number.getText().toString())){
                    resendUnameBinding.number.setError(getString(R.string.emptyField));
                } else {
                    String phoneNumber = resendUnameBinding.ccp.getSelectedCountryCode()+resendUnameBinding.number.getText();
                    if(isValidPhone(phoneNumber)){
                        authenticationManager.forgetUniqueName(phoneNumber, new ForgetUniqueNameListerner() {
                            @Override
                            public void onSuccess(String message) {
                                showToast(getString(R.string.uniqueName_resend)+" "+phoneNumber);
                                startActivity(new Intent(ResendUname.this,SignInActivity.class));
                                finish();
                            }

                            @Override
                            public void onFailed(String message, int responseCode) {
                                showToast(message);
                            }

                            @Override
                            public void startLoading(String requestId) {
                                showProgressDialog(getString(R.string.sending_message), false);
                            }

                            @Override
                            public void endLoading(String requestId) {
                                dismissProgressDialog();
                            }
                        });
                    } else {
                        resendUnameBinding.number.setError(getString(R.string.invalidNumber));
                    }
                }
            }
        });
    }

    public static final Pattern PHONE
            = Pattern.compile(
            "(\\+[0-9]+[\\- ]*)?"
                    + "(\\([0-9]+\\)[\\- ]*)?"
                    + "([0-9][0-9\\- ]+[0-9])");

    public static boolean isValidPhone(CharSequence target) {
        return (!TextUtils.isEmpty(target) && PHONE.matcher(target).matches());
    }

}
