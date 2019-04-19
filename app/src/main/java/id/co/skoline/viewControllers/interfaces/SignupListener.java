package id.co.skoline.viewControllers.interfaces;

import id.co.skoline.model.response.UserResponse;

public interface SignupListener extends BaseApiCallListener {
    void onSuccess(UserResponse userResponseList);
    void onFailed(String message, int responseCode);
}
