package id.co.skoline.viewControllers.interfaces;

import java.util.List;

import id.co.skoline.model.response.KlassesResponse;

public interface KlassesListener extends BaseApiCallListener{


    void onSuccess(KlassesResponse klassesResponse);
    void onFailed(String message, int responseCode);
}
