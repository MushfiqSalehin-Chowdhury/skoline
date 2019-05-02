package id.co.skoline.viewControllers.interfaces;

import id.co.skoline.model.response.SignupErrorResponse;
import id.co.skoline.model.response.UserResponse;

public interface SignupListener extends BaseApiCallListener {
    void onSuccess(SignupErrorResponse signupErrorResponseList);
    void onFailed(String message, int responseCode);
}
