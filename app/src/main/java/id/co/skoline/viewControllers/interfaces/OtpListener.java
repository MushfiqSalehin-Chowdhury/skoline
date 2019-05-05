package id.co.skoline.viewControllers.interfaces;

import java.util.List;

import id.co.skoline.model.response.OtpResponse;

public interface OtpListener extends BaseApiCallListener {
    void onSuccess(OtpResponse otpResponse);
    void onFailed(String message, int responseCode);
}
