package id.co.skoline.viewControllers.interfaces;

import id.co.skoline.model.response.TokenResponse;
import id.co.skoline.model.response.UserResponse;

public interface SignInListener extends BaseApiCallListener{
    void onSuccess(TokenResponse tokenResponseList);
    void onFailed(String message, int responseCode);
}
