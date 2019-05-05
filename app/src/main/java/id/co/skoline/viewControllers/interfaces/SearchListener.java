package id.co.skoline.viewControllers.interfaces;

import java.util.List;

import id.co.skoline.model.response.SearchResponse;

public interface SearchListener extends BaseApiCallListener {
    void onSuccess(List<SearchResponse> searchResponseList);
    void onFailed(String message, int responseCode);
}
