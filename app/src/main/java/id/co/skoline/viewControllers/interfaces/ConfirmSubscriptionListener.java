package id.co.skoline.viewControllers.interfaces;

import java.util.List;

import id.co.skoline.model.response.SubjectResponse;

public interface ConfirmSubscriptionListener extends BaseApiCallListener{
    void onSuccess(String message);
    void onFailed(String message, int responseCode);

}
