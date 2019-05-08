package id.co.skoline.viewControllers.interfaces;

import id.co.skoline.model.response.VerifyOtpResponse;

public interface VerifyOtpListener extends BaseApiCallListener{
    void onSuccess(VerifyOtpResponse verifyOtpResponse);
    void onFailed(String message, int responseCode);
}
