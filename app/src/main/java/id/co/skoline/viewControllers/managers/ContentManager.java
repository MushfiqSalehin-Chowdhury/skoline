package id.co.skoline.viewControllers.managers;

import android.content.Context;
import android.provider.MediaStore;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import id.co.skoline.model.configuration.ApiHandler;
import id.co.skoline.model.response.KlassesResponse;
import id.co.skoline.model.response.SearchResponse;
import id.co.skoline.model.response.SubjectResponse;
import id.co.skoline.model.response.TopicItemsResponse;
import id.co.skoline.model.response.TopicResponse;

import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.viewControllers.interfaces.SearchListener;
import id.co.skoline.viewControllers.interfaces.TopicItemsListener;
import id.co.skoline.viewControllers.interfaces.KlassesListener;
import id.co.skoline.viewControllers.interfaces.SubjectsListener;
import id.co.skoline.viewControllers.interfaces.TopicsListener;
import id.co.skoline.viewControllers.interfaces.VideoCompletedListerner;
import okhttp3.ResponseBody;

import static id.co.skoline.model.configuration.ResponseCode.INVALID_JSON_RESPONSE;

public class ContentManager {
    private Context context;
    private ApiHandler apiHandler;
    KlassesListener klassesListener;
    SubjectsListener subjectsListener;
    TopicsListener topicsListener;
    TopicItemsListener topicItemsListener;
    SearchListener searchListener;
    VideoCompletedListerner videoCompletedListerner;
    private String reqIdKlasses;
    private String reqIdSubjects;
    private String reqIdTopics;
    private String reqIdAdvanture;
    private String reqIdSearch;
    private String reqIdVideoCompleted;

