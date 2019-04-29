package id.co.skoline.viewControllers.managers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import id.co.skoline.model.configuration.ApiHandler;
import id.co.skoline.model.response.KlassesResponse;
import id.co.skoline.model.response.SubjectResponse;
import id.co.skoline.model.response.TopicItemsResponse;
import id.co.skoline.model.response.TopicResponse;

import id.co.skoline.model.utils.ShareInfo;
import id.co.skoline.viewControllers.interfaces.TopicItemsListener;
import id.co.skoline.viewControllers.interfaces.KlassesListener;
import id.co.skoline.viewControllers.interfaces.SubjectsListener;
import id.co.skoline.viewControllers.interfaces.TopicsListener;
import okhttp3.ResponseBody;

import static id.co.skoline.model.configuration.ResponseCode.INVALID_JSON_RESPONSE;

public class ContentManager {
    private Context context;
    private ApiHandler apiHandler;
    KlassesListener klassesListener;
    SubjectsListener subjectsListener;
    TopicsListener topicsListener;
    TopicItemsListener topicItemsListener;
    private String reqIdKlasses;
    private String reqIdSubjects;
    private String reqIdTopics;
    private String reqIdAdvanture;
    private String reqIdSignIn;
    private String reqIdSignUp;

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
            }
            @Override
            public void successResponse(String requestId, ResponseBody responseBody, String baseUrl, String path, String requestType) {
                if(requestId.equals(reqIdKlasses)){
                    try {
                        Type listType = new TypeToken<List<KlassesResponse>>() {}.getType();
                        JSONArray arrayResponse = new JSONArray(responseBody.string());
                        klassesListener.onSuccess(new Gson().fromJson(arrayResponse.toString(), listType));
                    } catch (Exception e) {
                        e.printStackTrace();
                        klassesListener.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                }
               else if(requestId.equals(reqIdSubjects)){
                    try {
                        Type listType = new TypeToken<List<SubjectResponse>>() {}.getType();
                        JSONArray arrayResponse = new JSONArray(responseBody.string());
                        subjectsListener.onSuccess(new Gson().fromJson(arrayResponse.toString(), listType));
                    } catch (Exception e) {
                        e.printStackTrace();
                        subjectsListener.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                }
                if(requestId.equals(reqIdTopics)){
                    try {
                        Type listType = new TypeToken<List<TopicResponse>>() {}.getType();
                        JSONArray arrayResponse = new JSONArray(responseBody.string());
                        topicsListener.onSuccess(new Gson().fromJson(arrayResponse.toString(), listType));
                    } catch (Exception e) {
                        e.printStackTrace();
                        topicsListener.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                }

                if ( requestId.equals(reqIdAdvanture)){
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                       topicItemsListener.onSuccess(new Gson().fromJson(jsonObject.toString(), TopicItemsResponse.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                       topicItemsListener.onFailed("Invalid JSON Response", INVALID_JSON_RESPONSE);
                    }
                }
            }
            @Override
            public void failResponse(String requestId, int responseCode, String message) {
                if(requestId.equals(reqIdKlasses)){
                    klassesListener.onFailed(message, responseCode);
                }
                if ( requestId.equals(reqIdAdvanture)){
                    topicItemsListener.onFailed(message,responseCode);
                }
            }
        };
    }

    public String getKlasses(KlassesListener klassesListener){
        this.klassesListener = klassesListener;
        this.reqIdKlasses = ShareInfo.getInstance().getRequestId();
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(), "/api/v1/klasses", "get", reqIdKlasses, new HashMap());
        return reqIdKlasses;
    }

    public String getSubjects(int klassId,SubjectsListener subjectsListener){
        this.subjectsListener = subjectsListener;
        this.reqIdSubjects = ShareInfo.getInstance().getRequestId();
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(), "/api/v1/klasses/"+klassId+"/subjects", "get", reqIdSubjects, new HashMap());
        return reqIdSubjects;
    }
    public String getTopics(int klassId,int subId,TopicsListener topicsListener){
        this.topicsListener = topicsListener;
        this.reqIdTopics = ShareInfo.getInstance().getRequestId();
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(), "/api/v1/klasses/"+klassId+"/subjects/"+subId+"/topics", "get", reqIdTopics, new HashMap());
        return reqIdTopics;
    }

    public String getAdvanture(int id,TopicItemsListener topicItemsListener){
        this.topicItemsListener = topicItemsListener;
        this.reqIdAdvanture = ShareInfo.getInstance().getRequestId();
        apiHandler.httpRequest(ShareInfo.getInstance().getBaseUrl(), "/api/v1/topics/"+id, "get", reqIdAdvanture, new HashMap());
        return reqIdAdvanture;
    }
}
