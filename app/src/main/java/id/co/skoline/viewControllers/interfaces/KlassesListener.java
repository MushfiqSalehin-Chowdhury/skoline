package id.co.skoline.viewControllers.interfaces;

import java.util.List;

import id.co.skoline.model.response.KlassesResponse;

public interface KlassesListener extends BaseApiCallListener{


    void onSuccess(List<KlassesResponse> klassesResponseList);
    void onFailed(String message, int responseCode);
}
