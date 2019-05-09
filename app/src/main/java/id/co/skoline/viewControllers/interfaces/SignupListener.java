package id.co.skoline.viewControllers.interfaces;

import id.co.skoline.model.response.SignupResponse;

public interface SignupListener extends BaseApiCallListener {
    void onSuccess(SignupResponse signupResponseList);
    void onFailed(String message, int responseCode);
}
