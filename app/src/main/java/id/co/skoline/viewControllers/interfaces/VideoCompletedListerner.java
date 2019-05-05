package id.co.skoline.viewControllers.interfaces;

import org.json.JSONObject;

import id.co.skoline.model.response.UserResponse;

public interface VideoCompletedListerner extends BaseApiCallListener {

    void onSuccess(String message);
    void onFailed(String message, int responseCode);
}
