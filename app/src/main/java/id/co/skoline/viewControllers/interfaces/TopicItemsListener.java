package id.co.skoline.viewControllers.interfaces;

import id.co.skoline.model.response.TopicItemsResponse;

public interface TopicItemsListener extends BaseApiCallListener{


    void onSuccess(TopicItemsResponse topicItemsResponseList);
    void onFailed(String message, int responseCode);

}
