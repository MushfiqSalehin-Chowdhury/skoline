package id.co.skoline.viewControllers.interfaces;

import id.co.skoline.model.response.SearchResponse;

public interface SearchListener extends BaseApiCallListener {
    void onSuccess(SearchResponse searchResponse);
    void onFailed(String message, int responseCode);
}
