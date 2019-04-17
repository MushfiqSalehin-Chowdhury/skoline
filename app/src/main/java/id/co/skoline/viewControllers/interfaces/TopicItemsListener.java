package id.co.skoline.viewControllers.interfaces;

import id.co.skoline.model.response.TopicItemsResponse;

public interface TopicItemsListener extends BaseApiCallListener{


    void onSuccess(TopicItemsResponse advantureResponseList);
    void onFailed(String message, int responseCode);

}