    public ContentManager(Context context){
        this.context = context;
        apiHandler = new ApiHandler(context) {
            @Override
            public void startApiCall(String requestId) {
                if(requestId.equals(reqIdKlasses)){
                    klassesListener.startLoading(requestId);
                }
                else if(requestId.equals(reqIdSubjects)){
                    subjectsListener.startLoading(requestId);
                }
                else if(requestId.equals(reqIdTopics)){
                  topicsListener.startLoading(requestId);
                }
                else if(requestId.equals(reqIdAdvanture)){
                    topicItemsListener.startLoading(requestId);
                }
                else if(requestId.equals(reqIdSearch)) {
                    searchListener.startLoading(requestId);
                }
                else if(requestId.equals(reqIdVideoCompleted)){
                    videoCompletedListerner.startLoading(requestId);
                }
            }

            @Override
            public void endApiCall(String requestId) {
                if(requestId.equals(reqIdKlasses)){
                    klassesListener.endLoading(requestId);
                }else if(requestId.equals(reqIdSubjects)){
                    subjectsListener.endLoading(requestId);
                }
                else if(requestId.equals(reqIdTopics)){
                    topicsListener.endLoading(requestId);
                }
                else if(requestId.equals(reqIdAdvanture)){
                    topicItemsListener.endLoading(requestId);
                }
                else if(requestId.equals(reqIdSearch)) {
                    searchListener.endLoading(requestId);
                }

                else if(requestId.equals(reqIdVideoCompleted)){
                    videoCompletedListerner.endLoading(requestId);
                }
            }
            @Override
            public void successResponse(String requestId, ResponseBody responseBody, String baseUrl, String path, String requestType) {
                if(requestId.equals(reqIdKlasses)){
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        klassesListener.onSuccess(new Gson().fromJson(jsonObject.toString(), KlassesResponse.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                        klassesListener.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                } else if(requestId.equals(reqIdSubjects)){
                    try {
                        Type listType = new TypeToken<List<SubjectResponse>>() {}.getType();
                        JSONArray arrayResponse = new JSONArray(responseBody.string());
                        subjectsListener.onSuccess(new Gson().fromJson(arrayResponse.toString(), listType));
                    } catch (Exception e) {
                        e.printStackTrace();
                        subjectsListener.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                } else if(requestId.equals(reqIdTopics)){
                    try {
                        Type listType = new TypeToken<List<TopicResponse>>() {}.getType();
                        JSONArray arrayResponse = new JSONArray(responseBody.string());
                        topicsListener.onSuccess(new Gson().fromJson(arrayResponse.toString(), listType));
                    } catch (Exception e) {
                        e.printStackTrace();
                        topicsListener.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                } else if ( requestId.equals(reqIdAdvanture)){
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                       topicItemsListener.onSuccess(new Gson().fromJson(jsonObject.toString(), TopicItemsResponse.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                       topicItemsListener.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                } else if(requestId.equals(reqIdVideoCompleted)){
                    videoCompletedListerner.onSuccess("");
                }
                else if ( requestId.equals(reqIdSearch)){
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        searchListener.onSuccess(new Gson().fromJson(jsonObject.toString(), SearchResponse.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                        searchListener.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                }
            }
            @Override
            public void failResponse(String requestId, int responseCode, String message) {
                if(requestId.equals(reqIdKlasses)){
                    klassesListener.onFailed(message, responseCode);
                }
                else if ( requestId.equals(reqIdAdvanture)){
                    topicItemsListener.onFailed(message,responseCode);
                } else if ( requestId.equals(reqIdVideoCompleted)){
                    videoCompletedListerner.onFailed(message,responseCode);
                }
                else if ( requestId.equals(reqIdSubjects)){
                    subjectsListener.onFailed(message,responseCode);
                }
                else if ( requestId.equals(reqIdTopics)){
                    topicsListener.onFailed(message,responseCode);
                }
                else if ( requestId.equals(reqIdSearch)){
                    searchListener.onFailed(message,responseCode);
                }
            }
        };
    }

    public String getKlasses(KlassesListener klassesListener){
        this.klassesListener = klassesListener;
        this.reqIdKlasses = ShareInfo.getInstance().getRequestId();
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(), "klasses", "get", reqIdKlasses, new HashMap());
        return reqIdKlasses;
    }

    public String getSubjects(int klassId,SubjectsListener subjectsListener){
        this.subjectsListener = subjectsListener;
        this.reqIdSubjects = ShareInfo.getInstance().getRequestId();
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(), "klasses/"+klassId+"/subjects", "get", reqIdSubjects, new HashMap());
        return reqIdSubjects;
    }
    public String getTopics(int klassId,int subId,TopicsListener topicsListener){
        this.topicsListener = topicsListener;
        this.reqIdTopics = ShareInfo.getInstance().getRequestId();
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(), "klasses/"+klassId+"/subjects/"+subId+"/topics", "get", reqIdTopics, new HashMap());
        return reqIdTopics;
    }

    public String getAdventure(int id,TopicItemsListener topicItemsListener){
        this.topicItemsListener = topicItemsListener;
        this.reqIdAdvanture = ShareInfo.getInstance().getRequestId();
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(), "topics/"+id, "get", reqIdAdvanture, new HashMap());
        return reqIdAdvanture;
    }

    public String getSearchResults (String search,SearchListener searchListener){
        this.searchListener = searchListener;
        this.reqIdSearch = ShareInfo.getInstance().getRequestId();
        HashMap hashMap = new HashMap();
        hashMap.put("", search);
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(), "", "post", reqIdSearch, hashMap);
        return reqIdSearch;
    }

    public String videoCompleted(String adventureId, VideoCompletedListerner videoCompletedListerner){
        this.videoCompletedListerner = videoCompletedListerner;
        this.reqIdVideoCompleted = ShareInfo.getInstance().getRequestId();
        HashMap hashMap = new HashMap();
        hashMap.put("adventure_id", adventureId);
        hashMap.put("status", 1); // 1 for video completed
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(), "users/user_progress/", "post", reqIdVideoCompleted, hashMap);
        return reqIdVideoCompleted;
    }
}
