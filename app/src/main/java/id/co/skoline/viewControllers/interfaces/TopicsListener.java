package id.co.skoline.viewControllers.interfaces;

import java.util.List;
import id.co.skoline.model.response.TopicResponse;

public interface TopicsListener extends BaseApiCallListener {

    void onSuccess(List<TopicResponse> topicResponseList);
    void onFailed(String message, int responseCode);
}
