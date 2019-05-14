package id.co.skoline.viewControllers.interfaces;

import java.util.List;

import id.co.skoline.model.response.FaqResponse;

public interface FaqListener extends BaseApiCallListener{
    void onSuccess(List<FaqResponse> faqResponseList);
    void onFailed(String message, int responseCode);
}
