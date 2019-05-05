package id.co.skoline.viewControllers.interfaces;

import java.util.List;

import id.co.skoline.model.response.SubjectResponse;
import id.co.skoline.model.response.SubscriptionResponse;

public interface SubscriptionListener extends BaseApiCallListener{
    void onSuccess(List<SubscriptionResponse> subscriptionResponses);
    void onFailed(String message, int responseCode);

}
